package com.jagija.smileapp.service;

import com.jagija.smileapp.dto.QuoteRequestDTO;
import com.jagija.smileapp.dto.QuoteResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface QuoteService {
    List<QuoteResponseDTO> getQuotesOfUser(Integer userId);
    QuoteResponseDTO getQuotebyQuoteId(Integer quoteId);
    QuoteResponseDTO createQuote(QuoteRequestDTO quoteRequestDTO);
    List<String> getQuoteImages(Integer quoteId);
}
