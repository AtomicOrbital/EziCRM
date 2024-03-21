package com.example.crm.service.impl;

import com.example.crm.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class UserSpecifications {
    public static Specification<UserEntity> hasUsername(String username) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), "%" + username + "%");
    }

    public static Specification<UserEntity> hasAddress(String address){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("address"), "%" + address + "%");
    }

    public static Specification<UserEntity> hasEmail(String email){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), "%" + email + "%");
    }

    public static Specification<UserEntity> hasPhone(String phone){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("phone"), "%" + phone + "%");
    }
    // Tìm nhưng người có tuổi lớn hơn
    public static Specification<UserEntity> isOlderThan(int age) {
        LocalDate maxBirthDate = LocalDate.now().minusYears(age);
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("dateOfBirth"), maxBirthDate);
    }

    // Tìm những người tuổi nhỏ hơn
    public static Specification<UserEntity> isYoungerThan(int age){
        LocalDate minBirthDate = LocalDate.now().minusYears(age + 1);
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("dateOfBirth"), minBirthDate));
    }

    public static Specification<UserEntity> isAgeBetween(int minAge, int maxAge){
        // Người dùng phải sinh sau ngày này để nhỏ hơn hoặc bằng minAge
        LocalDate maxBirthDate = LocalDate.now().minusYears(minAge + 1);
        // Người dùng phải sinh trước ngày này để lơn hơn hoặc bằng maxAge
        LocalDate minBirthDate = LocalDate.now().minusYears(maxAge);

        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("dateOfBirth"), minBirthDate, maxBirthDate);
    }

}
