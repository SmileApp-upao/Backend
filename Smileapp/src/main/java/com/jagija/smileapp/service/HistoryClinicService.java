package com.jagija.smileapp.service;

import com.jagija.smileapp.dto.HistoryClinicRequestDTO;
import com.jagija.smileapp.dto.HistoryClinicResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface HistoryClinicService {
    HistoryClinicResponseDTO getHistoryClinic();
    HistoryClinicResponseDTO addHistoryClinic(HistoryClinicRequestDTO historyClinicRequestDTO);
    HistoryClinicResponseDTO updateHistoryClinic(HistoryClinicRequestDTO historyClinicRequestDTO);
}
