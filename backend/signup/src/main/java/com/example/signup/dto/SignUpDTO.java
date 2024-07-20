package com.example.signup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    private String id;
    private String idNumber;
    private String email;
    private String name;
    private String nickName;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private String address;
    private String detailedAddress;
    private String gender;
    private String userType;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private LocalDateTime lastLoginAt;
    private String token;
}
