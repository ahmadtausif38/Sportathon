package com.hcl.elch.sportathon.admin.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MatchHistoryPojo {
    private Long matchId;
    private String gameName;
    private Long teamId1;
    private Long teamId2;
    private LocalDate matchDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String venue;
    private Long winningTeamId;
    private Boolean wasWinner;

}
