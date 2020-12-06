import React, {useState, useEffect} from "react";
import { Switch, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/AuthorizationService";
import WeatherSearch from "./components/WeatherSearch";
import Login from "./components/Login";
import Register from "./components/Register";
import Home from "./components/Home";


function App() {


  const initialState = {
        currentUser: undefined,
      };
      const [state, setState] = useState(initialState);
  
  useEffect(() => {
  
  
      const user = AuthService.getCurrentUser();
  
      if (user) {
        setState({
          currentUser: user
        });
      }
    }, []);
  
    const logOut = () => {
      AuthService.logout();
    }
  


  return (
    <div>
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <a href="/" className="navbar-brand">
          Weather Base

        </a>
        <div className="navbar-nav mr-auto">
        {state.currentUser  &&
          <li className="nav-item">
            <Link to={"/add"} className="nav-link">
              Search
            </Link>
          </li>
        }
        </div>
        {state.currentUser  ? (
            <div className="navbar-nav ml-auto">
              <li className="nav-item">
                <a href="/login" className="nav-link" onClick={logOut}>
                  LogOut
                </a>
              </li>
            </div>
          ) : (
            <div className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link to={"/login"} className="nav-link">
                  Login
                </Link>
              </li>

              <li className="nav-item">
                <Link to={"/register"} className="nav-link">
                  Sign Up
                </Link>
              </li>
            </div>
          )}
      </nav>
      <div className="container mt-3">
        <Switch>
          <Route exact path="/add" component={WeatherSearch} />
          <Route exact path={["/", "/home"]} component={Home} />
          <Route exact path="/login" component={Login} />
          <Route exact path="/register" component={Register} />
        </Switch>
      </div>
    </div>
  );
}

export default App;
