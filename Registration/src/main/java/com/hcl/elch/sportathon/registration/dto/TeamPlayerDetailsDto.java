package com.hcl.elch.sportathon.registration.dto;

import com.hcl.elch.sportathon.registration.entities.TeamDetails;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamPlayerDetailsDto {

//    private int playerId;
    @NotBlank(message = "Player Email is required !!")
    @Email(regexp = "^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$", message = "Please provide a valid email address !!")
    private String email;

    @NotBlank(message = "PlayerName cannot be blank !!")
    private String name;

    @NotBlank(message = "Player phone cannot be blank !!")
    @Size(min = 10, message = "Phone number must be of 10 or more digits.")
    private String phone;

    @NotBlank(message = "Player gender cannot be blank !!")
    private String gender;

//    private TeamDetailsDto team;

    private Long gameId;


}
