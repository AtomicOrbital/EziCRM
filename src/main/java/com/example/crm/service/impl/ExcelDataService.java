package com.example.crm.service.impl;

import com.example.crm.entity.UserAttributesEntity;
import com.example.crm.entity.UserEntity;
import com.example.crm.repository.UserAttributesRepository;
import com.example.crm.repository.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
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

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d+$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z\\s]+$");

    private static boolean isValidEmail(String email){
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private static boolean isValidPhone(String phone){
        return PHONE_PATTERN.matcher(phone).matches();
    }

    private static boolean isValidUsername(String username){
        return USERNAME_PATTERN.matcher(username).matches();
    }

    public static boolean isValidExcelFile(MultipartFile file){
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
    }

    private static String getCellValueAsString(Cell cell) {
        DataFormatter formatter = new DataFormatter();
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // Đối với các ô chứa ngày tháng, chuyển đổi chúng thành định dạng chuỗi mong muốn
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    return dateFormatter.format(cell.getLocalDateTimeCellValue());
                } else {
                    // Đối với các ô chứa số, chuyển chúng thành định dạng chuỗi
//                    return Double.toString(cell.getNumericCellValue());
                    return formatter.formatCellValue(cell);
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
            case _NONE:
            case ERROR:
                return "";
            default:
                throw new IllegalStateException("Unexpected cell type: " + cell.getCellType());
        }
    }
    public static List<UserEntity> getUsersDataFromExcel(InputStream inputStream) throws Exception {
        List<UserEntity> userEntities = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();
            int rowIndex = 0;
            for(Row row : sheet){
                if(rowIndex == 0){
                    rowIndex++;
                    continue;
                }
                boolean isValidRow = false;
                UserEntity userEntity = new UserEntity();

                for (int cellIndex = 0; cellIndex < rowCount; cellIndex++) {
                    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String cellValue = getCellValueAsString(cell);
                    switch (cellIndex) {
                        case 0:
                            if(!cellValue.trim().isEmpty() && isValidUsername(cellValue)){
                                userEntity.setUsername(cellValue);
                                isValidRow = true;
                            } else {
                                System.err.println("Invalid username format: " + cellValue + " on row " + (row.getRowNum() + 1));
                            }
                            break;
                        case 1:
                            userEntity.setAddress(cellValue);
                            break;
                        case 2:
                            if(!cellValue.trim().isEmpty()){
                                try {
                                    LocalDate localDate = LocalDate.parse(cellValue, formatter);
                                    userEntity.setDateOfBirth(localDate);
                                } catch (DateTimeParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case 3:
                            String email = getCellValueAsString(cell);
                            if (!isValidEmail(email)) {
                                System.err.println("Invalid email format: " + email + " on row " + (row.getRowNum() + 1));
                                isValidRow = false;
                            } else {
                                userEntity.setEmail(email);
                            }
                            break;
                        case 4:
                            String phone = getCellValueAsString(cell);
                            if (!isValidPhone(phone)) {
                                System.err.println("Invalid phone format on row " + phone + " on row " + (row.getRowNum() + 1));
                                isValidRow = false;
                            } else {
                                userEntity.setPhone(phone);
                            }
                            break;
                        case 5:
                            userEntity.setCreatedAt(new Date());
                            break;
                    }
                }
                if(isValidRow){
                    userEntities.add(userEntity);
                }
            }
        }catch (IOException e){
            e.getStackTrace();
        }
        return userEntities;
    }



    public void saveUserToDatabase(MultipartFile file, List<Long> attributeIds) throws  Exception {
        if(isValidExcelFile(file)){
            try {
                List<UserEntity> userEntities = getUsersDataFromExcel(file.getInputStream());
                List<UserAttributesEntity> attributes = userAttributesRepository.findAllById(attributeIds);

                for(UserEntity user : userEntities){
                    List<UserAttributesEntity> userAttributes  = new ArrayList<>();
                    for (UserAttributesEntity attribute : attributes) {
                        UserAttributesEntity userAttribute = new UserAttributesEntity();
                        userAttribute.setUserEntity(user);
                        // gán nhóm thuộc tính
                        userAttribute.setAttributeGroupEntity(attribute.getAttributeGroupEntity());
                        userAttribute.setName(attribute.getName());
                        userAttribute.setValue(attribute.getValue());
                        userAttributes.add(userAttribute);
                    }
                    user.setUserAttributes(userAttributes);
                }
                userRepository.saveAll(userEntities);
            } catch (IOException e){
                throw new IllegalArgumentException("The file is not a valid excel file");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
