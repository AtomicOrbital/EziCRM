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

@Service
public class ExcelDataService {
    @Autowired
    private UserRepository userRepository;

    public static boolean isValidExcelFile(MultipartFile file){
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
    }

    private static String getCellValueAsString(Cell cell) {
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
                    return Double.toString(cell.getNumericCellValue());
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
            XSSFSheet sheet = workbook.getSheet("users");
            int rowIndex = 0;
            for(Row row : sheet){
                if(rowIndex == 0){
                    rowIndex++;
                    continue;
                }
//                Iterator<Cell> cellIterator = row.iterator();
                UserEntity userEntity = new UserEntity();

                for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
                    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String cellValue = getCellValueAsString(cell);
                    switch (cellIndex) {
                        case 0:
                            userEntity.setUsername(getCellValueAsString(cell));
                            break;
                        case 1:
                            userEntity.setAddress(cell.getStringCellValue());
                            break;
                        case 2:
                            try {
                                LocalDate localDate = LocalDate.parse(cellValue, formatter);
                                userEntity.setDateOfBirth(localDate);
                            } catch (DateTimeParseException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            userEntity.setEmail(cell.getStringCellValue());
                            break;
                        case 4:
                            userEntity.setPhone(cell.getStringCellValue());
                            break;

                    }
                }
                userEntities.add(userEntity);
            }
        }catch (IOException e){
            e.getStackTrace();
        }
        return userEntities;
    }



    public void saveUserToDatabase(MultipartFile file){
        if(isValidExcelFile(file)){
            try {
                List<UserEntity> userEntities = getUsersDataFromExcel(file.getInputStream());
                userRepository.saveAll(userEntities);
            }catch (IOException e){
                throw new IllegalArgumentException("The file is not a valid excel file");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
