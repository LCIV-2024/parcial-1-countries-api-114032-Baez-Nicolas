package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.SaveCountries.SaveCountriesRequestDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.ICountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CountryController {

    private final ICountryService countryService;

    //1) Obtener todos los paises o un pais por nombre o codigo
    @GetMapping("/countries")
    public List<Country> getAllCountries(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) String code) {
        if (name != null) {
            return countryService.getCountriesByName(name);
        } else if (code != null) {
            return countryService.getCountriesByCode(code);
        } else {
            return countryService.getAllCountries();
        }
    }

    //2) Obtener todos los paises por continente
    @GetMapping("/countries/{continent}/continent")
    public List<CountryDTO> getCountriesByContinent(@RequestParam(required = true) String continent) {
        return countryService.getCountriesByContinent(continent);
    }

    //3) Obtener todos los paises por idioma
    @GetMapping("/countries/{language}/language")
    public List<CountryDTO> getCountriesByLanguage(@RequestParam(required = true) String language) {
        return countryService.getCountriesByLanguage(language);
    }

    //4) Obtener el nombre del pais con mas fronteras
    @GetMapping("/countries/most-borders")
    public CountryDTO getCountryWithMostBorders() {
        return countryService.getCountryWithMostBorders();
    }

    //5) Guardar una cantidad aleatoria de paises
    @PostMapping("/countries")
    public List<CountryDTO> saveRandomCountries(@RequestBody SaveCountriesRequestDTO request) {
        return countryService.saveRandomCountries(request);
    }
}