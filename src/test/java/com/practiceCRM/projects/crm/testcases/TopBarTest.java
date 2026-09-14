package com.practiceCRM.projects.crm.testcases;

import com.practiceCRM.common.BaseTest;
import static com.practiceCRM.keywords.WebUI.*;
import com.practiceCRM.projects.crm.pages.CommonPageCRM;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("CRM Project")
@Feature("Top Bar Features")
public class TopBarTest extends BaseTest {

    private CommonPageCRM commonPage;

    @BeforeMethod(alwaysRun = true)
    public void loginAndNavigateToDashboard() {
        // Đăng nhập trước mỗi test case, lấy ra CommonPageCRM để sử dụng các hàm click Top Bar
        commonPage = getSignInPage().openDashboardPage();
    }

    @Test(priority = 1, description = "Kiểm tra icon To Do hiển thị và có thể click")
    @Story("Top Bar Navigation")
    public void testTopbarTodo() {
        verifyElementPresent(commonPage.topbarBtnTodo, 5, "Nút To Do không hiển thị");
        commonPage.clickTopbarTodo();
    }

    @Test(priority = 2, description = "Kiểm tra icon Starred Projects hiển thị và có thể mở dropdown")
    @Story("Top Bar Navigation")
    public void testTopbarStarredProjects() {
        verifyElementPresent(commonPage.topbarBtnStarredProjects, 5, "Nút Starred Projects không hiển thị");
        commonPage.clickTopbarStarredProjects();
    }

    @Test(priority = 3, description = "Kiểm tra icon Starred Clients hiển thị và có thể mở dropdown")
    @Story("Top Bar Navigation")
    public void testTopbarStarredClients() {
        verifyElementPresent(commonPage.topbarBtnStarredClients, 5, "Nút Starred Clients không hiển thị");
        commonPage.clickTopbarStarredClients();
    }

    @Test(priority = 4, description = "Kiểm tra icon Dashboards hiển thị và có thể mở dropdown")
    @Story("Top Bar Navigation")
    public void testTopbarDashboards() {
        verifyElementPresent(commonPage.topbarBtnDashboards, 5, "Nút Dashboards không hiển thị");
        commonPage.clickTopbarDashboards();
    }

    @Test(priority = 5, description = "Kiểm tra icon Global Search hiển thị và có thể mở modal")
    @Story("Top Bar Navigation")
    public void testTopbarGlobalSearch() {
        verifyElementPresent(commonPage.topbarBtnGlobalSearch, 5, "Nút Global Search không hiển thị");
        commonPage.clickTopbarGlobalSearch();
    }

    @Test(priority = 6, description = "Kiểm tra icon Quick Add hiển thị và có thể mở dropdown")
    @Story("Top Bar Navigation")
    public void testTopbarQuickAdd() {
        verifyElementPresent(commonPage.topbarBtnQuickAdd, 5, "Nút Quick Add không hiển thị");
        commonPage.clickTopbarQuickAdd();
        // Kiểm tra 1 menu con bên trong hiển thị
        verifyElementPresent(commonPage.topbarQuickAddTask, 5, "Nút Add Task bên trong Quick Add không hiển thị");
    }

    @Test(priority = 7, description = "Kiểm tra icon Language hiển thị và có thể mở dropdown")
    @Story("Top Bar Navigation")
    public void testTopbarLanguage() {
        verifyElementPresent(commonPage.topbarBtnLanguage, 5, "Nút Language không hiển thị");
        commonPage.clickTopbarLanguage();
    }

    @Test(priority = 8, description = "Kiểm tra icon Reminders hiển thị và có thể click")
    @Story("Top Bar Navigation")
    public void testTopbarReminders() {
        verifyElementPresent(commonPage.topbarBtnReminders, 5, "Nút Reminders không hiển thị");
        commonPage.clickTopbarReminders();
    }

    @Test(priority = 9, description = "Kiểm tra icon Notifications hiển thị và có thể mở dropdown")
    @Story("Top Bar Navigation")
    public void testTopbarNotifications() {
        verifyElementPresent(commonPage.topbarBtnNotifications, 5, "Nút Notifications không hiển thị");
        commonPage.clickTopbarNotifications();
    }

    @Test(priority = 10, description = "Kiểm tra icon Messages hiển thị và có thể mở dropdown")
    @Story("Top Bar Navigation")
    public void testTopbarMessages() {
        verifyElementPresent(commonPage.topbarBtnMessages, 5, "Nút Messages không hiển thị");
        commonPage.clickTopbarMessages();
    }

    @Test(priority = 11, description = "Kiểm tra User Profile dropdown hiển thị và có nút Sign Out")
    @Story("Top Bar Navigation")
    public void testTopbarUserProfile() {
        verifyElementPresent(commonPage.dropdownAccount, 5, "Nút User Profile không hiển thị");
        commonPage.signOut();
    }
}
