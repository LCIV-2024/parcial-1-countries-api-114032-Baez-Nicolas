package ar.edu.utn.frc.tup.lciii.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    private String name;
    @Column
    private long population;
    @Column
    private double area;
    @Column
    private String code;
    @Column
    private String region;

    @ElementCollection
    private List<String> borders;

    @ElementCollection
    @MapKeyColumn(name = "language_key")
    @Column(name = "language_value")
    private Map<String, String> languages;
}