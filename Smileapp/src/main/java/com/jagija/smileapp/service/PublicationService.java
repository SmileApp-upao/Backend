package com.jagija.smileapp.service;

import com.jagija.smileapp.dto.PublicationRequestDTO;
import com.jagija.smileapp.dto.PublicationResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PublicationService {

    List<PublicationResponseDTO> getAllPublications();
    PublicationResponseDTO getPublicationById(Integer id);
    List<PublicationResponseDTO> getPublicationsByDentist();
    PublicationResponseDTO addPublication(PublicationRequestDTO publication);
    void deletePublication(Integer id);
}
