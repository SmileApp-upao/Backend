package com.jagija.smileapp.service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.jagija.smileapp.dto.ReportResponseDTO;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {
    public ByteArrayInputStream generateMedicalReportPdf(ReportResponseDTO reportResponseDTO) {


        try {
            ByteArrayOutputStream firstPartOut  = new ByteArrayOutputStream();
            PdfWriter firstWriter = new PdfWriter(firstPartOut);
            PdfDocument firstPdf = new PdfDocument(firstWriter);
            Document document = new Document(firstPdf, PageSize.A4);

            // Portada
            document.setMargins(80, 50, 80, 50);

            // Logo
            // Intenta cargar el logo desde el classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/images/upao.png");
            if (inputStream == null) {
                throw new FileNotFoundException("El archivo 'static/images/upao.png' no fue encontrado en el classpath.");
            }

            // Crea el ImageData a partir del InputStream
            ImageData imageData = ImageDataFactory.create(IOUtils.toByteArray(inputStream));
            Image logo = new Image(imageData);

            // Ajusta el tamaño del logo
            logo.scaleToFit(500, 500); // Ajusta las dimensiones del logo
            logo.setFixedPosition(50, firstPdf.getDefaultPageSize().getTop() - 100); // Coloca el logo en la posición deseada

            // Agrega el logo al documento
            document.add(logo);

            // Títulos de la portada
            document.add(new Paragraph("\nESCUELA DE ESTOMATOLOGIA")
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
            document.add(new Paragraph(reportResponseDTO.getFullName())
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("__________________________________________")
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("PACIENTE")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n"));

            document.add(new Paragraph(reportResponseDTO.getLastname())
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("__________________________________________")
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("APELLIDOS")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n"));

            document.add(new Paragraph(reportResponseDTO.getName())
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("__________________________________________")
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("NOMBRES")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n\n"));

// Fecha de apertura y otros detalles
            document.add(new Paragraph("FECHA DE APERTURA: ______/______/______")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph("ALUMNO ENCARGADO DE APERTURA DE HISTORIA CLÍNICA:")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph(reportResponseDTO.getDentistFullName())
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.LEFT));

            document.add(new Paragraph("DOCENTE SUPERVISOR: ___________________________________________")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.LEFT));

            document.add(new Paragraph("\n"));

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
            document.add(createDualLine("Fecha de Nacimiento", reportResponseDTO.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), "\t\t\t\t\t\tEdad", reportResponseDTO.getAge() != null ? reportResponseDTO.getAge().toString() : "________"));
            document.add(createDualLine("Lugar de Nacimiento", reportResponseDTO.getBirthPlace(), "\t\t\t\t\tRaza", reportResponseDTO.getRaze()));
            document.add(createDualLine("Lugar de Procedencia", reportResponseDTO.getBirthPlace(), "\t\t\t\t\tDNI", reportResponseDTO.getDni()));
            document.add(createLine("Domicilio", reportResponseDTO.getDir()));
            document.add(createDualLine("Teléfono", reportResponseDTO.getPhone(), "\t\t\t\t\t\tCorreo Electrónico", reportResponseDTO.getEmail()));
            document.add(createLine("Tiempo de residencia en Trujillo", reportResponseDTO.getResidentime()));
            document.add(createLine("Estado civil", reportResponseDTO.getCivilState()));
            document.add(createLine("Grado de instrucción", reportResponseDTO.getStudyGrade()));
            document.add(createLine("Profesión", reportResponseDTO.getProfession()));
            document.add(createLine("Ocupación", reportResponseDTO.getOccupation()));
            document.add(createLine("Centro de estudio o trabajo", reportResponseDTO.getWorkCenter()));
            document.add(createLine("Dirección del centro de estudio o trabajo", reportResponseDTO.getWorkDir()));
            document.add(createLine("Religión", reportResponseDTO.getReligion()));
            document.add(createLine("Vivienda", reportResponseDTO.getHome()));

            // Datos de emergencia
            document.add(createDualLine("En caso de emergencia llamar a", reportResponseDTO.getEmergencyContactName(), "\t\tParentesco", reportResponseDTO.getEmergencyContactParent()));
            document.add(createDualLine("Domicilio", reportResponseDTO.getEmergencyContactDir(), "\t\t\t\t\t\t\t\tTeléfono", reportResponseDTO.getEmergencyContactPhone()));

            // Sección B - Motivo de consulta
            document.add(new Paragraph("\nB. MOTIVO DE CONSULTA:")
                    .setBold().setFontSize(12).setUnderline().setTextAlignment(TextAlignment.LEFT));
            document.add(new Paragraph(reportResponseDTO.getConsultationReason() != null ? reportResponseDTO.getConsultationReason() : "________"));

            // Pie de página
            document.add(new Paragraph("\nAPELLIDOS Y NOMBRES: " + reportResponseDTO.getFullName() +
                    "   N° H.C.: " + (reportResponseDTO.getHistoryClinicId() != null ? reportResponseDTO.getHistoryClinicId().toString() : "________"))
                    .setBold().setFontSize(10));

            document.close();

            /*=========================================== CONCATENACION CON EL SIGUIENTE PDF=========================================*/
            ByteArrayOutputStream combinedOut = new ByteArrayOutputStream();
            PdfWriter combinedWriter = new PdfWriter(combinedOut);
            PdfDocument combinedPdf = new PdfDocument(combinedWriter);

            // Leer el primer PDF desde memoria y copiar sus páginas al PDF combinado
            PdfDocument firstPartPdf = new PdfDocument(new PdfReader(new ByteArrayInputStream(firstPartOut.toByteArray())));
            firstPartPdf.copyPagesTo(1, firstPartPdf.getNumberOfPages(), combinedPdf);
            firstPartPdf.close();

            // Leer el PDF de "historial.pdf" desde /static y copiar sus páginas al PDF combinado
            PdfDocument headerPdf = new PdfDocument(new PdfReader(new ClassPathResource("static/historial.pdf").getInputStream()));
            for (int i = 1; i <= headerPdf.getNumberOfPages(); i++) {
                PdfPage page = headerPdf.getPage(i);
                page.setMediaBox(PageSize.A4); // Asegura que cada página adicional tenga tamaño A4
                combinedPdf.addPage(page.copyTo(combinedPdf));
            }
            headerPdf.close();

            combinedPdf.close();

            return new ByteArrayInputStream(combinedOut.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
}

