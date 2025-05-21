package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.dto.Review;
import com.example.demo.exception.PackageNotFound;
import com.example.demo.feignclient.ReviewClient;
import com.example.demo.model.Pack;
import com.example.demo.repository.TravelPackageRepository;

@Service
public class TravelPackageServiceImpl implements TravelPackageService {
	
	Logger log=LoggerFactory.getLogger(TravelPackageServiceImpl.class);
 
    @Autowired
    TravelPackageRepository repository;

    @Autowired
    ReviewClient reviewClient;

    /**
     * Saves a new travel package to the database.
     * @param pack The travel package to be saved.
     * @return Success message if saved, otherwise an error message.
     */
    
    @Override
    public String savePack(Pack pack) {
        log.info("In PackServiceImpl savePack method...");
        Optional<Pack> existingPack = repository.findById(pack.getPackageId());

        if (existingPack.isPresent()) {
            log.warn("Pack already exists: " + pack.getPackageId());
            return "Pack already exists!";
        }
        repository.save(pack);
        log.info("Pack saved successfully: " + pack.getPackageId());
        
        return "Successfully saved";
    }


    /**
     * Updates an existing travel package in the database.
     * @param pack The travel package object containing updated details.
     * @return The updated travel package object.
     */
    
    @Override
    public Pack updatePack(Pack pack) {
    	log.info("In PackServiceImpl update pack method ....");
        return repository.save(pack);
    }
   
    public String increaseAvailability(int packageId) {
        Optional<Pack> packageOpt = repository.findById(packageId);

        if (packageOpt.isPresent()) {
            Pack travelPackage = packageOpt.get();
            travelPackage.setAvailability(travelPackage.getAvailability() + 1);
            repository.save(travelPackage);
            log.info("Availability updated successfully for package ID: " + packageId);
            return "increased";

           
        } else {
            log.warn("Package not found with ID: " + packageId);
            return null;
        }
    }
    
    public String decreaseAvailability(int packageId) {
    	 Pack travelPackage = repository.findById(packageId)
                 .orElseThrow(() -> new RuntimeException("Package not found"));

         if (travelPackage.getAvailability() > 0) {
             travelPackage.setAvailability(travelPackage.getAvailability() - 1);
             repository.save(travelPackage);
             return "Availability updated";
         } else {
             return "No available slots";
         }
    }


    
    /**
     * Deletes a travel package and all associated reviews from the database.
     * @param packageId ID of the travel package to be deleted.
     * @return Success message confirming deletion of package and related reviews.
     */
    public String deletePack(int packageId) {
    	
    	log.info("In PackServiceImpl delete pack method ....");
        // Retrieve all reviews associated with the package
        List<Review> reviews = reviewClient.getReviewByPackage(packageId);

        // Remove each review associated with the package
        for (Review review : reviews) {
            reviewClient.removeReview(review.getReviewId());
        }

        // Delete the package from the repository
        repository.deleteById(packageId);

        return "Package and related reviews deleted successfully";
    }

    /**
     * Retrieves a travel package by its ID.
     * @param packId ID of the travel package to retrieve.
     * @return The travel package object if found.
     * @throws PackageNotFound If the package does not exist.
     */
    @Override
    public Pack getPack(int packId) throws PackageNotFound {
    	log.info("In PackServiceImpl get pack method ....");
        Optional<Pack> optional = repository.findById(packId);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new PackageNotFound("Not found");
        }
    }

    /**
     * Retrieves all available travel packages from the database.
     * @return A list of all travel packages.
     */
    @Override
    public List<Pack> getAllPack() {
    	log.info("In PackServiceImpl get all pack method ....");
        return repository.findAll();
    }
}
