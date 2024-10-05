package ar.edu.utn.frc.tup.lciii.service.Impl;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CountryServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //1) Test de todas las countries
    @Test
    void testGetAllCountries() {
        List<Map<String, Object>> mockApiResponse = List.of(
                Map.of("name", Map.of("common", "Country1"), "population", 1000, "area", 500.0, "region", "Region1", "cca3", "C1", "borders", List.of(), "languages", Map.of()),
                Map.of("name", Map.of("common", "Country2"), "population", 2000, "area", 1000.0, "region", "Region2", "cca3", "C2", "borders", List.of(), "languages", Map.of())
        );

        when(restTemplate.getForObject(any(String.class), eq(List.class))).thenReturn(mockApiResponse);

        List<Country> countries = countryService.getAllCountries();

        assertEquals(2, countries.size());
        assertEquals("Country1", countries.get(0).getName());
        assertEquals("Country2", countries.get(1).getName());

        verify(countryRepository, times(1)).saveAll(anyList());
    }

    //2) Test de encontrar countries por nombre
    @Test
    void testGetCountriesByName() {
        List<CountryEntity> mockCountryEntities = List.of(
                CountryEntity.builder().name("Country1").build(),
                CountryEntity.builder().name("Country2").build()
        );

        when(countryRepository.findByNameContainingIgnoreCase("Country")).thenReturn(mockCountryEntities);

        List<Country> countries = countryService.getCountriesByName("Country");

        assertEquals(2, countries.size());
        assertEquals("Country1", countries.get(0).getName());
        assertEquals("Country2", countries.get(1).getName());

        verify(countryRepository, times(1)).findByNameContainingIgnoreCase("Country");
    }

    //3) Test de encontrar countries por codigo
    @Test
    void testGetCountriesByCode() {
        List<CountryEntity> mockCountryEntities = List.of(
                CountryEntity.builder().code("C1").build(),
                CountryEntity.builder().code("C2").build()
        );

        when(countryRepository.findByCodeContainingIgnoreCase("C")).thenReturn(mockCountryEntities);

        List<Country> countries = countryService.getCountriesByCode("C");

        assertEquals(2, countries.size());
        assertEquals("C1", countries.get(0).getCode());
        assertEquals("C2", countries.get(1).getCode());

        verify(countryRepository, times(1)).findByCodeContainingIgnoreCase("C");
    }

    //4) Test de obtener todos los paises por continente
    @Test
    void testGetCountriesByContinent() {
        List<CountryEntity> mockCountryEntities = List.of(
                CountryEntity.builder().code("C1").name("Country1").region("Continent1").build(),
                CountryEntity.builder().code("C2").name("Country2").region("Continent1").build()
        );

        when(countryRepository.findByRegionIgnoreCase("Continent1")).thenReturn(mockCountryEntities);

        List<CountryDTO> countries = countryService.getCountriesByContinent("Continent1");

        assertEquals(2, countries.size());
        assertEquals("C1", countries.get(0).getCode());
        assertEquals("Country1", countries.get(0).getName());
        assertEquals("C2", countries.get(1).getCode());
        assertEquals("Country2", countries.get(1).getName());

        verify(countryRepository, times(1)).findByRegionIgnoreCase("Continent1");
    }
}