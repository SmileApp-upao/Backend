package com.jagija.smileapp.mapper;

import com.jagija.smileapp.dto.PatientRequestDTO;
import com.jagija.smileapp.dto.PatientResponseDTO;
import com.jagija.smileapp.model.entity.Patient;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PatientMapper {

    private final ModelMapper modelMapper;

    public Patient convertToEntity(PatientRequestDTO patientRequestDTO) {
        return modelMapper.map(patientRequestDTO, Patient.class);
    }

    public PatientResponseDTO convertToDTO(Patient patient) {
        return modelMapper.map(patient, PatientResponseDTO.class);
    }

    public List<PatientResponseDTO> convertToListDTO(List<Patient> patients) {
        return patients.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
