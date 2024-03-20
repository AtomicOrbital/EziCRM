package com.example.crm.service.impl;

import com.example.crm.entity.RoleEntity;
import com.example.crm.entity.UserEntity;
import com.example.crm.payload.request.UserRequest;
import com.example.crm.payload.response.UserResponse;
import com.example.crm.repository.RoleRepository;
import com.example.crm.repository.UserAttributesRepository;
import com.example.crm.repository.UserRepository;
import com.example.crm.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAttributesRepository userAttributesRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserResponse mapToUserResponse(UserEntity userEntity){
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userEntity.getUserId());
        userResponse.setUserName(userEntity.getUsername());
        userResponse.setAddress(userEntity.getAddress());
        userResponse.setEmail(userEntity.getEmail());
        userResponse.setPhone(userEntity.getPhone());
        userResponse.setCreatedAt(userEntity.getCreatedAt());

        //Tính toán tuổi
        if(userEntity.getDateOfBirth() != null){
            LocalDate birthDate = userEntity.getDateOfBirth();
            LocalDate currentDate = LocalDate.now();
            int age = Period.between(birthDate, currentDate).getYears();
            userResponse.setAge(age);
        }

        if(userEntity.getRole() != null){
            userResponse.setRoleName(userEntity.getRole().getName());
        }

        //Truy vấn các thuộc tính động
        List<UserResponse.AttributeResponse> dynamicAttributes = userAttributesRepository
                .findByUserEntityUserId(userEntity.getUserId())
                .stream()
                .map(attribute -> new UserResponse.AttributeResponse(attribute.getName(), attribute.getValue()))
                .collect(Collectors.toList());

        userResponse.setDynamicAttributes(dynamicAttributes);
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
        userEntity.setUsername(userRequest.getUsername());
//        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userEntity.setAddress(userRequest.getAddress());
        userEntity.setDateOfBirth(userRequest.getDateOfBirth());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPhone(userRequest.getPhone());
        userEntity.setCreatedAt(new Date());

//        Integer roleId = Integer.valueOf(userRequest.getIdRole());

//        RoleEntity roleEntity = roleRepository.findById(userRequest.getIdRole())
//                    .orElseThrow(() -> new RuntimeException("Role not found"));
//        userEntity.setRole(roleEntity);


        UserEntity savedUser = userRepository.save(userEntity);
        return mapToUserResponse(savedUser);
    }

    @Override
    public UserResponse updatedUser(Long id, UserRequest userRequest) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userEntity.setUsername(userRequest.getAddress());
//        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userEntity.setAddress(userRequest.getAddress());
        userEntity.setDateOfBirth(userRequest.getDateOfBirth());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPhone(userRequest.getPhone());
//        RoleEntity role = roleRepository.findById(userRequest.getIdRole())
//                .orElseThrow(() -> new RuntimeException("Role not found"));
//        userEntity.setRole(role);

        UserEntity updatedUser = userRepository.save(userEntity);
        return mapToUserResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> searchUsers(String username, String address, String email, String phone, Integer minAge, Integer maxAge) {
        Specification<UserEntity> spec = Specification.where(null);

        if(username != null){
            spec = spec.and(UserSpecifications.hasUsername(username));
        }

        if(address != null){
            spec = spec.and(UserSpecifications.hasAddress(address));
        }

        if(email != null) {
            spec = spec.and(UserSpecifications.hasEmail(email));
        }

        if(phone != null){
            spec = spec.and(UserSpecifications.hasPhone(phone));
        }

        // Tìm kiếm theo khoảng tuổi
        if(minAge != null && maxAge != null){
            // Nếu cả minAge và maxAge đều được search
            spec = spec.and(UserSpecifications.isAgeBetween(minAge, maxAge));
        } else if(minAge != null){
            // Chỉ có minAge
            spec = spec.and(UserSpecifications.isOlderThan(minAge));
        } else if(maxAge != null) {
            spec = spec.and(UserSpecifications.isYoungerThan(maxAge));
        }

        List<UserEntity> users = userRepository.findAll(spec);
        return users.stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }
}
