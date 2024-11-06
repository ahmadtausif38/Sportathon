package com.hcl.elch.sportathon.admin.repositories;

import com.hcl.elch.sportathon.admin.entities.MatchDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MatchDetailRepository extends JpaRepository<MatchDetails, Long> {

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM MatchDetails m " +
            "WHERE ((m.teamId1 = :teamId1 AND m.teamId2 = :teamId2) OR (m.teamId1 = :teamId2 AND m.teamId2 = :teamId2)) " +
            "AND m.gameName = :gameName " +
            "AND FUNCTION('DATE', m.matchDate) = :matchDate")
    boolean existsBySameGameAndTeamsAndMatchDate(
            @Param("gameName") String gameName,
            @Param("teamId1") Long teamId1,
            @Param("teamId2") Long teamId2,
            @Param("matchDate") LocalDate matchDate);

    @Query("SELECT CASE WHEN COUNT(md) > 0 THEN true ELSE false END FROM MatchDetails md " +
            "WHERE ((md.teamId1 = :player1Id AND md.teamId2 != :player2Id) OR (md.teamId1 != :player2Id AND md.teamId2 = :player1Id) " +
            "OR (md.teamId1 = :player2Id AND md.teamId2 != :player1Id) OR (md.teamId1 != :player1Id AND md.teamId2 = :player2Id)) " +
            "AND md.matchDate = :matchDate " +
            "AND ((:startTime < md.endTime AND :endTime > md.startTime))")
    boolean existsByPlayersAndOverlappingTimeSlot(
            @Param("player1Id") Long player1Id,
            @Param("player2Id") Long player2Id,
            @Param("matchDate") LocalDate matchDate,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    @Query("SELECT m FROM MatchDetails m " +
            "WHERE (m.teamId1 = :playerId OR m.teamId2 = :playerId) " +
            "AND m.matchDate >= :currentDate " +
            "ORDER BY m.matchDate ASC")
    List<MatchDetails> findUpcomingMatchesForPlayer(
            @Param("playerId") Long playerId,
            @Param("currentDate") LocalDate currentDate
    );

    List<MatchDetails> findBymatchDate(LocalDate date);

    @Query("SELECT m FROM MatchDetails m WHERE (m.teamId1 = :playerId OR m.teamId2 = :playerId) AND m.gameName = :gameName AND m.matchDate >= :today")
    List<MatchDetails> findUpcomingMatchesByPlayerIdAndGameName(
            @Param("playerId") Long playerId,
            @Param("gameName") String gameName,
            @Param("today") LocalDate today
    );


}
