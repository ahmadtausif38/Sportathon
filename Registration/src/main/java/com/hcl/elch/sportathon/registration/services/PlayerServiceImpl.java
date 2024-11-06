package com.hcl.elch.sportathon.registration.services;

import com.hcl.elch.sportathon.registration.dto.TeamPlayerDetailsDto;
import com.hcl.elch.sportathon.registration.entities.TeamPlayerDetails;
import com.hcl.elch.sportathon.registration.exceptions.DataNotFoundException;
import com.hcl.elch.sportathon.registration.repository.PlayerRegistrationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final Logger log= LogManager.getLogger(PlayerServiceImpl.class);

    @Autowired
    private PlayerRegistrationRepository playerRepo;

    @Override
    public List<TeamPlayerDetails> viewAllPlayers() {
        return playerRepo.findAll();
    }

    @Override
    public TeamPlayerDetails viewPlayerByPlayerId(Long id) throws DataNotFoundException {
        return playerRepo.findByPlayerId(id).orElseThrow(()->new DataNotFoundException("Player doesn't exist with playerId = "+id));
    }

    @Override
    public List<TeamPlayerDetails> viewPlayerByEmail(String email) {
        return playerRepo.findByEmail(email);
    }

    @Override
    public List<TeamPlayerDetails> viewPlayerByGameId(Long gameId) {
        return playerRepo.findByGameId(gameId);
    }


    @Override
    public TeamPlayerDetails updatePlayerByPlayerId(TeamPlayerDetailsDto player,Long playerId) throws DataNotFoundException {
        TeamPlayerDetails existingPlayer=viewPlayerByPlayerId(playerId);

        existingPlayer.setEmail(player.getEmail().toLowerCase());
        existingPlayer.setName(player.getName().toLowerCase());
        existingPlayer.setGender(player.getGender().toLowerCase());
        existingPlayer.setPhone(player.getPhone());
        log.info(" Updated Player {} ",existingPlayer);
        return playerRepo.save(existingPlayer);
    }
}
