package dev.phoenixtype.bespokeapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDetailDTO {
    private String partLabel;
    private BigDecimal partValue;
    
    // Constructors, getters, and setters
}
