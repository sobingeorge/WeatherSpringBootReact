import React, { useState, useEffect } from "react";
import WeatherSearchService from "../services/WeatherService";
import { Link } from "react-router-dom";

const WeatherSearch = props => {


  useEffect(() => {
    fetchHistory()
  },[]);


  const initialTutorialState = {
    id: null,
    city: "",
    description: ""
  };

  const initialCityState = {
    id: null,
    name: "",
    weatherDescription: "",
    currentTemp: "",
    minTemp: "",
    maxTemp: "",
    sunrise: "",
    sunset: ""
  };
  const [searchParam, setSearchParam] = useState(initialTutorialState);
  const [currentIndex, setCurrentIndex] = useState(-1);
  const [citySearch, setCitySearch] = useState(initialCityState);
  const [searchResults, setSearchResults] = useState([]);
  const [currentCity, setCurrentCity] = useState(null);
  const handleInputChange = event => {
    const { name, value } = event.target;
    setSearchParam({ ...searchParam, [name]: value });
  };

  const searchByCity = () => {
    var data = {
      city: searchParam.city,
      description: searchParam.description
    };
    WeatherSearchService.citySearch(data)
      .then(response => {
        setCitySearch({
          id: response.data.id,
          name: response.data.cityName,
          weatherDescription: response.data.weatherDescription,
          currentTemp: response.data.currentTemp,
          minTemp: response.data.minTemp,
          maxTemp: response.data.maxTemp,
          sunrise: response.data.sunrise,
          sunset: response.data.sunset
        });
        console.log();
        fetchHistory();  
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
      
  };

 
  const fetchHistory = () => {
      WeatherSearchService.citySearchHistory()
      .then(response => {
        console.log(response.data);
          setSearchResults(response.data);
        console.log({searchResults});
        
      })
      .catch(e => {
        console.log(e);
      });

  };

  const refreshList = () => {
    fetchHistory();
    setCurrentCity(null);
    setCurrentIndex(-1);
  };

  const setActiveCity = (searchResults, index) => {
    setCurrentCity(searchResults);
    setCurrentIndex(index);
  };

  const bulkDeleteHistory = () => {
    WeatherSearchService.bulkDeleteHistory()
      .then(response => {
        console.log(response.data);
        refreshList();
      })
      .catch(e => {
        console.log(e);
      });
  };

  const deleteHistory = () => {
    WeatherSearchService.deleteHistory(currentCity.id)
      .then(response => {
        console.log(response.data);
        refreshList();
      })
      .catch(e => {
        console.log(e);
      });
  };

  

  return (
    <div>
    

<div className="list row">
<div className="col-md-8">
  <div className="input-group mb-3">
    <input
      type="text"
      className="form-control"
      placeholder="Search by city"
      id="title"
      name="city"
      value={searchParam.city}
      onChange={handleInputChange}
    />
    <div className="input-group-append">
      <button
        className="btn btn-outline-secondary"
        type="button"
        onClick={searchByCity}
      >
        Search
      </button>
    </div>
  </div>
  <div>
    {citySearch &&
    <table class="table table-hover">
      <tr>
        <th>City</th>
        <th>Weather Description</th>
        <th>Current Temp</th>
        <th>Min Temp</th>
        <th>Max Temp</th>
        <th>Sunrise</th>
        <th>Sunset</th>
        </tr>
        <tr>
          <td>{citySearch && citySearch.name}</td>
          <td>{citySearch && citySearch.weatherDescription}</td>
          <td>{citySearch && citySearch.currentTemp}</td>
          <td>{citySearch && citySearch.minTemp}</td>
          <td>{citySearch && citySearch.maxTemp}</td>
          <td>{citySearch && citySearch.sunrise}</td>
          <td>{citySearch && citySearch.sunset}</td>
          </tr>
      </table>
}
</div>

</div>
<div className="col-md-6">
  <h4>Weather Search History</h4>

  <ul className="list-group">
    {searchResults &&
      searchResults.map((searchResult, index) => (
        <li
          className={
            "list-group-item " + (index === currentIndex ? "active" : "")
          }
          onClick={() => setActiveCity(searchResult, index)}
          key={index} >
          {searchResult.cityName}
        </li>
      ))}
  </ul>

  <button
    className="m-3 btn btn-sm btn-danger"
    onClick={bulkDeleteHistory}>
    Bulk Delete
  </button>
</div>
<div className="col-md-6">
  {currentCity ? (
    <div>
      <div>
            <label>
                <strong>Weather Description:</strong>
              </label>{" "}
              {currentCity.weatherDescription}
            </div>
            <div>
              <label>
                <strong>Current Temp:</strong>
              </label>{" "}
              {currentCity.currentTemp}
            </div>
            <div>
              <label>
                <strong>Min Temp:</strong>
              </label>{" "}
              {currentCity.minTemp}
            </div>
            <div>
              <label>
                <strong>Max Temp:</strong>
              </label>{" "}
              {currentCity.maxTemp}
            </div>
            <div>
              <label>
                <strong>Sunrise:</strong>
              </label>{" "}
              {currentCity.sunrise}
            </div>
            <div>
              <label>
                <strong>Sunset:</strong>
              </label>{" "}
              {currentCity.sunset}
            </div>
      <button className="badge badge-danger mr-2" onClick={deleteHistory}>
            Delete
      </button>
</div>
  ) : (
    <div>
      <br />
    </div>
  )}
</div>
</div>


</div>
  );
};

export default WeatherSearch;