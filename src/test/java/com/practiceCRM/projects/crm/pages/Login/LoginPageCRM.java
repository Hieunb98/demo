package com.practiceCRM.projects.crm.pages.Login;

import com.practiceCRM.constants.FrameworkConstants;
import com.practiceCRM.helpers.ExcelHelpers;


import com.practiceCRM.projects.crm.pages.CommonPageCRM;
import com.practiceCRM.projects.crm.pages.Dashboard.DashboardPageCRM;
import com.practiceCRM.reports.AllureManager;
import com.practiceCRM.projects.crm.models.SignInDTO;
import com.practiceCRM.utils.DecodeUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

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
        SignInDTO adminUser = ExcelHelpers.getDataList(
                FrameworkConstants.EXCEL_CRM_LOGIN, "SignIn", SignInDTO.class).get(0);
        openWebsite(FrameworkConstants.URL_CRM);
        verifyContains(getCurrentUrl(), pageUrl, "The url of sign in page not match.");
        verifyEquals(getPageTitle(), pageTitle, "The title of sign in page not match.");
        clearText(inputEmail);
        clearText(inputPassword);
        if (adminUser.getEmail() != null && !adminUser.getEmail().isEmpty()) {
            setText(inputEmail, adminUser.getEmail());
        }
        if (adminUser.getPassword() != null && !adminUser.getPassword().isEmpty()) {
            setText(inputPassword, DecodeUtils.decrypt(adminUser.getPassword()));
        }
        clickElement(buttonSignIn);
        waitForElementVisible(getDashboardPage().menuDashboard, 10);
        verifyContains(getCurrentUrl(), getDashboardPage().pageUrl,
                "Sign in failed. Can not redirect to Dashboard page.");

        return getDashboardPage();
    }

    @Step("Login thành công với data provider")
    public DashboardPageCRM signInSuccess(SignInDTO data) {
        AllureManager.saveTextLog("Run: " + data.getTestCaseName());
        openWebsite(FrameworkConstants.URL_CRM);
        verifyContains(getCurrentUrl(), pageUrl, "The url of sign in page not match.");
        verifyEquals(getPageTitle(), pageTitle, "The title of sign in page not match.");
        clearText(inputEmail);
        clearText(inputPassword);
        if (data.getEmail() != null && !data.getEmail().isEmpty()) {
            setText(inputEmail, data.getEmail());
        }
        if (data.getPassword() != null && !data.getPassword().isEmpty()) {
            setText(inputPassword, DecodeUtils.decrypt(data.getPassword()));
        }
        clickElement(buttonSignIn);
        waitForElementVisible(getDashboardPage().menuDashboard, 10);
        verifyContains(getCurrentUrl(), getDashboardPage().pageUrl,
                "Sign in failed. Can not redirect to Dashboard page.");

        return getDashboardPage();
    }

    @Step("Login thất bại với data provider")
    public void signInFalse(SignInDTO data) {
        AllureManager.saveTextLog("Run: " + data.getTestCaseName());
        openWebsite(FrameworkConstants.URL_CRM);
        verifyContains(getCurrentUrl(), pageUrl, "The url of sign in page not match.");
        verifyEquals(getPageTitle(), pageTitle, "The title of sign in page not match.");
        clearText(inputEmail);
        clearText(inputPassword);
        if (data.getEmail() != null && !data.getEmail().isEmpty()) {
            setText(inputEmail, data.getEmail());
        }
        if (data.getPassword() != null && !data.getPassword().isEmpty()) {
            setText(inputPassword, DecodeUtils.decrypt(data.getPassword()));
        }
        clickElement(buttonSignIn);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), data.getExpectedUrl(),
                "khi case false nhưng URL sai " + getCurrentUrl());
        switch (data.getExpectedError() != null ? data.getExpectedError() : "") {
            case "This field is required.":
                boolean isEmailEmpty = data.getEmail() == null
                        || data.getEmail().isEmpty();
                boolean isPasswordEmpty = data.getPassword() == null
                        || data.getPassword().isEmpty();

                // Nếu Email để trống, check lỗi Email
                if (isEmailEmpty) {
                    verifyEquals(getTextElement(labelEmailError), data.getExpectedError(),
                            "Message lỗi Email không đúng!");
                }
                // Nếu Password để trống, check lỗi Password
                if (isPasswordEmpty) {
                    verifyEquals(getTextElement(labelPasswordError), data.getExpectedError(),
                            "Message lỗi Password không đúng!");
                }
                break;
            case "Authentication failed!":
                verifyEquals(getTextElement(alertErrorMessage), data.getExpectedError(),
                        "khi case false nhưng message sai " + getTextElement(alertErrorMessage));
                break;
            case "Please enter a valid email address.":
                verifyEquals(getTextElement(labelEmailError), data.getExpectedError(),
                        "khi case false nhưng message sai " + getTextElement(labelEmailError));
                break;
            default:
                break;
        }
    }

}
