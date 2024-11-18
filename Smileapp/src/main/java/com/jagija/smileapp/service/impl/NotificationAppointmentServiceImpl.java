package com.jagija.smileapp.service.impl;

import com.jagija.smileapp.Exceptions.ResourceNotFoundException;
import com.jagija.smileapp.Integration.notification.email.dto.Mail;
import com.jagija.smileapp.Integration.notification.email.service.EmailService;
import com.jagija.smileapp.dto.ClinicResponseDTO;
import com.jagija.smileapp.dto.QuoteResponseDTO;
import com.jagija.smileapp.mapper.ClinicMapper;
import com.jagija.smileapp.mapper.QuoteMapper;
import com.jagija.smileapp.model.entity.Clinic;
import com.jagija.smileapp.model.entity.Dentist;
import com.jagija.smileapp.model.entity.Quote;
import com.jagija.smileapp.model.entity.User;
import com.jagija.smileapp.repository.ClinicRepository;
import com.jagija.smileapp.repository.DentistRepository;
import com.jagija.smileapp.repository.QuoteRepository;
import com.jagija.smileapp.repository.UserRepository;
import com.jagija.smileapp.service.NotificationAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private final DentistRepository dentistRepository;

    @Transactional
    @Override
    public void createAndSendNotification(String email) throws Exception{
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el email: " + email));

        Quote quote = quoteRepository.findFirstByPatient_IdOrderByDateDesc(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontraron citas para el usuario: " + email));

        Integer dentistId = quote.getDentist().getId();

        Dentist dentist = dentistRepository.getReferenceById(dentistId);
        String emailDentist = dentist.getUser().getEmail();
        Clinic clinic = clinicRepository.findByDentistas_Id(dentistId);

        QuoteResponseDTO quoteResponseDTO = quoteMapper.convertToDTO(quote);
        ClinicResponseDTO clinicResponseDTO = clinicMapper.convertToDTO(clinic);

        Map<String, Object> model = buildEmailModel(quoteResponseDTO, clinicResponseDTO);

        model.put("isDentist", false);
        Mail patientMail = emailService.createMail(
                user.getEmail(),
                "Notificación de Cita Médica",
                model,
                "govench6@gmail.com"
        );
        emailService.sendEmail(patientMail, "email/NotificationAppointment");

        model.put("isDentist", true);
        Mail dentistMail = emailService.createMail(
                emailDentist,
                "Notificación de Cita Médica",
                model,
                "govench6@gmail.com"
        );
        emailService.sendEmail(dentistMail, "email/NotificationAppointment");
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

    @Scheduled(cron = "0 0 6 * * ?") // Ejecuta todos los días a las 6 AM
    @Transactional
    public void sendDailyReminder() throws Exception {
        LocalDate reminderDay = LocalDate.now().plusDays(1); // El día siguiente es el día del recordatorio

        List<Quote> quotes = quoteRepository.findByDate(reminderDay); // Busca citas para el día siguiente

        for (Quote quote : quotes) {
            sendReminder(quote);
        }
    }

    @Transactional
    public void sendReminder(Quote quote) throws Exception {
        String userEmail = quote.getPatient().getEmail();
        Integer dentistId = quote.getDentist().getId();

        Clinic clinic = clinicRepository.findByDentistas_Id(dentistId);
        QuoteResponseDTO quoteResponseDTO = quoteMapper.convertToDTO(quote);
        ClinicResponseDTO clinicResponseDTO = clinicMapper.convertToDTO(clinic);

        Map<String, Object> model = buildEmailModel(quoteResponseDTO, clinicResponseDTO);

        Mail mail = emailService.createMail(
                userEmail,
                "⏰ Recordatorio de Cita Médica",
                model,
                "govench6@gmail.com"
        );

        emailService.sendEmail(mail, "email/ReminderAppointment");
    }

}
