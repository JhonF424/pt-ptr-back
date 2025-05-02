package com.prueba.proteccion.pruebaTecnicaBackend.services;

import com.prueba.proteccion.pruebaTecnicaBackend.models.FibonacciSequence;
import com.prueba.proteccion.pruebaTecnicaBackend.repositories.FibonacciRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FibonacciServiceTest {

    @Test
    public void testProcessTimeRequest_GeneratesCorrectSequenceAndSavesIt() {
        FibonacciRepository mockRepository = Mockito.mock(FibonacciRepository.class);
        FibonacciService service = new FibonacciService(mockRepository);

        int hour = 12;
        int minute = 34; // seedX = 3, seedY = 4
        int second = 6;  // count = 6 → total 8 elementos (2 semillas + 6 generados)

        when(mockRepository.save(any(FibonacciSequence.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        FibonacciSequence result = service.processTimeRequest(hour, minute, second);

        List<Integer> parsedSequence = parseSequence(result.getSequence());

        assertEquals(3, result.getSeedX(), "SeedX debe ser el primer dígito del minuto (3)");
        assertEquals(4, result.getSeedY(), "SeedY debe ser el segundo dígito del minuto (4)");
        assertEquals(6, result.getCount(), "El contador debe coincidir con los segundos");

        assertEquals(8, parsedSequence.size(), "La secuencia debe tener 8 números (2 semillas + 6 generados)");

        System.out.println("Test pasado: Secuencia generada correctamente: " + parsedSequence);
    }

    private List<Integer> parseSequence(String raw) {
        raw = raw.replaceAll("\\[|\\]|\\s", "");
        String[] tokens = raw.split(",");
        List<Integer> result = new ArrayList<>();
        for (String token : tokens) {
            if (!token.isEmpty()) {
                result.add(Integer.parseInt(token));
            }
        }
        return result;
    }
}
