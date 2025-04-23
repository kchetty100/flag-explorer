package com.explorer.flag.controller;

import com.explorer.flag.model.Country;
import com.explorer.flag.model.CountryDetails;
import com.explorer.flag.service.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CountryController.class) // Load only the web layer (controller + related MVC config)
public class CountryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CountryService countryService; // Mock the service dependency

  @Test
  void testGetAllCountries_returnsCountriesList() throws Exception {
    // Mock service response
    List<Country> mockCountries = List.of(
        new Country("Testland", "https://flag.png")
    );
    when(countryService.getAllCountries()).thenReturn(mockCountries);

    // Call endpoint and verify response
    mockMvc.perform(get("/countries"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].name").value("Testland"))
        .andExpect(jsonPath("$[0].flag").value("https://flag.png"));
  }

  @Test
  void testGetCountryDetails_whenFound() throws Exception {
    // Mock valid country details
    CountryDetails mockDetails = new CountryDetails("Testland", "Capital City", 123456, "https://flag.png");
    when(countryService.getCountryDetails("Testland")).thenReturn(Optional.of(mockDetails));

    mockMvc.perform(get("/countries/Testland"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name").value("Testland"))
        .andExpect(jsonPath("$.capital").value("Capital City"))
        .andExpect(jsonPath("$.population").value(123456))
        .andExpect(jsonPath("$.flag").value("https://flag.png"));
  }

  @Test
  void testGetCountryDetails_whenNotFound() throws Exception {
    // Simulate country not found
    when(countryService.getCountryDetails("Unknown")).thenReturn(Optional.empty());

    mockMvc.perform(get("/countries/Unknown"))
        .andExpect(status().isNotFound());
  }
}
