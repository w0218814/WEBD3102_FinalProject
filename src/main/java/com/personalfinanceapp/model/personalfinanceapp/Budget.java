package com.personalfinanceapp.model.personalfinanceapp;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private String period; // e.g., "monthly"
    private String category;

    // Constructors
    public Budget() {
        // Default constructor
    }

    // Implement other constructors if needed

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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Other methods (e.g., toString, equals, hashCode) can be added as needed
}
