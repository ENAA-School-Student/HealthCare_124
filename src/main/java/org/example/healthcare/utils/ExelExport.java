package org.example.healthcare.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.healthcare.dto.rendezVous.RendezVousResponseDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExelExport {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static ByteArrayInputStream listRendezVousToExcel(List<RendezVousResponseDto> rendezVousList) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Rendez-vous");

            // Header Style
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

            // Row for Header
            Row headerRow = sheet.createRow(0);

            String[] columns = {"ID", "Date & Heure", "Statut", "Médecin", "Spécialité"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Cell Style for data
            CellStyle dataCellStyle = workbook.createCellStyle();
            dataCellStyle.setAlignment(HorizontalAlignment.LEFT);

            // Filling data
            int rowIdx = 1;
            for (RendezVousResponseDto rv : rendezVousList) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(rv.getId());
                row.createCell(1).setCellValue(rv.getDateRendezVous().format(DATE_FORMATTER));
                row.createCell(2).setCellValue(rv.getStatut().toString());
                
                String medecinName = rv.getMedecin() != null ? 
                    rv.getMedecin().getNom() + " " + rv.getMedecin().getPrenom() : "N/A";
                row.createCell(3).setCellValue(medecinName);
                
                String specialite = rv.getMedecin() != null ? rv.getMedecin().getSpecialite() : "N/A";
                row.createCell(4).setCellValue(specialite);
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la génération du fichier Excel", e);
        }
    }
}
