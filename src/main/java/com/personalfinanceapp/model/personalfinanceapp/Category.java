package com.personalfinanceapp.model.personalfinanceapp;

import jakarta.persistence.*;
import java.util.Set;

import com.personalfinanceapp.model.personalfinanceapp.Transaction;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Transaction> transactions;

    // Constructors
    public Category() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    // Other methods (e.g., toString, equals, hashCode) can be added as needed
}
