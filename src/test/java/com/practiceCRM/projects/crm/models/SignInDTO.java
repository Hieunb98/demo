package com.practiceCRM.projects.crm.models;

import com.poiji.annotation.ExcelCellName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SignInDTO - Data Transfer Object cho dữ liệu đăng nhập từ file Excel.
 * Được ánh xạ tự động thông qua thư viện Poiji (@ExcelCellName).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInDTO {

    @ExcelCellName("TESTCASENAME")
    private String testCaseName;

    @ExcelCellName("EMAIL")
    private String email;

    @ExcelCellName("PASSWORD")
    private String password;

    @ExcelCellName("EXPECTED_TITLE")
    private String expectedTitle;

    @ExcelCellName("EXPECTED_ERROR")
    private String expectedError;

    @ExcelCellName("EXPECTED_URL")
    private String expectedUrl;
}
