package com.prueba.proteccion.pruebaTecnicaBackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class FibonacciSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime generationTime;
    
    @Column(name = "hour_value")
    private int hour;
    
    @Column(name = "minute_value")
    private int minute;
    
    @Column(name = "second_value")
    private int second;
    
    private int seedX;
    
    private int seedY;
    
    private int count;
    
    @Column(length = 1000)
    private String sequence;
    
    public FibonacciSequence() {
    }
    
    public FibonacciSequence(LocalDateTime generationTime, int hour, int minute, int second, 
                            int seedX, int seedY, int count, List<Integer> sequence) {
        this.generationTime = generationTime;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.seedX = seedX;
        this.seedY = seedY;
        this.count = count;
        this.sequence = sequence.toString();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getGenerationTime() {
        return generationTime;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int getSeedX() {
        return seedX;
    }

    public int getSeedY() {
        return seedY;
    }

    public int getCount() {
        return count;
    }

    public String getSequence() {
        return sequence;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGenerationTime(LocalDateTime generationTime) {
        this.generationTime = generationTime;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public void setSeedX(int seedX) {
        this.seedX = seedX;
    }

    public void setSeedY(int seedY) {
        this.seedY = seedY;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}