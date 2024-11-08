package com.jagija.smileapp.service;

import com.jagija.smileapp.dto.EmergencyRequestDTO;
import com.jagija.smileapp.dto.EmergencyResponseDTO;

public interface EmergencyService {
    public EmergencyResponseDTO updateEmergencyInfo(Integer id, EmergencyRequestDTO emergencyRequestDTO);
    public EmergencyResponseDTO createEmergencyInfo(EmergencyRequestDTO emergencyRequestDTO);
}
