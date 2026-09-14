package com.practiceCRM.dataprovider;

import static com.practiceCRM.constants.FrameworkConstants.*;

import org.testng.annotations.DataProvider;
import com.practiceCRM.constants.FrameworkConstants;
import com.practiceCRM.helpers.ExcelHelpers;
import com.practiceCRM.helpers.SystemHelpers;

public final class DataProviderSignIn {

    private DataProviderSignIn() {
        super();
    }

    @DataProvider(name = "getSignInSuccessWithData", parallel = true)
    public static Object[][] getSignInSuccessWithData() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        return excelHelpers.getDataHashTable(
                SystemHelpers.getCurrentDir() + EXCEL_CRM_LOGIN, "SignIn", 1, 4);
    }

    @DataProvider(name = "getSignInFalseWithData", parallel = true)
    public static Object[][] getSignInFalseWithData() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        return excelHelpers.getDataHashTable(
                SystemHelpers.getCurrentDir() + EXCEL_CRM_LOGIN, "SignIn", 5, 12);
    }
}
