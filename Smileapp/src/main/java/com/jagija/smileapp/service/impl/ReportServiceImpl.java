package com.jagija.smileapp.service.impl;

import com.jagija.smileapp.dto.*;
import com.jagija.smileapp.mapper.*;
import com.jagija.smileapp.model.entity.*;
import com.jagija.smileapp.repository.*;
import com.jagija.smileapp.service.ReportService;
import com.jagija.smileapp.service.UserService;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    public ReportResponseDTO generateReport(Integer userId, Integer dentistId, Integer quoteId) {
        ReportResponseDTO reportResponseDTO = new ReportResponseDTO();

        // Obtener el paciente usando el user_id
        User userPatient = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        // Obtener el paciente real (de tipo Patient) desde el user asociado
        Patient patient = userPatient.getPatient();  // Aquí asumo que tu User tiene un método getPatient()

        // Obtener el dentista
        User userDentist = userRepository.findById(dentistId)
                .orElseThrow(() -> new RuntimeException("Dentista no encontrado"));

        // Obtener la cita asociada al paciente usando el user_id

        Quote quote = quoteRepository.findById(quoteId).orElseThrow(() -> new RuntimeException("Quota no encontrado"));
        if(quote==null)
        {
            System.out.println("CITA NO ENCONTRADA");
        }
        // Asignar datos del paciente y usuario
        reportResponseDTO.setName(patient.getName());
        reportResponseDTO.setLastname(patient.getLastname());
        reportResponseDTO.setFullName(patient.getName() + " " + patient.getLastname());
        reportResponseDTO.setGender(patient.getGender());
        reportResponseDTO.setBirthday(patient.getBirthday());
        reportResponseDTO.setAge(calculateAge(patient.getBirthday()));
        reportResponseDTO.setDni(patient.getDni());
        reportResponseDTO.setPhone(patient.getPhone());
        reportResponseDTO.setEmail(patient.getUser().getEmail());
        reportResponseDTO.setDentistFullName(userDentist.getDentist().getName() + " " + userDentist.getDentist().getLastname());

        // Obtener la historia clínica y asignar datos adicionales
        HistoryClinic historyClinic = historyClinicRepository.findByPatient_Id(patient.getId());
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
            reportResponseDTO.setHistoryClinicId(historyClinic.getId());
            reportResponseDTO.setRaze(historyClinic.getRaze());
            reportResponseDTO.setDir(historyClinic.getDir());
            reportResponseDTO.setResidentime(historyClinic.getResidentime());
        }

        // Asignar datos de emergencia
        Emergency emergency = patient.getEmergency();
        if (emergency != null) {
            reportResponseDTO.setEmergencyContactName(emergency.getName());
            reportResponseDTO.setEmergencyContactParent(emergency.getParent());
            reportResponseDTO.setEmergencyContactDir(emergency.getDir());
            reportResponseDTO.setEmergencyContactPhone(emergency.getPhone());
        }

        if(quote!=null)
        { reportResponseDTO.setConsultationReason(quote.getReason()); // Primer motivo de consulta
        }
        // Obtener el primer motivo de consulta desde Quote

        return reportResponseDTO;
    }

    private Integer calculateAge(LocalDate birthday) {
        return birthday != null ? Period.between(birthday, LocalDate.now()).getYears() : null;
    }
}

