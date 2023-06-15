package dev.phoenixtype.bespokeapp.service;

import dev.phoenixtype.bespokeapp.model.*;
import dev.phoenixtype.bespokeapp.repository.MeasurementRepository;
import dev.phoenixtype.bespokeapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    
    private final MeasurementRepository measurementRepository;
    private final UserRepository userRepository;

    public void addMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = new Measurement();
        User user = userRepository.findById(measurementDTO.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));

        measurement.setUser(user);
        measurement.setApparelType(measurementDTO.getApparelType());

        List<MeasurementDetail> measurementDetails = new ArrayList<>();
        for (MeasurementDetailDTO measurementDetailDTO : measurementDTO.getMeasurementDetails()) {
            MeasurementDetail measurementDetail = new MeasurementDetail();
            measurementDetail.setPartLabel(measurementDetailDTO.getPartLabel());
            measurementDetail.setPartValue(measurementDetailDTO.getPartValue());
            measurementDetail.setMeasurement(measurement);
            measurementDetails.add(measurementDetail);
        }
        measurement.setMeasurementDetails(measurementDetails);
        measurementRepository.save(measurement);
    }

    public List<MeasurementDTO> getUserMeasurements(Long userId) {
        List<Measurement> measurements = measurementRepository.findByUserId(userId);
        List<MeasurementDTO> measurementDTOs = new ArrayList<>();

        for (Measurement measurement : measurements) {
            MeasurementDTO measurementDTO = new MeasurementDTO();
            measurementDTO.setUserId(measurement.getUser().getId());
            measurementDTO.setApparelType(measurement.getApparelType());

            List<MeasurementDetailDTO> measurementDetailDTOs = new ArrayList<>();
            for (MeasurementDetail measurementDetail : measurement.getMeasurementDetails()) {
                MeasurementDetailDTO measurementDetailDTO = new MeasurementDetailDTO();
                measurementDetailDTO.setPartLabel(measurementDetail.getPartLabel());
                measurementDetailDTO.setPartValue(measurementDetail.getPartValue());
                measurementDetailDTOs.add(measurementDetailDTO);
            }

            measurementDTO.setMeasurementDetails(measurementDetailDTOs);
            measurementDTOs.add(measurementDTO);
        }

        return measurementDTOs;
    }


    public boolean updateMeasurement(Long measurementId, MeasurementDTO measurementDTO) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(measurementId);
        if (optionalMeasurement.isPresent()) {
            Measurement measurement = optionalMeasurement.get();
            measurement.setApparelType(measurementDTO.getApparelType());

            // Clear the existing measurementDetails collection
            measurement.getMeasurementDetails().clear();

            for (MeasurementDetailDTO measurementDetailDTO : measurementDTO.getMeasurementDetails()) {
                MeasurementDetail measurementDetail = new MeasurementDetail();
                measurementDetail.setPartLabel(measurementDetailDTO.getPartLabel());
                measurementDetail.setPartValue(measurementDetailDTO.getPartValue());
                measurementDetail.setMeasurement(measurement);
                measurement.getMeasurementDetails().add(measurementDetail);
            }
            measurementRepository.save(measurement);
            return true;
        }
        return false;
    }

    
    public boolean deleteMeasurement(Long measurementId) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(measurementId);
        
        if (optionalMeasurement.isPresent()) {
            Measurement measurement = optionalMeasurement.get();
            measurementRepository.delete(measurement);
            return true;
        }
        return false;
    }
    
    public boolean replaceMeasurement(Long measurementId, MeasurementDTO measurementDTO) {
        boolean deleted = deleteMeasurement(measurementId);
        if (deleted) {
            addMeasurement(measurementDTO);
            return true;
        }
        return false;
    }
}
