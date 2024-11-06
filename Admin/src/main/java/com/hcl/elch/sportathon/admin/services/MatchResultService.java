package com.hcl.elch.sportathon.admin.services;

import com.hcl.elch.sportathon.admin.customException.ValueNotPresentException;
import com.hcl.elch.sportathon.admin.customException.WrongValueException;
import com.hcl.elch.sportathon.admin.entities.MatchDetails;
import com.hcl.elch.sportathon.admin.entities.MatchResult;
import com.hcl.elch.sportathon.admin.pojo.MatchResultPojo;
import com.hcl.elch.sportathon.admin.repositories.MatchDetailRepository;
import com.hcl.elch.sportathon.admin.repositories.MatchResultRepository;
import com.hcl.elch.sportathon.admin.response.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchResultService {

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Autowired
    private MatchDetailRepository matchDetailRepository;

    @Transactional
    public List<MatchResult> getAllMatchResult(){
        return this.matchResultRepository.findAll();
    }

    @Transactional
    public Response createMatchResult(MatchResultPojo matchResultPojo) throws Exception {
         MatchDetails match = matchDetailRepository.findById(matchResultPojo.getMatchId()).orElseThrow(()-> new WrongValueException("Id doesn't match"));
         Optional<MatchResult> existingResult = matchResultRepository.findBymatchId(match);
         if (existingResult.isPresent()) {
             throw new WrongValueException("A MatchResult already exists for the given match matchId");
         }
         MatchResult matchResult = new MatchResult();
         matchResult.setMatchId(match);
         matchResult.setWinningTeamId(matchResultPojo.getWinningTeamId());
         matchResult.setRemarks(matchResultPojo.getRemarks());
         this.matchResultRepository.save(matchResult);
         return new Response("Inserted");
    }

    @Transactional
    public Optional<MatchResult> getMatchResultById(Long resultId){
        return this.matchResultRepository.findById(resultId);
    }

    @Transactional
    public Response deleteMatchResultById(Long resultId){
        this.matchResultRepository.deleteById(resultId);
        return new Response("Match Result Deleted Successfully");
    }

    @Transactional
    public Response updateMatchResultById(MatchResultPojo matchResultPojo, Long resultId) throws Exception{
        MatchResult existingResult = this.matchResultRepository.findById(resultId).orElseThrow(()->new ValueNotPresentException("Match result Not Found By this Id"));
        existingResult.setWinningTeamId(matchResultPojo.getWinningTeamId());
        existingResult.setRemarks(matchResultPojo.getRemarks());
        this.matchResultRepository.save(existingResult);
        return new Response("Result updated Successfully");
    }

    public List<MatchResult> getMatchResultsByGameName(String gameName){
        List<MatchResult> allMatchResults = matchResultRepository.findAll();
        return allMatchResults.stream().filter(matchResult -> matchResult.getMatchId().getGameName().equals(gameName)).collect(Collectors.toList());
    }

}
