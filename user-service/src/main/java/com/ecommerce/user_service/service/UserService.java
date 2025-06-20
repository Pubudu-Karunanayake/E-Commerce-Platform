package com.ecommerce.user_service.service;

import com.ecommerce.user_service.dto.request.CreateUserDto;
import com.ecommerce.user_service.dto.request.UpdateUserDto;
import com.ecommerce.user_service.dto.response.UserResponseDto;
import com.ecommerce.user_service.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    UserResponseDto createUser (CreateUserDto createUserDto);
    UserResponseDto updateUser (Integer id, UpdateUserDto updateUserDto) throws UserNotFoundException;
    UserResponseDto getUserById (Integer id) throws UserNotFoundException;
    void deleteUser (Integer id) throws UserNotFoundException;
    List<UserResponseDto> getAllUsers();
    void addLoyaltyPoints (Integer id) throws UserNotFoundException;
    void deductLoyaltyPoints (Integer id) throws UserNotFoundException;

}
