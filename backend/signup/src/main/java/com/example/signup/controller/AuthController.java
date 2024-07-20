package com.example.signup.controller;

import com.example.signup.dto.LoginDTO;
import com.example.signup.dto.LoginResponseDTO;
import com.example.signup.dto.ResponseDTO;
import com.example.signup.dto.SignUpDTO;
import com.example.signup.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/signUp")
    public ResponseDTO<?> signUp(@RequestBody SignUpDTO requestBody) {
        ResponseDTO<?> result = authService.signUp(requestBody);
        return result;
    }

    @PostMapping("/counselorLogIn")
    public ResponseDTO<?> counselorLogIn(@RequestBody LoginDTO requestBody) {
        ResponseDTO<?> result = authService.counselorLogin(requestBody);
        return result;
    }

    @PostMapping("/managerLogin")
    public ResponseEntity<?> managerLogin(@RequestBody LoginDTO requestBody) {
        ResponseDTO<?> result = authService.managerLogin(requestBody);
        return setToken(result);
    }

    // Response 결과에 따라 Header에 Token 설정
    private ResponseEntity<?> setToken(ResponseDTO<?> result) {
        // 요청이 성공인 경우
        if (result.getResult()) {
            // result -> data -> token 추출
            LoginResponseDTO loginResponseDTO = (LoginResponseDTO) result.getData();

            // Header에 Auth에 Token 지정, Body에는 result 그대로 작성 (result 내의 token은 제거해도 될듯)
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + loginResponseDTO.getToken())
                    .body(result);
        } else {
            return ResponseEntity.ok().body(result);
        }
    }
}
