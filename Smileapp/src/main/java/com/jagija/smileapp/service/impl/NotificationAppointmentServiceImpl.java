package com.jagija.smileapp.service.impl;

import com.jagija.smileapp.Exceptions.ResourceNotFoundException;
import com.jagija.smileapp.Integration.notification.email.dto.Mail;
import com.jagija.smileapp.Integration.notification.email.service.EmailService;
import com.jagija.smileapp.dto.ClinicResponseDTO;
import com.jagija.smileapp.dto.QuoteResponseDTO;
import com.jagija.smileapp.mapper.ClinicMapper;
import com.jagija.smileapp.mapper.QuoteMapper;
import com.jagija.smileapp.model.entity.Clinic;
import com.jagija.smileapp.model.entity.Quote;
import com.jagija.smileapp.model.entity.User;
import com.jagija.smileapp.repository.ClinicRepository;
import com.jagija.smileapp.repository.QuoteRepository;
import com.jagija.smileapp.repository.UserRepository;
import com.jagija.smileapp.service.NotificationAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationAppointmentServiceImpl implements NotificationAppointmentService {

    private final UserRepository userRepository;
    private final QuoteRepository quoteRepository;
    private final EmailService emailService;
    private final QuoteMapper quoteMapper;
    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;

    @Transactional
    @Override
    public void createAndSendNotification(String email) throws Exception{
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el email: " + email));

        // Recuperar la cita más reciente del usuario
        Quote quote = quoteRepository.findFirstByPatient_IdOrderByDateDesc(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontraron citas para el usuario: " + email));

        Clinic clinic = clinicRepository.findByDentistas_Id(quote.getDentist().getId());

        QuoteResponseDTO quoteResponseDTO = quoteMapper.convertToDTO(quote);
        ClinicResponseDTO clinicResponseDTO = clinicMapper.convertToDTO(clinic);

        Map<String, Object> model = buildEmailModel(quoteResponseDTO, clinicResponseDTO);

        Mail mail = emailService.createMail(
                user.getEmail(), // Destinatario
                "Notificación de Cita Médica", // Asunto
                model, // Modelo de datos
                "govench6@gmail.com" // Remitente
        );
        emailService.sendEmail(mail, "email/NotificationAppointment");
    }

    private Map<String, Object> buildEmailModel(QuoteResponseDTO quoteResponseDTO, ClinicResponseDTO clinicResponseDTO) {
        Map<String, Object> model = new HashMap<>();
        model.put("quoteId", quoteResponseDTO.getId());
        model.put("patientName", quoteResponseDTO.getPatientName());
        model.put("date", quoteResponseDTO.getDate());
        model.put("hour", quoteResponseDTO.getHour());
        model.put("clinicName", quoteResponseDTO.getClinicname());
        model.put("clinicDirection", quoteResponseDTO.getClinicdirection());
        model.put("reason", quoteResponseDTO.getReason());
        model.put("dentistName", quoteResponseDTO.getDentistName());
        model.put("dentistLastName", quoteResponseDTO.getDentistLastName());
        model.put("clinicPhone", clinicResponseDTO.getTelf());
        model.put("clinicEmail", clinicResponseDTO.getEmail());
        return model;
    }
}
