package com.loginms.controller;


import com.loginms.config.JwtAuthenticationFilter;
import com.loginms.dto.*;
import com.loginms.repository.UserRepository;
import com.loginms.services.UserService;
import com.loginms.services.impl.AuthenticationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.loginms.entities.User;
import com.loginms.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.loginms.services.impl.JWTServiceImpl;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
       try {
           return ResponseEntity.ok(authenticationService.signup(signUpRequest));
       }catch(Exception e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this email is already registered");
       }

    }
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest){
       try {
           return ResponseEntity.ok(authenticationService.sigin(signinRequest));
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email or password");
       }
    }

//    @PostMapping("/validateToken")
//    @ResponseBody
//    public boolean validate_Token(@RequestHeader("Authorization") String tokenHeader) throws Exception {
//        boolean tokenValidity=false;
//        log.info("hi1");
//        try {
//            tokenValidity = jwtAuthenticationFilter.validateToken(tokenHeader);
//            log.info("hi4");
//        }
//        catch (Exception exception){
//            log.info("hi2");
//            System.out.println("Invalid Token ");
//
//        }
//        return tokenValidity;
//    }

    @GetMapping("/userDetails/{id}")
    public ResponseEntity<?> fetchUserDetailsById(@PathVariable Long id){
        return new ResponseEntity<>(authenticationService.viewUserById(id),HttpStatus.OK);

    }

    @GetMapping("/userDetailsByEmail/{email}")
    public ResponseEntity<?> fetchByEmail(@PathVariable String email){
        return new ResponseEntity<>(authenticationService.viewUserByEmail(email),HttpStatus.OK);

    }


        @PostMapping("/refresh")
    public ResponseEntity<AccessTokenRequest> token(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}

