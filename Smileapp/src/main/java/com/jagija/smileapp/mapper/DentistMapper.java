package com.jagija.smileapp.mapper;

import com.jagija.smileapp.dto.DentistRequestDTO;
import com.jagija.smileapp.dto.DentistResponseDTO;
import com.jagija.smileapp.model.entity.Dentist;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DentistMapper {

    private final ModelMapper modelMapper;

    public Dentist convertToEntity(DentistRequestDTO dentistRequestDTO) {
        return modelMapper.map(dentistRequestDTO, Dentist.class);
    }

    public DentistResponseDTO convertToDTO(Dentist dentist) {
        return modelMapper.map(dentist, DentistResponseDTO.class);
    }

    public List<DentistResponseDTO> convertToListDTO(List<Dentist> dentists) {
        return dentists.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
