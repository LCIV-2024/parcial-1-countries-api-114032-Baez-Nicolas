package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.SaveCountries.SaveCountriesRequestDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICountryService {

    //1) Obtener todos los paises
    public List<Country> getAllCountries();

    //2) Obtener un pais por nombre
    public List<Country> getCountriesByName(String name);

    //3) Obtener un pais por codigo
    public List<Country> getCountriesByCode(String code);

    //4) Obtener todos los paises por continente
    public List<CountryDTO> getCountriesByContinent(String continent);

    //5) Obtener todos los paises por idioma
    public List<CountryDTO> getCountriesByLanguage(String language);

    //6) Obtener el nombre del pais con mas fronteras
    public CountryDTO getCountryWithMostBorders();

    //7) Guardar una cantidad aleatoria de paises
    public List<CountryDTO> saveRandomCountries(SaveCountriesRequestDTO request);
}
