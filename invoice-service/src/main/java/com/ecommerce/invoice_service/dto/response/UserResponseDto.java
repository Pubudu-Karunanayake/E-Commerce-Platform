package com.ecommerce.invoice_service.dto.response;

import lombok.Data;

@Data
public class UserResponseDto {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Integer loyaltyPoints;
}
