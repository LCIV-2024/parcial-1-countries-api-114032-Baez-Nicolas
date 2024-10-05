package ar.edu.utn.frc.tup.lciii.service.Impl;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.SaveCountries.SaveCountriesRequestDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import ar.edu.utn.frc.tup.lciii.service.ICountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService implements ICountryService {

        private final CountryRepository countryRepository;
        private final RestTemplate restTemplate;

        //1) Obtener todos los paises
        @Override
        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                List<Country> countries = response.stream().map(this::mapToCountry).collect(Collectors.toList());
                saveCountries(countries);
                return countries;
        }

        //2) Obtener un pais por nombre
        @Override
        public List<Country> getCountriesByName(String name) {
                return countryRepository.findByNameContainingIgnoreCase(name).stream()
                        .map(this::mapToModel)
                        .collect(Collectors.toList());
        }

        //3) Obtener un pais por codigo
        @Override
        public List<Country> getCountriesByCode(String code) {
                return countryRepository.findByCodeContainingIgnoreCase(code).stream()
                        .map(this::mapToModel)
                        .collect(Collectors.toList());
        }

        //4) Obtener todos los paises por continente
        @Override
        public List<CountryDTO> getCountriesByContinent(String continent) {
                return countryRepository.findByRegionIgnoreCase(continent).stream()
                        .map(country -> new CountryDTO(country.getCode(), country.getName()))
                        .collect(Collectors.toList());
        }

        //5) Obtener todos los paises por idioma
        @Override
        public List<CountryDTO> getCountriesByLanguage(String language) {
                return countryRepository.findByLanguagesContainingIgnoreCase(language).stream()
                        .map(country -> new CountryDTO(country.getCode(), country.getName()))
                        .collect(Collectors.toList());
        }

        //6) Obtener el nombre del pais con mas fronteras
        @Override
        public CountryDTO getCountryWithMostBorders() {
                return countryRepository.findAll().stream()
                        .max(Comparator.comparingInt(country -> country.getBorders().size()))
                        .map(country -> new CountryDTO(country.getCode(), country.getName()))
                        .orElse(null);
        }

        //7) Guardar una cantidad aleatoria de paises
        @Override
        public List<CountryDTO> saveRandomCountries(SaveCountriesRequestDTO request) {
                List<Country> allCountries = getAllCountries();
                Collections.shuffle(allCountries);
                List<Country> selectedCountries = allCountries.stream()
                        .limit(request.getAmountOfCountryToSave())
                        .collect(Collectors.toList());
                saveCountries(selectedCountries);
                return selectedCountries.stream()
                        .map(country -> new CountryDTO(country.getCode(), country.getName()))
                        .collect(Collectors.toList());
        }

        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .code((String) countryData.get("cca3"))
                        .borders((List<String>) countryData.get("borders"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .build();
        }

        private void saveCountries(List<Country> countries) {
                List<CountryEntity> countryEntities = countries.stream().map(this::mapToEntity).collect(Collectors.toList());
                countryRepository.saveAll(countryEntities);
        }

        private CountryEntity mapToEntity(Country country) {
                return CountryEntity.builder()
                        .name(country.getName())
                        .population(country.getPopulation())
                        .area(country.getArea())
                        .region(country.getRegion())
                        .code(country.getCode())
                        .borders(country.getBorders())
                        .languages(country.getLanguages())
                        .build();
        }

        private Country mapToModel(CountryEntity countryEntity) {
                return Country.builder()
                        .name(countryEntity.getName())
                        .population(countryEntity.getPopulation())
                        .area(countryEntity.getArea())
                        .region(countryEntity.getRegion())
                        .code(countryEntity.getCode())
                        .borders(countryEntity.getBorders())
                        .languages(countryEntity.getLanguages())
                        .build();
        }
}