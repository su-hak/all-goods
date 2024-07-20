package com.example.signup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "set")
public class ResponseDTO<D> {
    private boolean result;
    private String message;
    private D data;

    // 성공 Return
    public static <D> ResponseDTO<D> setSuccess(String message) {
        return ResponseDTO.set(true, message, null);
    }

    // 실패 Return
    public static <D> ResponseDTO<D> setFailed(String message) {
        return ResponseDTO.set(false, message, null);
    }

    // 성공 Return + Data
    public static <D> ResponseDTO<D> setSuccessData(String message, D data) {
        return ResponseDTO.set(true, message, data);
    }

    // 실패 Return + Data
    public static <D> ResponseDTO<D> setFailedData(String message, D data) {
        return ResponseDTO.set(false, message, data);
    }

    // 요청 성공 여부 확인
    public boolean getResult() {
        return result;
    }
}
