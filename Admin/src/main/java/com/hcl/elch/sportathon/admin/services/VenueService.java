package com.hcl.elch.sportathon.admin.services;

import com.hcl.elch.sportathon.admin.customException.DuplicateValueException;
import com.hcl.elch.sportathon.admin.customException.NullValueException;
import com.hcl.elch.sportathon.admin.customException.ValueNotPresentException;
import com.hcl.elch.sportathon.admin.entities.Venue;
import com.hcl.elch.sportathon.admin.pojo.VenuePojo;
import com.hcl.elch.sportathon.admin.repositories.VenueRepository;
import com.hcl.elch.sportathon.admin.response.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VenueService {
    @Autowired
    private VenueRepository venueRepository;


    @Transactional
    public Response createVenue(VenuePojo venuePojo) throws Exception{
        if(isNullOrEmpty(venuePojo.getVenueName()) || isNullOrEmpty(venuePojo.getLocation())){
            throw new NullValueException("Field can't be empty or null");
        }
        if(!venueRepository.existsByVenueNameAndLocation(venuePojo.getVenueName(), venuePojo.getLocation())){
            Venue venue = new Venue();
            venue.setVenueName(venuePojo.getVenueName());
            venue.setLocation(venuePojo.getLocation());
            venueRepository.save(venue);
            return new Response("New Venue Successfully created");
        }
        throw new DuplicateValueException("Venue and location already available");
    }

    private boolean isNullOrEmpty(String str){
        return str == null || str.trim().isEmpty();
    }
    @Transactional
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    @Transactional
    public Optional<Venue> getVenueById(Long venueId) throws Exception{
        if(!this.venueRepository.existsByvenueID(venueId)){
            throw new ValueNotPresentException("ID doesn't Exist");
        }
        return venueRepository.findById(venueId);
    }


    @Transactional
    public Response updateVenue(Long venueId, VenuePojo updatedVenue) throws Exception{
         if(this.venueRepository.existsByVenueNameAndLocation(updatedVenue.getVenueName(), updatedVenue.getLocation())){
             throw new DuplicateValueException("Venue and location already available");
         }
         Venue existingVenue = venueRepository.findById(venueId).orElseThrow(()-> new ValueNotPresentException("Venue ID does't present"));
         if(!isNullOrEmpty(updatedVenue.getVenueName())){
             existingVenue.setVenueName(updatedVenue.getVenueName());
         }
         if(!isNullOrEmpty(updatedVenue.getLocation())){
             existingVenue.setLocation(updatedVenue.getLocation());
         }
         venueRepository.save(existingVenue);
         return new Response("Venue Updated Successfully");
    }

    @Transactional
    public Response deleteVenue(Long venueId) {
        venueRepository.deleteById(venueId);
        return new Response("Venue deleted Successfully");
    }
}