package com.jagija.smileapp.service.impl;

import com.jagija.smileapp.Exceptions.ResourceNotFoundException;
import com.jagija.smileapp.dto.DocumentRequestDTO;
import com.jagija.smileapp.dto.DocumentResponseDTO;
import com.jagija.smileapp.mapper.DentistMapper;
import com.jagija.smileapp.mapper.DocumentMapper;
import com.jagija.smileapp.model.entity.Dentist;
import com.jagija.smileapp.model.entity.Document;
import com.jagija.smileapp.model.entity.User;

import com.jagija.smileapp.repository.DocumentRepository;
import com.jagija.smileapp.service.RepositoryService;
import com.jagija.smileapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class RepositoryServiceImpl implements RepositoryService {
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final UserService userService;

    @Override
    public List<DocumentResponseDTO> getAllDocumentsofUser(Integer dentistId) {
        return documentMapper.convertToListDTO(documentRepository.findByDentist_Id(dentistId));
    }

    @Override
    public DocumentResponseDTO getDocumentById(Integer id) {
        return documentMapper.convertToDTO(documentRepository.findById(id).orElse(null));
    }

    @Override
    public DocumentResponseDTO addDocument(DocumentRequestDTO document) {
        Integer userID=userService.getAuthenticatedUserIdFromJWT();
        Dentist dentista = userService.getUserbyId(userID).getDentist();
        Document documentEntity = documentMapper.convertToEntity(document);
        documentEntity.setDentist(dentista);
        documentEntity.setDateUpload(LocalDate.now());

        return documentMapper.convertToDTO(documentRepository.save(documentEntity));
    }

    @Override
    public DocumentResponseDTO updateDocument(DocumentRequestDTO document, Integer id) {
        Document documentEntity = documentRepository.findById(id).orElse(null);
        if(documentEntity==null)
        {
            throw new ResourceNotFoundException("Documento no encontrado");
        }
        if(document.getName()!=null)documentEntity.setName(document.getName());
        if(document.getDescription()!=null)documentEntity.setDescription(document.getDescription());
        documentEntity = documentRepository.save(documentEntity);
        return documentMapper.convertToDTO(documentEntity);
    }

    @Override
    public void deleteDocument(Integer id) {
        Document documentEntity = documentRepository.findById(id).orElse(null);
        User user = userService.getUserbyId(userService.getAuthenticatedUserIdFromJWT());
        if(documentEntity==null)
        {
            throw new ResourceNotFoundException("Documento no encontrado");
        }
        if(user.getDentist().getId() != documentEntity.getDentist().getId())
        {
            throw new ResourceNotFoundException("Usuario no tiene permiso para eliminar este documento");
        }
        documentRepository.deleteById(id);
    }
}
