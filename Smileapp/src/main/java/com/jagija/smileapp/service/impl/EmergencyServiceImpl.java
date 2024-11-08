package com.jagija.smileapp.service.impl;

import com.jagija.smileapp.Exceptions.ResourceNotFoundException;
import com.jagija.smileapp.dto.EmergencyRequestDTO;
import com.jagija.smileapp.dto.EmergencyResponseDTO;
import com.jagija.smileapp.mapper.EmergencyMapper;
import com.jagija.smileapp.model.entity.Emergency;
import com.jagija.smileapp.repository.EmergencyRepository;
import com.jagija.smileapp.service.EmergencyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmergencyServiceImpl implements EmergencyService {
    private final EmergencyRepository emergencyRepository;
    private final EmergencyMapper emergencyMapper;


    @Override
    public EmergencyResponseDTO updateEmergencyInfo(Integer id, EmergencyRequestDTO emergencyRequestDTO) {
        Emergency emergency = emergencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Informacion de emergencia no encontrada con el numero de ID"+id));
        if(emergencyRequestDTO.getDir()!= null)emergency.setDir(emergencyRequestDTO.getDir());
        if(emergencyRequestDTO.getParent()!= null)emergency.setParent(emergencyRequestDTO.getParent());
        if(emergencyRequestDTO.getName()!= null)emergency.setName(emergencyRequestDTO.getName());
        if(emergencyRequestDTO.getPhone()!= null)emergency.setPhone(emergencyRequestDTO.getPhone());

        emergencyRepository.save(emergency);

        return emergencyMapper.convertToDTO(emergency);
    }

    @Override
    public EmergencyResponseDTO createEmergencyInfo(EmergencyRequestDTO emergencyRequestDTO) {
        Emergency emergency = emergencyMapper.convertToEntity(emergencyRequestDTO);
        emergencyRepository.save(emergency);
        return emergencyMapper.convertToDTO(emergency);
    }
}
