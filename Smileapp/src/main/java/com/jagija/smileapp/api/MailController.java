package com.jagija.smileapp.api;

import com.jagija.smileapp.service.NotificationAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final NotificationAppointmentService notificationAppointmentService;

    @PostMapping("/notification")
    public ResponseEntity<String> sendNotification() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("Usuario no autenticado", HttpStatus.UNAUTHORIZED);
        }

        String email = authentication.getName();

        notificationAppointmentService.createAndSendNotification(email);
        return new ResponseEntity<>("Correo enviado", HttpStatus.OK);
    }
}
