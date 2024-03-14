package com.example.crm.controller;

import com.example.crm.payload.BaseResponse;
import com.example.crm.payload.request.UserRequest;
import com.example.crm.payload.response.UserResponse;
import com.example.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<BaseResponse> getAllUsers(){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<UserResponse> userResponses = userService.getAllUsers();
            baseResponse.setStatus(200);
            baseResponse.setMessage("Users fetched successfully");
            baseResponse.setData(userResponses);
            return ResponseEntity.ok(baseResponse);
        } catch(Exception e){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getUserById(@PathVariable Long id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            UserResponse userResponse = userService.getUserById(id);
            baseResponse.setMessage("Users fetch successfully");
            baseResponse.setData(userResponse);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PostMapping
    public ResponseEntity<BaseResponse> createUser(@RequestBody UserRequest userRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            UserResponse userResponse = userService.createUser(userRequest);
            baseResponse.setStatus(201);
            baseResponse.setMessage("User added Successfully");
            baseResponse.setData(userResponse);
            return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
        }catch (Exception e){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            UserResponse userResponse = userService.updatedUser(id, userRequest);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("User updated successfully");
            baseResponse.setData(userResponse);
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteUser(@PathVariable Long id) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            userService.deleteUser(id);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("User deleted successfully");
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

}
