package com.ecommerce.user_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDto {
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    private String address;

    @Pattern(
            regexp = "^(\\+94|0)[0-9]{9}$",
            message = "Invalid mobile number. It must start with +94 or 0 and contain 10 digits"
    )
    private String phone;
}
