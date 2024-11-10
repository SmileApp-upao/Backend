package com.jagija.smileapp.api;

import com.jagija.smileapp.dto.QuoteRequestDTO;
import com.jagija.smileapp.dto.QuoteResponseDTO;
import com.jagija.smileapp.model.entity.User;
import com.jagija.smileapp.service.QuoteService;
import com.jagija.smileapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/citas")
public class QuoteController {
    private final QuoteService quoteService;
    private final UserService userService;

    @PostMapping("/create")
    private ResponseEntity<QuoteResponseDTO> createQuote(@Valid @RequestBody QuoteRequestDTO quoteRequestDTO)
    {   User user =   userService.getUserbyId(userService.getAuthenticatedUserIdFromJWT());
        if(user.getRole().getName().equals("DENTIST"))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        return new ResponseEntity<>(quoteService.createQuote(quoteRequestDTO), HttpStatus.CREATED);
    }
    @GetMapping("/user/{id}")
    private ResponseEntity<List<QuoteResponseDTO>> getAllQuotesByUserID(@PathVariable Integer id) {
        return new ResponseEntity<>(quoteService.getQuotesOfUser(id), HttpStatus.OK);
    }
    @GetMapping("/propias")
    private ResponseEntity<List<QuoteResponseDTO>> getMyQuotes() {
        return new ResponseEntity<>(quoteService.getQuotesOfUser(userService.getAuthenticatedUserIdFromJWT()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<QuoteResponseDTO> getQuoteById(@PathVariable Integer id) {
        return new ResponseEntity<>(quoteService.getQuotebyQuoteId(id), HttpStatus.OK);
    }

}
