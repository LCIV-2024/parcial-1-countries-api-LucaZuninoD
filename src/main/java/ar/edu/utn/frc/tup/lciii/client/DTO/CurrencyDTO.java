package ar.edu.utn.frc.tup.lciii.client.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrencyDTO {
    private String name;
    private String symbol;
}
