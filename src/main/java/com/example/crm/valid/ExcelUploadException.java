package com.example.crm.valid;

import java.util.List;

public class ExcelUploadException extends RuntimeException {
    private List<String> errorMessages;

    public ExcelUploadException(List<String> errorMessages) {
        super(String.join(", ", errorMessages));
        this.errorMessages = errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}

