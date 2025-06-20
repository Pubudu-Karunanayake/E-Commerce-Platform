package com.ecommerce.user_service.service.impl;

import com.ecommerce.user_service.dto.request.CreateUserDto;
import com.ecommerce.user_service.dto.request.UpdateUserDto;
import com.ecommerce.user_service.dto.response.UserResponseDto;
import com.ecommerce.user_service.exception.UserNotFoundException;
import com.ecommerce.user_service.model.User;
import com.ecommerce.user_service.repository.UserRepository;
import com.ecommerce.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
        User user = userRepository.findById(id).orElseThrow(()->
                new UserNotFoundException("There is no user for id = " + id));
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
        User user = userRepository.findById(id).orElseThrow(()->
                new UserNotFoundException("There is no user for id = " + id));
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
            throw new UserNotFoundException("There is no user already for id = " + id );
        }
        userRepository.deleteById(id);
    }
    @Override
    public List<UserResponseDto> getAllUsers() {
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
        User user = userRepository.findById(id).orElseThrow(()->
                new UserNotFoundException("There is no existing user for id = " + id));
        user.setLoyaltyPoints(user.getLoyaltyPoints() + 1);
        userRepository.save(user);
    }
    @Override
    public void deductLoyaltyPoints(Integer id) throws UserNotFoundException{
        User user = userRepository.findById(id).orElseThrow(()->
                new UserNotFoundException("There is no existing user for id = " + id));
        user.setLoyaltyPoints(user.getLoyaltyPoints() - 1);
        userRepository.save(user);
    }
}
