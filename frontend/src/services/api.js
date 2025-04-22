// src/services/api.js
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080'; // Default Spring Boot port

export const getAllCountries = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/countries`);
    return response.data;
  } catch (error) {
    console.error('Error fetching countries:', error);
    return [];
  }
};

export const getCountryDetails = async (name) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/countries/${name}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching country details:', error);
    return null;
  }
};