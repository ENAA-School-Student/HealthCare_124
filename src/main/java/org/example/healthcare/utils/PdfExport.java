package org.example.healthcare.utils;

import com.lowagie.text.Document;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.example.healthcare.dto.consultation.ConsultationResponseDto;
import org.example.healthcare.dto.dossierMedicale.DossierMedicaleResponseDto;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

public class PdfExport {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public ByteArrayInputStream genererPdf(DossierMedicaleResponseDto dto) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font mainTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, new Color(0, 51, 102));
            Font sectionTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, new Color(0, 102, 204));
            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.DARK_GRAY);
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);

            Paragraph title = new Paragraph("DOSSIER MÉDICAL", mainTitleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(30);
            document.add(title);

            Paragraph patientHeader = new Paragraph("Informations du Patient", sectionTitleFont);
            patientHeader.setSpacingAfter(10);
            document.add(patientHeader);

            PdfPTable patientTable = new PdfPTable(2);
            patientTable.setWidthPercentage(100);
            patientTable.setSpacingAfter(20);

            addInfoRow(patientTable, "Nom complet :", dto.getPatient().getNom() + " " + dto.getPatient().getPrenom(), labelFont, valueFont);
            addInfoRow(patientTable, "Téléphone :", dto.getPatient().getTelephone(), labelFont, valueFont);
            addInfoRow(patientTable, "Date de naissance :", dto.getPatient().getDateNaissance().toString(), labelFont, valueFont);
            addInfoRow(patientTable, "ID Dossier :", String.valueOf(dto.getId()), labelFont, valueFont);
            addInfoRow(patientTable, "Date de création :", dto.getDateCreation().format(DATE_FORMATTER), labelFont, valueFont);
            
            document.add(patientTable);

            Paragraph consultationHeader = new Paragraph("Historique des Consultations", sectionTitleFont);
            consultationHeader.setSpacingBefore(10);
            consultationHeader.setSpacingAfter(15);
            document.add(consultationHeader);

            if (dto.getConsultations() == null || dto.getConsultations().isEmpty()) {
                document.add(new Paragraph("Aucune consultation enregistrée.", valueFont));
            } else {
                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.setWidths(new float[]{20, 25, 25, 30});

                addTableHeader(table, "Date");
                addTableHeader(table, "Médecin");
                addTableHeader(table, "Diagnostic");
                addTableHeader(table, "Observation");

                for (ConsultationResponseDto consultation : dto.getConsultations()) {
                    table.addCell(new Phrase(consultation.getDate_consultation().format(DATE_FORMATTER), valueFont));
                    String medecinName = consultation.getMedecin() != null ? 
                        "Dr. " + consultation.getMedecin().getNom() + " " + consultation.getMedecin().getPrenom() : "N/A";
                    table.addCell(new Phrase(medecinName, valueFont));
                    table.addCell(new Phrase(consultation.getDiagnostic() != null ? consultation.getDiagnostic() : "-", valueFont));
                    table.addCell(new Phrase(consultation.getObservation() != null ? consultation.getObservation() : "-", valueFont));
                }
                document.add(table);
            }

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private static void addInfoRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell cellLabel = new PdfPCell(new Phrase(label, labelFont));
        cellLabel.setBorder(Rectangle.NO_BORDER);
        cellLabel.setPaddingBottom(5);
        table.addCell(cellLabel);

        PdfPCell cellValue = new PdfPCell(new Phrase(value, valueFont));
        cellValue.setBorder(Rectangle.NO_BORDER);
        cellValue.setPaddingBottom(5);
        table.addCell(cellValue);
    }

    private static void addTableHeader(PdfPTable table, String headerTitle) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(new Color(0, 102, 204));
        header.setBorderWidth(1);
        header.setPadding(8);
        header.setPhrase(new Phrase(headerTitle, headerFont));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);
    }

    public ByteArrayInputStream genererRapportSimple(DossierMedicaleResponseDto dto) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, new Color(0, 51, 102));
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, new Color(0, 102, 204));
            Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 11, Color.BLACK);

            // Title
            Paragraph title = new Paragraph("RAPPORT MÉDICAL SIMPLIFIÉ", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Patient Info
            document.add(new Paragraph("Patient : " + dto.getPatient().getNom() + " " + dto.getPatient().getPrenom(), textFont));
            document.add(new Paragraph("Date de naissance : " + dto.getPatient().getDateNaissance(), textFont));
            document.add(new Paragraph("Généré le : " + java.time.LocalDateTime.now().format(DATE_FORMATTER), textFont));
            document.add(new Chunk(new com.lowagie.text.pdf.draw.LineSeparator()));

            // Latest Consultations Summary
            Paragraph summaryHeader = new Paragraph("Résumé des dernières consultations", sectionFont);
            summaryHeader.setSpacingBefore(10);
            summaryHeader.setSpacingAfter(10);
            document.add(summaryHeader);

            if (dto.getConsultations() != null && !dto.getConsultations().isEmpty()) {
                // Sort consultations by date descending and take top 3
                java.util.List<ConsultationResponseDto> recent = dto.getConsultations().stream()
                    .sorted((c1, c2) -> c2.getDate_consultation().compareTo(c1.getDate_consultation()))
                    .limit(3)
                    .toList();

                for (ConsultationResponseDto c : recent) {
                    Paragraph p = new Paragraph();
                    p.add(new Chunk(c.getDate_consultation().format(DATE_FORMATTER) + " : ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)));
                    p.add(new Chunk(c.getDiagnostic() != null ? c.getDiagnostic() : "Pas de diagnostic", textFont));
                    p.setSpacingAfter(5);
                    document.add(p);
                }
            } else {
                document.add(new Paragraph("Aucun historique disponible.", textFont));
            }

            document.add(new Paragraph("\n\nSignature du médecin :\n____________________", textFont));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}
