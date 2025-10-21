package com.example.MicroservicioTareas.controllers;

import com.example.MicroservicioTareas.entity.Tarea;
import com.example.MicroservicioTareas.service.ITareaService;
import com.example.MicroservicioTareas.service.ReporteService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/v1/reportes")
@RequiredArgsConstructor
public class ReporteController {


        private final ReporteService reporteService;
        private final ITareaService tareaService;

        @GetMapping("/excel/{idUsuario}")
        public ResponseEntity<ByteArrayResource> generarExcel
        (@PathVariable Long idUsuario,
        @RequestParam(required = false) String estado,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) throws IOException {
            
            List<Tarea> tareasFiltradas = tareaService.obtenerTareasFiltradas(idUsuario,estado, fechaInicio, fechaFin);
            //List<Tarea> tareas = tareaRepository.findAllByIdUsuario_Id(idUsuario);
            
            byte[] excelBytes = reporteService.generarReporteTareas(tareasFiltradas);
            
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Reporte_Tareas_" + LocalDate.now() + ".xlsx");
            
           
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(new ByteArrayResource(excelBytes));
        }
}
