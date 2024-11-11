package com.jagija.smileapp.service;

import com.jagija.smileapp.dto.ReportResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ReportService {
    ReportResponseDTO generateReport(Integer patientId);
}
