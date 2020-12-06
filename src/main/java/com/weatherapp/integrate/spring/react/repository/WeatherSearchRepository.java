package com.weatherapp.integrate.spring.react.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weatherapp.integrate.spring.react.model.WeatherSearch;

@Repository
public interface WeatherSearchRepository extends JpaRepository<WeatherSearch, Long>{
	List<WeatherSearch> findBycityNameContaining(String cityName);

}
