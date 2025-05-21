package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.PackageNotFound;
import com.example.demo.model.Pack;
import com.example.demo.service.TravelPackageService;

@RestController
@RequestMapping("/package")
public class TravelPackageController {
	@Autowired
	TravelPackageService service;

	@PostMapping("/save")
	public String savePack(@RequestBody @Validated Pack pack) {
		return service.savePack(pack);
	}

	@PutMapping("/update")
	public Pack updatePack(@RequestBody @Validated Pack pack) {
		return service.updatePack(pack);
	}

	@DeleteMapping("/deleteById/{did}")
	public String deletePack(@PathVariable("did") int packId) {
		return service.deletePack(packId);
	}

	@GetMapping("/fetchById/{fid}")
	public Pack checkPackage(@PathVariable("fid") int packId) throws PackageNotFound {
		return service.getPack(packId);
	}

	@GetMapping("/fetchAll")
	public List<Pack> getAllPack() {
		return service.getAllPack();
	}
	@PutMapping("/update/{packageId}")
	public String increaseAvailability(@PathVariable("packageId") int packageId) {
		 return service.increaseAvailability(packageId);
	 }
	@PutMapping("/update2/{packageId}")
	public String decreaseAvailability(@PathVariable("packageId") int packageId) {
		 return service.decreaseAvailability(packageId);
	 }
}
