package com.jagija.smileapp.mapper;

import com.jagija.smileapp.dto.EmergencyRequestDTO;
import com.jagija.smileapp.dto.EmergencyResponseDTO;
import com.jagija.smileapp.model.entity.Emergency;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class EmergencyMapper {

    private final ModelMapper modelMapper;

    public Emergency convertToEntity(EmergencyRequestDTO emergencyRequestDTO) {
        return modelMapper.map(emergencyRequestDTO, Emergency.class);
    }

    public EmergencyResponseDTO convertToDTO(Emergency emergency) {
        return modelMapper.map(emergency, EmergencyResponseDTO.class);
    }

    public List<EmergencyResponseDTO> convertToListDTO(List<Emergency> emergencyList) {
        return emergencyList.stream()
                .map(this::convertToDTO)
                .toList();
    }

}
