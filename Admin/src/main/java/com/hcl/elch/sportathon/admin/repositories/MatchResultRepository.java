package com.hcl.elch.sportathon.admin.repositories;

import com.hcl.elch.sportathon.admin.entities.MatchDetails;
import com.hcl.elch.sportathon.admin.entities.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchResultRepository extends JpaRepository<MatchResult, Long> {
    Optional<MatchResult> findBymatchId(MatchDetails matchDetails);

}
