package com.ab.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "world_places_dtl", indexes = { @Index(name = "cty_nm_idx", columnList = "cityName,cityState", unique = true) })
public class WorldPlacesEnitiy extends DefaultColumnEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int cityId;
	private String cityName;
	private String cityState;
	
	@Column(columnDefinition = "varchar(255) default 'India'")
	private String stateCountry;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityState() {
		return cityState;
	}

	public void setCityState(String cityState) {
		this.cityState = cityState;
	}

	public String getStateCountry() {
		return stateCountry;
	}

	public void setStateCountry(String stateCountry) {
		this.stateCountry = stateCountry;
	}

	@Override
	public String toString() {
		return "WorldPlacesEnitiy [id=" + id + ", cityId=" + cityId + ", cityName=" + cityName + ", cityState="
				+ cityState + ", stateCountry=" + stateCountry + "]";
	}

}
