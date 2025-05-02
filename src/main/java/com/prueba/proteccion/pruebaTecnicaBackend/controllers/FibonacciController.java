package com.prueba.proteccion.pruebaTecnicaBackend.controllers;

import com.prueba.proteccion.pruebaTecnicaBackend.dtos.FibonacciRequest;
import com.prueba.proteccion.pruebaTecnicaBackend.models.FibonacciSequence;
import com.prueba.proteccion.pruebaTecnicaBackend.services.FibonacciService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/fibonacci")
@Tag(name = "Fibonacci", description = "API para generar secuencias Fibonacci basadas en la hora")
public class FibonacciController {

    private final FibonacciService fibonacciService;

    @Autowired
    public FibonacciController(FibonacciService fibonacciService) {
        this.fibonacciService = fibonacciService;
    }

    @GetMapping("/current")
    @Operation(
        summary = "Genera una secuencia Fibonacci basada en la hora actual del sistema",
        responses = {
            @ApiResponse(responseCode = "200", description = "Secuencia generada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    public ResponseEntity<?> getCurrentFibonacci() {
        try {
            LocalTime time = LocalTime.now();
            FibonacciSequence result = fibonacciService.processTimeRequest(
                time.getHour(), time.getMinute(), time.getSecond()
            );
            return ResponseEntity.ok(mapResponse(result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno", "message", e.getMessage()));
        }
    }

    @PostMapping
    @Operation(
        summary = "Genera una secuencia Fibonacci a partir de una hora específica enviada por el usuario",
        responses = {
            @ApiResponse(responseCode = "200", description = "Secuencia generada correctamente"),
            @ApiResponse(responseCode = "400", description = "Petición inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    public ResponseEntity<?> postFibonacci(@Valid @RequestBody FibonacciRequest request) {
        try {
            // Obtener hora, minuto y segundo usando los métodos del DTO
            int hour = request.getHour();
            int minute = request.getMinute();
            int second = request.getSecond();
    
            FibonacciSequence result = fibonacciService.processTimeRequest(hour, minute, second);
            return ResponseEntity.ok(mapResponse(result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Formato de hora inválido", "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno", "message", e.getMessage()));
        }
    }

    @GetMapping("/history")
    @Operation(
        summary = "Recupera todas las secuencias Fibonacci generadas",
        responses = {
            @ApiResponse(responseCode = "200", description = "Historial recuperado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al recuperar el historial")
        }
    )
    public ResponseEntity<?> getAllSequences() {
        try {
            List<Map<String, Object>> result = fibonacciService.getAllSequences().stream()
                    .map(this::mapResponse)
                    .toList();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al recuperar historial", "message", e.getMessage()));
        }
    }

    private Map<String, Object> mapResponse(FibonacciSequence sequence) {
        return Map.of(
            "id", sequence.getId(),
            "generationTime", sequence.getGenerationTime(),
            "time", String.format("%02d:%02d:%02d", sequence.getHour(), sequence.getMinute(), sequence.getSecond()),
            "seedX", sequence.getSeedX(),
            "seedY", sequence.getSeedY(),
            "count", sequence.getCount(),
            "sequence", sequence.getSequence()
        );
    }
}
