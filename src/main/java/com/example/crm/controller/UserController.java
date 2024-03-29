package com.example.crm.controller;

import com.example.crm.payload.BaseResponse;
import com.example.crm.payload.request.UserRequest;
import com.example.crm.payload.response.ErrorResponse;
import com.example.crm.payload.response.UserResponse;
import com.example.crm.service.UserService;
import com.example.crm.service.impl.ExcelDataService;
import com.example.crm.valid.ExcelUploadException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ExcelDataService excelDataService;
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

    @GetMapping("/users/pageable")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
//            @RequestParam(value = "sort", defaultValue = "userId,asc") String[] sort
    ){
//        Sort sortParams = Sort.by(Arrays.stream(sort)
//                .map(sortStr -> {
//                    String[] s = sortStr.split(",");
//                    return new Sort.Order(Sort.Direction.fromString(s[1]), s[0]);
//                })
//                .collect(Collectors.toList()));

        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponse> userResponses = userService.getAllUsers(pageable);

        BaseResponse baseResponse = new BaseResponse();
        try {
            baseResponse.setStatus(200);
            baseResponse.setMessage("Users fetched successfully");
            baseResponse.setData(userResponses);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
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
            baseResponse.setStatus(200);
            baseResponse.setMessage("Users fetch successfully");
            baseResponse.setData(userResponse);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(
        @RequestParam(value = "username", required = false) String username,
        @RequestParam(value = "address", required = false) String address,
        @RequestParam(value = "email", required = false) String email,
        @RequestParam(value = "phone", required = false) String phone,
        @RequestParam(value = "minAge", required = false) Integer minAge,
        @RequestParam(value = "maxAge", required = false) Integer maxAge
    ){
        try{
            List<UserResponse> users = userService.searchUsers(username, address, email, phone, minAge, maxAge);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatus(200);
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(users);

            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi tìm kiếm : " + e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity<BaseResponse> createUser(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult){
        BaseResponse baseResponse = new BaseResponse();
        if(bindingResult.hasErrors()){
            String errorMessgae = bindingResult.getFieldError().getDefaultMessage();
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(errorMessgae);
            return ResponseEntity.badRequest().body(baseResponse);
        }
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

    @PostMapping("/import-excel")
    public ResponseEntity<?> importUsersFromExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam("attributeIds") List<Long> attributeIds
    ){
        try {
            excelDataService.saveUserToDatabase(file, attributeIds);
            return ResponseEntity.ok().body("File processed successfully");
        } catch (ExcelUploadException e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST, "Error processing file", e.getErrorMessages()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", Arrays.asList("An unexpected error occurred")));
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
