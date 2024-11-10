package com.jagija.smileapp.api;

import com.jagija.smileapp.dto.HistoryClinicRequestDTO;
import com.jagija.smileapp.dto.HistoryClinicResponseDTO;
import com.jagija.smileapp.model.entity.HistoryClinic;
import com.jagija.smileapp.model.entity.User;
import com.jagija.smileapp.service.HistoryClinicService;
import com.jagija.smileapp.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class HistoryClinicController {
    private final HistoryClinicService historyClinicService;
    private final UserService userService;

    @GetMapping("/History")
    public ResponseEntity<HistoryClinicResponseDTO> getUserHistory() {
        User user= userService.getUserbyId(userService.getAuthenticatedUserIdFromJWT());
        if(user.getRole().getName().equals("DENTIST"))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        return new ResponseEntity<>(historyClinicService.getHistoryClinic(), HttpStatus.OK);
    }
    @PostMapping("/add/History")
    public ResponseEntity<HistoryClinicResponseDTO> createHistory(@Valid @RequestBody HistoryClinicRequestDTO historyClinicRequestDTO)
    {   User user= userService.getUserbyId(userService.getAuthenticatedUserIdFromJWT());
        if(user.getRole().getName().equals("DENTIST"))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        return new ResponseEntity<>(historyClinicService.addHistoryClinic(historyClinicRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update/History")
    public ResponseEntity<HistoryClinicResponseDTO> updateHistory(@RequestBody HistoryClinicRequestDTO historyClinicRequestDTO)
    {
        User user= userService.getUserbyId(userService.getAuthenticatedUserIdFromJWT());
        if(user.getRole().getName().equals("DENTIST"))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        return new ResponseEntity<>(historyClinicService.updateHistoryClinic(historyClinicRequestDTO), HttpStatus.OK);
    }
}
