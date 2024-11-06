package com.hcl.elch.sportathon.admin.repositories;

import com.hcl.elch.sportathon.admin.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
    boolean existsByVenueNameAndLocation(String venueName, String location);
    boolean existsByvenueID(Long venueID);

    Venue findByvenueID(Long venueID);
}
