package com.weatherapp.integrate.spring.react.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapp.integrate.spring.react.model.WeatherInput;
import com.weatherapp.integrate.spring.react.model.WeatherSearch;
import com.weatherapp.integrate.spring.react.repository.WeatherSearchRepository;
import com.weatherapp.integrate.spring.react.responseobject.WeatherResponseWrapper;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class WeatherController {

	@Autowired
	WeatherSearchRepository weatherSearchRepository;

	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}

	private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);
	
	@PostMapping("/citySearch")
	public ResponseEntity<WeatherSearch> searchWeatherByCity(@RequestBody WeatherInput request) {
		ResponseEntity<WeatherSearch> response = null;
		try {
			WeatherSearch weatherSearch = fetchFromOpenweather(request);
			weatherSearchRepository.save(weatherSearch);
			response = new ResponseEntity<>(weatherSearch, HttpStatus.CREATED);
			return response;
		} catch (Exception e) {
			logger.error(e.getMessage());
			response = new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
			return response;
		}
	}

	private WeatherSearch fetchFromOpenweather(WeatherInput request)
			throws IOException, JsonParseException, JsonMappingException {
		ResponseEntity<String> response = rest().getForEntity("http://api.openweathermap.org/data/2.5/weather?q="
				+ request.getCity() + "&appid=9ff77c8357daab02bea20c615dbf430d&units=metric", String.class);
		ObjectMapper mapper = new ObjectMapper();
		WeatherResponseWrapper weatherResp = mapper.readValue(response.getBody(), WeatherResponseWrapper.class);
		WeatherSearch weatherSearch = new WeatherSearch();
		weatherSearch.setCityName(weatherResp.getName());
		weatherSearch.setWeatherDescription(weatherResp.getWeather().getDescription());
		weatherSearch.setCurrentTemp(weatherResp.getMain().getTemp());
		weatherSearch.setMinTemp(weatherResp.getMain().getTemp_min());
		weatherSearch.setMaxTemp(weatherResp.getMain().getTemp_max());
		weatherSearch.setSunrise(Timestamp.from(weatherResp.getSys().getSunrise()));
		weatherSearch.setSunset(Timestamp.from(weatherResp.getSys().getSunset()));
		return weatherSearch;
	}

	@GetMapping("/citySearchHistory")
	public ResponseEntity<List<WeatherSearch>> getCitySearchHistory(@RequestParam(required = false) String title) {
		ResponseEntity<List<WeatherSearch>> response = null;
		try {
			List<WeatherSearch> historyList = new ArrayList<WeatherSearch>();
			if (title == null) {
				weatherSearchRepository.findAll().forEach(historyList::add);
			}
			if (historyList.isEmpty()) {
				response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			response = new ResponseEntity<>(historyList, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@DeleteMapping("/bulkDeleteHistory")
	public ResponseEntity<HttpStatus> bulkDeleteHistory() {
		try {
			weatherSearchRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/deleteHistory/{id}")
	public ResponseEntity<HttpStatus> deleteHistory(@PathVariable("id") long id) {
		try {
			weatherSearchRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
