package com.hcl.elch.sportathon.admin.services;

import com.hcl.elch.sportathon.admin.customException.WrongValueException;
import com.hcl.elch.sportathon.admin.entities.MatchDetails;
import com.hcl.elch.sportathon.admin.entities.MatchResult;
import com.hcl.elch.sportathon.admin.pojo.MatchHistoryPojo;
import com.hcl.elch.sportathon.admin.repositories.MatchDetailRepository;
import com.hcl.elch.sportathon.admin.repositories.MatchResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchHistoryService {

    @Autowired
    private MatchDetailRepository matchDetailRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    public List<MatchHistoryPojo> getMatchHistoryWithResults(Long teamId) {
        List<MatchResult> allMatchResults = matchResultRepository.findAll();
        return allMatchResults.stream()
                .filter(matchResult ->
                        matchResult.getMatchId().getTeamId1().equals(teamId) ||
                                matchResult.getMatchId().getTeamId2().equals(teamId))
                .map(matchResult -> {
                    MatchDetails matchDetails = matchResult.getMatchId();
                    Long winningTeamId = matchResult.getWinningTeamId();
                    boolean isTeam1 = matchDetails.getTeamId1().equals(teamId);
                    MatchHistoryPojo matchHistory = new MatchHistoryPojo();
                    matchHistory.setMatchId(matchDetails.getMatchID());
                    matchHistory.setGameName(matchDetails.getGameName());
                    matchHistory.setTeamId1(teamId);
                    matchHistory.setTeamId2(isTeam1 ? matchDetails.getTeamId2(): matchDetails.getTeamId1());
                    matchHistory.setMatchDate(matchDetails.getMatchDate());
                    matchHistory.setStartTime(matchDetails.getStartTime());
                    matchHistory.setEndTime(matchDetails.getEndTime());
                    matchHistory.setVenue(matchDetails.getVenue().getVenueName());
                    matchHistory.setWinningTeamId(winningTeamId);
                    matchHistory.setWasWinner(matchResult.getWinningTeamId().equals(teamId));
                    return matchHistory;
                }).collect(Collectors.toList());
    }

}
