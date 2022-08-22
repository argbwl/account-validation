package com.ab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ab.logger.AVLogger;
import com.ab.repo.WorldPlacesRepo;

@Service
@Transactional(rollbackFor = Exception.class)
public class WorldPlacesServiceImpl implements IWorldPlacesService {
	
	private static AVLogger logger = AVLogger.getLogger(WorldPlacesServiceImpl.class);

	@Autowired
	private WorldPlacesRepo worldPlacesRepo;
	
	@Override
	public List<String> getCountriesList() {
		List<String> cntryList = worldPlacesRepo.getAllCountries();
		logger.info("Retrived Countries Size : {}",cntryList.size());
		return cntryList;
	}

	@Override
	@Cacheable(cacheNames = "countryDetailsCache", key = "#country")
	public List<String> getStatesByCountry(String country) {
		List<String> sttList = worldPlacesRepo.getAllStatesByStateCountry(country);
		logger.info("Retrived States Size : {}",sttList.size());
		return sttList;
	}

	@Override
	@Cacheable(cacheNames = "stateDetailsCache", key = "#state")
	public List<String> getCitiesByState(String state) {
		List<String> cityList = worldPlacesRepo.getAllCitiesByCityState(state);
		logger.info("Retrived cities Size : {}",cityList.size());
		return cityList;
	}

}
