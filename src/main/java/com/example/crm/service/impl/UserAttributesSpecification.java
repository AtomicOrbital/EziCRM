package com.example.crm.service.impl;

import com.example.crm.entity.UserAttributesEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserAttributesSpecification {
    public static Specification<UserAttributesEntity> hasName(String name){
        return (root, query, criteriaBuilder) -> {
            if(name == null) return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
//        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }

    public static Specification<UserAttributesEntity> hasValue(String value){
        return (root, query, criteriaBuilder) -> {
            if(value == null) return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            return criteriaBuilder.like(root.get("value"), "%" + value + "%");
        };
//        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("value"), value);
    }
}
