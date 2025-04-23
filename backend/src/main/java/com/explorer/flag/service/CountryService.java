package com.explorer.flag.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.explorer.flag.model.Country;
import com.explorer.flag.model.CountryDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CountryService {

  private static final Logger logger = LoggerFactory.getLogger(CountryService.class);

  private final RestTemplate restTemplate;
  private final String apiBaseUrl;

  public CountryService(RestTemplate restTemplate,
                        @Value("${restcountries.api.base-url}")
                        String apiBaseUrl) {
    this.restTemplate = restTemplate;
    this.apiBaseUrl = apiBaseUrl;
  }

  @Cacheable("countries")
  public List<Country> getAllCountries() {
    logger.info("Fetching all countries from external API");
    Map<String, Object>[] response = restTemplate.getForObject(apiBaseUrl + "/all", Map[].class);

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

  @Cacheable(value = "countryDetails", key = "#name")
  public Optional<CountryDetails> getCountryDetails(String name) {
    logger.info("Fetching details for country: {}", name);
    try {
      Map<String, Object>[] response =
          restTemplate.getForObject(apiBaseUrl + "/name/" + name, Map[].class);
      if (response == null || response.length == 0) {
        return Optional.empty();
      }

      Map<String, Object> countryData = response[0];
      Map<String, String> nameMap = (Map<String, String>) countryData.get("name");
      Map<String, String> flagsMap = (Map<String, String>) countryData.get("flags");
      List<String> capitals = (List<String>) countryData.get("capital");
      Number population = (Number) countryData.get("population");

      CountryDetails details = new CountryDetails(
          nameMap.get("common"),
          capitals != null && !capitals.isEmpty() ? capitals.get(0) : "N/A",
          population != null ? population.intValue() : 0,
          flagsMap.get("png")
      );

      return Optional.of(details);

    } catch (HttpClientErrorException.NotFound e) {
      logger.warn("Country not found: {}", name);
      return Optional.empty();
    } catch (Exception e) {
      logger.error("Error fetching country details for: {}", name, e);
      return Optional.empty();
    }
  }
}
