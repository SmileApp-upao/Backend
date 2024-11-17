package com.jagija.smileapp.api;

import com.jagija.smileapp.dto.*;
import com.jagija.smileapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    @PostMapping("/register/patient") //Paciente es el rol 1
    public ResponseEntity<UserProfileDTO> registerPatient(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserProfileDTO userProfileDTO = userService.registerPatient(userRegistrationDTO);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
    }

    @PostMapping("/register/dentist")//Dentista es el rol 2
    public ResponseEntity<UserProfileDTO> registerDentist(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) throws IOException {
        UserProfileDTO userProfileDTO = userService.registerDentist(userRegistrationDTO);
        return new ResponseEntity<>(userProfileDTO, HttpStatus.CREATED);
    }

  @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        AuthResponseDTO authResponseDTO = userService.login(loginDTO);
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/validarCop")
    public  ResponseEntity<String> prueba(@Valid @RequestBody VallidCopDTO vallidCopDTO) throws IOException {
       if (userService.validCop(vallidCopDTO))
       {
          return new ResponseEntity<>("Habilitado", HttpStatus.OK);
       }
        else
       {
           return new ResponseEntity<>("No habilitado", HttpStatus.OK);
       }
    }

    @GetMapping("/dataCop")
    public ResponseEntity<?> obtenerDatosCop(@Valid @RequestBody VallidCopDTO vallidCopDTO) throws IOException {
        Map<String, String> datos = userService.obtenerDatosCop(vallidCopDTO);
        if (!datos.isEmpty()) {
            return new ResponseEntity<>(datos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontraron datos para el código COP proporcionado.", HttpStatus.BAD_REQUEST);
        }
    }


}
