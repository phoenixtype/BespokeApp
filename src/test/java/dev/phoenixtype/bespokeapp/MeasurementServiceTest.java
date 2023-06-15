package dev.phoenixtype.bespokeapp;

import dev.phoenixtype.bespokeapp.model.*;
import dev.phoenixtype.bespokeapp.repository.MeasurementRepository;
import dev.phoenixtype.bespokeapp.repository.UserRepository;
import dev.phoenixtype.bespokeapp.service.MeasurementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MeasurementServiceTest {

    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MeasurementService measurementService;

    @Test
    public void testAddMeasurement() {
        // Prepare test data
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setUserId(1L);
        measurementDTO.setApparelType("Shirt");

        MeasurementDetailDTO measurementDetailDTO = new MeasurementDetailDTO();
        measurementDetailDTO.setPartLabel("Chest");
        measurementDetailDTO.setPartValue(BigDecimal.valueOf(36));
        measurementDTO.getMeasurementDetails().add(measurementDetailDTO);

        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Execute the method
        measurementService.addMeasurement(measurementDTO);

        // Verify the save method is called with the correct argument
        ArgumentCaptor<Measurement> measurementCaptor = ArgumentCaptor.forClass(Measurement.class);
        verify(measurementRepository).save(measurementCaptor.capture());

        Measurement savedMeasurement = measurementCaptor.getValue();
        assertEquals("Shirt", savedMeasurement.getApparelType());
        assertEquals(1, savedMeasurement.getMeasurementDetails().size());
        assertEquals("Chest", savedMeasurement.getMeasurementDetails().get(0).getPartLabel());
        assertEquals(BigDecimal.valueOf(36), savedMeasurement.getMeasurementDetails().get(0).getPartValue());
        assertEquals(user, savedMeasurement.getUser());
    }

    @Test
    public void testGetUserMeasurements() {
        // Prepare test data
        User user = new User();
        user.setId(1L);

        Measurement measurement = new Measurement();
        measurement.setId(1L);
        measurement.setApparelType("Shirt");
        measurement.setUser(user);

        MeasurementDetail measurementDetail = new MeasurementDetail();
        measurementDetail.setId(1L);
        measurementDetail.setPartLabel("Chest");
        measurementDetail.setPartValue(BigDecimal.valueOf(36));
        measurementDetail.setMeasurement(measurement);

        measurement.getMeasurementDetails().add(measurementDetail);

        List<Measurement> measurements = List.of(measurement);
        when(measurementRepository.findByUserId(1L)).thenReturn(measurements);

        // Execute the method
        List<MeasurementDTO> measurementDTOs = measurementService.getUserMeasurements(1L);

        // Verify the returned DTOs
        assertEquals(1, measurementDTOs.size());

        MeasurementDTO measurementDTO = measurementDTOs.get(0);
        assertEquals(1L, measurementDTO.getUserId().longValue());
        assertEquals("Shirt", measurementDTO.getApparelType());
        assertEquals(1, measurementDTO.getMeasurementDetails().size());
        assertEquals("Chest", measurementDTO.getMeasurementDetails().get(0).getPartLabel());
        assertEquals(BigDecimal.valueOf(36), measurementDTO.getMeasurementDetails().get(0).getPartValue());
    }

//    @Test
//    public void testUpdateMeasurement() {
//        // Prepare test data
//        MeasurementDTO measurementDTO = new MeasurementDTO();
//        measurementDTO.setApparelType("Shirt");
//
//        MeasurementDetailDTO measurementDetailDTO = new MeasurementDetailDTO();
//        measurementDetailDTO.setPartLabel("Chest");
//        measurementDetailDTO.setPartValue(BigDecimal.valueOf(40));
//        measurementDTO.getMeasurementDetails().add(measurementDetailDTO);
//
//        Measurement existingMeasurement = new Measurement();
//        existingMeasurement.setId(1L);
//        existingMeasurement.setApparelType("Trousers");
//
//        MeasurementDetail existingMeasurementDetail = new MeasurementDetail();
//        existingMeasurementDetail.setId(1L);
//        existingMeasurementDetail.setPartLabel("Waist");
//        existingMeasurementDetail.setPartValue(BigDecimal.valueOf(32));
//        existingMeasurementDetail.setMeasurement(existingMeasurement);
//        existingMeasurement.getMeasurementDetails().add(existingMeasurementDetail);
//
//        Optional<Measurement> optionalMeasurement = Optional.of(existingMeasurement);
//        when(measurementRepository.findById(1L)).thenReturn(optionalMeasurement);
//
//        // Execute the method
//        boolean updated = measurementService.updateMeasurement(1L, measurementDTO);
//
//        // Verify the save method is called with the correct argument
//        ArgumentCaptor<Measurement> measurementCaptor = ArgumentCaptor.forClass(Measurement.class);
//        verify(measurementRepository).save(measurementCaptor.capture());
//
//        Measurement updatedMeasurement = measurementCaptor.getValue();
//        assertEquals("Shirt", updatedMeasurement.getApparelType());
//        assertEquals(1, updatedMeasurement.getMeasurementDetails().size());
//        assertEquals("Chest", updatedMeasurement.getMeasurementDetails().get(0).getPartLabel());
//        assertEquals(BigDecimal.valueOf(40), updatedMeasurement.getMeasurementDetails().get(0).getPartValue());
//        assertEquals(existingMeasurementDetail, updatedMeasurement.getMeasurementDetails().get(0));
//        assertEquals(existingMeasurement, updatedMeasurement);
//
//        assertTrue(updated);
//    }

    @Test
    public void testDeleteMeasurement() {
        // Prepare test data
        Measurement existingMeasurement = new Measurement();
        existingMeasurement.setId(1L);
        existingMeasurement.setApparelType("Shirt");

        Optional<Measurement> optionalMeasurement = Optional.of(existingMeasurement);
        when(measurementRepository.findById(1L)).thenReturn(optionalMeasurement);

        // Execute the method
        boolean deleted = measurementService.deleteMeasurement(1L);

        // Verify the delete method is called with the correct argument
        ArgumentCaptor<Measurement> measurementCaptor = ArgumentCaptor.forClass(Measurement.class);
        verify(measurementRepository).delete(measurementCaptor.capture());

        Measurement deletedMeasurement = measurementCaptor.getValue();
        assertEquals(existingMeasurement, deletedMeasurement);

        assertTrue(deleted);
    }

//    @Test
//    public void testReplaceMeasurement() {
//        // Prepare test data
//        MeasurementDTO measurementDTO = new MeasurementDTO();
//        measurementDTO.setUserId(1L);
//        measurementDTO.setApparelType("Shirt");
//
//        MeasurementDetailDTO measurementDetailDTO = new MeasurementDetailDTO();
//        measurementDetailDTO.setPartLabel("Chest");
//        measurementDetailDTO.setPartValue(BigDecimal.valueOf(36));
//        measurementDTO.getMeasurementDetails().add(measurementDetailDTO);
//
//        User user = new User();
//        user.setId(1L);
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        // Execute the method
//        boolean replaced = measurementService.replaceMeasurement(1L, measurementDTO);
//
//
//        // Verify that deleteMeasurement and addMeasurement methods are called
//        verify(measurementService).deleteMeasurement(1L);
//        verify(measurementService).addMeasurement(measurementDTO);
//
//        assertTrue(replaced);
//    }
}
