package com.pedeai.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name="tb_restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;
    @Column(nullable = false, length = 24)
    private String name;
    @Column(length = 126, nullable = false)
    private String description;
    @Column(nullable = false)
    private boolean isActive;
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
