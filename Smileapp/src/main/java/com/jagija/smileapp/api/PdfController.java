package com.jagija.smileapp.api;

import com.jagija.smileapp.dto.ReportResponseDTO;
import com.jagija.smileapp.model.entity.Patient;
import com.jagija.smileapp.repository.PatientRepository;
import com.jagija.smileapp.service.PdfService;
import com.jagija.smileapp.service.ReportService;
import com.jagija.smileapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
@RestController
@AllArgsConstructor
@RequestMapping("/reports")
public class PdfController {
    private final PdfService pdfService;
    private final ReportService reportService;
    private final UserService userService;
    @CrossOrigin(origins = {"https://fguevara-guevara.web.app","http://localhost:4200"}, allowCredentials = "true")
    @GetMapping("/pdf/{patientId}")
    public ResponseEntity<InputStreamResource> downloadUserReportPdf(@PathVariable Integer patientId) {
        Integer dentistId  = userService.getAuthenticatedUserIdFromJWT();
        ReportResponseDTO reportResponseDTO = reportService.generateReport(patientId, dentistId);
        ByteArrayInputStream pdfStream = pdfService.generateMedicalReportPdf(reportResponseDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=HISTORIAL-CLINICO.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}
