package com.weatherapp.integrate.spring.react.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WEATHER_SEARCH")
public class WeatherSearch {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "CITY_NAME")
	private String cityName;
	
	@Column(name = "DESC")
	private String weatherDescription;
	
	@Column(name = "CURR_TEMP")
	private Double currentTemp;
	
	@Column(name = "MIN_TEMP")
	private Double minTemp;
	
	@Column(name = "MAX_TEMP")
	private Double maxTemp;
	
	@Column(name = "SUNRISE")
	private Timestamp sunrise;
	
	@Column(name = "SUNSET")
	private Timestamp sunset;
	
	
	public WeatherSearch() {
		
	}


	public long getId() {
		return id;
	}

	public String getCityName() {
		return cityName;
	}


	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	public String getWeatherDescription() {
		return weatherDescription;
	}


	public void setWeatherDescription(String weatherDescription) {
		this.weatherDescription = weatherDescription;
	}


	public Double getCurrentTemp() {
		return currentTemp;
	}


	public void setCurrentTemp(Double currentTemp) {
		this.currentTemp = currentTemp;
	}


	public Double getMinTemp() {
		return minTemp;
	}


	public void setMinTemp(Double minTemp) {
		this.minTemp = minTemp;
	}


	public Double getMaxTemp() {
		return maxTemp;
	}


	public void setMaxTemp(Double maxTemp) {
		this.maxTemp = maxTemp;
	}


	public Date getSunrise() {
		return sunrise;
	}


	public void setSunrise(Timestamp sunrise) {
		this.sunrise = sunrise;
	}


	public Date getSunset() {
		return sunset;
	}


	public void setSunset(Timestamp sunset) {
		this.sunset = sunset;
	}


	@Override
	public String toString() {
		return "WeatherSearch [id=" + id + ", cityName=" + cityName + ", weatherDescription=" + weatherDescription
				+ ", currentTemp=" + currentTemp + ", minTemp=" + minTemp + ", maxTemp=" + maxTemp + ", sunrise="
				+ sunrise + ", sunset=" + sunset + "]";
	}
	
	

}
