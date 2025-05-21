package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Pack;

@Repository
public interface TravelPackageRepository extends JpaRepository<Pack , Integer>{
     
	Optional<Pack> findById(int packageId);
}
