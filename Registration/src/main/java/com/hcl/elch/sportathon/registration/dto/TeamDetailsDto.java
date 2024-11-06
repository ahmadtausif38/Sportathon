package com.hcl.elch.sportathon.registration.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDetailsDto {

//    private Integer registrationId;
    @NotBlank(message = "Team name cannot be Null !!")
    private String teamName;

    private LocalDate creationDate;
    @NotNull(message = "CandidateId cannot be null !!")
    private Long candidateId;

    @NotNull(message = "Team gameId cannot be null !!")
    private Long gameId;
//    private Integer teamId;
    private List<TeamPlayerDetailsDto> players;

}
