package dev.phoenixtype.bespokeapp.controller;


import dev.phoenixtype.bespokeapp.model.MeasurementDTO;
import dev.phoenixtype.bespokeapp.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/measurement")
@RequiredArgsConstructor
public class MeasurementController {

    private final MeasurementService measurementService;

    @PostMapping("/measurements")
    public ResponseEntity<String> addMeasurement(@RequestBody MeasurementDTO measurementDTO) {
        measurementService.addMeasurement(measurementDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Measurement added successfully");
    }

    @GetMapping("/measurements/{userId}")
    public ResponseEntity<List<MeasurementDTO>> getUserMeasurements(@PathVariable Long userId) {
        List<MeasurementDTO> measurements = measurementService.getUserMeasurements(userId);
        if (measurements.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(measurements);
    }


    @PatchMapping("/measurements/{measurementId}")
    public ResponseEntity<String> updateMeasurement(@PathVariable Long measurementId,
                                                    @RequestBody MeasurementDTO measurementDTO) {
        boolean updated = measurementService.updateMeasurement(measurementId, measurementDTO);
        if (updated) {
            return ResponseEntity.ok("Measurement updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Measurement not found");
        }
    }

    @PutMapping("/measurements/replace/{measurementId}")
    public ResponseEntity<String> replaceMeasurement(@PathVariable Long measurementId,
                                                     @RequestBody MeasurementDTO measurementDTO) {
        boolean replaced = measurementService.replaceMeasurement(measurementId, measurementDTO);
        if (replaced) {
            return ResponseEntity.ok("Measurement replaced successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Measurement not found");
        }
    }

    @DeleteMapping("/measurements/{measurementId}")
    public ResponseEntity<String> deleteMeasurement(@PathVariable Long measurementId) {
        boolean deleted = measurementService.deleteMeasurement(measurementId);
        if (deleted) {
            return ResponseEntity.ok("Measurement deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Measurement not found");
        }
    }
}
