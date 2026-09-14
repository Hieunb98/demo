package com.practiceCRM.projects.crm.testcases;

import com.practiceCRM.common.BaseTest;
import static com.practiceCRM.keywords.WebUI.*;
import com.practiceCRM.projects.crm.pages.Dashboard.DashboardPageCRM;
import com.practiceCRM.enums.FailureHandling;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * DashboardTest - Test class cho module Dashboard của RISE CRM.
 * Bao phủ 37 test cases từ testcases_dashboard.csv.
 * Extends BaseTest để tái sử dụng khởi tạo WebDriver + Allure listener.
 */
@Epic("Regression Test CRM")
@Feature("Dashboard Test")
public class DashboardTest extends BaseTest {

        private DashboardPageCRM dashboardPage;

        @BeforeMethod(alwaysRun = true)
        public void loginAndNavigateToDashboard() {
                // Đăng nhập trước mỗi test case bằng hàm đọc từ Excel mới tạo
                dashboardPage = getSignInPage().openDashboardPage();
        }

        // =================================================================
        // NHÓM 1: SIDEBAR NAVIGATION (TC_001 → TC_010)
        // =================================================================

        @Test(priority = 1, groups = {
                        "sidebar" }, description = "CRM_DASHBOARD_TC_001 - Kiểm tra điều hướng Sidebar đến trang Dashboard")
        @Story("Sidebar Navigation")
        public void testNavigateSidebarToDashboard() {
                clickMenuDashboard();
        }

        @Test(priority = 2, groups = {
                        "sidebar" }, description = "CRM_DASHBOARD_TC_002 - Kiểm tra điều hướng Sidebar đến trang Events")
        @Story("Sidebar Navigation")
        public void testNavigateSidebarToEvents() {
                clickMenuEvents();
        }

        @Test(priority = 3, groups = {
                        "sidebar" }, description = "CRM_DASHBOARD_TC_003 - Kiểm tra điều hướng Sidebar đến trang Clients")
        @Story("Sidebar Navigation")
        public void testNavigateSidebarToClients() {
                clickMenuClients();
        }

        @Test(priority = 4, groups = {
                        "sidebar" }, description = "CRM_DASHBOARD_TC_004 - Kiểm tra điều hướng Sidebar đến trang Projects")
        @Story("Sidebar Navigation")
        public void testNavigateSidebarToProjects() {
                clickMenuProjects();
        }

        @Test(priority = 5, groups = {
                        "sidebar" }, description = "CRM_DASHBOARD_TC_005 - Kiểm tra điều hướng Sidebar đến trang Tasks")
        @Story("Sidebar Navigation")
        public void testNavigateSidebarToTasks() {
                clickMenuTasks();
        }

        @Test(priority = 6, groups = {
                        "sidebar" }, description = "CRM_DASHBOARD_TC_006 - Kiểm tra điều hướng Sidebar đến trang Leads")
        @Story("Sidebar Navigation")
        public void testNavigateSidebarToLeads() {
                clickMenuLeads();
        }

        @Test(priority = 7, groups = {
                        "sidebar" }, description = "CRM_DASHBOARD_TC_007 - Kiểm tra điều hướng Sidebar đến trang Tickets")
        @Story("Sidebar Navigation")
        public void testNavigateSidebarToTickets() {
                clickMenuTickets();
        }

        @Test(priority = 8, groups = {
                        "sidebar" }, description = "CRM_DASHBOARD_TC_008 - Kiểm tra điều hướng Sidebar sub-menu Sales > Invoices")
        @Story("Sidebar Navigation")
        public void testNavigateSidebarSalesToInvoices() {
                clickMenuSalesInvoices();
        }

        @Test(priority = 9, groups = {
                        "sidebar" }, description = "CRM_DASHBOARD_TC_009 - Kiểm tra điều hướng Sidebar sub-menu Team > Team members")
        @Story("Sidebar Navigation")
        public void testNavigateSidebarTeamToTeamMembers() {
                clickMenuTeamMembers();
        }

