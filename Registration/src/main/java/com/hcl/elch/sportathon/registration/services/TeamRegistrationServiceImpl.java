package com.hcl.elch.sportathon.registration.services;

import com.hcl.elch.sportathon.registration.dto.CandidateDto;
import com.hcl.elch.sportathon.registration.dto.TeamPlayerDetailsDto;
import com.hcl.elch.sportathon.registration.dto.TeamWithPlayerRequest;
import com.hcl.elch.sportathon.registration.entities.GameDetails;
import com.hcl.elch.sportathon.registration.entities.TeamDetails;
import com.hcl.elch.sportathon.registration.entities.TeamPlayerDetails;
import com.hcl.elch.sportathon.registration.exceptions.DataAlreadyExist;
import com.hcl.elch.sportathon.registration.exceptions.DataNotFoundException;
import com.hcl.elch.sportathon.registration.repository.GameRegistrationRepository;
import com.hcl.elch.sportathon.registration.repository.PlayerRegistrationRepository;
import com.hcl.elch.sportathon.registration.repository.TeamRegistrationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TeamRegistrationServiceImpl implements TeamRegistrationService{

    private static final Logger log= LogManager.getLogger(TeamRegistrationServiceImpl.class);

    @Autowired
    private APIClient apiClient;
    @Autowired
    private  EmailService emailService;
    @Autowired
    private TeamRegistrationRepository teamRepo;

    @Autowired
    private PlayerRegistrationRepository playerRepo;

    @Autowired
    private GameRegistrationRepository gameRepo;

    @Override
    public void saveTeamWithPlayers( TeamWithPlayerRequest team) throws Exception {
        log.info("Inside saveTeamPlayers() method...");

        GameDetails game = gameRepo.findByGameId(team.getGameId()).orElseThrow(()-> new DataNotFoundException("Game does not exist with gameId = "+team.getGameId()));

        // check first - candidateId exist or not
//        CandidateDto candidate=candidateRepo.findByCandidateId(team.getCandidateId());
        CandidateDto candidate=apiClient.getDepartmentById(team.getCandidateId());
        log.info("Candidate Data for candidateId = {} from User service :- {}",team.getCandidateId(),candidate);
        if ( candidate== null) {
            log.error("Given captianId doesn't exist");
            throw new DataNotFoundException("Candidate data not found for this candidateId = " + team.getCandidateId());
        }

        //checking whether given team-captain has already registered in this game as a player or not.
        // Inside TeamplayerDetails table checking with email and gameId

        else if (playerRepo.findByEmailAndGameId(candidate.getEmail(), team.getGameId()).isPresent()) {
                throw new DataAlreadyExist(String.format("This candidate with email id = %s and candidate Id =%s is already registered in this game as a player...",candidate.getEmail(),team.getCandidateId()));
        }
        else if (!teamRepo.existsByCandidateIdAndGame(team.getCandidateId(), game)) {
            log.info("Saving Team and Players...");

            TeamDetails actualTeam = TeamDetails.builder()
                    .creationDate(LocalDate.now())
                    .candidateId(team.getCandidateId())
                    .teamName(team.getTeamName().toLowerCase())
                    .game(game)
                    .build();

            // Adding Player into TeamPlayerDetails Table
            List<TeamPlayerDetailsDto> players = team.getPlayers();
            List<TeamPlayerDetails> updatedPlayers = new ArrayList<>();
            if (players != null) {
                log.info("Player details is checking ...");
                for (TeamPlayerDetailsDto player : players) {
                    // Here we have to call usermicroservices and fetch candidateId using email

                    CandidateDto candidate1=apiClient.getDepartmentByEmail(player.getEmail());
                    log.info("Candidate Data for EmailId = {} from User service :- {}",player.getEmail(),candidate);

                    Long candidateId;
                    if(candidate1!=null){
                        candidateId=candidate1.getId();
                    }
                    else {
                        candidateId=null;
                    }
                    //Checking whether captain or candidate Already registered in this game or not in teamDetails table
                    if (candidateId != null && teamRepo.existsByCandidateIdAndGame(candidateId, game)) {
                        throw new DataAlreadyExist(String.format("Player with email = %s, is already registered as Captain with candidateId = %s  !!.", player.getEmail(), candidateId));
                    }

                    //Fetching Playerdetails who is already registered in this game, using email-id and game-id
                    Optional<TeamPlayerDetails> existingPlayer = playerRepo.findByEmailAndGameId(player.getEmail(), player.getGameId());
                    if (existingPlayer.isPresent()) {
                        throw new DataAlreadyExist(String.format("Player with email %s is already registered with gameId %s  !!.", player.getEmail(), player.getGameId()));
                    } else {
                        TeamPlayerDetails player1 = TeamPlayerDetails.builder()
                                .email(player.getEmail().toLowerCase())
                                .gameId(team.getGameId())
                                .name(player.getName().toLowerCase())
                                .gender(player.getGender().toLowerCase())
                                .phone(player.getPhone())
                                .build();
                        updatedPlayers.add(player1);
                    }
                }

                //Saving team with player
                actualTeam.setPlayers(updatedPlayers);
                TeamDetails savedTeam = teamRepo.save(actualTeam);
                log.debug("Saved Team with player :- {}", savedTeam.toString());
                log.info("Team saved with players ...");

                emailService.mailSendingForRegistration(candidate.getEmail(),candidate.getName(),actualTeam.getGame().getGameName());

            } else {
                //saving  team without player
                log.info("Player is registering as individual ...");
                TeamDetails savedTeam = teamRepo.save(actualTeam);
                log.debug("Saved Team as Individual player :- {}", savedTeam.toString());
                log.info("Individual player data saved...");
            }

        } else {
            throw new DataAlreadyExist(String.format("Team or player already registered in this sport with candidateId = %s.", team.getCandidateId()));
        }
    }

    @Override
    public List<TeamDetails> viewTeamWithPlayer() {
        return teamRepo.findAll();
    }

    @Override
    public TeamDetails viewTeamByTeamId(Long id) throws DataNotFoundException {
        return teamRepo.findByTeamId(id).orElseThrow(()-> new DataNotFoundException("Team Detail not exist with teamId = "+id));
    }

    @Override
    public List<TeamDetails> viewTeamByGameId(Long gameId) {
//        return teamRepo.findByGameGameId(gameId);

        return  teamRepo.findTeamByGameId(gameId);
    }

    @Override
    public List<String> viewAllTeamName() {
        return teamRepo.findAllTeamName();
    }

    @Override
    public String viewTeamNameByTeamId(Long teamId) throws DataNotFoundException {
        return teamRepo.findTeamNameByTeamId(teamId).orElseThrow(()-> new DataNotFoundException("Data Not found For this team-id = "+teamId));
    }

}
