package com.hcl.elch.sportathon.registration.repository;

import com.hcl.elch.sportathon.registration.entities.TeamPlayerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRegistrationRepository extends JpaRepository<TeamPlayerDetails,Long> {

    Optional<TeamPlayerDetails> findByEmailAndGameId(String email, Long gameId);

    List<TeamPlayerDetails> findByEmail(String email);

    Optional<TeamPlayerDetails>  findByPlayerId(Long playerId);

    List<TeamPlayerDetails> findByGameId(Long gameId);


}
