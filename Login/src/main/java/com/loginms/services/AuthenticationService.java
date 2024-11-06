package com.loginms.services;


import com.loginms.dto.*;
import com.loginms.entities.User;

public interface AuthenticationService {

    String signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse sigin(SigninRequest signinRequest);
    AccessTokenRequest refreshToken(RefreshTokenRequest refreshTokenRequest);
    UserDTO viewUserById (Long id);
    UserDTO viewUserByEmail(String email);

}