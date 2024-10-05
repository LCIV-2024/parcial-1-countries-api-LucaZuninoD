package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.DTO.ResponseDTO;
import ar.edu.utn.frc.tup.lciii.client.DTO.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.CountryEntity;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import ar.edu.utn.frc.tup.lciii.configs.CountryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {

    @InjectMocks
    private CountryService countryService;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CountryMapper countryMapper;

    private List<CountryDTO> countryDTOList;

    @BeforeEach
    public void setUp() {
        countryDTOList = new ArrayList<>();
        countryDTOList.add(CountryDTO.builder().cca3("ARG").common("Argentina").region("South America").build());
        countryDTOList.add(CountryDTO.builder().cca3("BRA").common("Brazil").region("South America").build());
        countryDTOList.add(CountryDTO.builder().cca3("CHL").common("Chile").region("South America").build());
    }

    @Test
    public void testGetAllCountries() {
        when(restTemplate.getForObject(anyString(), eq(CountryDTO[].class))).thenReturn(countryDTOList.toArray(new CountryDTO[0]));

        List<CountryDTO> result = countryService.getAllCountries();

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(CountryDTO[].class));
    }

    @Test
    public void testGetCountriesByName() {
        when(restTemplate.getForObject(anyString(), eq(CountryDTO[].class))).thenReturn(countryDTOList.toArray(new CountryDTO[0]));

        List<CountryDTO> result = countryService.getCountriesByName("Bra");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Brazil", result.get(0).getCommon());
    }

    @Test
    public void testGetCountriesByCode() {
        when(restTemplate.getForObject(anyString(), eq(CountryDTO[].class))).thenReturn(countryDTOList.toArray(new CountryDTO[0]));

        List<CountryDTO> result = countryService.getCountriesByCode("ARG");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Argentina", result.get(0).getCommon());
    }

    @Test
    public void testGetCountriesByContinent() {
        when(restTemplate.getForObject(anyString(), eq(CountryDTO[].class))).thenReturn(countryDTOList.toArray(new CountryDTO[0]));

        List<CountryDTO> result = countryService.getCountriesByContinent("South America");

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    public void testGetCountriesByLanguage() {
        CountryDTO countryWithLanguage = CountryDTO.builder()
                .cca3("ESP")
                .common("Spain")
                .languages(Map.of("es", "Spanish"))
                .build();
        countryDTOList.add(countryWithLanguage);

        when(restTemplate.getForObject(anyString(), eq(CountryDTO[].class))).thenReturn(countryDTOList.toArray(new CountryDTO[0]));

        List<CountryDTO> result = countryService.getCountriesByLanguage("Spanish");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Spain", result.get(0).getCommon());
    }

    @Test
    public void testGetCountryWithMostBorders() {
        CountryDTO countryWithMostBorders = CountryDTO.builder()
                .cca3("RUS")
                .common("Russia")
                .borders(Map.of("CHN", "China", "MNG", "Mongolia", "PRK", "North Korea"))
                .build();
        countryDTOList.add(countryWithMostBorders);

        when(restTemplate.getForObject(anyString(), eq(CountryDTO[].class))).thenReturn(countryDTOList.toArray(new CountryDTO[0]));

        CountryDTO result = countryService.getCountryWithMostBorders();

        assertNotNull(result);
        assertEquals("Russia", result.getCommon());
    }

    @Test
    public void testSaveRandomCountries() {
        when(restTemplate.getForObject(anyString(), eq(CountryDTO[].class))).thenReturn(countryDTOList.toArray(new CountryDTO[0]));

        CountryEntity entity1 = new CountryEntity("ARG", "Argentina");
        CountryEntity entity2 = new CountryEntity("BRA", "Brazil");
        when(countryMapper.toEntity(any(CountryDTO.class))).thenReturn(entity1).thenReturn(entity2);

        when(countryRepository.saveAll(anyList())).thenReturn(Arrays.asList(entity1, entity2));

        List<ResponseDTO> result = countryService.saveRandomCountries(2);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(countryRepository, times(1)).saveAll(anyList());
    }
}