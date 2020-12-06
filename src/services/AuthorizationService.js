import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

const register = (email, dob, password) => {
  return axios.post(API_URL + "signup", {
    email,
    dob,
    password,
  });
};

const login = (username, password) => {
  return axios
    .post(API_URL + "signin", {
      username,
      password,
    })
    .then((response) => {
      if (response.data.accessToken) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }

      return response.data;
    });
};

const logout = () => {
  localStorage.removeItem("user");
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const authHeader = () => {
    const user = JSON.parse(localStorage.getItem('user'));
  
    if (user && user.accessToken) {
      // return { Authorization: 'Bearer ' + user.accessToken }; // for Spring Boot back-end
      //return { 'x-access-token': user.accessToken };       // for Node.js Express back-end
      return { Authorization: 'Bearer ' + user.accessToken  };
    } else {
      return {};
    }
  }
  


export default {
  register,
  login,
  logout,
  getCurrentUser,
  authHeader
};
