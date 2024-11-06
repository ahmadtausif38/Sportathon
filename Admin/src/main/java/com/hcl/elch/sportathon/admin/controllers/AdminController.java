package com.hcl.elch.sportathon.admin.controllers;


import com.hcl.elch.sportathon.admin.entities.MatchDetails;
import com.hcl.elch.sportathon.admin.entities.MatchResult;
import com.hcl.elch.sportathon.admin.pojo.MatchDetailPojo;
import com.hcl.elch.sportathon.admin.pojo.MatchResultPojo;
import com.hcl.elch.sportathon.admin.pojo.VenuePojo;
import com.hcl.elch.sportathon.admin.services.MatchDetailService;
import com.hcl.elch.sportathon.admin.services.MatchHistoryService;
import com.hcl.elch.sportathon.admin.services.MatchResultService;
import com.hcl.elch.sportathon.admin.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/match")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private VenueService venueService;
    @Autowired
    private MatchDetailService matchDetailService;

    @Autowired
    private MatchResultService matchResultService;

    @Autowired
    private MatchHistoryService matchHistoryService;

//    ----------------------- Venue-------------------------------

    @GetMapping("/venue")
    public ResponseEntity<?> getVenueList(){
        return ResponseEntity.status(HttpStatus.OK).body(this.venueService.getAllVenues());
    }

    @GetMapping("venue/{id}")
    public ResponseEntity<?> getVenueById(@Validated @PathVariable Long id) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(this.venueService.getVenueById(id));
    }

    @PostMapping("/venue")
    public ResponseEntity<?> createVenue(@Validated @RequestBody VenuePojo venuePojo) throws Exception{
        return ResponseEntity.status(HttpStatus.OK). body(this.venueService.createVenue(venuePojo));
    }

    @DeleteMapping("venue/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.venueService.deleteVenue(id));
    }

    @PutMapping("venue/{id}")
    public ResponseEntity<?> updateVenueByID(@Validated @PathVariable Long id, @RequestBody VenuePojo venue) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(this.venueService.updateVenue(id, venue));
    }

//    --------------------------- Match Details ------------------------------

    @GetMapping("schedule")
    public ResponseEntity<?> getAllMatchDetails(){
        return ResponseEntity.status(HttpStatus.OK).body(this.matchDetailService.getAllMatchDetails());
    }

    @PostMapping("schedule")
    public ResponseEntity<?> scheduleMatch(@Validated @RequestBody MatchDetailPojo matchDetailPojo) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(this.matchDetailService.scheduleMatch(matchDetailPojo));
    }

    @DeleteMapping("schedule/{matchID}")
    public ResponseEntity<?> deleteMatchById(@Validated @PathVariable Long matchID){
        return ResponseEntity.status(HttpStatus.OK).body(this.matchDetailService.deleteMatchById(matchID));
    }

//    PUT Mapping

    @GetMapping("upcomingmatch/{playerId}")
    public ResponseEntity<?> getUpcomingMatchById(@PathVariable Long playerId){
        return ResponseEntity.status(HttpStatus.OK).body(this.matchDetailService.getUpcomingMatchesForPlayer(playerId));
    }

    @GetMapping("history/{playerId}")
    public ResponseEntity<?> getMatchHistoryByPlayerId(@PathVariable Long playerId){
        return ResponseEntity.status(HttpStatus.OK).body(this.matchHistoryService.getMatchHistoryWithResults(playerId));
    }


//    ----------------------------- Match Result -----------------------------------------

    @GetMapping("result")
    public ResponseEntity<?> getAllMatchResult(){
        return ResponseEntity.status(HttpStatus.OK).body(this.matchResultService.getAllMatchResult());
    }

    @GetMapping("result/{resultId}")
    public ResponseEntity<?> getMatchResultById(@PathVariable Long resultId){
        return ResponseEntity.status(HttpStatus.OK).body(this.matchResultService.getMatchResultById(resultId));
    }

    @PostMapping("result")
    public ResponseEntity<?> setMatchResult(@Validated @RequestBody MatchResultPojo matchResultPojo) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(this.matchResultService.createMatchResult(matchResultPojo));
    }

    @DeleteMapping("result/{resultId}")
    public ResponseEntity<?> deleteMatchResultById(@PathVariable Long resultId){
        return ResponseEntity.status(HttpStatus.OK).body(this.matchResultService.deleteMatchResultById(resultId));
    }

    @PutMapping("result/{resultId}")
    public ResponseEntity<?> updateMatchResultById(@RequestBody MatchResultPojo matchResultPojo, @PathVariable Long resultId) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(this.matchResultService.updateMatchResultById(matchResultPojo, resultId));
    }

    @GetMapping("date/{date}")
    public ResponseEntity<?> getMatchesByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date){
        List<MatchDetails> matches = this.matchDetailService.getAllMatchesByDate(date);
        if(matches.isEmpty()){
            return ResponseEntity.ok(Map.of("message", "No matches scheduled for this date"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(matches);
    }

    @GetMapping("upcomingmatch/{teamId}/{gameName}")
    public ResponseEntity<?> getUpcomingMatchesByTeamIDAndGame(@PathVariable Long teamId, @PathVariable String gameName){
        List<MatchDetails> matches = this.matchDetailService.getUpcomingMatchesByPlayerIdAndGameName(teamId, gameName);
        return ResponseEntity.status(HttpStatus.OK).body(matches);
    }

    @GetMapping("result/gamename/{gameName}")
    public ResponseEntity<?> getMatchResultByGameName(@PathVariable String gameName){
        List<MatchResult> results = this.matchResultService.getMatchResultsByGameName(gameName);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }
}
