package ar.edu.utn.frc.tup.lciii.client.DTO;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {
    private String common;
    private String official;
    private List<String> tld;
    private String cca2;
    private String ccn3;
    private String cca3;
    private String cioc;
    private boolean independent;
    private boolean unMember;
    private Map<String, CurrencyDTO> currencies;
    private List<String> capital;
    private String region;
    private String subregion;
    private Map<String, String> languages;
    private Map<String, String> borders;
    private double area;
    private long population;
    private String flag;
    private List<Double> latlng;
    private List<String> timezones;

    public CountryDTO(String code, String name) {
    }


}



