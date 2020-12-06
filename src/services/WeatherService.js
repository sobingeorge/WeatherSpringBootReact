import http from "../http-common";
import authHeader from "./AuthorizationService";

const citySearch = data => {
  
  return http.post("/citySearch",  data, { headers: authHeader.authHeader()});
};

const citySearchHistory = () => {
  return http.get("/citySearchHistory",  { headers: authHeader.authHeader()});
};

const bulkDeleteHistory = () => {
  return http.delete(`/bulkDeleteHistory`,  { headers: authHeader.authHeader()});
};

const deleteHistory = (id) => {
  return http.delete(`/deleteHistory/${id}`, { headers: authHeader.authHeader()});
};

export default {
  citySearchHistory,
  citySearch,
  bulkDeleteHistory,
  deleteHistory
};
