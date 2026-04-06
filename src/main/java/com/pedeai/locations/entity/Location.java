package com.pedeai.locations.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tb_locations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private Double latitude;
    @Column(nullable = false)
    private Double longitude;
    private String street;
    private String number;
    private String city;
    private String state;
    private String zipcode;
    private String country;
}
