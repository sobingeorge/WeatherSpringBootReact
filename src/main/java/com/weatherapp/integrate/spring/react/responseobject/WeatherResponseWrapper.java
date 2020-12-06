package com.weatherapp.integrate.spring.react.responseobject;

import java.time.Instant;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponseWrapper {

	private Weather[] weather;
	private Main main;
	private Sys sys;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Sys getSys() {
		return sys;
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}

	public Weather getWeather() {
		if(null != weather && weather.length > 0) {
			return weather[0];
		}
		return null;
	}

	public void setWeather(Weather[] weather) {
		this.weather = weather;
	}

	@Override
	public String toString() {
		return "WeatherResponseWrapper [weather=" + Arrays.toString(weather) + ", main=" + main + ", sys=" + sys
				+ ", name=" + name + "]";
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Weather {

		private String main;
		private String description;

		public String getMain() {
			return main;
		}

		public void setMain(String main) {
			this.main = main;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return "Weather [main=" + main + ", description=" + description + "]";
		}

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Main {

		private double temp;
		private double temp_min;
		private double temp_max;

		public double getTemp() {
			return temp;
		}

		public void setTemp(double temp) {
			this.temp = temp;
		}

		public double getTemp_min() {
			return temp_min;
		}

		public void setTemp_min(double temp_min) {
			this.temp_min = temp_min;
		}

		public double getTemp_max() {
			return temp_max;
		}

		public void setTemp_max(double temp_max) {
			this.temp_max = temp_max;
		}

		@Override
		public String toString() {
			return "Main [temp=" + temp + ", temp_min=" + temp_min + ", temp_max=" + temp_max + "]";
		}

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Sys {
		private Instant sunrise;
		private Instant sunset;

		public Instant getSunrise() {
			return sunrise;
		}

		public void setSunrise(String sunrise) {
			Instant instant = Instant.ofEpochSecond(Long.parseLong(sunrise));
			this.sunrise = instant;
		}

		public Instant getSunset() {
			return sunset;
		}

		public void setSunset(String sunset) {
			Instant instant = Instant.ofEpochSecond(Long.valueOf(sunset));
			this.sunset = instant;
		}

		@Override
		public String toString() {
			return "Sys [sunrise=" + sunrise + ", sunset=" + sunset + "]";
		}

	}

}
