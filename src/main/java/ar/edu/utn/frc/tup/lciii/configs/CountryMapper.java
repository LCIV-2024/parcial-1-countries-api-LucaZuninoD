package ar.edu.utn.frc.tup.lciii.configs;
import ar.edu.utn.frc.tup.lciii.model.CountryEntity;
import ar.edu.utn.frc.tup.lciii.client.DTO.CountryDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountryMapper {

    public CountryEntity toEntity(CountryDTO dto) {
        return CountryEntity.builder()
                .cioc(dto.getCioc())
                .common(dto.getCommon())
                .population(dto.getPopulation())
                .area(dto.getArea())
                .build();
    }

    public CountryDTO toDTO(CountryEntity entity) {
        return CountryDTO.builder()
                .cioc(entity.getCioc())
                .common(entity.getCommon())
                .population(entity.getPopulation())
                .area(entity.getArea())
                .build();
    }

    public List<CountryDTO> toDTOList(List<CountryEntity> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }
}