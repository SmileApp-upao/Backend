package com.jagija.smileapp.service.impl;

import com.jagija.smileapp.Exceptions.ResourceNotFoundException;
import com.jagija.smileapp.dto.EmergencyRequestDTO;
import com.jagija.smileapp.dto.EmergencyResponseDTO;
import com.jagija.smileapp.mapper.EmergencyMapper;
import com.jagija.smileapp.model.entity.Emergency;
import com.jagija.smileapp.model.entity.User;
import com.jagija.smileapp.repository.EmergencyRepository;
import com.jagija.smileapp.service.EmergencyService;
import com.jagija.smileapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class EmergencyServiceImpl implements EmergencyService {
    private final EmergencyRepository emergencyRepository;
    private final EmergencyMapper emergencyMapper;

    @Override
    public EmergencyResponseDTO getEmergencyInfo(Integer id) {
        if(emergencyRepository.findByPatient_Id(id)==null)
        {
            throw new ResourceNotFoundException("El usuario no tiene informacion de emergencia asociada");
        }
        return emergencyMapper.convertToDTO(emergencyRepository.findByPatient_Id(id));
    }

    @Override
    public EmergencyResponseDTO updateEmergencyInfo(Integer userId, EmergencyRequestDTO emergencyRequestDTO) {

        Emergency actuallyEmergency = emergencyRepository.findByPatient_Id(userId);
        if(actuallyEmergency==null)
        {
            throw new ResourceNotFoundException("El usuario no tiene informacion de emergencia asociada");
        }
       if(emergencyRequestDTO.getName()!=null)actuallyEmergency.setName(emergencyRequestDTO.getName());
       if(emergencyRequestDTO.getDir()!=null)actuallyEmergency.setDir(emergencyRequestDTO.getDir());
       if(emergencyRequestDTO.getPhone()!=null)actuallyEmergency.setPhone(emergencyRequestDTO.getPhone());
       if(emergencyRequestDTO.getParent()!=null)actuallyEmergency.setParent(emergencyRequestDTO.getParent());
       return emergencyMapper.convertToDTO(emergencyRepository.save(actuallyEmergency));
    }


}
