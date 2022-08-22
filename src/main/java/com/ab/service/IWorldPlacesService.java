package com.ab.service;

import java.util.List;

public interface IWorldPlacesService {

	public List<String> getCountriesList();
	public List<String> getStatesByCountry(String country);
	public List<String> getCitiesByState(String state);
	
	
}
