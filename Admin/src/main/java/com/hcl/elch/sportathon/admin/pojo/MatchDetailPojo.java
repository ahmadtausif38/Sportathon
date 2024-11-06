package com.hcl.elch.sportathon.admin.pojo;

import com.hcl.elch.sportathon.admin.entities.Venue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class MatchDetailPojo {
        @NotNull(message = "Game Name cannot be Null")
        @NotBlank(message = "Game Name cannot be blank")
        private String gameName;

        @NotNull(message = "Team 1 ID cannot be Null")
        private Long teamId1;

        @NotNull(message = "Team 2 ID cannot be Null")
        private Long teamId2;

        @NotNull(message = "Match date cannot be Null")
        private LocalDate matchDate;

        @NotNull(message = "Start time cannot be Null")
        private LocalTime startTime;

        @NotNull(message = "End time cannot be null")
        private LocalTime endTime;

        @NotNull(message = "Venue Id cannot be null")
        private Long venueId;
}
