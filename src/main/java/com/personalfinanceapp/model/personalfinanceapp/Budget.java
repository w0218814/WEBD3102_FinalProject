package com.personalfinanceapp.model.personalfinanceapp;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private String period;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Transient field for serialization
    @Transient
    private String categoryName;

    // Default constructor
    public Budget() {}

    // Getters and setters for all fields including category
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCategoryName() {
        if (categoryName == null || categoryName.isEmpty()) {
            categoryName = category != null ? category.getName() : null;
        }
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
