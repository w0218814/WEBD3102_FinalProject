package com.personalfinanceapp.model.personalfinanceapp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Changed to AUTO for SQLite
    private Long id;

    private BigDecimal amount;
    private String type;

    // SQLite doesn't have a native date type, so dates are often stored as text in ISO format.
    @Column(name = "investmentDate", columnDefinition = "TEXT")
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

    public LocalDate getInvestmentDate() {
        return investmentDate;
    }

    public void setInvestmentDate(LocalDate investmentDate) {
        this.investmentDate = investmentDate;
    }

    // Other methods (e.g., toString, equals, hashCode) can be added as needed
}
