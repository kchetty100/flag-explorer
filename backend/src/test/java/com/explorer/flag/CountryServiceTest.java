package com.explorer.flag;


@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private CountryService countryService;

  @Test
  void getAllCountries_ShouldReturnFormattedList() {
    // Mock API response
    Object[] apiResponse = new Object[]{
        Map.of(
            "name", Map.of("common", "Canada"),
            "flags", Map.of("png", "https://flagcdn.com/ca.svg")
        )
    };

    when(restTemplate.getForObject(anyString(), eq(Object[].class)))
        .thenReturn(apiResponse);

    List<Country> result = countryService.getAllCountries();

    assertEquals(1, result.size());
    assertEquals("Canada", result.get(0).getName());
  }

  @Test
  void getCountryDetails_ShouldHandleMissingCapital() {
    Object[] apiResponse = new Object[]{
        Map.of(
            "name", Map.of("common", "Nauru"),
            "flags", Map.of("png", "https://flagcdn.com/nr.svg"),
            "population", 10800
            // No capital field
        )
    };

    when(restTemplate.getForObject(anyString(), eq(Object[].class)))
        .thenReturn(apiResponse);

    CountryDetails result = countryService.getCountryDetails("Nauru");

    assertEquals("N/A", result.getCapital());
  }
}