package com.practiceCRM.projects.crm.testcases;

import com.practiceCRM.common.BaseTest;
import com.practiceCRM.dataprovider.DataProviderSignIn;
import com.practiceCRM.driver.DriverManager;
import com.practiceCRM.keywords.WebUI;
import com.practiceCRM.projects.crm.models.LogInModel;
import com.practiceCRM.projects.crm.pages.Dashboard.DashboardPageCRM;
import com.practiceCRM.projects.crm.pages.Login.LoginPageCRM;
import com.practiceCRM.reports.AllureManager;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import java.util.Hashtable;

@Epic("Regression Test CRM")
@Feature("Login Test")
public class LoginTest extends BaseTest {

    // Using library DataProvider with read Hashtable
    @Test(priority = 1, description = "Login_Happy_Case", dataProvider = "getSignInSuccessWithData", dataProviderClass = DataProviderSignIn.class)
    public void signInSuccessWithDataProvider(Hashtable<String, String> data) {
        getSignInPage().signInSuccess(data);
    }

    @Test(priority = 2, description = "Login_Error_Case", dataProvider = "getSignInFalseWithData", dataProviderClass = DataProviderSignIn.class)
    public void signInFalseWithDataProvider(Hashtable<String, String> data) {
        getSignInPage().signInFalse(data);
    }
}
