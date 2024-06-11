package com.asdc.dalexchange.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "seller")
public class Seller {

    @Id
    @Column(name = "seller_id")
    private Long sellerId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "seller_id")
    private User user;

    @Column(name = "seller_rating")
    private double sellerRating;

    @Column(name = "seller_joining_date")
    private LocalDateTime sellerJoiningDate;
}
