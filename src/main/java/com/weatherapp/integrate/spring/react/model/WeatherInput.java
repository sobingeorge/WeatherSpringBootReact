package com.weatherapp.integrate.spring.react.model;

import javax.persistence.*;

@Entity
@Table(name = "WEATHER_INPUT")
public class WeatherInput {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "CITY")
	private String city;

	
	public WeatherInput() {

	}
	
	public WeatherInput(String title) {
		this.city = title;
		
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public long getId() {
		return id;
	}




}
