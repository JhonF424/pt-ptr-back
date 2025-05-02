package com.prueba.proteccion.pruebaTecnicaBackend.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.proteccion.pruebaTecnicaBackend.models.FibonacciSequence;
import com.prueba.proteccion.pruebaTecnicaBackend.repositories.FibonacciRepository;

@Service
public class FibonacciService {

    private final FibonacciRepository fibonacciRepository;
    private final MailService mailService;

    @Autowired
    public FibonacciService(FibonacciRepository fibonacciRepository, MailService mailService) {
        this.fibonacciRepository = fibonacciRepository;
        this.mailService = mailService;
    }

    /**
     * Genera una secuencia Fibonacci a partir de dos semillas y devuelve un número específico de términos.
     * 
     * @param seedX Primera semilla
     * @param seedY Segunda semilla
     * @param count Cantidad de términos a generar
     * @return Lista de términos Fibonacci en orden descendente
     */
    public List<Integer> generateFibonacciSequence(int seedX, int seedY, int count) {
        if (count <= 0) {
            return Collections.emptyList();
        }
        
        List<Integer> fibSequence = new ArrayList<>();
        
        fibSequence.add(seedX);
        fibSequence.add(seedY);

        int a = seedX;
        int b = seedY;

        for (int i = 0; i < count; i++) {
            int next = a + b;
            fibSequence.add(next);
            a = b;
            b = next;
        }
        
        Collections.reverse(fibSequence);
        
        return fibSequence;
    }
    
    /**
     * Procesa una solicitud de generación de secuencia Fibonacci basada en una hora específica
     * 
     * @param hour Hora (0-23)
     * @param minute Minuto (0-59)
     * @param second Segundo (0-59)
     * @return Objeto FibonacciSequence creado y guardado
     */
    public FibonacciSequence processTimeRequest(int hour, int minute, int second) {
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59 || second < 0 || second > 59) {
            throw new IllegalArgumentException("Parámetros de tiempo inválidos");
        }
    
        int seedX = minute / 10;  // Primer dígito del minuto
        int seedY = minute % 10;  // Segundo dígito del minuto
    
        int count = second;
    
        List<Integer> sequence = generateFibonacciSequence(seedX, seedY, count);
    
        FibonacciSequence fibonacciSequence = new FibonacciSequence(
                LocalDateTime.now(),
                hour,
                minute,
                second,
                seedX,
                seedY,
                count,
                sequence
        );
    
        FibonacciSequence savedSequence = fibonacciRepository.save(fibonacciSequence);
        
        String body = String.format(
        "Secuencia generada:\nHora: %02d:%02d:%02d\nSemillas: %d, %d\nCantidad: %d\nSecuencia: %s",
        hour, minute, second, seedX, seedY, count, sequence.toString()
        );

        mailService.sendFibonacciEmail("jfrancogp02@gmail.com", "Resultado de Fibonacci", body);
        // TODO: Revisar por qué no llegan los correos a pesar de recibir un 202

        return savedSequence;
    }
    
    /**
     * Obtiene todas las secuencias guardadas en la base de datos
     * 
     * @return Lista de todas las secuencias generadas
     */
    public List<FibonacciSequence> getAllSequences() {
        return fibonacciRepository.findAll();
    }
}