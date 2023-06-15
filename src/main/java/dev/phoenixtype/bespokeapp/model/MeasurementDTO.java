package dev.phoenixtype.bespokeapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDTO {
    private Long userId;
    private String apparelType;
    private List<MeasurementDetailDTO> measurementDetails;
}
