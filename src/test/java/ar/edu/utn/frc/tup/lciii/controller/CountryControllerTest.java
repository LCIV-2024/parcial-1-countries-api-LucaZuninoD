package ar.edu.utn.frc.tup.lciii.controller;

import ar.edu.utn.frc.tup.lciii.DTO.ResponseDTO;
import ar.edu.utn.frc.tup.lciii.client.DTO.CountryDTO;
import ar.edu.utn.frc.tup.lciii.controllers.CountryController;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CountryControllerTest {

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCountries_ShouldReturnListOfCountries() {
        List<CountryDTO> expectedCountries = Arrays.asList(new CountryDTO(), new CountryDTO());
        when(countryService.getAllCountries()).thenReturn(expectedCountries);

        ResponseEntity<List<CountryDTO>> response = countryController.getAllCountries();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCountries, response.getBody());
    }

    @Test
    void getFilteredCountriesByName_WithName_ShouldReturnFilteredList() {
        String name = "United";
        List<CountryDTO> expectedCountries = Arrays.asList(new CountryDTO(), new CountryDTO());
        when(countryService.getCountriesByName(name)).thenReturn(expectedCountries);

        ResponseEntity<List<CountryDTO>> response = countryController.getFilteredCountriesByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCountries, response.getBody());
    }

    @Test
    void getFilteredCountriesByName_WithoutName_ShouldReturnAllCountries() {
        List<CountryDTO> expectedCountries = Arrays.asList(new CountryDTO(), new CountryDTO());
        when(countryService.getAllCountries()).thenReturn(expectedCountries);

        ResponseEntity<List<CountryDTO>> response = countryController.getFilteredCountriesByName(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCountries, response.getBody());
    }

    @Test
    void getFilteredCountriesByCode_WithCode_ShouldReturnFilteredList() {
        String code = "US";
        List<CountryDTO> expectedCountries = Arrays.asList(new CountryDTO());
        when(countryService.getCountriesByCode(code)).thenReturn(expectedCountries);

        ResponseEntity<List<CountryDTO>> response = countryController.getFilteredCountriesByCode(code);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCountries, response.getBody());
    }

    @Test
    void getCountriesByContinent_ShouldReturnFilteredList() {
        String continent = "Europe";
        List<CountryDTO> expectedCountries = Arrays.asList(new CountryDTO(), new CountryDTO());
        when(countryService.getCountriesByContinent(continent)).thenReturn(expectedCountries);

        ResponseEntity<List<CountryDTO>> response = countryController.getCountriesByContinent(continent);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCountries, response.getBody());
    }

    @Test
    void getCountriesByLanguage_ShouldReturnFilteredList() {
        String language = "English";
        List<CountryDTO> expectedCountries = Arrays.asList(new CountryDTO(), new CountryDTO());
        when(countryService.getCountriesByLanguage(language)).thenReturn(expectedCountries);

        ResponseEntity<List<CountryDTO>> response = countryController.getCountriesByLanguage(language);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCountries, response.getBody());
    }

    @Test
    void getCountryWithMostBorders_ShouldReturnCountry() {
        CountryDTO expectedCountry = new CountryDTO();
        when(countryService.getCountryWithMostBorders()).thenReturn(expectedCountry);

        ResponseEntity<CountryDTO> response = countryController.getCountryWithMostBorders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCountry, response.getBody());
    }

    @Test
    void getCountryWithMostBorders_WhenNoCountryFound_ShouldReturnNotFound() {
        when(countryService.getCountryWithMostBorders()).thenReturn(null);

        ResponseEntity<CountryDTO> response = countryController.getCountryWithMostBorders();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void saveRandomCountries_WithValidAmount_ShouldReturnSavedCountries() {
        int amount = 5;
        List<ResponseDTO> expectedResponses = Arrays.asList(new ResponseDTO("US", "United States"), new ResponseDTO("CA", "Canada"));
        when(countryService.saveRandomCountries(amount)).thenReturn(expectedResponses);

        CountryController.SaveCountriesRequest request = new CountryController.SaveCountriesRequest();
        request.setAmountOfCountryToSave(amount);

        ResponseEntity<List<ResponseDTO>> response = countryController.saveRandomCountries(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponses, response.getBody());
    }

    @Test
    void saveRandomCountries_WithInvalidAmount_ShouldReturnBadRequest() {
        CountryController.SaveCountriesRequest request = new CountryController.SaveCountriesRequest();
        request.setAmountOfCountryToSave(0);

        ResponseEntity<List<ResponseDTO>> response = countryController.saveRandomCountries(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}