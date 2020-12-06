package com.weatherapp.integrate.spring.react;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.weatherapp.integrate.spring.react.repository.WeatherSearchRepository;

@SpringBootApplication
public class SpringBootReactStarterApplication {
	
	@Autowired
	WeatherSearchRepository weatherSearchRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactStarterApplication.class, args);
	}

}
