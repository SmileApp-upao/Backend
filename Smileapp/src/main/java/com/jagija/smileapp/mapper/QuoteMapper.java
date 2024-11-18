package com.jagija.smileapp.mapper;

import com.jagija.smileapp.dto.QuoteRequestDTO;
import com.jagija.smileapp.dto.QuoteResponseDTO;
import com.jagija.smileapp.model.entity.Clinic;
import com.jagija.smileapp.model.entity.Quote;
import com.jagija.smileapp.model.entity.QuoteImage;
import com.jagija.smileapp.model.entity.User;
import com.jagija.smileapp.repository.ClinicRepository;
import com.jagija.smileapp.repository.UserRepository;
import com.jagija.smileapp.service.ClinicService;
import com.jagija.smileapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuoteMapper {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;

    public Quote convertToEntity(QuoteRequestDTO quoteRequestDTO) {
        return modelMapper.map(quoteRequestDTO, Quote.class);
    }

    public QuoteResponseDTO convertToDTO(Quote quote) {
        Clinic clinic = clinicRepository.findByDentistas_Id(userService.getUserbyId(quote.getDentist().getId()).getDentist().getId());
        QuoteResponseDTO quoteResponseDTO = modelMapper.map(quote, QuoteResponseDTO.class);
        quoteResponseDTO.setId(quote.getId());
        quoteResponseDTO.setDentistUserId(userService.getUserbyId(quote.getDentist().getId()).getId());
        quoteResponseDTO.setPatientId(userService.getUserbyId(quote.getPatient().getId()).getPatient().getId());
        quoteResponseDTO.setPatientName(userService.getUserbyId(quote.getPatient().getId()).getPatient().getName());
        quoteResponseDTO.setDentistId(userService.getUserbyId(quote.getDentist().getId()).getDentist().getId());
        quoteResponseDTO.setDentistName(userService.getUserbyId(quote.getDentist().getId()).getDentist().getName());
        quoteResponseDTO.setDentistLastName(userService.getUserbyId(quote.getDentist().getId()).getDentist().getLastname());

        quoteResponseDTO.setClinicdirection(clinic.getAddress());
        quoteResponseDTO.setClinicname(clinic.getName());
        quoteResponseDTO.setClinicdescription(clinic.getDesc());
        quoteResponseDTO.setClinicId(clinic.getId());
        if (quote.getImages() != null) {
            quoteResponseDTO.setFilePaths(quote.getImages().stream()
                    .map(QuoteImage::getFilePath)
                    .collect(Collectors.toList()));
        }

        return quoteResponseDTO;
    }

    public List<QuoteResponseDTO> convertToListDTO(List<Quote> quotes) {
        return quotes.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public Map<String, Object> convertToCalendarEvent(Quote quote) {
        User patient = userService.getUserbyId(quote.getPatient().getId());
        User dentist = userService.getUserbyId(quote.getDentist().getId());
        Clinic clinic = clinicRepository.findByDentistas_Id(dentist.getDentist().getId());

        // Crear el evento con el formato requerido
        Map<String, Object> event = new HashMap<>();
        event.put("patientId", patient.getId());
        event.put("title", patient.getPatient().getName());
        event.put("IdCita", quote.getId());
        event.put("start", quote.getDate() + "T" + quote.getHour());
        event.put("extendedProps", Map.of(
                "clinicName", clinic.getName(),
                "clinicDescription", clinic.getDesc(),
                "clinicDirection", clinic.getAddress(),
                "patientName", patient.getPatient().getName(),
                "dentistName", dentist.getDentist().getName(),
                "dentistLastName", dentist.getDentist().getLastname(),
                "reason", quote.getReason()
        ));
        return event;
    }

    public List<Map<String, Object>> convertToCalendarEvents(List<Quote> quotes) {
        return quotes.stream()
                .map(this::convertToCalendarEvent)
                .collect(Collectors.toList());
    }
}
