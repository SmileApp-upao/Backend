package com.jagija.smileapp.service.impl;

import com.jagija.smileapp.dto.PublicationRequestDTO;
import com.jagija.smileapp.dto.PublicationResponseDTO;
import com.jagija.smileapp.mapper.PublicationMapper;
import com.jagija.smileapp.model.entity.Dentist;
import com.jagija.smileapp.model.entity.Publication;
import com.jagija.smileapp.model.entity.User;
import com.jagija.smileapp.repository.PublicationRepository;
import com.jagija.smileapp.service.PublicationService;
import com.jagija.smileapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationServicieImpl implements PublicationService {

    private final PublicationMapper publicationMapper;
    private final PublicationRepository publicationRepository;
    private final IUploadFileServiceImpl uploadFileService;
    private final UserService userService;

    @Override
    public List<PublicationResponseDTO> getAllPublications() {
        List<Publication> publications = publicationRepository.findAll();
        return publicationMapper.convertToListDTO(publications);
    }

    @Override
    public PublicationResponseDTO getPublicationById(Integer id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
        return publicationMapper.convertToDTO(publication);
    }

    @Override
    public PublicationResponseDTO addPublication(PublicationRequestDTO publicationRequestDTO) {
        Publication publication = publicationMapper.convertToEntity(publicationRequestDTO);
        Integer AutenticatedId=userService.getAuthenticatedUserIdFromJWT();
        User user = userService.getUserbyId(AutenticatedId);

        String imagePath;

        try {
            imagePath = uploadFileService.copy(publicationRequestDTO.getImage());
        } catch (IOException e){
            throw new RuntimeException("Error al cargar la imagen: " + e.getMessage(), e);
        }
        publication.setImage(imagePath);
        publication.setDentist(user.getDentist());
        return publicationMapper.convertToDTO(publicationRepository.save(publication));
    }

    @Override
    public List<PublicationResponseDTO> getPublicationsByDentist() {
        Integer AutenticatedId=userService.getAuthenticatedUserIdFromJWT();
        User user = userService.getUserbyId(AutenticatedId);

        List<Publication> publication = publicationRepository.findPublicationsByDentist_Id(user.getDentist().getId());

        return publicationMapper.convertToListDTO(publication);
    }

    @Override
    public void deletePublication(Integer id) {
        publicationRepository.deleteById(id);
    }

}
