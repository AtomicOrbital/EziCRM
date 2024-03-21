package com.example.crm.payload.request;

import com.example.crm.payload.response.RoleResponse;
import com.example.crm.valid.DateOfBirthContraint;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class UserRequest {
    @NotBlank(message = "username cannot be empty")
    @Size(min = 3, max = 50, message = "username must be between 3 and 50 characters")
    private String username;
    @NotBlank(message = "Address cannot be empty")
    private String address;
//    private String password;
    @NotNull(message = "Date of birth is required")
    @DateOfBirthContraint
    private LocalDate dateOfBirth;
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email invalidate ")
    private String email;
    @Pattern(regexp = "^\\d+$", message = "Phone number must be numeric")
    private String phone;

//    private Integer idRole;


    public UserRequest() {
    }

    public UserRequest(String username, String address, LocalDate dateOfBirth, String email, String phone) {
        this.username = username;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
