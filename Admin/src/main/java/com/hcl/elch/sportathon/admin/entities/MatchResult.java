package com.hcl.elch.sportathon.admin.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class MatchResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private MatchDetails matchId;

    @Column(nullable = false)
    private Long winningTeamId;

    @Column(nullable = false)
    private String remarks;

}
