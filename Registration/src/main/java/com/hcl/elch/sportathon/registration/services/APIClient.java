package com.hcl.elch.sportathon.registration.services;

import com.hcl.elch.sportathon.registration.dto.CandidateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "USER-SERVICE", url = "http://localhost:8081")
public interface APIClient {
    @GetMapping(value = "/api/v1/auth/userDetails/{id}")
    CandidateDto getDepartmentById(@PathVariable("id") Long candidateId);

    @GetMapping(value = "/api/v1/auth/userDetailsByEmail/{email}")
    CandidateDto getDepartmentByEmail(@PathVariable("email") String email);
}

