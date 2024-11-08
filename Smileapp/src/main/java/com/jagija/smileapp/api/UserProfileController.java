package com.jagija.smileapp.api;

import com.jagija.smileapp.dto.UserProfileDTO;
import com.jagija.smileapp.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user/profile")
public class UserProfileController {
    private final UserService userService;

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
