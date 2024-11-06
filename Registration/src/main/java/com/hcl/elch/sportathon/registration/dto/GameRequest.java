package com.hcl.elch.sportathon.registration.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameRequest {
    @Valid
    private GameDetailsDto game;
    private Integer id;
}
