package com.hcl.elch.sportathon.registration.repository;

import com.hcl.elch.sportathon.registration.entities.GameDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRegistrationRepository extends JpaRepository<GameDetails,Long> {

   Optional<GameDetails> findByGameId(Long id);
   Optional<GameDetails> findByGameName(String name);

   List<GameDetails> findByGameType(String gameType);

}
