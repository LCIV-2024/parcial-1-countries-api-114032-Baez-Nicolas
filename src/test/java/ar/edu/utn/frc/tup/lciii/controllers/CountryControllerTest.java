package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.ICountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CountryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ICountryService countryService;

    @InjectMocks
    private CountryController countryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    //2) Test de obtener todos los paises por continente
    @Test
    void testGetCountriesByContinent() throws Exception {
        List<CountryDTO> mockCountries = List.of(
                new CountryDTO("C1", "Country1"),
                new CountryDTO("C2", "Country2")
        );

        when(countryService.getCountriesByContinent("Continent1")).thenReturn(mockCountries);

        mockMvc.perform(get("/api/countries/Continent1/continent")
                .param("continent", "Continent1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].code").value("C1"))
                .andExpect(jsonPath("$[0].name").value("Country1"))
                .andExpect(jsonPath("$[1].code").value("C2"))
                .andExpect(jsonPath("$[1].name").value("Country2"));

        verify(countryService, times(1)).getCountriesByContinent("Continent1");
    }

    //3) Test de obtener todos los paises por idioma
    @Test
    void testGetCountriesByLanguage() throws Exception {
        List<CountryDTO> mockCountries = List.of(
                new CountryDTO("C1", "Country1"),
                new CountryDTO("C2", "Country2")
        );

        when(countryService.getCountriesByLanguage("Language1")).thenReturn(mockCountries);

        mockMvc.perform(get("/api/countries/Language1/language")
                        .param("language", "Language1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].code").value("C1"))
                .andExpect(jsonPath("$[0].name").value("Country1"))
                .andExpect(jsonPath("$[1].code").value("C2"))
                .andExpect(jsonPath("$[1].name").value("Country2"));

        verify(countryService, times(1)).getCountriesByLanguage("Language1");
    }
}