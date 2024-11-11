package com.jagija.smileapp.service.impl;

import com.jagija.smileapp.dto.*;
import com.jagija.smileapp.mapper.*;
import com.jagija.smileapp.model.entity.*;
import com.jagija.smileapp.repository.*;
import com.jagija.smileapp.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private HistoryClinicRepository historyClinicRepository;
    @Autowired
    private EmergencyRepository emergencyRepository;
    @Autowired
    private QuoteRepository quoteRepository;

    @Override
    public ReportResponseDTO generateReport(Integer patientId) {
        ReportResponseDTO reportResponseDTO = new ReportResponseDTO();

        // Obtener el paciente y su usuario asociado
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        User user = patient.getUser();

        // Asignar datos del paciente y usuario
        reportResponseDTO.setFullName(patient.getName() + " " + patient.getLastname());
        reportResponseDTO.setGender(patient.getGender());
        reportResponseDTO.setBirthday(patient.getBirthday());
        reportResponseDTO.setAge(calculateAge(patient.getBirthday()));
        reportResponseDTO.setDni(patient.getDni());
        reportResponseDTO.setPhone(patient.getPhone());
        reportResponseDTO.setEmail(user.getEmail());

        // Obtener la historia clínica y asignar datos adicionales
        HistoryClinic historyClinic = historyClinicRepository.findByPatient_Id(patientId);
        if (historyClinic != null) {
            reportResponseDTO.setBloodType(historyClinic.getBloodType());
            reportResponseDTO.setRh(historyClinic.getRh());
            reportResponseDTO.setBirthPlace(historyClinic.getBirthPlace());
            reportResponseDTO.setCivilState(historyClinic.getCivilState());
            reportResponseDTO.setStudyGrade(historyClinic.getStudyGrade());
            reportResponseDTO.setProfession(historyClinic.getProfession());
            reportResponseDTO.setOccupation(historyClinic.getOccupation());
            reportResponseDTO.setWorkCenter(historyClinic.getWorkCenter());
            reportResponseDTO.setWorkDir(historyClinic.getWorkDir());
            reportResponseDTO.setReligion(historyClinic.getReligion());
            reportResponseDTO.setHome(historyClinic.getHome());
            reportResponseDTO.setHistoryClinicId(historyClinic.getId()); // ID de la historia clínica
        }

        // Asignar datos de emergencia
        Emergency emergency = patient.getEmergency();
        if (emergency != null) {
            reportResponseDTO.setEmergencyContactName(emergency.getName());
            reportResponseDTO.setEmergencyContactParent(emergency.getParent());
            reportResponseDTO.setEmergencyContactDir(emergency.getDir());
            reportResponseDTO.setEmergencyContactPhone(emergency.getPhone());
        }

        // Obtener el primer motivo de consulta desde Quote
        List<Quote> quotes = quoteRepository.findByPatient_Id(patientId);
        if (!quotes.isEmpty()) {
            reportResponseDTO.setConsultationReason(quotes.get(0).getReason()); // Primer motivo de consulta
        }

        return reportResponseDTO;
    }

    private Integer calculateAge(LocalDate birthday) {
        return birthday != null ? Period.between(birthday, LocalDate.now()).getYears() : null;
    }
}

