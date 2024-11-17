package com.jagija.smileapp.mapper;

import com.jagija.smileapp.dto.DocumentRequestDTO;
import com.jagija.smileapp.dto.DocumentResponseDTO;
import com.jagija.smileapp.model.entity.Document;
import com.jagija.smileapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class DocumentMapper {

    private final ModelMapper modelMapper;
    private final UserMapper userMapper;
    public Document convertToEntity(DocumentRequestDTO documentRequestDTO) {
        Document doc=  modelMapper.map(documentRequestDTO, Document.class);
        return doc;
    }

    public DocumentResponseDTO convertToDTO(Document document) {
        DocumentResponseDTO documentResponseDTO =  modelMapper.map(document, DocumentResponseDTO.class);
        documentResponseDTO.setDateUpload(LocalDate.now());
        documentResponseDTO.setDentistProfile(userMapper.toUserProfileDTO(document.getDentist().getUser()));
        return documentResponseDTO;
    }

    public List<DocumentResponseDTO> convertToListDTO(List<Document> documents) {
        return documents.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
