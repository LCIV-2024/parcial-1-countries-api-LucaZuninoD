package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.DTO.ResponseDTO;
import ar.edu.utn.frc.tup.lciii.client.DTO.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/countries")
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @GetMapping("/countries/")
    public ResponseEntity<List<CountryDTO>> getFilteredCountriesByName(
            @RequestParam(required = false) String name) {
        if (name != null) {
            return ResponseEntity.ok(countryService.getCountriesByName(name));
        }
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @GetMapping("/countries/{code}")
    public ResponseEntity<List<CountryDTO>> getFilteredCountriesByCode(
            @PathVariable String code) {
        return ResponseEntity.ok(countryService.getCountriesByCode(code));
    }

    @GetMapping("/countries/{continent}/continent")
    public ResponseEntity<List<CountryDTO>> getCountriesByContinent(@PathVariable String continent) {
        return ResponseEntity.ok(countryService.getCountriesByContinent(continent));
    }

    @GetMapping("/countries/{language}/language")
    public ResponseEntity<List<CountryDTO>> getCountriesByLanguage(@PathVariable String language) {
        return ResponseEntity.ok(countryService.getCountriesByLanguage(language));
    }

    @GetMapping("/countries/most-borders")
    public ResponseEntity<CountryDTO> getCountryWithMostBorders() {
        CountryDTO country = countryService.getCountryWithMostBorders();
        return country != null ? ResponseEntity.ok(country) : ResponseEntity.notFound().build();
    }

    @PostMapping("/countries")
    public ResponseEntity<List<ResponseDTO>> saveRandomCountries(@RequestBody SaveCountriesRequest request) {
        if (request.getAmountOfCountryToSave() <= 0 || request.getAmountOfCountryToSave() > 10) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(countryService.saveRandomCountries(request.getAmountOfCountryToSave()));
    }

    public static class SaveCountriesRequest {
        private int amountOfCountryToSave;

        public int getAmountOfCountryToSave() {
            return amountOfCountryToSave;
        }

        public void setAmountOfCountryToSave(int amountOfCountryToSave) {
            this.amountOfCountryToSave = amountOfCountryToSave;
        }
    }
}