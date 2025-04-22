package com.explorer.flag;






@WebMvcTest(CountryController.class)
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @Test
    void getAllCountries_ShouldReturn200() throws Exception {
        List<Country> mockCountries = List.of(
            new Country("Canada", "https://flagcdn.com/ca.svg"),
            new Country("Brazil", "https://flagcdn.com/br.svg")
        );

        when(countryService.getAllCountries()).thenReturn(mockCountries);

        mockMvc.perform(get("/countries"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0].name").value("Canada"));
    }

    @Test
    void getCountryDetails_ValidName_ShouldReturn200() throws Exception {
        CountryDetails details = new CountryDetails(
            "Canada", "Ottawa", 38000000, "https://flagcdn.com/ca.svg");

        when(countryService.getCountryDetails("Canada")).thenReturn(details);

        mockMvc.perform(get("/countries/Canada"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.capital").value("Ottawa"));
    }

    @Test
    void getCountryDetails_InvalidName_ShouldReturn404() throws Exception {
        when(countryService.getCountryDetails("Invalid")).thenReturn(null);

        mockMvc.perform(get("/countries/Invalid"))
               .andExpect(status().isNotFound());
    }
}