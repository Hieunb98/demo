package com.practiceCRM.dataprovider;

import static com.practiceCRM.constants.FrameworkConstants.*;

import com.practiceCRM.helpers.ExcelHelpers;
import com.practiceCRM.helpers.SystemHelpers;
import com.practiceCRM.projects.crm.models.SignInDTO;
import org.testng.annotations.DataProvider;

import java.util.List;

public final class DataProviderSignIn {

    private DataProviderSignIn() {
        super();
    }

    @DataProvider(name = "getSignInSuccessWithData", parallel = true)
    public static Object[][] getSignInSuccessWithData() {
        List<SignInDTO> list = ExcelHelpers.getDataList(
                SystemHelpers.getCurrentDir() + EXCEL_CRM_LOGIN, "SignIn", SignInDTO.class);
        return list.subList(0, Math.min(4, list.size())).stream()
                .map(dto -> new Object[]{dto})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "getSignInFalseWithData", parallel = true)
    public static Object[][] getSignInFalseWithData() {
        List<SignInDTO> list = ExcelHelpers.getDataList(
                SystemHelpers.getCurrentDir() + EXCEL_CRM_LOGIN, "SignIn", SignInDTO.class);
        int startIndex = 4;
        int endIndex = Math.min(12, list.size());
        return list.subList(startIndex, endIndex).stream()
                .map(dto -> new Object[]{dto})
                .toArray(Object[][]::new);
    }
}
