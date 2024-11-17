package com.jagija.smileapp.service.impl;
import com.jagija.smileapp.Exceptions.ResourceNotFoundException;
import com.jagija.smileapp.Exceptions.UserNotFoundException;
import com.jagija.smileapp.dto.QuoteRequestDTO;
import com.jagija.smileapp.dto.QuoteResponseDTO;
import com.jagija.smileapp.mapper.QuoteMapper;
import com.jagija.smileapp.model.entity.*;
import com.jagija.smileapp.repository.ClinicRepository;
import com.jagija.smileapp.repository.QuoteImageRepository;
import com.jagija.smileapp.repository.QuoteRepository;
import com.jagija.smileapp.service.QuoteService;
import com.jagija.smileapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {
    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;
    private final ClinicRepository clinicRepository;
    private final UserService userService;
    private final IUploadFileServiceImpl uploadFileService;
    private final QuoteImageRepository quoteImageRepository;

    @Override
    public List<QuoteResponseDTO> getQuotesOfUser(Integer userId) {
        User user = userService.getUserbyId(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        if(user.getRole().getName().equals("DENTIST"))
        {
            return quoteMapper.convertToListDTO(quoteRepository.findByDentist_Id(userId));
        }
        if(user.getRole().getName().equals("PATIENT"))
        {
            return quoteMapper.convertToListDTO(quoteRepository.findByPatient_Id(userId));
        }
        throw new UserNotFoundException("User not found");
    }

    @Override
    public QuoteResponseDTO getQuotebyQuoteId(Integer quoteId) {
        return quoteMapper.convertToDTO(quoteRepository.findById(quoteId).orElse(null));
    }

    @Override
    public QuoteResponseDTO createQuote(QuoteRequestDTO quoteRequestDTO) {
        Dentist dentist = userService.getUserbyId(quoteRequestDTO.getDentistId()).getDentist();
        User patient = userService.getUserbyId(userService.getAuthenticatedUserIdFromJWT());
        if(patient.getPatient().getHistoryClinic()==null)
        {
            throw new IllegalArgumentException("El usuario no tiene una historia clinica");
        }
        Clinic clinca = clinicRepository.findByDentistas_Id(dentist.getId());

        if(!userService.getUserbyId(quoteRequestDTO.getDentistId()).getRole().getName().equals("DENTIST"))
        {
            throw new UserNotFoundException("Ingrese un dentista valido");
        }
        if (quoteRequestDTO.getDate().isBefore(LocalDate.now(ZoneId.of("America/Lima")))) {
            throw new RuntimeException("La fecha de la cita no debe ser menor a la actual");
        }
        if(quoteRequestDTO.getHour().isBefore(clinca.getOpenHour()))
        {
            throw new RuntimeException("La clinica aun no esta abierta");
        }
        if(quoteRequestDTO.getHour().isAfter(clinca.getCloseHour()))
        {
            throw new RuntimeException("La clinica ya esta cerrada");
        }

        DayOfWeek appointmentDay = quoteRequestDTO.getDate().getDayOfWeek();
        List<String> openDays = Arrays.asList(clinca.getOpenDays().split(","));
        if (!Clinic.isOpenOnDay(openDays, appointmentDay)) {
            throw new RuntimeException("La clínica no atiende hoy");
        }
        LocalDateTime localDateTime = LocalDateTime.of(
                quoteRequestDTO.getDate(),
                quoteRequestDTO.getHour()
        );

        if(localDateTime.isBefore(LocalDateTime.now(ZoneId.of("America/Lima"))))
        {
            throw new RuntimeException("La fecha y la Hora de la cita no debe ser menor a la actual");
        }
        // Verificar si el dentista ya tiene una cita en la misma fecha y hora
        LocalDate date = quoteRequestDTO.getDate();
        LocalTime startTime = quoteRequestDTO.getHour();
        LocalTime endTime = startTime.plusHours(4);

        List<Quote> existingAppointments = quoteRepository.findAppointmentsByDentistAndTime(
                quoteRequestDTO.getDentistId(), date, startTime, endTime);

        if (!existingAppointments.isEmpty()) {
            throw new RuntimeException("El dentista ya tiene una cita en esa fecha y hora.");
        }

        // Si el dentista está disponible, crea la nueva cita
        Quote quote = quoteMapper.convertToEntity(quoteRequestDTO);
        quote.setId(null);
        quote.setEndtime(endTime);
        quote.setPatient(patient);


        quote = quoteRepository.save(quote);

        if (quoteRequestDTO.getImages() != null && !quoteRequestDTO.getImages().isEmpty()) {
            for (MultipartFile image : quoteRequestDTO.getImages()) {
                if (!image.isEmpty()) {
                    try {
                        String fileName = uploadFileService.copy(image);
                        QuoteImage quoteImage = new QuoteImage();
                        quoteImage.setQuote(quote);
                        quoteImage.setFilePath(fileName);
                        quoteImageRepository.save(quoteImage);
                    } catch (IOException e) {
                        throw new RuntimeException("Error al guardar la imagen: " + e.getMessage(), e);
                    }
                }
            }
        }

        return quoteMapper.convertToDTO(quote);
    }

    @Override
    public List<String> getQuoteImages(Integer quoteId) {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id: " + quoteId));

        List<QuoteImage> quoteImages = quoteImageRepository.findByQuoteId(quoteId);
        return quoteImages.stream()
                .map(QuoteImage::getFilePath)
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getQuotesForCalendar(Integer userId) {
        List<Quote> quotes = quoteRepository.findByPatient_Id(userId);
        return quoteMapper.convertToCalendarEvents(quotes);
    }

}