        @Test(priority = 10, groups = {
                        "sidebar" }, description = "CRM_DASHBOARD_TC_010 - Kiểm tra điều hướng Sidebar sub-menu Recruitments > Candidates")
        @Story("Sidebar Navigation")
        public void testNavigateSidebarRecruitmentsToCandidate() {
                clickMenuRecruitmentCandidates();
        }

        // =================================================================
        // NHÓM 2: TOP BAR CLOCK IN/OUT (TC_011 → TC_012)
        // =================================================================

        @Test(priority = 11, groups = {
                        "topbar" }, description = "CRM_DASHBOARD_TC_011 - Kiểm tra nút Clock Out hiển thị trên Top Bar khi đã Clock In")
        @Story("Top Bar Clock In/Out")
        public void testClockOutButtonDisplayed() {
                // Verify nút Clock Out đang hiển thị (nghĩa là đang trong trạng thái Clocked
                // In)
                verifyElementPresent(dashboardPage.btnClockOut, 5,
                                "TC_011: Nút Clock Out không hiển thị trên Top Bar.");
                verifyElementPresent(dashboardPage.textClockStarted, 5,
                                "TC_011: Text 'Clock started at' không hiển thị sau khi Clock In.");
        }

        @Test(priority = 12, groups = {
                        "topbar" }, description = "CRM_DASHBOARD_TC_012 - Kiểm tra trạng thái Clock In được ghi nhận trên Top Bar")
        @Story("Top Bar Clock In/Out")
        public void testClockStartedTextDisplayed() {
                String clockText = getTextElement(dashboardPage.textClockStarted);
                verifyContains(clockText, "Clock started at",
                                "TC_012: Text trạng thái Clock In không đúng: " + clockText);
        }

        // =================================================================
        // NHÓM 3: TOP BAR WIDGETS (TC_013 → TC_018)
        // =================================================================

