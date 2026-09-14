package com.practiceCRM.projects.crm.testcases.projects;

import com.practiceCRM.common.BaseTest;
import com.practiceCRM.driver.DriverManager;
import static com.practiceCRM.keywords.WebUI.*;
import com.practiceCRM.enums.FailureHandling;
import com.practiceCRM.projects.crm.models.ProjectModel;
import com.practiceCRM.projects.crm.pages.Projects.ProjectPageCRM;
import com.practiceCRM.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.LocalDate;

/**
 * BaseProjectTest - Lớp cơ sở quản lý Test Data fixtures và dọn dẹp dữ liệu
 * phục vụ cho toàn bộ các test class trong module Projects.
 */
public class BaseProjectTest extends BaseTest {

        // Quản lý trang Projects cho toàn bộ test methods kế thừa
        protected ProjectPageCRM projectPage;

        // FailureHandling mặc định cho các test case cần Soft Assert
        protected FailureHandling soft = FailureHandling.CONTINUE_ON_FAILURE;

        @BeforeMethod(alwaysRun = true)
        public void loginAndNavigateToProjects() {
                projectPage = getSignInPage().openDashboardPage().clickMenuProjects();
        }

        // =========================================================================
        // GLOBAL TEST DATA — tồn tại xuyên suốt toàn bộ suite test của Project
        // =========================================================================
        protected static String projectOpenWithLabel;
        protected static String projectOpenNoLabel;
        protected static String projectToDelete;
        protected static String projectCompleted;
        protected static String projectHold;
        protected static String projectCanceled;
        protected static String projectDoing;
        protected static String availableLabel;
        protected static String setupLabelColor;

