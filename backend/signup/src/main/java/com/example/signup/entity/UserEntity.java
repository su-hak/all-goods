package com.example.signup.entity;

import com.example.signup.dto.SignUpDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    private String id;
    private String idNumber;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String nickName;
    private String address;
    private String detailedAddress;
    private String gender;
    private String userType;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private LocalDateTime lastLoginAt;

    public UserEntity(SignUpDTO dto) {
        this.id = dto.getId();
        this.idNumber = dto.getIdNumber();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.name = dto.getName();
        this.phoneNumber = dto.getPhoneNumber();
        this.nickName = dto.getNickName();
        this.address = dto.getAddress();
        this.detailedAddress = dto.getDetailedAddress();
        this.gender = dto.getGender();
        this.userType = dto.getUserType();
        this.token = "";
        this.createdAt = LocalDateTime.now();
        this.editedAt = LocalDateTime.now();
    }
}
