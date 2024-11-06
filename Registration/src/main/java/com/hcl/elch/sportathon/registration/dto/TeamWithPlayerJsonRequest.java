package com.hcl.elch.sportathon.registration.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamWithPlayerJsonRequest {
    @Valid
    private TeamWithPlayerRequest team;
    private Integer id;
}
