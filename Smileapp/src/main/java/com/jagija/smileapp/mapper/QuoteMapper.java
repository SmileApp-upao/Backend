package com.jagija.smileapp.mapper;

import com.jagija.smileapp.dto.QuoteRequestDTO;
import com.jagija.smileapp.dto.QuoteResponseDTO;
import com.jagija.smileapp.model.entity.Quote;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class QuoteMapper {

    private final ModelMapper modelMapper;

    public Quote convertToEntity(QuoteRequestDTO quoteRequestDTO) {
        return modelMapper.map(quoteRequestDTO, Quote.class);
    }

    public QuoteResponseDTO convertToDTO(Quote quote) {
        return modelMapper.map(quote, QuoteResponseDTO.class);
    }

    public List<QuoteResponseDTO> convertToListDTO(List<Quote> quotes) {
        return quotes.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
