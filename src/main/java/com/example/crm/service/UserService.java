package com.example.crm.service;

import com.example.crm.entity.UserEntity;
import com.example.crm.payload.request.UserRequest;
import com.example.crm.payload.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse createUser(UserRequest userRequest) throws Exception;
    UserResponse updatedUser(Long id, UserRequest userRequest) throws Exception;
    void deleteUser(Long id);
    List<UserResponse> searchUsers(String username, String address, String email, String phone, Integer minAge, Integer maxAge);
    Page<UserResponse> getAllUsers(Pageable pageable);
}
