package com.ecommerce.user_service.dto.response;

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
