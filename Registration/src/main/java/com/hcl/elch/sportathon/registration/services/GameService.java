package com.hcl.elch.sportathon.registration.services;

import com.hcl.elch.sportathon.registration.dto.GameDetailsDto;
import com.hcl.elch.sportathon.registration.dto.GameRequest;
import com.hcl.elch.sportathon.registration.entities.GameDetails;
import com.hcl.elch.sportathon.registration.exceptions.DataAlreadyExist;
import com.hcl.elch.sportathon.registration.exceptions.DataNotFoundException;

import java.util.List;

public interface GameService {

    // add GameDetailsDto class here
    public void addGames(GameRequest request) throws Exception;
    public GameDetails showGameById(Long id) throws DataNotFoundException;
    public List<GameDetails> showAllGames();

    public List<GameDetails> showGamesByType(String gameType);

}
