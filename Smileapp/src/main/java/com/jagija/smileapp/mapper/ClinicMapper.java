package com.jagija.smileapp.mapper;

import com.jagija.smileapp.dto.ClinicRequestDTO;
import com.jagija.smileapp.dto.ClinicResponseDTO;
import com.jagija.smileapp.model.entity.Clinic;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ClinicMapper {

    private final ModelMapper modelMapper;

    public Clinic convertToEntity(ClinicRequestDTO clinicRequestDTO) {
        return modelMapper.map(clinicRequestDTO, Clinic.class);
    }

    public ClinicResponseDTO convertToDTO(Clinic clinic) {
        return modelMapper.map(clinic, ClinicResponseDTO.class);
    }

    public List<ClinicResponseDTO> convertToListDTO(List<Clinic> clinics) {
        return clinics.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
