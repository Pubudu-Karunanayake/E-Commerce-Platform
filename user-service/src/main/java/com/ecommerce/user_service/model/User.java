package com.ecommerce.user_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    private String name;
    @Column(name = "loyalty_points")
    private Integer loyaltyPoints = 0;
    private String email;
    private String address;
    @Column(name = "mobile_number")
    private String mobileNumber;
}
