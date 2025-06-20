package com.ecommerce.user_service.controller;

import com.ecommerce.user_service.dto.request.CreateUserDto;
import com.ecommerce.user_service.dto.request.UpdateUserDto;
import com.ecommerce.user_service.dto.response.UserResponseDto;
import com.ecommerce.user_service.exception.UserNotFoundException;
import com.ecommerce.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        UserResponseDto userResponseDto = userService.createUser(createUserDto);
        URI location = URI.create("/api/users/" + userResponseDto.getId());
        return ResponseEntity.created(location).body(userResponseDto);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> updateUser
            (@PathVariable Integer id, @Valid @RequestBody UpdateUserDto updateUserDto)
            throws UserNotFoundException {
        UserResponseDto userResponseDto = userService.updateUser(id, updateUserDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Integer id) throws UserNotFoundException {
        UserResponseDto userResponseDto = userService.getUserById(id);
        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer id) throws UserNotFoundException {
        userService.deleteUser(id);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> userResponsDtos = userService.getAllUsers();
        return ResponseEntity.ok(userResponsDtos);
    }

    @PostMapping("/loyalty-points/additions/{id}")
    public void AddLoyaltyPoints (@PathVariable Integer id) throws UserNotFoundException {
        userService.addLoyaltyPoints(id);
    }

    @PostMapping("/loyalty-points/deductions/{id}")
    public void deductLoyaltyPoints (@PathVariable Integer id) throws UserNotFoundException {
        userService.deductLoyaltyPoints(id);
    }
}
