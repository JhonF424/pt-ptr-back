package com.prueba.proteccion.pruebaTecnicaBackend.dtos;

import jakarta.validation.constraints.*;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class FibonacciRequest {

    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9])$", 
             message = "El formato de hora debe ser HH:MM:SS")
    private String time; // Formato HH:MM:SS

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHour() {
        try {
            LocalTime localTime = LocalTime.parse(time);
            return localTime.getHour();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de hora inválido", e);
        }
    }

    public int getMinute() {
        try {
            LocalTime localTime = LocalTime.parse(time);
            return localTime.getMinute();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de hora inválido", e);
        }
    }

    public int getSecond() {
        try {
            LocalTime localTime = LocalTime.parse(time);
            return localTime.getSecond();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de hora inválido", e);
        }
    }
}
