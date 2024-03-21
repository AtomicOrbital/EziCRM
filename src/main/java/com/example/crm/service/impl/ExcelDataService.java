package com.example.crm.service.impl;

import com.example.crm.entity.UserAttributesEntity;
import com.example.crm.entity.UserEntity;
import com.example.crm.repository.UserAttributesRepository;
import com.example.crm.repository.UserRepository;
import com.example.crm.valid.ExcelUploadException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class ExcelDataService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAttributesRepository userAttributesRepository;

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z\\s]+$");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^[A-Za-z0-9\\s.,-/]+$");

    private static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        int atPos = email.indexOf('@');
        int dotPos = email.lastIndexOf('.');
        return atPos > 0 && dotPos > atPos + 1 && dotPos < email.length() - 1;
    }

    private boolean isValidPhone(String phone) {
        if (phone.length() == 10 || phone.length() == 11) {
            for (int i = 0; i < phone.length(); i++) {
                if (!Character.isDigit(phone.charAt(i))) {
                    return false;
                }
            }
            return !userRepository.findByPhone(phone).isPresent();
        }
        return false;
    }

    private static boolean isValidUsername(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }

    private static boolean isValidAddress(String address) {
        return address != null && !address.trim().isEmpty() && ADDRESS_PATTERN.matcher(address).matches();
    }

    public static boolean isValidExcelFile(MultipartFile file) {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(file.getContentType());
    }

    private static String getCellValueAsString(Cell cell) {
        DataFormatter formatter = new DataFormatter();
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    return dateFormatter.format(cell.getLocalDateTimeCellValue());
                } else {
                    return formatter.formatCellValue(cell);
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private boolean isNotEmpty(UserEntity userEntity) {
        return userEntity.getUsername() != null && !userEntity.getUsername().trim().isEmpty()
                && userEntity.getAddress() != null && !userEntity.getAddress().trim().isEmpty()
                && userEntity.getEmail() != null && !userEntity.getEmail().trim().isEmpty()
                && userEntity.getPhone() != null && !userEntity.getPhone().trim().isEmpty();
    }

    private boolean rowIsEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }


    public List<UserEntity> getUsersDataFromExcel(InputStream inputStream) throws ExcelUploadException {
        List<UserEntity> userEntities = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int rowCount = sheet.getLastRowNum();
            int rowIndex = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) {
                    continue;
                }
                // Kiểm tra xem dòng có trống không trước khi xử lý
                if (rowIsEmpty(row)) {
                    continue;
                }
                boolean isValidRow = true;
                UserEntity userEntity = new UserEntity();
//                Iterator<Cell> cellIterator = row.iterator();
//                int cellIndex = 0;
                for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++){
                    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String cellValue = getCellValueAsString(cell);
                    switch (cellIndex) {
                        case 0: // Username
                            if (!isValidUsername(cellValue)) {
                                errorMessages.add("Invalid username format: " + cellValue + " on row " + (row.getRowNum() + 1));
                                isValidRow = false;
                            } else {
                                userEntity.setUsername(cellValue);
                            }
                            break;
                        case 1: // Address
                            if (!isValidAddress(cellValue)) {
                                errorMessages.add("Invalid address format: " + cellValue + " on row " + (row.getRowNum() + 1));
                                isValidRow = false;
                            } else {
                                userEntity.setAddress(cellValue);
                            }
                            break;
                        case 2: // Date of Birth
                            try {
                                LocalDate dateOfBirth  = LocalDate.parse(cellValue, formatter);
                                if (dateOfBirth.isAfter(LocalDate.now())) {
                                    errorMessages.add("Date of birth cannot be in the future: " + cellValue + " on row " + (row.getRowNum() + 1));
                                    isValidRow = false;
                                } else {
                                    userEntity.setDateOfBirth(dateOfBirth);
                                }
                            } catch (DateTimeParseException e) {
                                errorMessages.add("Invalid date of birth format: " + cellValue + " on row " + (row.getRowNum() + 1));
                                isValidRow = false;
                            }
                            break;
                        case 3: // Email
                            if (!isValidEmail(cellValue)) {
                                errorMessages.add("Invalid email format: " + cellValue + " on row " + (row.getRowNum() + 1));
                                isValidRow = false;
                            } else {
                                UserEntity existingUserByEmail = userRepository.findByEmail(cellValue);
                                if (existingUserByEmail != null) {
                                    errorMessages.add("Email already exists: " + cellValue + " on row " + (row.getRowNum() + 1));
                                    isValidRow = false;
                                } else {
                                    userEntity.setEmail(cellValue);
                                }
                            }
                            break;
                        case 4: // Phone
                            if (!isValidPhone(cellValue)) {
                                errorMessages.add("Invalid phone number: " + cellValue + " on row " + (row.getRowNum() + 1));
                                isValidRow = false;
                            } else {
                                userEntity.setPhone(cellValue);
                            }
                            break;
                        case 5: // Creation Date
                            userEntity.setCreatedAt(new Date());
                            break;

                    }
//                    cellIndex++;
                }
                if (isValidRow && isNotEmpty(userEntity)) {
                    userEntities.add(userEntity);
                }
            }
        } catch (IOException e) {
            throw new ExcelUploadException(Collections.singletonList("Failed to process Excel file: " + e.getMessage()));
        }

        if (!errorMessages.isEmpty()) {
            throw new ExcelUploadException(errorMessages);
        }

        return userEntities;
    }

    public void saveUserToDatabase(MultipartFile file, List<Long> attributeIds) throws ExcelUploadException, IOException {
        if (!isValidExcelFile(file)) {
            throw new ExcelUploadException(Collections.singletonList("The file is not a valid Excel file."));
        }

        List<UserEntity> userEntities = getUsersDataFromExcel(file.getInputStream());
        List<UserAttributesEntity> attributes = userAttributesRepository.findAllById(attributeIds);

        for (UserEntity user : userEntities) {
            List<UserAttributesEntity> userAttributes = new ArrayList<>();
            for (UserAttributesEntity attribute : attributes) {
                UserAttributesEntity userAttribute = new UserAttributesEntity();
                userAttribute.setUserEntity(user);
                userAttribute.setAttributeGroupEntity(attribute.getAttributeGroupEntity());
                userAttribute.setName(attribute.getName());
                userAttribute.setValue(attribute.getValue());
                userAttributes.add(userAttribute);
            }
            user.setUserAttributes(userAttributes);
        }
        userRepository.saveAll(userEntities);
    }
}