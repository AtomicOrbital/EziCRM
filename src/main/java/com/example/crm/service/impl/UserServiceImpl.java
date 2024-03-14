package com.example.crm.service.impl;

import com.example.crm.entity.UserEntity;
import com.example.crm.payload.request.UserRequest;
import com.example.crm.payload.response.UserResponse;
import com.example.crm.repository.UserRepository;
import com.example.crm.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private UserResponse mapToUserResponse(UserEntity userEntity){
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userEntity.getUserId());
        userResponse.setUserName(userEntity.getUsername());
        userResponse.setFullName(userEntity.getFullName());
        userResponse.setEmail(userEntity.getEmail());
        userResponse.setPhone(userEntity.getPhone());
        userResponse.setCreatedAt(userEntity.getCreatedAt());
        return userResponse;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToUserResponse(userEntity);
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRequest.getUserName());
        userEntity.setPassword(userRequest.getPassword());
        userEntity.setFullName(userRequest.getFullName());
        userEntity.setDateOfBirth(userRequest.getDateOfBirth());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPhone(userRequest.getPhone());
        userEntity.setCreatedAt(new Date());

        UserEntity savedUser = userRepository.save(userEntity);
        return mapToUserResponse(savedUser);
    }

    @Override
    public UserResponse updatedUser(Long id, UserRequest userRequest) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userEntity.setUsername(userRequest.getUserName());
        userEntity.setPassword(userRequest.getPassword());
        userEntity.setFullName(userRequest.getFullName());
        userEntity.setDateOfBirth(userRequest.getDateOfBirth());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPhone(userRequest.getPhone());

        UserEntity updatedUser = userRepository.save(userEntity);
        return mapToUserResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
