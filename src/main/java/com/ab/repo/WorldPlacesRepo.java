package com.ab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ab.entity.WorldPlacesEnitiy;

@Repository
public interface WorldPlacesRepo extends JpaRepository<WorldPlacesEnitiy,Integer>{
	
	@Query("select DISTINCT(c.stateCountry) from WorldPlacesEnitiy c order by c.stateCountry")
	public List<String> getAllCountries();
	
	@Query("select DISTINCT(p.cityState) from WorldPlacesEnitiy p where p.stateCountry = :stateCountry order by p.cityState asc")
	public List<String> getAllStatesByStateCountry(String stateCountry);
	
	@Query("select p.cityName from WorldPlacesEnitiy p where p.cityState = :cityState order by p.cityName asc")
	public List<String> getAllCitiesByCityState(String cityState);
	
	
}
