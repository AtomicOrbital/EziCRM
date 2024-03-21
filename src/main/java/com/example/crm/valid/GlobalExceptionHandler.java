package com.example.crm.valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.crm.valid.ExcelUploadException;
import com.example.crm.payload.response.ErrorResponse;

import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Xử lý ExcelUploadException cụ thể
    @ExceptionHandler(ExcelUploadException.class)
    public ResponseEntity<Object> handleExcelUploadException(ExcelUploadException ex) {
        List<String> errors = ex.getErrorMessages();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Error processing Excel file", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    // Xử lý tất cả các ngoại lệ chưa được xác định cụ thể
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", Arrays.asList(ex.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
