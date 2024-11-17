package com.jagija.smileapp.api;

import com.jagija.smileapp.dto.QuoteRequestDTO;
import com.jagija.smileapp.dto.QuoteResponseDTO;
import com.jagija.smileapp.model.entity.User;
import com.jagija.smileapp.service.QuoteService;
import com.jagija.smileapp.service.UserService;
import com.jagija.smileapp.service.impl.IUploadFileServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.core.io.Resource;

import javax.persistence.criteria.CriteriaBuilder;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/citas")
public class QuoteController {
    private final QuoteService quoteService;
    private final UserService userService;
    private final IUploadFileServiceImpl uploadFileService;

    @PostMapping("/create")
    private ResponseEntity<QuoteResponseDTO> createQuote(@Valid @ModelAttribute QuoteRequestDTO quoteRequestDTO)

    {   User user =   userService.getUserbyId(userService.getAuthenticatedUserIdFromJWT());
        if(user.getRole().getName().equals("DENTIST"))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        QuoteResponseDTO createdQuote = quoteService.createQuote(quoteRequestDTO);
        return new ResponseEntity<>(createdQuote, HttpStatus.CREATED);
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

    @GetMapping("/{quoteId}/images")
    public ResponseEntity<List<String>> getQuoteImageFilenames(@PathVariable Integer quoteId) {
        List<String> imageFilenames = quoteService.getQuoteImages(quoteId);
        return ResponseEntity.ok(imageFilenames);
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource resource;
        try {
            resource = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(getContentType(filename))
                .body(resource);
    }

    private MediaType getContentType(String filename) {
        if (filename.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (filename.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

}
