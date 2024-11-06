package com.loginms.dto;

import lombok.Data;

@Data
public class AccessTokenRequest {
    private String accessToken;
    private String refreshToken;
}
