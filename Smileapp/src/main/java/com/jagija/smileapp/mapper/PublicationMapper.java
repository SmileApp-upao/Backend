package com.jagija.smileapp.mapper;

import com.jagija.smileapp.dto.PublicationRequestDTO;
import com.jagija.smileapp.dto.PublicationResponseDTO;
import com.jagija.smileapp.model.entity.Publication;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PublicationMapper {

    private final ModelMapper modelMapper;

    public Publication convertToEntity(PublicationRequestDTO publicationRequestDTO) {
        return modelMapper.map(publicationRequestDTO, Publication.class);
    }

    public PublicationResponseDTO convertToDTO(Publication publication) {
        return modelMapper.map(publication, PublicationResponseDTO.class);
    }

    public List<PublicationResponseDTO> convertToListDTO(List<Publication> publications) {
        return publications.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
