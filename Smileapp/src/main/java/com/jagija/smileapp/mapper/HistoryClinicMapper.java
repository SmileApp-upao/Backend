package com.jagija.smileapp.mapper;

import com.jagija.smileapp.dto.HistoryClinicRequestDTO;
import com.jagija.smileapp.dto.HistoryClinicResponseDTO;
import com.jagija.smileapp.model.entity.HistoryClinic;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class HistoryClinicMapper {

    private final ModelMapper modelMapper;

    public HistoryClinic convertToEntity(HistoryClinicRequestDTO historyClinicRequestDTO) {
        return modelMapper.map(historyClinicRequestDTO, HistoryClinic.class);
    }

    public HistoryClinicResponseDTO convertToDTO(HistoryClinic historyClinic) {
        return modelMapper.map(historyClinic, HistoryClinicResponseDTO.class);
    }

    public List<HistoryClinicResponseDTO> convertToListDTO(List<HistoryClinic> historyClinics) {
        return  historyClinics.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
