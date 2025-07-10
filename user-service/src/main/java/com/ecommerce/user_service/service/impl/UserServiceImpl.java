package com.ecommerce.user_service.service.impl;

import com.ecommerce.user_service.dto.request.CreateUserDto;
import com.ecommerce.user_service.dto.request.UpdateUserDto;
import com.ecommerce.user_service.dto.response.UserResponseDto;
import com.ecommerce.user_service.exception.UserNotFoundException;
import com.ecommerce.user_service.model.User;
import com.ecommerce.user_service.repository.UserRepository;
import com.ecommerce.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public UserResponseDto createUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setName(createUserDto.getName());
        user.setEmail(createUserDto.getEmail());
        user.setAddress(createUserDto.getAddress());
        user.setMobileNumber(createUserDto.getPhone());
        User savedUser = userRepository.save(user);
        log.info("Created user with id : {}", savedUser.getId());

        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getMobileNumber(),
                savedUser.getAddress(),
                savedUser.getLoyaltyPoints()
        );
    }
    @Override
    public UserResponseDto updateUser(Integer id, UpdateUserDto updateUserDto) throws UserNotFoundException{
        User user = userRepository.findById(id).orElseThrow(()->{
            log.error("Failed to update user.User not found with id : {}", id);
            return new UserNotFoundException("There is no user for id = " + id);
        });
        if (updateUserDto.getName() != null) {
            user.setName(updateUserDto.getName());
        }
        if (updateUserDto.getEmail() != null) {
            user.setEmail(updateUserDto.getEmail());
        }
        if (updateUserDto.getAddress() != null) {
            user.setAddress(updateUserDto.getAddress());
        }
        if (updateUserDto.getPhone() != null) {
            user.setMobileNumber(updateUserDto.getPhone());
        }
        User savedUser = userRepository.save(user);
        log.info("Updated user with id : {}", savedUser.getId());
        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getMobileNumber(),
                savedUser.getAddress(),
                savedUser.getLoyaltyPoints()
        );
    }
    @Override
    public UserResponseDto getUserById(Integer id) throws UserNotFoundException{
        User user = userRepository.findById(id).orElseThrow(()->{
            log.error("Failed to fetch user.User not found with id : {}", id);
            return new UserNotFoundException("There is no user for id = " + id);
        });
        log.info("Fetched user with id : {}", user.getId());
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getMobileNumber(),
                user.getAddress(),
                user.getLoyaltyPoints()
        );
    }
    @Override
    public void deleteUser(Integer id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            log.error("Failed to delete user.User not found with id : {}", id);
            throw new UserNotFoundException("There is no user already for id = " + id );
        }
        userRepository.deleteById(id);
        log.info("Deleted user with id : {}", id);
    }
    @Override
    public List<UserResponseDto> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getMobileNumber(),
                        user.getAddress(),
                        user.getLoyaltyPoints()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void addLoyaltyPoints(Integer id) throws UserNotFoundException{
        User user = userRepository.findById(id).orElseThrow(()->{
            log.error("Failed to add loyalty points. User not found with id : {}", id);
            return new UserNotFoundException("There is no existing user for id = " + id);
        });
        user.setLoyaltyPoints(user.getLoyaltyPoints() + 1);
        userRepository.save(user);
        log.info("Added loyalty points for user with id : {}", id);
    }
    @Override
    public void deductLoyaltyPoints(Integer id) throws UserNotFoundException{
        User user = userRepository.findById(id).orElseThrow(()->{
            log.error("Failed to deduct loyalty points. User not found with id : {}", id);
            return new UserNotFoundException("There is no existing user for id = " + id);
        });
        user.setLoyaltyPoints(user.getLoyaltyPoints() - 1);
        userRepository.save(user);
        log.info("Deducted loyalty points for user with id : {}", id);
    }
}
