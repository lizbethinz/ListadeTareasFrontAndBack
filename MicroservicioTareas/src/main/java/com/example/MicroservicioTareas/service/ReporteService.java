package com.example.MicroservicioTareas.service;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.MicroservicioTareas.entity.Tarea;

// Imports básicos
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.time.format.DateTimeFormatter;
// Import para XSSFColor
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;


@Service
public class ReporteService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public byte[] generarReporteTareas(List<Tarea> tareas) throws IOException {
        
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Datos_Tareas"); 
        

        String[] headers = {"Nombre", "Descripción", "Completada", "Fecha Finalización","Cancelada","Fecha cancelación", "Fecha Creación", "Fecha Objetivo"};
        Row headerRow = sheet.createRow(0);
        

        XSSFCellStyle headerStyle = (XSSFCellStyle) workbook.createCellStyle();
        

        byte[] rgb = {(byte) 0x3A, (byte) 0x9A, (byte) 0xE8};
        XSSFColor headerColor = new XSSFColor(rgb, null); 
        headerStyle.setFillForegroundColor(headerColor);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        

        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(font);


        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (Tarea tarea : tareas) {
            Row row = sheet.createRow(rowNum++);


            String fechaFinalizacion = (tarea.getFechaFinalizacion() != null) 
                ? tarea.getFechaFinalizacion().format(DATE_FORMATTER) 
                : "-";
            String fechaCancelacion = (tarea.getFechaCancelacion() != null) 
                ? tarea.getFechaCancelacion().format(DATE_FORMATTER) 
                : "-";
            String fechaCreacion = tarea.getFechaCreacion().format(DATE_FORMATTER);
            String fechaObjetivo = (tarea.getFechaObjetivo() != null) 
                ? tarea.getFechaObjetivo().format(DATE_FORMATTER) 
                : "-";
                
            String estadoCompletada = tarea.isCompletada() ? "Sí" : "No";
            String estadoCancelada = tarea.isCancelada() ? "Sí" : "No";
            

            
            // Columna 0: Nombre
            row.createCell(0).setCellValue(tarea.getNombre());
            // Columna 1: Descripción
            row.createCell(1).setCellValue(tarea.getDescripcion());
            // Columna 2: Completada
            row.createCell(2).setCellValue(estadoCompletada);
            // Columna 3: Fecha Finalización
            row.createCell(3).setCellValue(fechaFinalizacion);
            // Columna 4: Cancelada
            row.createCell(4).setCellValue(estadoCancelada);
            // Columna 5: Fecha Cancelación
            row.createCell(5).setCellValue(fechaCancelacion);
            // Columna 6: Fecha Creación
            row.createCell(6).setCellValue(fechaCreacion);
            // Columna 7: Fecha Objetivo
            row.createCell(7).setCellValue(fechaObjetivo);
        }

        // Ajustar automáticamente el ancho de las columnas
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } finally {
            workbook.close();
        }
    }
}