package com.loginms.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {
    @GetMapping
    public  ResponseEntity<Boolean> sayHello(@RequestHeader("Authorization") String token){
           return ResponseEntity.ok(true);

    }
}
