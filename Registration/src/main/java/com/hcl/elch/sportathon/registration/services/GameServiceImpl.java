package com.hcl.elch.sportathon.registration.services;

import com.hcl.elch.sportathon.registration.controller.RegistrationController;
import com.hcl.elch.sportathon.registration.dto.GameDetailsDto;
import com.hcl.elch.sportathon.registration.dto.GameRequest;
import com.hcl.elch.sportathon.registration.entities.GameDetails;
import com.hcl.elch.sportathon.registration.exceptions.DataAlreadyExist;
import com.hcl.elch.sportathon.registration.exceptions.DataNotFoundException;
import com.hcl.elch.sportathon.registration.repository.GameRegistrationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GameServiceImpl implements GameService{
    private static final Logger log= LogManager.getLogger(GameServiceImpl.class);
    @Autowired
    private GameRegistrationRepository gameRepo;

    //add game DTO class here
    @Override
    public void addGames(GameRequest request) throws Exception {
        GameDetailsDto game=request.getGame();

            GameDetails existingGame=gameRepo.findByGameName(game.getGameName()).orElse(null);
            if(existingGame==null){
                GameDetails newGame=GameDetails.builder()
                        .gameName(game.getGameName().toLowerCase())
                        .gameType(game.getGameType().toLowerCase())
                        .numberOfPlayers(game.getNumberOfPlayers())
                        .build();
                gameRepo.save(newGame);
                log.info("Game data saved !!");
            }
          else {
                String errorMessage = String.format("Game %s already exists with id: %s", existingGame.getGameName(), existingGame.getGameId());
                log.error(errorMessage);
                throw new DataAlreadyExist(errorMessage);

          }
    }

    @Override
    public GameDetails showGameById(Long id) throws DataNotFoundException {
        System.out.println("Game ID :- "+id);
        return gameRepo.findByGameId(id)
                .orElseThrow(() -> new DataNotFoundException("Data not exist for game id: " + id));
    }

    @Override
    public List<GameDetails> showAllGames() {
        return gameRepo.findAll();
    }

    @Override
    public List<GameDetails> showGamesByType(String gameType) {
        System.out.println("Showing game by gameName");
        return gameRepo.findByGameType(gameType);
    }


}
