package com.jagija.smileapp.api;

import com.jagija.smileapp.dto.EmergencyRequestDTO;
import com.jagija.smileapp.dto.EmergencyResponseDTO;
import com.jagija.smileapp.model.entity.User;
import com.jagija.smileapp.service.EmergencyService;
import com.jagija.smileapp.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user/profile")
@AllArgsConstructor
public class EmergencyController {

    private final UserService userService;
    private final EmergencyService emergencyService;

    @GetMapping("/emergencyInfo")
    private ResponseEntity<EmergencyResponseDTO> getUserEmergencyInfo()
    {   User user= userService.getUserbyId(userService.getAuthenticatedUserIdFromJWT());
        return  new ResponseEntity<>(emergencyService.getEmergencyInfo(user.getPatient().getId()), HttpStatus.OK);
    }

    @PostMapping("/add/emergencyInfo")
    private ResponseEntity<EmergencyResponseDTO> createEmergencyInfo(@Valid @RequestBody EmergencyRequestDTO emergencyRequestDTO)
    {
        return new ResponseEntity<> (userService.createEmergencyInfo(emergencyRequestDTO), HttpStatus.CREATED);
    }

}
