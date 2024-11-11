package com.jagija.smileapp.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.jagija.smileapp.dto.ReportResponseDTO;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {
    public ByteArrayInputStream generateMedicalReportPdf(ReportResponseDTO reportResponseDTO) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);

            // Portada
            document.setMargins(80, 50, 80, 50);

            // Logo
            String logoPath = getClass().getResource("/static/images/upao.png").getPath(); // Cambia la ruta al logo aquí
            Image logo = new Image(ImageDataFactory.create(logoPath)).scaleToFit(100, 100);
            logo.setWidth(500);  // Aumentar el tamaño del logo
            logo.setFixedPosition(50, pdfDocument.getDefaultPageSize().getTop() - 100);
            document.add(logo);

            // Títulos de la portada
            document.add(new Paragraph("\n\nESCUELA DE ESTOMATOLOGIA")
                    .setBold()
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("CENTRO ODONTOLÓGICO")
                    .setBold()
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("HISTORIA CLINICA ESTOMATOLÓGICA")
                    .setUnderline()
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("NÚMERO DE HISTORIA CLÍNICA: "+reportResponseDTO.getHistoryClinicId())
                    .setBold()
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n\n"));

            // Información del paciente
            String pacienteNombre = "NOMBRE DEL PACIENTE AQUÍ";  // Reemplaza con el nombre real del paciente
            document.add(new Paragraph(pacienteNombre)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("_________________________")
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("PACIENTE")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n"));

            String apellidosPaciente = "APELLIDOS DEL PACIENTE AQUÍ";  // Reemplaza con los apellidos reales del paciente
            document.add(new Paragraph(apellidosPaciente)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("_________________________")
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("APELLIDOS")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n"));

            String nombresPaciente = "NOMBRES DEL PACIENTE AQUÍ";  // Reemplaza con los nombres reales del paciente
            document.add(new Paragraph(nombresPaciente)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("_________________________")
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("NOMBRES")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n\n"));

// Fecha de apertura y otros detalles
            document.add(new Paragraph("FECHA DE APERTURA: ______/______/______")
                    .setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("ALUMNO ENCARGADO DE APERTURA DE HISTORIA CLÍNICA:")
                    .setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("NOMBRE DEL DENTISTA AQUÍ")  // Reemplaza con el nombre del dentista
                    .setTextAlignment(TextAlignment.LEFT));

            document.add(new Paragraph("DOCENTE SUPERVISOR: ___________________________________________")
                    .setTextAlignment(TextAlignment.LEFT));

            document.add(new Paragraph("\n\n\n"));

            // Firma del docente
            document.add(new Paragraph("_____________________________")
                    .setTextAlignment(TextAlignment.RIGHT));
            document.add(new Paragraph("FIRMA DEL DOCENTE")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT));

            // Añadir nueva página para el contenido de la historia clínica
            document.add(new AreaBreak());

            /*======================================== PAGINA 2  ========================================*/

            // Título principal
            document.add(new Paragraph("I. ANAMNESIS:")
                    .setBold().setFontSize(14).setUnderline().setTextAlignment(TextAlignment.LEFT));

            // Sección de Datos de Filiación
            document.add(new Paragraph("A. DATOS DE FILIACIÓN     GRUPO SANGUÍNEO: " +
                    (reportResponseDTO.getBloodType() != null ? reportResponseDTO.getBloodType() : "________") +
                    "   RH: " + (reportResponseDTO.getRh() != null ? reportResponseDTO.getRh() : "________"))
                    .setFontSize(12).setTextAlignment(TextAlignment.LEFT));

            // Datos de filiación con estilo de formulario
            document.add(createLine("Nombres y apellidos", reportResponseDTO.getFullName()));
            document.add(createLine("Sexo", reportResponseDTO.getGender()));
            document.add(createDualLine("Fecha de Nacimiento", reportResponseDTO.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), "Edad", reportResponseDTO.getAge() != null ? reportResponseDTO.getAge().toString() : "________"));
            document.add(createDualLine("Lugar de Nacimiento", reportResponseDTO.getBirthPlace(), "Raza", "________"));
            document.add(createDualLine("Lugar de Procedencia", "________", "DNI", reportResponseDTO.getDni()));
            document.add(createLine("Domicilio", reportResponseDTO.getAddress()));
            document.add(createDualLine("Teléfono", reportResponseDTO.getPhone(), "Correo Electrónico", reportResponseDTO.getEmail()));
            document.add(createLine("Tiempo de residencia en Trujillo", "________"));
            document.add(createLine("Estado civil", reportResponseDTO.getCivilState()));
            document.add(createLine("Grado de instrucción", reportResponseDTO.getStudyGrade()));
            document.add(createLine("Profesión", reportResponseDTO.getProfession()));
            document.add(createLine("Ocupación", reportResponseDTO.getOccupation()));
            document.add(createLine("Centro de estudio o trabajo", reportResponseDTO.getWorkCenter()));
            document.add(createLine("Dirección del centro de estudio o trabajo", reportResponseDTO.getWorkDir()));
            document.add(createLine("Religión", reportResponseDTO.getReligion()));
            document.add(createLine("Vivienda", reportResponseDTO.getHomeType()));

            // Datos de emergencia
            document.add(createDualLine("En caso de emergencia llamar a:", reportResponseDTO.getEmergencyContactName(), "Parentesco", reportResponseDTO.getEmergencyContactParent()));
            document.add(createDualLine("Domicilio", reportResponseDTO.getEmergencyContactDir(), "Teléfono", reportResponseDTO.getEmergencyContactPhone()));

            // Sección B - Motivo de consulta
            document.add(new Paragraph("\nB. MOTIVO DE CONSULTA:")
                    .setBold().setFontSize(12).setUnderline().setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph(reportResponseDTO.getConsultationReason() != null ? reportResponseDTO.getConsultationReason() : "________"));

            // Pie de página
            document.add(new Paragraph("\nAPELLIDOS Y NOMBRES: " + reportResponseDTO.getFullName() +
                    "   N° H.C.: " + (reportResponseDTO.getHistoryClinicId() != null ? reportResponseDTO.getHistoryClinicId().toString() : "________"))
                    .setBold().setFontSize(10));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private Paragraph createLine(String label, String value) {
        return new Paragraph(label + ": " + (value != null ? value : "________"))
                .setFontSize(12).setTextAlignment(TextAlignment.LEFT);
    }

    private Paragraph createDualLine(String label1, String value1, String label2, String value2) {
        return new Paragraph(label1 + ": " + (value1 != null ? value1 : "________") + "    " +
                label2 + ": " + (value2 != null ? value2 : "________"))
                .setFontSize(12).setTextAlignment(TextAlignment.LEFT);
    }

    private Paragraph createLineWithLabel(String label) {
        return new Paragraph(label)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(12)
                .setBorderBottom(new SolidBorder(1));
    }
}

