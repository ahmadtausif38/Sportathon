package com.hcl.elch.sportathon.registration.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TeamDetails")
public class TeamDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "candidate_id")
    private Long candidateId;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    private GameDetails game;

    @OneToMany(targetEntity = TeamPlayerDetails.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id",referencedColumnName = "team_id")
    private List<TeamPlayerDetails> players;

}
