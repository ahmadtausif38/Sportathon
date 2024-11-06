package com.hcl.elch.sportathon.registration.services;

import com.hcl.elch.sportathon.registration.dto.TeamPlayerDetailsDto;
import com.hcl.elch.sportathon.registration.entities.TeamPlayerDetails;
import com.hcl.elch.sportathon.registration.exceptions.DataNotFoundException;

import java.util.List;

public interface PlayerService {

    public List<TeamPlayerDetails> viewAllPlayers();
    public TeamPlayerDetails viewPlayerByPlayerId(Long id) throws DataNotFoundException;
    public List<TeamPlayerDetails> viewPlayerByEmail(String email);
    public TeamPlayerDetails updatePlayerByPlayerId(TeamPlayerDetailsDto player,Long id) throws DataNotFoundException;

    public List<TeamPlayerDetails> viewPlayerByGameId(Long teamId);
}
