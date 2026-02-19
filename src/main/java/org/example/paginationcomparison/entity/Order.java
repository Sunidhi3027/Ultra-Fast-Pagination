package org.example.paginationcomparison.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "orders",
        indexes = {
                @Index(name = "idx_orders_created_id", columnList = "createdAt DESC, id DESC")
        }
)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private String description;

    // Constructors
    public Order() {}

    public Order(Instant createdAt, String description) {
        this.createdAt = createdAt;
        this.description = description;
    }

    // Getters
    public Long getId() { return id; }
    public Instant getCreatedAt() { return createdAt; }
    public String getDescription() { return description; }
}