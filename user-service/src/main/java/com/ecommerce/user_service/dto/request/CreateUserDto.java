package com.ecommerce.user_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDto {

    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters!")
    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Email is required!")
    @Email(message = "Invalid email format!")
    private String email;

    @NotBlank
    private String address;

    @NotBlank(message = "Mobile number is required!")
    @Pattern(regexp = "^(\\+94|0)[0-9]{9}$", message = "Invalid mobile number!")
    private String phone;
}
