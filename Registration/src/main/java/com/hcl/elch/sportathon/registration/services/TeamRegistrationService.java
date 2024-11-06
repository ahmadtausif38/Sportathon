package com.hcl.elch.sportathon.registration.services;

import com.hcl.elch.sportathon.registration.dto.TeamDetailsDto;
import com.hcl.elch.sportathon.registration.dto.TeamPlayerDetailsDto;
import com.hcl.elch.sportathon.registration.dto.TeamWithPlayerRequest;
import com.hcl.elch.sportathon.registration.entities.TeamDetails;
import com.hcl.elch.sportathon.registration.exceptions.DataAlreadyExist;
import com.hcl.elch.sportathon.registration.exceptions.DataNotFoundException;

import java.util.List;
import java.util.Map;

public interface TeamRegistrationService {

//    public void saveTeamWithPlayers(Map<String,TeamWithPlayerRequest> request);
        public void saveTeamWithPlayers(TeamWithPlayerRequest request) throws Exception;
        public List<TeamDetails> viewTeamWithPlayer();

        public TeamDetails viewTeamByTeamId(Long id) throws DataNotFoundException;

        public List<TeamDetails> viewTeamByGameId(Long gameId);

        public List<String> viewAllTeamName();

        public  String viewTeamNameByTeamId(Long teamId) throws DataNotFoundException;

//        public List<TeamDetails> viewTeamByGameName(String gameName);
}
