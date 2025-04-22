package com.explorer.flag.service;

import com.explorer.flag.model.Country;
import com.explorer.flag.model.CountryDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CountryService {

  private final RestTemplate restTemplate = new RestTemplate();
  //todo : Move this to a properties file and create placeholders
  private static final String API_BASE_URL = "https://restcountries.com/v3.1";

  public List<Country> getAllCountries() {
    Map<String, Object>[] response = restTemplate.getForObject(API_BASE_URL + "/all", Map[].class);

    return Arrays.stream(response)
        .map(country -> {
          Map<String, String> nameMap = (Map<String, String>) country.get("name");
          Map<String, String> flagsMap = (Map<String, String>) country.get("flags");
          return new Country(
              nameMap.get("common"),
              flagsMap.get("png")
          );
        })
        .collect(Collectors.toList());
  }

  public CountryDetails getCountryDetails(String name) {
    try {
      Map<String, Object>[] response = restTemplate.getForObject(API_BASE_URL + "/name/" + name, Map[].class);
      if (response == null || response.length == 0) {
        return null;
      }

      Map<String, Object> countryData = response[0];
      Map<String, String> nameMap = (Map<String, String>) countryData.get("name");
      Map<String, String> flagsMap = (Map<String, String>) countryData.get("flags");
      List<String> capitals = (List<String>) countryData.get("capital");
      Number population = (Number) countryData.get("population");

      return new CountryDetails(
          nameMap.get("common"),
          capitals != null && !capitals.isEmpty() ? capitals.get(0) : "N/A",
          population != null ? population.intValue() : 0,
          flagsMap.get("png")
      );
    } catch (HttpClientErrorException.NotFound e) {
      return null; // Return null if the country is not found
    }
  }
}