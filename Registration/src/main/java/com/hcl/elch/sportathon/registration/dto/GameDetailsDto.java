package com.hcl.elch.sportathon.registration.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameDetailsDto {

    private Long gameId;
    @NotBlank(message = "Game-Name required !!")
    private String gameName;

    @NotBlank(message = "Game-type required !!")
    private String gameType;

    @NotNull(message = "Number of players cannot be null")
    @Positive(message = "Number of players must be a positive integer")
    private Long numberOfPlayers;
}
