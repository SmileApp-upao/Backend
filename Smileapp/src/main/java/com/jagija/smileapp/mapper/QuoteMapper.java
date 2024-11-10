package com.jagija.smileapp.mapper;

import com.jagija.smileapp.dto.QuoteRequestDTO;
import com.jagija.smileapp.dto.QuoteResponseDTO;
import com.jagija.smileapp.model.entity.Quote;
import com.jagija.smileapp.repository.ClinicRepository;
import com.jagija.smileapp.service.ClinicService;
import com.jagija.smileapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuoteMapper {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ClinicRepository clinicRepository;
    public Quote convertToEntity(QuoteRequestDTO quoteRequestDTO) {
        return modelMapper.map(quoteRequestDTO, Quote.class);
    }

    public QuoteResponseDTO convertToDTO(Quote quote) {
        QuoteResponseDTO quoteResponseDTO = modelMapper.map(quote, QuoteResponseDTO.class);
        quoteResponseDTO.setId(quote.getId());
        quoteResponseDTO.setPatientName(userService.getUserbyId(quote.getPatient().getId()).getPatient().getName());
        quoteResponseDTO.setDentistName(userService.getUserbyId(quote.getDentist().getId()).getDentist().getName());
        quoteResponseDTO.setDirection(clinicRepository.findByDentistas_Id(userService.getUserbyId(quote.getDentist().getId()).getDentist().getId()).getAddress());
        return quoteResponseDTO;
    }

    public List<QuoteResponseDTO> convertToListDTO(List<Quote> quotes) {
        return quotes.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
