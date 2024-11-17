package com.jagija.smileapp.api;

import com.jagija.smileapp.dto.ClinicRequestDTO;
import com.jagija.smileapp.dto.ClinicResponseDTO;
import com.jagija.smileapp.mapper.ClinicMapper;
import com.jagija.smileapp.model.entity.User;
import com.jagija.smileapp.repository.ClinicRepository;
import com.jagija.smileapp.service.ClinicService;
import com.jagija.smileapp.service.IUploadFileService;
import com.jagija.smileapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clinic")
public class ClinicController {
    private final ClinicService clinicService;
    private final UserService userService;
    private final IUploadFileService uploadFileService;

    @PostMapping("/add")
    private ResponseEntity<ClinicResponseDTO> createClinic(@Valid @ModelAttribute ClinicRequestDTO clinicRequestDTO)
    {   User user = userService.getUserbyId(userService.getAuthenticatedUserIdFromJWT());
        if(user.getRole().getName().equals("PATIENT"))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        return new ResponseEntity<>(clinicService.addClinic(clinicRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/clinics")
    private ResponseEntity<List<ClinicResponseDTO>> getClinics()
    {
      return new  ResponseEntity<> (clinicService.getallClinics(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ClinicResponseDTO> getClinicById(@PathVariable Integer id)
    {
        return new  ResponseEntity<> (clinicService.getClinicById(id),HttpStatus.OK);
    }

    @PutMapping("/update")
    private ResponseEntity<ClinicResponseDTO> editClinic(@RequestBody ClinicRequestDTO clinicRequestDTO)
    {
        User user = userService.getUserbyId(userService.getAuthenticatedUserIdFromJWT());
        if(user.getRole().getName().equals("PATIENT"))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        return new ResponseEntity<>(clinicService.updateInfoClinic(clinicRequestDTO),HttpStatus.OK);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<?> updateClinicImage(@PathVariable Integer id, @RequestParam("image") MultipartFile image) {
        clinicService.UpdateImage(id, image);
        return new ResponseEntity<>("Imagen de clinica editada con exito",HttpStatus.OK);
    }

    @GetMapping("/dentist/{id}")
    private ResponseEntity<ClinicResponseDTO> getClinicByDentistId(@PathVariable Integer id) {
        return new ResponseEntity<>(clinicService.getClinicByDentistId(id), HttpStatus.OK);
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
