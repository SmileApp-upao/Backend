package com.jagija.smileapp.api;

import com.jagija.smileapp.dto.EmergencyRequestDTO;
import com.jagija.smileapp.dto.EmergencyResponseDTO;
import com.jagija.smileapp.dto.UserProfileDTO;
import com.jagija.smileapp.model.entity.User;
import com.jagija.smileapp.repository.UserRepository;
import com.jagija.smileapp.service.EmergencyService;
import com.jagija.smileapp.service.IUploadFileService;
import com.jagija.smileapp.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class UserProfileController {
    @Autowired
    private  UserService userService;

    private final IUploadFileService uploadFileService;

    @PutMapping("/{id}")
    private ResponseEntity<UserProfileDTO> updateProfile(@PathVariable Integer id, @Valid @RequestBody UserProfileDTO userProfileDTO)
    {
        Integer tokenId= userService.getAuthenticatedUserIdFromJWT();
        if(tokenId!=id)
        {
            throw new IllegalArgumentException("No puedes editar un perfil que no es el tuyo");
        }
        UserProfileDTO updatedProfile = userService.uptadteUserProfile(id, userProfileDTO);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    private ResponseEntity<UserProfileDTO> getById(@PathVariable Integer id)
    {
        UserProfileDTO userProfileDTO = userService.getUserProfilebyId(id);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<?> updateUserImage(@PathVariable Integer id, @RequestParam("image") MultipartFile image) {
        userService.updateUserImage(id, image);
        return new ResponseEntity<>("Imagen de perfil editada con exito",HttpStatus.OK);
    }

    @GetMapping("/uploads/{filename}")
    public ResponseEntity<Resource> goImage(@PathVariable String filename) {
        Resource resource;
        try {
            resource = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra
        }

        return ResponseEntity.ok()
                .contentType(getContentType(filename)) // Determina el tipo de contenido
                .body(resource);
    }

    //Metodo para obtener el tipo de contenido basado en la extension del archivo
    private MediaType getContentType(String filename) {
        if (filename.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (filename.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM; // Tipo por defecto
        }
    }
}
