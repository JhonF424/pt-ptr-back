package com.prueba.proteccion.pruebaTecnicaBackend.repositories;

import com.prueba.proteccion.pruebaTecnicaBackend.models.FibonacciSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FibonacciRepository extends JpaRepository<FibonacciSequence, Long> {
    
}