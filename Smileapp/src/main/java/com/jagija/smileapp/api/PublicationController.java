package com.jagija.smileapp.api;

import com.jagija.smileapp.dto.PublicationRequestDTO;
import com.jagija.smileapp.dto.PublicationResponseDTO;
import com.jagija.smileapp.service.PublicationService;
import com.jagija.smileapp.service.IUploadFileService;
import com.jagija.smileapp.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/publications")
public class PublicationController {

    private final PublicationService publicationService;
    private final IUploadFileService uploadFileService;
    private final StorageService storageService;
    @PostMapping("/create")
    public ResponseEntity<PublicationResponseDTO> createPublication(@ModelAttribute PublicationRequestDTO publicationRequestDTO) {
        PublicationResponseDTO createdPublication = publicationService.addPublication(publicationRequestDTO);
        return new ResponseEntity<>(createdPublication, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PublicationResponseDTO>> getAllPublications() {
        List<PublicationResponseDTO> publications = publicationService.getAllPublications();
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @GetMapping("/{publicationId}")
    public ResponseEntity<PublicationResponseDTO> getPublicationById(@PathVariable Integer publicationId) {
        PublicationResponseDTO publication = publicationService.getPublicationById(publicationId);
        return new ResponseEntity<>(publication, HttpStatus.OK);
    }

    @GetMapping("/dentist")
    public ResponseEntity<List<PublicationResponseDTO>> getPublicationsByDentist() {
        List<PublicationResponseDTO> publications = publicationService.getPublicationsByDentist();
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @GetMapping("/dentist/{id}")
    public ResponseEntity<List<PublicationResponseDTO>> getPublicationsByDentist(@PathVariable Integer id) {
        List<PublicationResponseDTO> publications = publicationService.getPublicationsByDentistId(id);
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @DeleteMapping("/{publicationId}")
    public ResponseEntity<?> deletePublication(@PathVariable Integer publicationId) {
        publicationService.deletePublication(publicationId);
        return new ResponseEntity<>("Publicacion eliminada con exito", HttpStatus.NO_CONTENT);
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

    @GetMapping("/file/{filename}")
    public ResponseEntity<Resource> getResource(@PathVariable String filename) throws IOException {
        Resource resource = storageService.loadAsResource(filename);
        String contentType = Files.probeContentType(resource.getFile().toPath());

        if (contentType == null) {
            contentType = "application/octet-stream"; // Tipo genérico si no se puede determinar
        }

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
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
