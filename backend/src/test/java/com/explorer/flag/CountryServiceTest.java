package com.explorer.flag;

import com.explorer.flag.model.Country;
import com.explorer.flag.model.CountryDetails;
import com.explorer.flag.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CountryServiceTest {

  private RestTemplate restTemplate;
  private CountryService countryService;

  private final String BASE_URL = "https://restcountries.com/v3.1";

  @BeforeEach
  void setUp() {
    restTemplate = mock(RestTemplate.class);
    countryService = new CountryService(restTemplate, BASE_URL);
  }

  @Test
  void testGetAllCountries() {
    Map<String, Object> countryMap = new HashMap<>();
    countryMap.put("name", Map.of("common", "Testland"));
    countryMap.put("flags", Map.of("png", "https://flag.png"));

    when(restTemplate.getForObject(BASE_URL + "/all", Map[].class))
        .thenReturn(new Map[]{ countryMap });

    List<Country> result = countryService.getAllCountries();

    assertEquals(1, result.size());
    assertEquals("Testland", result.get(0).getName());
    assertEquals("https://flag.png", result.get(0).getFlag());
  }

  @Test
  void testGetCountryDetails() {
    Map<String, Object> countryMap = new HashMap<>();
    countryMap.put("name", Map.of("common", "Testland"));
    countryMap.put("flags", Map.of("png", "https://flag.png"));
    countryMap.put("capital", List.of("Test City"));
    countryMap.put("population", 123456);

    when(restTemplate.getForObject(BASE_URL + "/name/Testland", Map[].class))
        .thenReturn(new Map[]{ countryMap });

    Optional<CountryDetails> result = countryService.getCountryDetails("Testland");

    assertTrue(result.isPresent());
    CountryDetails details = result.get();
    assertEquals("Testland", details.getName());
    assertEquals("Test City", details.getCapital());
    assertEquals(123456, details.getPopulation());
    assertEquals("https://flag.png", details.getFlag());
  }

  @Test
  void testGetCountryDetails_notFound() {
    when(restTemplate.getForObject(BASE_URL + "/name/Unknown", Map[].class))
        .thenReturn(new Map[0]);

    Optional<CountryDetails> result = countryService.getCountryDetails("Unknown");
    assertFalse(result.isPresent());
  }
}
