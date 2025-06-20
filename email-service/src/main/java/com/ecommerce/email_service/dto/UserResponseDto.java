package com.ecommerce.email_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Integer loyaltyPoints;
}