        @Test(priority = 13, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_013 - Kiểm tra widget 'My open tasks' hiển thị số nguyên")
        @Story("Top Bar Widgets")
        public void testMyOpenTasksCountIsInteger() {
                String count = dashboardPage.getMyOpenTasksCount();
                verifyTrue(count != null && count.matches("\\d+"),
                                "TC_013: My open tasks count không phải số nguyên: " + count);
        }

        @Test(priority = 14, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_014 - Kiểm tra click widget 'My open tasks' điều hướng đúng")
        @Story("Top Bar Widgets")
        public void testClickMyOpenTasksNavigates() {
                dashboardPage.clickWidgetMyOpenTasks();
                verifyContains(getCurrentUrl(), getTaskPage().pageUrl,
                                "TC_014: Click My open tasks không điều hướng đến " + getTaskPage().pageUrl);
        }

        @Test(priority = 15, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_015 - Kiểm tra widget 'Events today' hiển thị số nguyên")
        @Story("Top Bar Widgets")
        public void testEventsTodayCountIsInteger() {
                String count = dashboardPage.getEventsTodayCount();
                verifyTrue(count != null && count.matches("\\d+"),
                                "TC_015: Events today count không phải số nguyên: " + count);
        }

        @Test(priority = 16, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_016 - Kiểm tra click widget 'Events today' điều hướng đúng")
        @Story("Top Bar Widgets")
        public void testClickEventsTodayNavigates() {
                dashboardPage.clickWidgetEventsToday();
                verifyContains(getCurrentUrl(), getEventPage().pageUrl,
                                "TC_016: Click Events today không điều hướng đến " + getEventPage().pageUrl);
        }

        @Test(priority = 17, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_017 - Kiểm tra widget 'Due' hiển thị đúng định dạng tiền tệ")
        @Story("Top Bar Widgets")
        public void testDueAmountIsMonetary() {
                String amount = dashboardPage.getDueAmount();
                verifyTrue(amount != null && amount.startsWith("$"),
                                "TC_017: Due amount không theo định dạng tiền tệ ($): " + amount);
        }

        @Test(priority = 18, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_018 - Kiểm tra click widget 'Due' điều hướng đúng")
        @Story("Top Bar Widgets")
        public void testClickDueNavigates() {
                dashboardPage.clickWidgetDue();
                verifyContains(getCurrentUrl(), getInvoicePage().pageUrl,
                                "TC_018: Click Due không điều hướng đến " + getInvoicePage().pageUrl);
        }

        // =================================================================
        // NHÓM 4: PROJECTS OVERVIEW WIDGET (TC_019)
        // =================================================================

        @Test(priority = 19, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_019 - Kiểm tra widget Projects Overview hiển thị đủ 3 trạng thái")
        @Story("Projects Overview Widget")
        public void testProjectsOverviewDisplaysAllStatuses() {
                dashboardPage.isProjectsOverviewDisplayed();
                verifyElementPresent(dashboardPage.projectsOpenCount, 5,
                                "TC_019: Số lượng Projects Open không hiển thị.", FailureHandling.CONTINUE_ON_FAILURE);
                verifyElementPresent(dashboardPage.projectsCompletedCount, 5,
                                "TC_019: Số lượng Projects Completed không hiển thị.",
                                FailureHandling.CONTINUE_ON_FAILURE);
                verifyElementPresent(dashboardPage.projectsHoldCount, 5,
                                "TC_019: Số lượng Projects Hold không hiển thị.", FailureHandling.CONTINUE_ON_FAILURE);
                stopSoftAssertAll();
        }

        // =================================================================
        // NHÓM 5: INVOICE OVERVIEW WIDGET (TC_020 → TC_026)
        // =================================================================

        @Test(priority = 20, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_020 - Kiểm tra widget Invoice Overview hiển thị đủ 5 loại trạng thái")
        @Story("Invoice Overview Widget")
        public void testInvoiceOverviewDisplaysAllStatuses() {
                dashboardPage.isInvoiceOverviewDisplayed();
                verifyElementPresent(dashboardPage.invoiceLinkOverdue, 5,
                                "TC_020: Link Overdue không hiển thị.", FailureHandling.CONTINUE_ON_FAILURE);
                verifyElementPresent(dashboardPage.invoiceLinkNotPaid, 5,
                                "TC_020: Link Not paid không hiển thị.", FailureHandling.CONTINUE_ON_FAILURE);
                verifyElementPresent(dashboardPage.invoiceLinkPartiallyPaid, 5,
                                "TC_020: Link Partially paid không hiển thị.", FailureHandling.CONTINUE_ON_FAILURE);
                verifyElementPresent(dashboardPage.invoiceLinkFullyPaid, 5,
                                "TC_020: Link Fully paid không hiển thị.", FailureHandling.CONTINUE_ON_FAILURE);
                verifyElementPresent(dashboardPage.invoiceLinkDraft, 5,
                                "TC_020: Link Draft không hiển thị.", FailureHandling.CONTINUE_ON_FAILURE);
                stopSoftAssertAll();
        }

        @Test(priority = 21, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_021 - Kiểm tra Invoice Overview hiển thị Total invoiced và Due")
        @Story("Invoice Overview Widget")
        public void testInvoiceOverviewTotalAndDue() {
                verifyElementPresent(dashboardPage.invoiceTotalInvoicedLabel, 5,
                                "TC_021: Label 'Total invoiced' không hiển thị.", FailureHandling.CONTINUE_ON_FAILURE);
                verifyElementPresent(dashboardPage.invoiceDueLabel, 5,
                                "TC_021: Label 'Due' trong Invoice Overview không hiển thị.",
                                FailureHandling.CONTINUE_ON_FAILURE);
                stopSoftAssertAll();
        }

        @Test(priority = 22, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_022 - Kiểm tra click 'Overdue' điều hướng đúng")
        @Story("Invoice Overview Widget")
        public void testClickInvoiceOverdueNavigates() {
                dashboardPage.clickInvoiceOverdue();
                verifyContains(getCurrentUrl(), "overdue",
                                "TC_022: Click Overdue không điều hướng đến trang Invoices filter overdue.");
        }

        @Test(priority = 23, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_023 - Kiểm tra click 'Not paid' điều hướng đúng")
        @Story("Invoice Overview Widget")
        public void testClickInvoiceNotPaidNavigates() {
                dashboardPage.clickInvoiceNotPaid();
                verifyContains(getCurrentUrl(), "not_paid",
                                "TC_023: Click Not paid không điều hướng đúng.");
        }

        @Test(priority = 24, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_024 - Kiểm tra click 'Partially paid' điều hướng đúng")
        @Story("Invoice Overview Widget")
        public void testClickInvoicePartiallyPaidNavigates() {
                dashboardPage.clickInvoicePartiallyPaid();
                verifyContains(getCurrentUrl(), "partially_paid",
                                "TC_024: Click Partially paid không điều hướng đúng.");
        }

        @Test(priority = 25, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_025 - Kiểm tra click 'Fully paid' điều hướng đúng")
        @Story("Invoice Overview Widget")
        public void testClickInvoiceFullyPaidNavigates() {
                dashboardPage.clickInvoiceFullyPaid();
                verifyContains(getCurrentUrl(), "fully_paid",
                                "TC_025: Click Fully paid không điều hướng đúng.");
        }

        @Test(priority = 26, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_026 - Kiểm tra click 'Draft' điều hướng đúng")
        @Story("Invoice Overview Widget")
        public void testClickInvoiceDraftNavigates() {
                dashboardPage.clickInvoiceDraft();
                verifyContains(getCurrentUrl(), "draft",
                                "TC_026: Click Draft không điều hướng đúng.");
        }

        // =================================================================
        // NHÓM 6: INCOME VS EXPENSES WIDGET (TC_027 → TC_028)
        // =================================================================

        @Test(priority = 27, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_027 - Kiểm tra widget Income vs Expenses hiển thị dữ liệu năm nay")
        @Story("Income vs Expenses Widget")
        public void testIncomeExpensesThisYearDisplayed() {
                verifyElementPresent(dashboardPage.widgetIncomeExpenses, 5,
                                "TC_027: Widget Income vs Expenses không hiển thị.");
                verifyElementPresent(dashboardPage.incomeExpensesThisYear, 5,
                                "TC_027: Mục 'This Year' không hiển thị.");
        }

        @Test(priority = 28, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_028 - Kiểm tra widget Income vs Expenses hiển thị dữ liệu năm ngoái")
        @Story("Income vs Expenses Widget")
        public void testIncomeExpensesLastYearDisplayed() {
                verifyElementPresent(dashboardPage.incomeExpensesLastYear, 5,
                                "TC_028: Mục 'Last Year' không hiển thị trong widget Income vs Expenses.");
        }

        // =================================================================
        // NHÓM 7: ALL TASKS OVERVIEW WIDGET (TC_029)
        // =================================================================

        @Test(priority = 29, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_029 - Kiểm tra widget All Tasks Overview hiển thị đủ các trạng thái")
        @Story("All Tasks Overview Widget")
        public void testAllTasksOverviewDisplaysAllStatuses() {
                dashboardPage.isAllTasksOverviewDisplayed();
                verifyElementPresent(dashboardPage.tasksLinkToDo, 5,
                                "TC_029: Link 'To do' không hiển thị.");
                verifyElementPresent(dashboardPage.tasksLinkDone, 5,
                                "TC_029: Link 'Done' không hiển thị.");
                verifyElementPresent(dashboardPage.tasksLinkExpired, 5,
                                "TC_029: Link 'Expired' không hiển thị.");
        }

        // =================================================================
        // NHÓM 8: TEAM MEMBERS OVERVIEW WIDGET (TC_030)
        // =================================================================

        @Test(priority = 30, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_030 - Kiểm tra widget Team Members Overview hiển thị đầy đủ thông tin")
        @Story("Team Members Overview Widget")
        public void testTeamMembersOverviewDisplayed() {
                dashboardPage.isTeamMembersOverviewDisplayed();
                verifyElementPresent(dashboardPage.teamMembersCountLink, 5,
                                "TC_030: Link Team members count không hiển thị.");
                verifyElementPresent(dashboardPage.teamOnLeaveTodayLink, 5,
                                "TC_030: Link On leave today không hiển thị.");
                verifyElementPresent(dashboardPage.teamClockedInLink, 5,
                                "TC_030: Link Clocked In không hiển thị.");
                verifyElementPresent(dashboardPage.teamClockedOutLink, 5,
                                "TC_030: Link Clocked Out không hiển thị.");
        }

        // =================================================================
        // NHÓM 9: TICKET STATUS WIDGET (TC_031 → TC_032)
        // =================================================================

        @Test(priority = 31, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_031 - Kiểm tra widget Ticket Status hiển thị đủ 3 loại vé")
        @Story("Ticket Status Widget")
        public void testTicketStatusDisplaysAllTypes() {
                dashboardPage.isTicketStatusDisplayed();
                verifyElementPresent(dashboardPage.ticketLinkNew, 5,
                                "TC_031: Link Ticket New không hiển thị.");
                verifyElementPresent(dashboardPage.ticketLinkOpen, 5,
                                "TC_031: Link Ticket Open không hiển thị.");
                verifyElementPresent(dashboardPage.ticketLinkClosed, 5,
                                "TC_031: Link Ticket Closed không hiển thị.");
        }

        @Test(priority = 32, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_032 - Kiểm tra click Ticket New điều hướng đúng")
        @Story("Ticket Status Widget")
        public void testClickTicketNewNavigates() {
                dashboardPage.clickTicketNew();
                verifyContains(getCurrentUrl(), "/tickets/index/new",
                                "TC_032: Click Ticket New không điều hướng đúng.");
        }

        // =================================================================
        // NHÓM 10: PROJECT TIMELINE & OPEN PROJECTS (TC_033 → TC_034)
        // =================================================================

        @Test(priority = 33, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_033 - Kiểm tra widget Project Timeline hiển thị lịch sử hoạt động")
        @Story("Project Timeline Widget")
        public void testProjectTimelineDisplayed() {
                dashboardPage.isProjectTimelineDisplayed();
                // Verify có ít nhất 1 entry activity trong timeline hoặc hiển thị trạng thái
                // rỗng
                verifyElementPresent(dashboardPage.timelineEntries, 5, "TC_033: Project Timeline không có entry nào.");
        }

        @Test(priority = 34, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_034 - Kiểm tra widget Open Projects hiển thị progress bar")
        @Story("Open Projects Widget")
        public void testOpenProjectsDisplayedWithProgressBar() {
                dashboardPage.isOpenProjectsDisplayed();
                verifyElementPresent(dashboardPage.openProjectsProgressBar, 5,
                                "TC_034: Open Projects không hiển thị Progress bar.");
        }

        // =================================================================
        // NHÓM 11: EMPTY STATE (TC_035 → TC_036)
        // =================================================================

        @Test(priority = 35, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_035 - Kiểm tra Empty State 'No event found!' khi không có sự kiện")
        @Story("Empty State")
        public void testEventsEmptyStateMessage() {
                // Dashboard hiện tại không có event → verify empty state hiển thị
                dashboardPage.isEventsEmptyStateDisplayed();
        }

        @Test(priority = 36, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_036 - Kiểm tra Empty State 'No announcement yet!'")
        @Story("Empty State")
        public void testAnnouncementsEmptyStateMessage() {
                dashboardPage.isAnnouncementsEmptyStateDisplayed();
        }

        // =================================================================
        // TC_037: LOGIN → DASHBOARD END-TO-END (đã được xử lý bởi @BeforeMethod)
        // Verify riêng tại đây: Sau login, tất cả widgets phải hiển thị đủ
        // =================================================================

        @Test(priority = 37, groups = {
                        "widgets" }, description = "CRM_DASHBOARD_TC_037 - Kiểm tra Dashboard hiển thị đầy đủ sau khi đăng nhập thành công")
        @Story("Login to Dashboard")
        public void testDashboardFullyLoadedAfterLogin() {
                // Verify URL đúng
                verifyContains(getCurrentUrl(), dashboardPage.pageUrl,
                                "TC_037: URL Dashboard sai sau khi đăng nhập.");
                // Verify đầy đủ các widget chính bằng Soft Assertion
                dashboardPage.verifyAllDashboardWidgetsPresent();
                stopSoftAssertAll();
        }
}
