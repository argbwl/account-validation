package com.ab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ab.logger.AVLogger;
import com.ab.service.IWorldPlacesService;

@RestController
@RequestMapping("wpc")
@CrossOrigin
public class WorldPlacesController {
	
	private static AVLogger logger = AVLogger.getLogger(WorldPlacesController.class);
	
	@Autowired
	private IWorldPlacesService worldPlacesService;

	@GetMapping("/get-cntries")
	public ResponseEntity<List<String>> getAllCountries(){
		logger.info("Received Request to retrive all countries");
		List<String> ctryList = worldPlacesService.getCountriesList();
		return new ResponseEntity<List<String>>(ctryList, HttpStatus.OK);
	}
	
	@GetMapping("/get-State/{ctry}")
	public ResponseEntity<List<String>> getAllStatesByCountry(@PathVariable String ctry){
		logger.info("Received Request to retrive all State by {}",ctry);
		List<String> ctryList = worldPlacesService.getStatesByCountry(ctry);
		return new ResponseEntity<List<String>>(ctryList, HttpStatus.OK);
	}
	
	@GetMapping("/get-cities/{stt}")
	public ResponseEntity<List<String>> getAllCitiesByState(@PathVariable String stt){
		logger.info("Received Request to retrive all Cities by State {}",stt);
		List<String> ctryList = worldPlacesService.getCitiesByState(stt);
		return new ResponseEntity<List<String>>(ctryList, HttpStatus.OK);
	}
}
