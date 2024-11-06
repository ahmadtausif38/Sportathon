package com.hcl.elch.sportathon.registration.repository;

import com.hcl.elch.sportathon.registration.entities.GameDetails;
import com.hcl.elch.sportathon.registration.entities.TeamDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TeamRegistrationRepository extends JpaRepository<TeamDetails, Long> {

    @Query(value = "SELECT * FROM TeamDetails td WHERE td.TeamId NOT IN (SELECT tpd.TeamId FROM TeamPlayerDetails tpd)", nativeQuery = true)
    List<TeamDetails> findTeamsWithoutPlayers();

    boolean existsByCandidateIdAndGame(Long candidateId, GameDetails game);

    Optional<TeamDetails> findByTeamId(Long id);

    //list of teams based on game id

    @Query("SELECT td FROM TeamDetails td WHERE td.game.gameId = :gameId")
    List<TeamDetails> findTeamByGameId(@Param("gameId") Long gameId);

    @Query(value = "SELECT team_name FROM team_details", nativeQuery = true)
    List<String> findAllTeamName();

    @Query(value = "SELECT team_name FROM team_details WHERE team_id = ?1", nativeQuery = true)
    Optional<String> findTeamNameByTeamId(Long teamId);
}
