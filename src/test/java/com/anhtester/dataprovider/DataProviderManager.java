/*
 * Copyright (c) 2022. Anh Tester
 * Automation Framework Selenium
 */

package com.anhtester.dataprovider;

import java.util.Hashtable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.anhtester.constants.FrameworkConstants;
import com.anhtester.helpers.ExcelHelpers;
import com.anhtester.helpers.PropertiesHelpers;
import com.anhtester.helpers.SystemHelpers;
import com.anhtester.projects.crm.models.ClientModel;
import com.anhtester.projects.crm.models.SignInModel;

/**
 * DataProviderManager là class đóng vai trò cung cấp dữ liệu (Test Data) cho
 * các hàm Test.
 * Sử dụng tính năng @DataProvider của TestNG.
 * Dữ liệu sẽ được kéo từ file Excel (thông qua ExcelHelpers) và chuyển thành
 * Hashtable (Key-Value)
 * để truyền trực tiếp vào các tham số của hàm @Test.
 */
public final class DataProviderManager {

    private DataProviderManager() {
        super();
        PropertiesHelpers.loadAllFiles();
    }

    @Test(dataProvider = "getSignInDataHashTable")
    public void testGetSignInData(Hashtable<String, String> data) {
        System.out.println("signInData.testCaseName = " + data.get(SignInModel.getTestCaseName()));
        System.out.println("signInData.username = " + data.get(SignInModel.getEmail()));
        System.out.println("signInData.password = " + data.get(SignInModel.getPassword()));
        System.out.println("signInData.expectedTitle = " + data.get(SignInModel.getExpectedTitle()));
        System.out.println("signInData.expectedError = " + data.get(SignInModel.getExpectedError()));

    }

    @Test(dataProvider = "getClientDataHashTable")
    public void testGetClientData(Hashtable<String, String> data) {
        System.out.println("clientData.TestCaseName = " + data.get(ClientModel.getTestCaseName()));
        System.out.println("clientData.CompanyName = " + data.get(ClientModel.getCompanyName()));
        System.out.println("clientData.OWNER = " + data.get(ClientModel.getOwner()));
        System.out.println("clientData.Address = " + data.get(ClientModel.getAddress()));
        System.out.println("clientData.CITY = " + data.get(ClientModel.getCity()));
        System.out.println("clientData.STATE = " + data.get(ClientModel.getState()));

    }

    /**
     * Nguồn dữ liệu (DataProvider) cho hàm Test Đăng nhập.
     * Thuộc tính `parallel = true` cho phép chạy nhiều luồng dữ liệu cùng lúc nếu
     * TestNG XML cấu hình parallel data-provider.
     * 
     * @return Object[][] chứa cấu trúc Hashtable mapping giữa Tên_Cột và Giá_trị.
     */
    @DataProvider(name = "getSignInDataHashTable", parallel = true)
    public static Object[][] getSignInData() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        // Lấy data từ dòng 1 đến 2 của sheet "SignIn" trong file EXCEL_DATA_FILE_PATH
        Object[][] data = excelHelpers.getDataHashTable(
                SystemHelpers.getCurrentDir() + FrameworkConstants.EXCEL_DATA_FILE_PATH, "SignIn", 1, 2);
        System.out.println(" : " + data);
        return data;
    }

    /**
     * Nguồn dữ liệu (DataProvider) cho hàm Test thêm Client.
     * `parallel = false` (chạy tuần tự).
     */
    @DataProvider(name = "getClientDataHashTable", parallel = false)
    public static Object[][] getClientData() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        // Lấy data dòng 1 của sheet "Client"
        Object[][] data = excelHelpers.getDataHashTable(
                SystemHelpers.getCurrentDir() + FrameworkConstants.EXCEL_DATA_FILE_PATH, "Client", 1, 1);
        System.out.println("getClientData: " + data);
        return data;
    }

}
