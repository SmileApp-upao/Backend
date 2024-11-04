package com.jagija.smileapp.mapper;

import com.jagija.smileapp.dto.DocumentRequestDTO;
import com.jagija.smileapp.dto.DocumentResponseDTO;
import com.jagija.smileapp.model.entity.Document;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DocumentMapper {

    private final ModelMapper modelMapper;

    public Document convertToEntity(DocumentRequestDTO documentRequestDTO) {
        return modelMapper.map(documentRequestDTO, Document.class);
    }

    public DocumentResponseDTO convertToDTO(Document document) {
        return modelMapper.map(document, DocumentResponseDTO.class);
    }

    public List<DocumentResponseDTO> convertToListDTO(List<Document> documents) {
        return documents.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
