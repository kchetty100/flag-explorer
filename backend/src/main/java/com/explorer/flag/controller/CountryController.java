package com.explorer.flag.controller;

import com.explorer.flag.model.Country;
import com.explorer.flag.model.CountryDetails;
import com.explorer.flag.service.CountryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
  public CountryDetails getCountryDetails(@PathVariable String name) {
    return service.getCountryDetails(name);
  }
}
