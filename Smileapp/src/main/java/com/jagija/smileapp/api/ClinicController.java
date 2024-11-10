package com.jagija.smileapp.api;

import com.jagija.smileapp.dto.ClinicRequestDTO;
import com.jagija.smileapp.dto.ClinicResponseDTO;
import com.jagija.smileapp.mapper.ClinicMapper;
import com.jagija.smileapp.model.entity.User;
import com.jagija.smileapp.repository.ClinicRepository;
import com.jagija.smileapp.service.ClinicService;
import com.jagija.smileapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clinic")
public class ClinicController {
    private final ClinicService clinicService;
    private final UserService userService;

    @PostMapping("/add")
    private ResponseEntity<ClinicResponseDTO> createClinic(@Valid @RequestBody ClinicRequestDTO clinicRequestDTO)
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
}
