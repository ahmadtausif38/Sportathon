package com.hcl.elch.sportathon.admin.services;

import com.hcl.elch.sportathon.admin.customException.ValueNotPresentException;
import com.hcl.elch.sportathon.admin.customException.WrongValueException;
import com.hcl.elch.sportathon.admin.entities.MatchDetails;
import com.hcl.elch.sportathon.admin.entities.Venue;
import com.hcl.elch.sportathon.admin.pojo.MatchDetailPojo;
import com.hcl.elch.sportathon.admin.pojo.VenuePojo;
import com.hcl.elch.sportathon.admin.repositories.MatchDetailRepository;
import com.hcl.elch.sportathon.admin.repositories.VenueRepository;
import com.hcl.elch.sportathon.admin.response.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.event.ObjectChangeListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class MatchDetailService {

    @Autowired
    private MatchDetailRepository matchDetailRepository;

    @Autowired
    private VenueRepository venueRepository;


    @Transactional
    public List<MatchDetails> getAllMatchDetails(){
        return this.matchDetailRepository.findAll();
    }

    @Transactional
    public Response scheduleMatch(MatchDetailPojo matchDetailPojo) throws Exception{

        this.validateTeamsAndDate(matchDetailPojo.getGameName(), matchDetailPojo.getTeamId1(),
                matchDetailPojo.getTeamId2(), matchDetailPojo.getMatchDate());

        this.validatePlayerAvailability(matchDetailPojo.getTeamId1(), matchDetailPojo.getTeamId2()
        , matchDetailPojo.getMatchDate(), matchDetailPojo.getStartTime(), matchDetailPojo.getEndTime());

        MatchDetails matchDetail = new MatchDetails();
        matchDetail.setGameName(matchDetailPojo.getGameName());
        matchDetail.setTeamId1(matchDetailPojo.getTeamId1());
        matchDetail.setTeamId2(matchDetailPojo.getTeamId2());
        matchDetail.setMatchDate(matchDetailPojo.getMatchDate());
        matchDetail.setStartTime(matchDetailPojo.getStartTime());
        matchDetail.setEndTime(matchDetailPojo.getEndTime());
        Venue venue = venueRepository.findById(matchDetailPojo.getVenueId()).
                orElseThrow(()-> new ValueNotPresentException("Venue Id doesn't present"));
        matchDetail.setVenue(venue);
        this.matchDetailRepository.save(matchDetail);
        return new Response("Match scheduled Successfully");
    }


    private void  validateTeamsAndDate(String gameName, Long teamId1, Long teamId2, LocalDate matchDate) throws Exception{
        if(teamId1.equals(teamId2)){
            throw new WrongValueException("Same teams cannot play against each other.");
        }
        boolean matchExists = this.matchDetailRepository.existsBySameGameAndTeamsAndMatchDate(gameName, teamId1, teamId2, matchDate)
                || this.matchDetailRepository.existsBySameGameAndTeamsAndMatchDate(gameName,teamId2, teamId1, matchDate);
        if(matchExists){
            throw new WrongValueException("A match with the same teams and date already exists.");
        }
    }

    private void validatePlayerAvailability(Long teamId1,Long teamId2, LocalDate matchDate, LocalTime startTime, LocalTime endTime) throws Exception{
        boolean playerBusy = this.matchDetailRepository.existsByPlayersAndOverlappingTimeSlot(teamId1, teamId1,matchDate, startTime, endTime);
        if(playerBusy){
            throw new WrongValueException("Player is already scheduled for another match during the specified time");
        }
    }

    @Transactional
    public Response deleteMatchById(Long matchID){
        this.matchDetailRepository.deleteById(matchID);
        return new Response("Match deleted successfully");
    }

    @Transactional
    public List<MatchDetails> getUpcomingMatchesForPlayer(Long playerId){
        LocalDate currentDate = LocalDate.now();
        return this.matchDetailRepository.findUpcomingMatchesForPlayer(playerId, currentDate);
    }

    @Transactional
    public List<MatchDetails> getUpcomingMatchesByPlayerIdAndGameName(Long playerId, String gameName){
        LocalDate today = LocalDate.now();
        return this.matchDetailRepository.findUpcomingMatchesByPlayerIdAndGameName(playerId, gameName, today);
    }

    @Transactional
    public List<MatchDetails> getAllMatchesByDate(LocalDate date){
        return this.matchDetailRepository.findBymatchDate(date);
    }

}
