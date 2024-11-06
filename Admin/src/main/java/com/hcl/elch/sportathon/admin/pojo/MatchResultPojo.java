package com.hcl.elch.sportathon.admin.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchResultPojo {

    @NotNull(message = "Match ID cannot be null")
    private Long matchId;

    @NotNull(message = "Winning Team Id cannot be null")
    private Long winningTeamId;

    @NotNull(message = "Remarks cannot be null")
    @NotBlank(message = "Remarks cannot be blank")
    private String remarks;
}
