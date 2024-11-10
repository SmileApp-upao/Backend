package com.jagija.smileapp.api;

import com.jagija.smileapp.dto.EmergencyRequestDTO;
import com.jagija.smileapp.dto.EmergencyResponseDTO;
import com.jagija.smileapp.dto.UserProfileDTO;
import com.jagija.smileapp.model.entity.User;
import com.jagija.smileapp.repository.UserRepository;
import com.jagija.smileapp.service.EmergencyService;
import com.jagija.smileapp.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class UserProfileController {
    @Autowired
    private  UserService userService;

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
}
