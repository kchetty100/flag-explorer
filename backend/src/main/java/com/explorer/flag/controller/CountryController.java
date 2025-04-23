package com.explorer.flag.controller;

import java.util.List;

import com.explorer.flag.model.Country;
import com.explorer.flag.model.CountryDetails;
import com.explorer.flag.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countries")
public class CountryController {

  private final CountryService service;

  public CountryController(CountryService service) {
    this.service = service;
  }

  @GetMapping
  public List<Country> getAllCountries() {
    return service.getAllCountries();
  }

  @GetMapping("/{name}")
  public ResponseEntity<CountryDetails> getCountryDetails(
      @PathVariable
      String name) {
    return service.getCountryDetails(name)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
