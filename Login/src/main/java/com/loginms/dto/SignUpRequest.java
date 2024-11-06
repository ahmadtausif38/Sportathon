package com.loginms.dto;

import lombok.Data;

@Data
public class SignUpRequest {

    private String name;
    private String email;
    private String gender;
    private String password;
    private String phoneNumber;
}
