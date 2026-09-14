package com.practiceCRM.projects.crm.pages.Login;

import com.practiceCRM.constants.FrameworkConstants;
import com.practiceCRM.helpers.ExcelHelpers;

import static com.practiceCRM.keywords.WebUI.*;
import com.practiceCRM.projects.crm.pages.CommonPageCRM;
import com.practiceCRM.projects.crm.pages.Dashboard.DashboardPageCRM;
import com.practiceCRM.reports.AllureManager;
import com.practiceCRM.projects.crm.models.LogInModel;
import com.practiceCRM.utils.DecodeUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.Hashtable;

public class LoginPageCRM extends CommonPageCRM {

    public String pageUrl = "/signin";
    public String pageTitle = "Sign in | RISE CRM | Anh Tester Demo";

    // Locators (Priority 1: By.id, Priority 2: By.cssSelector)
    public By inputEmail = By.id("email");
    public By inputPassword = By.id("password");
    public By buttonSignIn = By.cssSelector("button[type='submit']");
    public By linkForgotPassword = By.xpath("//a[normalize-space()='Forgot password?']");
    public By linkSignUp = By.xpath("//a[normalize-space()='Sign up']");

    public By labelEmailError = By.id("email-error");
    public By labelPasswordError = By.id("password-error");
    public By alertErrorMessage = By.cssSelector(".alert-danger");

    @Step("Sign in with email and password")
    public DashboardPageCRM openDashboardPage() {
        ExcelHelpers excel = new ExcelHelpers();
        excel.setExcelFile(FrameworkConstants.EXCEL_CRM_LOGIN, "SignIn");
        openWebsite(FrameworkConstants.URL_CRM);
        verifyContains(getCurrentUrl(), pageUrl, "The url of sign in page not match.");
        verifyEquals(getPageTitle(), pageTitle, "The title of sign in page not match.");
        clearText(inputEmail);
        clearText(inputPassword);
        setText(inputEmail, excel.getCellData(LogInModel.getEmail(), 1));
        setText(inputPassword, DecodeUtils.decrypt(excel.getCellData(LogInModel.getPassword(), 1)));
        clickElement(buttonSignIn);
        waitForElementVisible(getDashboardPage().menuDashboard, 10);
        verifyContains(getCurrentUrl(), getDashboardPage().pageUrl,
                "Sign in failed. Can not redirect to Dashboard page.");

        return getDashboardPage();
    }

    @Step("Login thành công với data provider")
    public DashboardPageCRM signInSuccess(Hashtable<String, String> data) {
        AllureManager.saveTextLog("Run: " + data.get(LogInModel.getTestCaseName()));
        openWebsite(FrameworkConstants.URL_CRM);
        verifyContains(getCurrentUrl(), pageUrl, "The url of sign in page not match.");
        verifyEquals(getPageTitle(), pageTitle, "The title of sign in page not match.");
        clearText(inputEmail);
        clearText(inputPassword);
        setText(inputEmail, data.get(LogInModel.getEmail()));
        setText(inputPassword, DecodeUtils.decrypt(data.get(LogInModel.getPassword())));
        clickElement(buttonSignIn);
        waitForElementVisible(getDashboardPage().menuDashboard, 10);
        verifyContains(getCurrentUrl(), getDashboardPage().pageUrl,
                "Sign in failed. Can not redirect to Dashboard page.");

        return getDashboardPage();
    }

    @Step("Login thất bại với data provider")
    public void signInFalse(Hashtable<String, String> data) {
        AllureManager.saveTextLog("Run: " + data.get(LogInModel.getTestCaseName()));
        openWebsite(FrameworkConstants.URL_CRM);
        verifyContains(getCurrentUrl(), pageUrl, "The url of sign in page not match.");
        verifyEquals(getPageTitle(), pageTitle, "The title of sign in page not match.");
        clearText(inputEmail);
        clearText(inputPassword);
        setText(inputEmail, data.get(LogInModel.getEmail()));
        setText(inputPassword, DecodeUtils.decrypt(data.get(LogInModel.getPassword())));
        clickElement(buttonSignIn);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), data.get(LogInModel.getExpectedUrl()),
                "khi case false nhưng URL sai " + getCurrentUrl());
        switch (data.get(LogInModel.getExpectedError())) {
            case "This field is required.":
                boolean isEmailEmpty = data.get(LogInModel.getEmail()) == null
                        || data.get(LogInModel.getEmail()).isEmpty();
                boolean isPasswordEmpty = data.get(LogInModel.getPassword()) == null
                        || data.get(LogInModel.getPassword()).isEmpty();

                // Nếu Email để trống, check lỗi Email
                if (isEmailEmpty) {
                    verifyEquals(getTextElement(labelEmailError), data.get(LogInModel.getExpectedError()),
                            "Message lỗi Email không đúng!");
                }
                // Nếu Password để trống, check lỗi Password
                if (isPasswordEmpty) {
                    verifyEquals(getTextElement(labelPasswordError), data.get(LogInModel.getExpectedError()),
                            "Message lỗi Password không đúng!");
                }
                break;
            case "Authentication failed!":
                verifyEquals(getTextElement(alertErrorMessage), data.get(LogInModel.getExpectedError()),
                        "khi case false nhưng message sai " + getTextElement(alertErrorMessage));
                break;
            case "Please enter a valid email address.":
                verifyEquals(getTextElement(labelEmailError), data.get(LogInModel.getExpectedError()),
                        "khi case false nhưng message sai " + getTextElement(labelEmailError));
                break;
            default:
                break;
        }
    }

}
