package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.DTO.ResponseDTO;
import ar.edu.utn.frc.tup.lciii.client.DTO.CountryDTO;
import ar.edu.utn.frc.tup.lciii.configs.CountryMapper;
import ar.edu.utn.frc.tup.lciii.model.CountryEntity;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CountryMapper countryMapper;

    private static final String COUNTRIES_API_URL = "https://restcountries.com/v3.1/all";

    public List<CountryDTO> getAllCountries() {
        return fetchCountriesFromExternalAPI();
    }

    public List<CountryDTO> getCountriesByName(String name) {
        return getAllCountries().stream()
                .filter(c -> c.getCommon().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<CountryDTO> getCountriesByCode(String code) {
        return getAllCountries().stream()
                .filter(c -> c.getCioc().toLowerCase().contains(code.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<CountryDTO> getCountriesByContinent(String continent) {
        return getAllCountries().stream()
                .filter(c -> c.getRegion().equalsIgnoreCase(continent))
                .collect(Collectors.toList());
    }

    public List<CountryDTO> getCountriesByLanguage(String language) {
        return getAllCountries().stream()
                .filter(c -> c.getLanguages() != null && c.getLanguages().containsValue(language))
                .collect(Collectors.toList());
    }

    public CountryDTO getCountryWithMostBorders() {
        return getAllCountries().stream()
                .max(Comparator.comparingInt(c -> c.getBorders() != null ? c.getBorders().size() : 0))
                .orElse(null);
    }

    public List<ResponseDTO> saveRandomCountries(int amount) {
        List<CountryDTO> allCountries = getAllCountries();
        Collections.shuffle(allCountries);
        List<CountryDTO> randomCountries = allCountries.stream()
                .limit(Math.min(amount, 10))
                .collect(Collectors.toList());
        List<CountryEntity> savedEntities = countryRepository.saveAll(
                randomCountries.stream().map(countryMapper::toEntity).collect(Collectors.toList())
        );
        return savedEntities.stream()
                .map(entity -> new ResponseDTO(entity.getCca3(), entity.getCommon()))
                .collect(Collectors.toList());
    }

    private List<CountryDTO> fetchCountriesFromExternalAPI() {
        List<Map<String, Object>> response = restTemplate.getForObject(COUNTRIES_API_URL, List.class);
        return response.stream().map(this::mapToCountry).collect(Collectors.toList());
    }

    private CountryDTO mapToCountry(Map<String, Object> countryData) {
        Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
        return CountryDTO.builder()
                .common((String) nameData.get("common"))
                .ccn3((String) countryData.get("ccn3"))
                .languages((Map<String, String>) countryData.get("languages"))
                .borders((Map<String, String>) countryData.get("borders"))
                .area(((Number) countryData.get("area")).doubleValue())
                .population(((Number) countryData.get("population")).longValue())
                .build();
    }
}