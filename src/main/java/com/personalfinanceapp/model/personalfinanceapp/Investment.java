package com.personalfinanceapp.model.personalfinanceapp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate; // Ensure java.time.LocalDate is imported

@Entity
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private String type; // e.g., "stocks", "bonds"

    // Changed the type from Date to LocalDate
    private LocalDate investmentDate;

    // Default constructor
    public Investment() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Update getter and setter to use LocalDate
    public LocalDate getInvestmentDate() {
        return investmentDate;
    }

    public void setInvestmentDate(LocalDate investmentDate) {
        this.investmentDate = investmentDate;
    }

    // Other methods (e.g., toString, equals, hashCode) can be added as needed
}
