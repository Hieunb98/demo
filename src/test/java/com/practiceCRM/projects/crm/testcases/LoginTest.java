package com.practiceCRM.projects.crm.testcases;

import com.practiceCRM.common.BaseTest;
import com.practiceCRM.dataprovider.DataProviderSignIn;
import com.practiceCRM.projects.crm.models.SignInDTO;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Epic("Regression Test CRM")
@Feature("Login Test")
public class LoginTest extends BaseTest {

    @Test(priority = 1, description = "Login_Happy_Case", dataProvider = "getSignInSuccessWithData", dataProviderClass = DataProviderSignIn.class)
    public void signInSuccessWithDataProvider(SignInDTO data) {
        getSignInPage().signInSuccess(data);
    }

    @Test(priority = 2, description = "Login_Error_Case", dataProvider = "getSignInFalseWithData", dataProviderClass = DataProviderSignIn.class)
    public void signInFalseWithDataProvider(SignInDTO data) {
        getSignInPage().signInFalse(data);
    }
}
