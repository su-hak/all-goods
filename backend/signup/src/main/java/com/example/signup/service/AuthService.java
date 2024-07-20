package com.example.signup.service;

import com.example.signup.dto.LoginDTO;
import com.example.signup.dto.LoginResponseDTO;
import com.example.signup.dto.ResponseDTO;
import com.example.signup.dto.SignUpDTO;
import com.example.signup.entity.UserEntity;
import com.example.signup.repository.UserRepository;
import com.example.signup.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenProvider tokenProvider;

    public ResponseDTO<?> signUp(SignUpDTO dto) {
        String id = dto.getId();
        String password = dto.getPassword();
        String confirmPassword = dto.getConfirmPassword();


        // id 중복 확인
        try {
            // 존재할 경우 true / 존재 안 할 경우 false
            if (userRepository.existsById(id)) {
                return ResponseDTO.setFailed("중복된 ID 입니다.");
            }


            // password 일치 확인
            if (!password.equals(confirmPassword)) {
                return ResponseDTO.setFailed("비밀번호가 일치하지 않습니다.");
            }

            // 비밀번호 암호화
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);

            // UserEntity 생성
            UserEntity userEntity = new UserEntity(dto);
            userEntity.setPassword(hashedPassword);

            // 데이터베이스에 유저 저장
            userRepository.save(userEntity);

            return ResponseDTO.setSuccess("회원 생성에 성공했습니다.");
        } catch (Exception e) {
            return ResponseDTO.setFailed("회원 생성 중 오류가 발생했습니다.");
        }
    }

    public ResponseDTO<LoginResponseDTO> counselorLogin(LoginDTO dto) {
        return login(dto, "COUNSELOR");
    }

    public ResponseDTO<LoginResponseDTO> managerLogin(LoginDTO dto) {
        return login(dto, "MANAGER");
    }

    public ResponseDTO<LoginResponseDTO> login(LoginDTO dto, String userType) {
        String id = dto.getId();
        String password = dto.getPassword();
        UserEntity userEntity;

        try {
            // 이메일로 사용자 정보 가져오기
            userEntity = userRepository.findById(id).orElse(null);
            if(userEntity == null) {
                return ResponseDTO.setFailed("입력하신 이메일로 등록된 계정이 존재하지 않습니다.");
            }

            // 사용자가 입력한 비밀번호를 BCryptPasswordEncoder를 사용하여 암호화
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = userEntity.getPassword();

            // 저장된 암호화된 비밀번호와 입력된 암호화된 비밀번호 비교
            if(!passwordEncoder.matches(password, encodedPassword)) {
                return ResponseDTO.setFailed("비밀번호가 일치하지 않습니다.");
            }

            // 사용자 유형 확인
            if (!userType.equals(userEntity.getUserType())) {
                return ResponseDTO.setFailed(userType + " 유형의 계정이 아닙니다.");
            }
        } catch (Exception e) {
            return ResponseDTO.setFailed("데이터베이스 연결에 실패하였습니다.");
        }

        userEntity.setPassword("");
        String name = userEntity.getName();

        int exprTime = 3600; // 1hour
        String token = tokenProvider.createJwt(id, exprTime);

        if (token == null) {
            return ResponseDTO.setFailed("토큰 생성에 실패했습니다.");
        }

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token, exprTime, userEntity);

        return ResponseDTO.setSuccessData("로그인에 성공했습니다", loginResponseDTO);
    }
}
