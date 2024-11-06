package com.loginms.services.impl;


import com.loginms.dto.*;
import com.loginms.entities.User;
import com.loginms.entities.UserRole;
import com.loginms.repository.UserRepository;
import com.loginms.services.AuthenticationService;
import com.loginms.services.JWTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public String signup(SignUpRequest signUpRequest) {
        User user = new User();
        String Role="USER";
        user.setGender(signUpRequest.getGender());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setUserRole(UserRole.USER);//UserRole.USER

        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        User user1=userRepository.save(user);


            return "User Registered successfully";

    }

    public JwtAuthenticationResponse sigin(SigninRequest signinRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));

        var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid Email or Password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setAccessToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        jwtAuthenticationResponse.setRole(user.getUserRole().name());
        jwtAuthenticationResponse.setName(user.getName());
        jwtAuthenticationResponse.setId(user.getId());

        log.info("User login successful");
        return jwtAuthenticationResponse;



    }

    public AccessTokenRequest refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail=jwtService.extractUserName(refreshTokenRequest.getToken());
        User user= userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(),user)){
            var jwt = jwtService.generateToken(user);
            AccessTokenRequest accessTokenRequest = new AccessTokenRequest();

            accessTokenRequest.setAccessToken(jwt);
            accessTokenRequest.setRefreshToken(refreshTokenRequest.getToken());


            return accessTokenRequest;
        }
        return null;
    }

    @Override
    public UserDTO viewUserById(Long id) {
//        User user = userRepository.findById(id).get();
        Optional<User> user1 = userRepository.findById(id);
        UserDTO userDTO;
        if (user1.isPresent()){
            User user=user1.get();
            userDTO = UserDTO.builder()
                    .email(user.getEmail())
                    .gender(user.getGender())
                    .id(user.getId())
                    .name(user.getName())
                    .phoneNumber(user.getPhoneNumber())
                    .build();
        }else{
            userDTO=null;
        }
        return userDTO;
    }

    @Override
    public UserDTO viewUserByEmail(String email) {
//        User user = userRepository.findByEmail(email).orElseThrow(()-> new DataNotFoundException(String.format("Data for this email = %s doesn't exist...",email)));
        Optional<User> user1 = userRepository.findByEmail(email);
        UserDTO userDTO;
        if (user1.isPresent()){
            User user=user1.get();
            userDTO = UserDTO.builder()
                    .email(user.getEmail())
                    .gender(user.getGender())
                    .id(user.getId())
                    .name(user.getName())
                    .phoneNumber(user.getPhoneNumber())
                    .build();
        }else{
            userDTO=null;
        }
        return userDTO;
    }
}
