package ar.edu.utn.frc.tup.lciii.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "countries")
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cca2;
    private String cca3;
    private String ccn3;
    private String cioc;
    private String common;
    private String official;
    private String region;
    private String subregion;
    private boolean independent;
    private boolean unMember;
    private Long population;
    private Double area;

    @ElementCollection
    @CollectionTable(name = "country_languages", joinColumns = @JoinColumn(name = "country_id"))
    @MapKeyColumn(name = "language_code")
    @Column(name = "language_name")
    private Map<String, String> languages;

    public CountryEntity(String code, String name) {
    }
}