        @Parameters("BROWSER")
        @BeforeTest(alwaysRun = true)
        public void setupTestData(@Optional("chrome") String browser) {
                createBrowser(browser);
                try {
                        // Đăng nhập vào Dashboard và điều hướng sang trang Projects qua menu sidebar
                        ProjectPageCRM setupPage = getSignInPage().openDashboardPage().clickMenuProjects();
                        long ts = System.currentTimeMillis();

                        // 1. Tạo label test mới kèm màu sắc ngẫu nhiên trước khi tạo project
                        setupLabelColor = ProjectModel.getRandomLabelColor();
                        setupPage.addNewLabelWithColor(ProjectModel.DATA_LABEL, setupLabelColor);
                        LogUtils.info("🏷️ [LABEL SETUP] Đã tạo label test mới: " + ProjectModel.DATA_LABEL + " (Màu: "
                                        + setupLabelColor + ")");
                        availableLabel = ProjectModel.DATA_LABEL;

                        LocalDate today = LocalDate.now();
                        String startDateThisMonth = today.toString(); // Rơi vào This Month, This Year, Next 7 Days
                        String startDateLastMonth = today.minusMonths(1).withDayOfMonth(15).toString(); // Rơi vào Last Month, This Year
                        String startDateThisYear = today.minusMonths(2).withDayOfMonth(10).toString(); // Rơi vào This Year
                        String startDateLastYear = today.minusYears(1).withDayOfMonth(10).toString(); // Rơi vào Last Year
                        String startDateNextMonth = today.plusMonths(1).withDayOfMonth(10).toString(); // Rơi vào Next Month

                        String deadlineExpired = today.minusDays(5).toString(); // Quá hạn (Expired)
                        String deadlineToday = today.toString(); // Hôm nay (Today)
                        String deadlineTomorrow = today.plusDays(1).toString(); // Ngày mai (Tomorrow)
                        String deadlineIn7Days = today.plusDays(5).toString(); // Nằm trong 7 ngày tới (In 7 days)
                        String deadlineIn15Days = today.plusDays(10).toString(); // Nằm trong 15 ngày tới (In 15 days)
                        String deadlineFuture = today.plusMonths(2).toString(); // Xa hơn 15 ngày

                        String prefix = ProjectModel.PREFIX_PROJECT;

                        // Project 1: Open + có Label → dùng cho Filter Status=Open, Filter Label, Filter Deadline Tomorrow, Filter Start date This Month
                        projectOpenWithLabel = prefix + "Open_Label_" + ts;
                        setupPage.addNewProject(projectOpenWithLabel, "", "", "5000",
                                        "Auto test data - Open with label", startDateThisMonth, deadlineTomorrow,
                                        availableLabel);

                        // Project 2: Open + không Label → dùng cho Filter empty label / search, Filter Deadline Today, Filter Start date This Month
                        projectOpenNoLabel = prefix + "Open_NoLabel_" + (ts + 1);
                        setupPage.addNewProject(projectOpenNoLabel, "", "", "7500",
                                        "Auto test data - Open no label", startDateThisMonth, deadlineToday, "");

                        // Project 3: Dùng riêng cho case Delete (tự tạo + tự xóa trong cleanup)
                        projectToDelete = prefix + "Delete_" + (ts + 2);
                        setupPage.addNewProject(projectToDelete, "", "", "1000", "Auto test data - for delete",
                                        startDateThisMonth, deadlineToday, availableLabel);

                        // Project 4: Status Completed + Deadline Expired + Start date Last Month
                        projectCompleted = prefix + "Completed_" + (ts + 3);
                        setupPage.addNewProject(projectCompleted, "", "", "2000", "Auto test data - Completed",
                                        startDateLastMonth, deadlineExpired, availableLabel);
                        setupPage.editProjectStatus(projectCompleted, ProjectModel.STATUS_COMPLETED);

                        // Project 5: Status Hold + Deadline In 7 days + Start date Last Year
                        projectHold = prefix + "Hold_" + (ts + 4);
                        setupPage.addNewProject(projectHold, "", "", "3000", "Auto test data - Hold", startDateLastYear,
                                        deadlineIn7Days, availableLabel);
                        setupPage.editProjectStatus(projectHold, ProjectModel.STATUS_HOLD);

                        // Project 6: Status Canceled + Deadline Future + Start date Next Month
                        projectCanceled = prefix + "Canceled_" + (ts + 5);
                        setupPage.addNewProject(projectCanceled, "", "", "4000", "Auto test data - Canceled",
                                        startDateNextMonth, deadlineFuture, availableLabel);
                        setupPage.editProjectStatus(projectCanceled, ProjectModel.STATUS_CANCELED);

                        // Project 7: Status Doing + Deadline In 15 days + Start date This Year
                        projectDoing = prefix + "Doing_" + (ts + 6);
                        setupPage.addNewProject(projectDoing, "", "", "4500", "Auto test data - Doing",
                                        startDateThisYear, deadlineIn15Days, availableLabel);
                        setupPage.editProjectStatus(projectDoing, ProjectModel.STATUS_DOING);

                        // =========================================================================
                        // GATEKEEPER CHECK: Xác nhận dữ liệu đầu vào đã sẵn sàng trước khi chạy suite
                        // =========================================================================
                        setupPage.searchProject(projectOpenWithLabel);
                        boolean isDataReady = isElementPresent(setupPage.getProjectLocatorByTitle(projectOpenWithLabel),
                                        5);
                        setupPage.clearSearch();

                        Assert.assertTrue(isDataReady,
                                        "🚨 CRITICAL ERROR: Khởi tạo dữ liệu đầu vào trong @BeforeClass thất bại (Không tìm thấy "
                                                        + projectOpenWithLabel + ")! Dừng toàn bộ test suite.");
                } finally {
                        DriverManager.quit();
                }
        }

        @Parameters("BROWSER")
        @AfterTest(alwaysRun = true)
        public void cleanupCreatedProjects(@Optional("chrome") String browser) {
                createBrowser(browser);
                try {
                        ProjectPageCRM cleanupPage = getSignInPage().openDashboardPage().clickMenuProjects();

                        // 1. Tìm kiếm tiền tố dự án -> Chọn 'All' của bảng dữ liệu -> Xóa tất cả project có tiền tố đó
                        try {
                                cleanupPage.deleteProjectsByPrefix(ProjectModel.PREFIX_PROJECT);
                        } catch (Exception e) {
                                LogUtils.warn("Không thể xóa các dự án có tiền tố '" + ProjectModel.PREFIX_PROJECT + "' trong cleanup: "
                                                + e.getMessage());
                        }

                        // 2. Sau khi xóa hết project, xóa label đã tạo và các label test tạm
                        try {
                                if (availableLabel != null && !availableLabel.isEmpty()) {
                                        cleanupPage.cleanupLabelData(availableLabel);
                                        LogUtils.info("🏷️ [LABEL CLEANUP] Đã dọn dẹp label test: " + availableLabel);
                                }
                                cleanupPage.cleanupLabelsByPrefix("Temp_");
                        } catch (Exception e) {
                                LogUtils.warn("Không thể xóa label test trong cleanup: " + e.getMessage());
                        }
                } finally {
                        DriverManager.quit();
                }
        }
}
