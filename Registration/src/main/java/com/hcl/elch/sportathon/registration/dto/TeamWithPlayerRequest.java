package com.hcl.elch.sportathon.registration.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamWithPlayerRequest {
//    private Integer registrationId;
//    private Integer teamId;
//    private LocalDate creationDate;
    @NotBlank(message = "Team name cannot be blank !!")
    private String teamName;

    @NotNull(message = "CandidateId cannot be null !!")
    private Long candidateId;

    @NotNull(message = "GameId cannot be null !!")
    private Long gameId;

    private List<@Valid TeamPlayerDetailsDto> players;
}
