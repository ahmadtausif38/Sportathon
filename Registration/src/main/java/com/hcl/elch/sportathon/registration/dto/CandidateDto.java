package com.hcl.elch.sportathon.registration.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateDto {

    private Long id;
    private String name;
    private String email;
    private String gender;
    private String phoneNumber;

}
