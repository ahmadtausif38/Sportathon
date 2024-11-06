package com.hcl.elch.sportathon.registration.controller;


import com.hcl.elch.sportathon.registration.dto.*;
import com.hcl.elch.sportathon.registration.entities.GameDetails;
import com.hcl.elch.sportathon.registration.exceptions.DataAlreadyExist;
import com.hcl.elch.sportathon.registration.exceptions.DataNotFoundException;

import com.hcl.elch.sportathon.registration.services.GameServiceImpl;
import com.hcl.elch.sportathon.registration.services.TeamRegistrationServiceImpl;
import com.hcl.elch.sportathon.registration.services.PlayerServiceImpl;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@CrossOrigin("*")
public class RegistrationController {
    private static final Logger log= LogManager.getLogger(RegistrationController.class);
    @Autowired
    private TeamRegistrationServiceImpl teamService;

    @Autowired
    private GameServiceImpl gameService;

    @Autowired
    private PlayerServiceImpl playerService;

    @GetMapping("/check")
    public String show(){
        return "Working...";
    }



    //                       TEAM ENDPOINTS

    @PostMapping("/team-register")
    public ResponseEntity<String> registerTeam(@Valid @RequestBody TeamWithPlayerJsonRequest request) throws Exception {

        TeamWithPlayerRequest teamRequest=request.getTeam();
        teamService.saveTeamWithPlayers(teamRequest);
        return new ResponseEntity<>("Team Registered successfully!!",HttpStatus.CREATED);

    }
    @GetMapping("/team")
    public ResponseEntity<?> viewAllTeamDetails(){
        return new ResponseEntity<>(teamService.viewTeamWithPlayer(),HttpStatus.OK);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<?> viewTeamByTeamId(@PathVariable Long teamId) throws DataNotFoundException {
        return new ResponseEntity<>(teamService.viewTeamByTeamId(teamId),HttpStatus.OK);
    }

    //fetch list of teams based on game id

    @GetMapping("/team/game/{gameId}")
    public ResponseEntity<?> viewTeamsByGameId(@PathVariable Long gameId) throws DataNotFoundException {
        return new ResponseEntity<>(teamService.viewTeamByGameId(gameId),HttpStatus.OK);
    }

    //    Fetch All teamName

    @GetMapping("/team/team-name")
    public ResponseEntity<?> viewAllTeamNames(){
        return new ResponseEntity<>(teamService.viewAllTeamName(),HttpStatus.OK);
    }

    //  Fetch team-name based on teamId
    @GetMapping("/team/team-name/{teamId}")
    public ResponseEntity<?> viewTeamNamebyTeamId(@PathVariable Long teamId) throws DataNotFoundException {
        return new ResponseEntity<>(teamService.viewTeamNameByTeamId(teamId),HttpStatus.OK);
    }


//                           GAME ENDPOINTS

    @PostMapping("/game-register")
    //pass game DTO class here
    public ResponseEntity<String> registerGame(@Valid @RequestBody GameRequest request) throws Exception {
        gameService.addGames(request);
        return new ResponseEntity<>("Game saved successfully!!",HttpStatus.CREATED);
    }

    @GetMapping("/game")
    public ResponseEntity<?> showAllGame()  {
        return new ResponseEntity<>(gameService.showAllGames(), HttpStatus.OK);
    }

    @GetMapping("/game/game-id/{gameId}")
    public ResponseEntity<GameDetails> showGameByGameId(@PathVariable Long gameId ) throws DataNotFoundException {
        return new ResponseEntity<>(gameService.showGameById(gameId), HttpStatus.OK);
    }

    @GetMapping("/game/game-type/{gameType}")
    public ResponseEntity<?> showGameByGameTpe(@PathVariable String gameType ) {
        return new ResponseEntity<>(gameService.showGamesByType(gameType), HttpStatus.OK);
    }


    //                       PLAYER ENDPOINTS


    @GetMapping("/player")
    public ResponseEntity<?> showAllPlayers( ) {
        return new ResponseEntity<>(playerService.viewAllPlayers(), HttpStatus.OK);
    }

    @GetMapping("/player/game-id/{gameId}")
    public ResponseEntity<?> showPlayerByTeamId(@PathVariable Long gameId ) {
        return new ResponseEntity<>(playerService.viewPlayerByGameId(gameId), HttpStatus.OK);
    }

    @GetMapping("/player/player-id/{playerId}")
    public ResponseEntity<?> showPlayerByPlayerId(@PathVariable Long playerId ) throws DataNotFoundException {
        return new ResponseEntity<>(playerService.viewPlayerByPlayerId(playerId), HttpStatus.OK);
    }

    @PutMapping("/player/update/{playerId}")
    public ResponseEntity<String> updatePlayerByPlayerId(@RequestBody TeamPlayerDetailsDto player, @PathVariable Long playerId) throws DataNotFoundException {
        playerService.updatePlayerByPlayerId(player,playerId);
        return new ResponseEntity<>("Player is updated...",HttpStatus.OK);
    }
}
