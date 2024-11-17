package com.jagija.smileapp.api;

import com.jagija.smileapp.dto.DocumentRequestDTO;
import com.jagija.smileapp.dto.DocumentResponseDTO;
import com.jagija.smileapp.model.entity.User;
import com.jagija.smileapp.service.RepositoryService;
import com.jagija.smileapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/repository")
public class RepoController {
    private final RepositoryService repositoryService;
    private final UserService userService;
    @PostMapping("/create")
    private ResponseEntity<DocumentResponseDTO> uploadDocument(@RequestBody DocumentRequestDTO documentRequestDTO)
    {
     return new ResponseEntity<>(repositoryService.addDocument(documentRequestDTO), HttpStatus.CREATED);
    }
    @GetMapping("/all")
    private ResponseEntity<List<DocumentResponseDTO>> getAllDocuments()
    {
        Integer userId= userService.getAuthenticatedUserIdFromJWT();
        User user = userService.getUserbyId(userId);
        return new ResponseEntity<>(repositoryService.getAllDocumentsofUser(user.getDentist().getId()), HttpStatus.OK);
    }
    @GetMapping("/document/{documentId}")
    private ResponseEntity<DocumentResponseDTO> getDocumentById(@PathVariable Integer documentId)
    {

        return new ResponseEntity<>(repositoryService.getDocumentById(documentId), HttpStatus.OK);
    }
    @PutMapping("/update/{documentId}")
    private ResponseEntity<DocumentResponseDTO> updateInfo(@RequestBody DocumentRequestDTO documentRequestDTO, @PathVariable Integer documentId)
    {
        return new ResponseEntity<>(repositoryService.updateDocument(documentRequestDTO,documentId), HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/delete/{documentId}")
    private ResponseEntity<Map<String, String>> deleteDocument(@PathVariable Integer documentId) {
        repositoryService.deleteDocument(documentId);

        // Crear el objeto JSON como un Map
        Map<String, String> response = new HashMap<>();
        response.put("message", "Eliminado correctamente");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
