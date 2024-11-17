package com.jagija.smileapp.service;

import com.jagija.smileapp.dto.DocumentRequestDTO;
import com.jagija.smileapp.dto.DocumentResponseDTO;
import com.jagija.smileapp.repository.DocumentRepository;

import java.util.List;

public interface RepositoryService {
    public List<DocumentResponseDTO> getAllDocumentsofUser(Integer dentistId);
    public DocumentResponseDTO getDocumentById(Integer id);
    public DocumentResponseDTO addDocument(DocumentRequestDTO document);
    public DocumentResponseDTO updateDocument(DocumentRequestDTO document,Integer id);
    public void deleteDocument(Integer id);
}
