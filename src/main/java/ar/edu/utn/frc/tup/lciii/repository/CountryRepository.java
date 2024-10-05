package ar.edu.utn.frc.tup.lciii.repository;

import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    List<CountryEntity> findByNameContainingIgnoreCase(String name);
    List<CountryEntity> findByCodeContainingIgnoreCase(String code);
    List<CountryEntity> findByRegionIgnoreCase(String region);
    List<CountryEntity> findByLanguagesContainingIgnoreCase(String language);
}