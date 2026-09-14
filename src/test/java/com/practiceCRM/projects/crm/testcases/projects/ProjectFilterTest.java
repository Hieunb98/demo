package com.practiceCRM.projects.crm.testcases.projects;

import static com.practiceCRM.keywords.WebUI.*;

import com.practiceCRM.projects.crm.pages.Projects.ProjectPageCRM;
import com.practiceCRM.projects.crm.models.ProjectModel;
import com.practiceCRM.utils.LogUtils;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * ProjectFilterTest - Test class cho các tính năng:
 * - Search (Chính xác, Không phân biệt hoa/thường, Negative không tồn tại, Xóa
 * trắng input)
 * - Single Filter (Status Open/Multi/All, Label, Deadline Options/Custom, Start
 * Date Options/Custom)
 * - Combined Filters (Ma trận kết hợp 2-4 bộ lọc, Negative rỗng, Nút Clear all
 * filters)
 */
@Epic("Regression Test CRM")
@Feature("Projects Search & Filters")
public class ProjectFilterTest extends BaseProjectTest {

        // =========================================================================
        // NHÓM 4: BỘ LỌC ĐƠN LẺ - SEARCH (TC_026 → TC_029)
        // =========================================================================

        @Test(priority = 26, groups = {
                        "search" }, description = "CRM_PROJECTS_TC_026 - Tìm kiếm dự án theo Title chính xác")
        @Story("Search")
        public void testSearchProjectByExactTitle() {
                // Dùng project đã tạo trong @BeforeClass — đảm bảo data luôn tồn tại
                projectPage.searchProject(projectOpenWithLabel);
                // 1. Xác thực tất cả các dòng hiển thị trên bảng đều chứa từ khóa tìm kiếm
                projectPage.verifyAllRowsHaveSearchResults(projectOpenWithLabel);
        }

        @Test(priority = 27, groups = {
                        "search" }, description = "CRM_PROJECTS_TC_027 - Tìm kiếm dự án theo từ khóa không phân biệt hoa thường")
        @Story("Search")
        public void testSearchProjectCaseInsensitive() {
                // Tìm bằng chữ In hoa
                String keyword = projectOpenWithLabel.toUpperCase();
                projectPage.searchProject(keyword);
                projectPage.verifyAllRowsHaveSearchResults(projectOpenWithLabel, soft);
                projectPage.clearSearch();
                // Tìm bằng chữ thường
                keyword = projectOpenWithLabel.toLowerCase();
                projectPage.searchProject(keyword);
                projectPage.verifyAllRowsHaveSearchResults(projectOpenWithLabel, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 28, groups = {
                        "search" }, description = "CRM_PROJECTS_TC_028 - Tìm kiếm dự án với từ khóa không tồn tại (Negative)")
        @Story("Search")
        public void testSearchProjectNonExistentKeyword() {
                projectPage.searchProject("ZZZ_NotExist_999999");
                verifyElementVisible(projectPage.tableEmptyMessage, 5,
                                "Thông báo bảng trống không xuất hiện khi search không có data.");
                String emptyMsgText = getTextElement(projectPage.tableEmptyMessage);
                verifyContains(emptyMsgText, ProjectModel.MSG_TABLE_SEARCH_EMPTY,
                                "Nội dung bảng trống không chính xác.");
        }

        @Test(priority = 29, groups = {
                        "search" }, description = "CRM_PROJECTS_TC_029 - Tìm kiếm khi xóa trắng ô Search")
        @Story("Search")
        public void testClearSearchInput() {
                projectPage.searchProject("ZZZ_NotExist_999999");
                verifyElementVisible(projectPage.tableEmptyMessage, 5,
                                "Cần có trạng thái 'no data' trước khi clear search.");
                projectPage.clearSearch();
                List<WebElement> tableRows = getWebElements(projectPage.tableRows);
                // Size > 1 tức là có dữ liệu
                verifyTrue(tableRows.size() > 1, "Bảng không hiển thị lại dữ liệu sau khi xóa trắng ô search.");
        }

        // =========================================================================
        // NHÓM 5: BỘ LỌC ĐƠN LẺ - STATUS, LABEL, DEADLINE (TC_030 → TC_042)
        // =========================================================================

        @Test(priority = 30, groups = {
                        "filters" }, description = "CRM_PROJECTS_TC_030 - Lọc dự án theo Status: 'Open'")
        @Story("Filter Status")
        public void testFilterStatusOpen() {
                List<String> statuses = ProjectModel.STATUS_OPTIONS;
                // Filter Status=Open → kết hợp Search để thu hẹp về đúng project mình tạo
                for (String status : statuses) {
                        projectPage.filterByStatus(status);
                        projectPage.verifyAllRowsHaveStatus(status, soft);
                }
                stopSoftAssertAll();
        }

        @Test(priority = 35, groups = {
                        "filters" }, description = "CRM_PROJECTS_TC_035A - Lọc dự án theo nhiều Status đồng thời (Multi-select: Open + Completed)")
        @Story("Filter Status")
        public void testFilterMultipleStatuses() {
                // 1. Định nghĩa các tổ hợp cần test (2 status, 3 status, 4 status)
                List<List<String>> multiStatusCases = Arrays.asList(
                                // Test case 2 statuses: Open + Completed
                                Arrays.asList(ProjectModel.STATUS_OPEN, ProjectModel.STATUS_COMPLETED),
                                // Test case 3 statuses: Open + Hold + Doing
                                Arrays.asList(ProjectModel.STATUS_OPEN, ProjectModel.STATUS_HOLD,
                                                ProjectModel.STATUS_DOING),
                                // Test case 4 statuses: Open + Completed + Canceled + Doing
                                Arrays.asList(ProjectModel.STATUS_OPEN, ProjectModel.STATUS_COMPLETED,
                                                ProjectModel.STATUS_CANCELED, ProjectModel.STATUS_DOING));
                // 2. Chạy vòng lặp qua từng tổ hợp
                for (List<String> statusGroup : multiStatusCases) {
                        LogUtils.info("🧪 Đang kiểm tra lọc nhóm Status: " + statusGroup);
                        projectPage.filterByMultipleStatuses(statusGroup);
                        projectPage.verifyAllRowsHaveAnyOfStatuses(statusGroup, soft);
                }
                stopSoftAssertAll();
        }

        @Test(priority = 36, groups = {
                        "filters" }, description = "CRM_PROJECTS_TC_035B - Reset bộ lọc Status về 'All' (hiển thị tất cả trạng thái)")
        @Story("Filter Status")
        public void testFilterStatusAll() {
                List<String> statusGroup = ProjectModel.STATUS_OPTIONS;
                projectPage.filterByMultipleStatuses(statusGroup);
                projectPage.verifyAllRowsHaveAnyOfStatuses(statusGroup, soft);
        }

        @Test(priority = 37, groups = {
                        "filters" }, description = "CRM_PROJECTS_TC_036 - Lọc dự án theo Label")
        @Story("Filter Label")
        public void testFilterByLabel() {
                projectPage.filterByLabel(availableLabel);
                projectPage.verifyAllRowsHaveLabel(availableLabel, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 37, groups = {
                        "filters" }, description = "CRM_PROJECTS_TC_037 - Lọc dự án theo Label không có dự án nào gắn nhãn")
        @Story("Filter Label")
        public void testFilterEmptyLabel() {
                projectPage.searchProject(projectOpenNoLabel);
                projectPage.verifySearchResultNoLabel(projectOpenNoLabel);
        }

        @Test(priority = 39, groups = {
                        "filters" }, description = "CRM_PROJECTS_TC_039 - Lọc dự án theo các tùy chọn Deadline định sẵn (Expired, Today, Tomorrow, In 7 days, In 15 days)")
        @Story("Filter Deadline")
        public void testFilterDeadlineOptions() {
                List<String> deadlineOptions = ProjectModel.DEADLINE_OPTIONS;

                for (String option : deadlineOptions) {
                        LogUtils.info("🧪 Đang kiểm tra lọc theo Deadline: " + option);
                        projectPage.filterByDeadline(option);
                        projectPage.verifyAllRowsHaveDeadline(option, soft);
                }
                stopSoftAssertAll();
        }

        @Test(priority = 40, groups = {
                        "filters" }, description = "CRM_PROJECTS_TC_040 - Lọc dự án theo Deadline tùy chọn ngày cụ thể (Custom)")
        @Story("Filter Deadline")
        public void testFilterDeadlineCustom() {
                // Kiểm tra trường hợp Deadline Custom với ngày cụ thể
                String customDate = LocalDate.now().plusDays(20).toString();
                LogUtils.info("🧪 Đang kiểm tra lọc theo Deadline Custom: " + customDate);
                projectPage.filterByDeadlineCustom(customDate);
                projectPage.verifyAllRowsHaveDeadlineCustom(customDate, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 41, groups = {
                        "filters" }, description = "CRM_PROJECTS_TC_041 - Lọc dự án theo các tùy chọn Start date định sẵn (This Month, Last Month, This Year, Last Year, Next 7 Days, Next Month)")
        @Story("Filter Start Date")
        public void testFilterStartDateOptions() {
                List<String> startDateOptions = ProjectModel.START_DATE_OPTIONS;

                for (String option : startDateOptions) {
                        LogUtils.info("🧪 Đang kiểm tra lọc theo Start date: " + option);
                        projectPage.filterByStartDate(option);
                        projectPage.verifyAllRowsHaveStartDate(option, soft);
                }
                stopSoftAssertAll();
        }

        @Test(priority = 42, groups = {
                        "filters" }, description = "CRM_PROJECTS_TC_042 - Lọc dự án theo Start date tùy chọn khoảng ngày cụ thể (Custom)")
        @Story("Filter Start Date")
        public void testFilterStartDateCustom() {
                // Kiểm tra trường hợp Start date Custom với khoảng ngày cụ thể
                LocalDate today = LocalDate.now();
                String customFromDate = today.minusDays(40).toString();
                String customToDate = today.plusDays(10).toString();
                LogUtils.info("🧪 Đang kiểm tra lọc theo Start date Custom: from=" + customFromDate + ", to="
                                + customToDate);
                projectPage.filterByStartDateCustom(customFromDate, customToDate);
                projectPage.verifyAllRowsHaveStartDateCustom(customFromDate, customToDate, soft);
                stopSoftAssertAll();
        }

        // =========================================================================
        // NHÓM 6: MA TRẬN KẾT HỢP CÁC BỘ LỌC (TC_043 → TC_054)
        // =========================================================================

        @Test(priority = 43, groups = {
                        "combined_filters" }, description = "CRM_PROJECTS_TC_043 - Kết hợp 2 bộ lọc: Status=Open + Search theo project đã tạo")
        @Story("Combine Filter (2)")
        public void testCombineFilterStatusAndSearch() {
                // Filter Status=Open + Search theo project mình tạo → chắc chắn có kết quả
                projectPage.filterByStatus(ProjectModel.STATUS_OPEN);
                projectPage.searchProject(projectOpenNoLabel);

                // 1. Xác thực tất cả các dòng hiển thị trên bảng đều có Status là Open
                projectPage.verifyAllRowsHaveStatus(ProjectModel.STATUS_OPEN, soft);

                // 2. Xác thực các dòng hiển thị đều chứa từ khóa tìm kiếm (projectOpenNoLabel)
                projectPage.verifyAllRowsHaveSearchResults(projectOpenNoLabel, soft);

                stopSoftAssertAll();
        }

        @Test(priority = 44, groups = {
                        "combined_filters" }, description = "CRM_PROJECTS_TC_044 - Kết hợp 2 bộ lọc: Status + Label")
        @Story("Combine Filter (2)")
        public void testCombineFilterStatusAndLabel() {
                projectPage.filterByStatus(ProjectModel.STATUS_OPEN);
                projectPage.filterByLabel(availableLabel);
                projectPage.verifyAllRowsHaveStatus(ProjectModel.STATUS_OPEN, soft);
                projectPage.verifyAllRowsHaveLabel(availableLabel, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 45, groups = {
                        "combined_filters" }, description = "CRM_PROJECTS_TC_045 - Kết hợp 2 bộ lọc: Status + Deadline")
        @Story("Combine Filter (2)")
        public void testCombineFilterStatusAndDeadline() {
                projectPage.filterByStatus(ProjectModel.STATUS_COMPLETED);
                projectPage.filterByDeadline(ProjectModel.DEADLINE_TODAY);
                projectPage.verifyAllRowsHaveStatus(ProjectModel.STATUS_COMPLETED, soft);
                projectPage.verifyAllRowsHaveDeadline(ProjectModel.DEADLINE_TODAY, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 46, groups = {
                        "combined_filters" }, description = "CRM_PROJECTS_TC_046 - Kết hợp 2 bộ lọc: Label + Search theo project mình tạo")
        @Story("Combine Filter (2)")
        public void testCombineFilterLabelAndSearch() {
                String searchKeyword = ProjectModel.PREFIX_PROJECT;
                projectPage.filterByLabel(availableLabel);
                projectPage.searchProject(searchKeyword);
                projectPage.verifyAllRowsHaveLabel(availableLabel, soft);
                projectPage.verifyAllRowsHaveSearchResults(searchKeyword, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 47, groups = {
                        "combined_filters" }, description = "CRM_PROJECTS_TC_047 - Kết hợp 2 bộ lọc: Label + Deadline")
        @Story("Combine Filter (2)")
        public void testCombineFilterLabelAndDeadline() {
                projectPage.filterByLabel(availableLabel);
                projectPage.filterByDeadline(ProjectModel.DEADLINE_TODAY);
                projectPage.verifyAllRowsHaveLabel(availableLabel, soft);
                projectPage.verifyAllRowsHaveDeadline(ProjectModel.DEADLINE_TODAY, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 48, groups = {
                        "combined_filters" }, description = "CRM_PROJECTS_TC_048 - Kết hợp 2 bộ lọc: Deadline + Search theo project mình tạo")
        @Story("Combine Filter (2)")
        public void testCombineFilterDeadlineAndSearch() {
                String searchKeyword = ProjectModel.PREFIX_PROJECT;
                projectPage.filterByDeadline(ProjectModel.DEADLINE_TODAY);
                projectPage.searchProject(searchKeyword);
                projectPage.verifyAllRowsHaveDeadline(ProjectModel.DEADLINE_TODAY, soft);
                projectPage.verifyAllRowsHaveSearchResults(searchKeyword, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 49, groups = {
                        "combined_filters" }, description = "CRM_PROJECTS_TC_049 - Kết hợp 3 bộ lọc: Status=Open + Label + Search")
        @Story("Combine Filter (3)")
        public void testCombineFilterStatusLabelSearch() {
                projectPage.filterByStatus(ProjectModel.STATUS_OPEN);
                projectPage.filterByLabel(availableLabel);
                projectPage.searchProject(ProjectModel.PREFIX_PROJECT);
                projectPage.verifyAllRowsHaveStatus(ProjectModel.STATUS_OPEN, soft);
                projectPage.verifyAllRowsHaveLabel(availableLabel, soft);
                projectPage.verifyAllRowsHaveSearchResults(ProjectModel.PREFIX_PROJECT, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 50, groups = {
                        "combined_filters" }, description = "CRM_PROJECTS_TC_050 - Kết hợp 3 bộ lọc: Status=Open + Deadline + Search")
        @Story("Combine Filter (3)")
        public void testCombineFilterStatusDeadlineSearch() {
                projectPage.filterByStatus(ProjectModel.STATUS_OPEN);
                projectPage.filterByDeadline(ProjectModel.DEADLINE_TODAY);
                projectPage.searchProject(ProjectModel.PREFIX_PROJECT);
                projectPage.verifyAllRowsHaveStatus(ProjectModel.STATUS_OPEN, soft);
                projectPage.verifyAllRowsHaveDeadline(ProjectModel.DEADLINE_TODAY, soft);
                projectPage.verifyAllRowsHaveSearchResults(ProjectModel.PREFIX_PROJECT, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 51, groups = {
                        "combined_filters" }, description = "CRM_PROJECTS_TC_051 - Kết hợp 3 bộ lọc: Label + Deadline + Search")
        @Story("Combine Filter (3)")
        public void testCombineFilterLabelDeadlineSearch() {
                projectPage.filterByLabel(availableLabel);
                projectPage.filterByDeadline(ProjectModel.DEADLINE_TODAY);
                projectPage.searchProject(ProjectModel.PREFIX_PROJECT);
                projectPage.verifyAllRowsHaveLabel(availableLabel, soft);
                projectPage.verifyAllRowsHaveDeadline(ProjectModel.DEADLINE_TODAY, soft);
                projectPage.verifyAllRowsHaveSearchResults(ProjectModel.PREFIX_PROJECT, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 52, groups = {
                        "combined_filters" }, description = "CRM_PROJECTS_TC_052 - Kết hợp đầy đủ 4 bộ lọc (All Filters)")
        @Story("Combine Filter (All)")
        public void testCombineAllFilters() {
                projectPage.filterByStatus(ProjectModel.STATUS_OPEN);
                projectPage.filterByLabel(availableLabel);
                projectPage.filterByDeadline(ProjectModel.DEADLINE_TODAY);
                projectPage.searchProject(ProjectModel.PREFIX_PROJECT);
                projectPage.verifyAllRowsHaveStatus(ProjectModel.STATUS_OPEN, soft);
                projectPage.verifyAllRowsHaveLabel(availableLabel, soft);
                projectPage.verifyAllRowsHaveDeadline(ProjectModel.DEADLINE_TODAY, soft);
                projectPage.verifyAllRowsHaveSearchResults(ProjectModel.PREFIX_PROJECT, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 53, groups = {
                        "combined_filters" }, description = "CRM_PROJECTS_TC_053 - Kết hợp các bộ lọc dẫn đến kết quả rỗng (No data found)")
        @Story("Combine Filter (Negative)")
        public void testCombineFilterYieldsEmptyResult() {
                projectPage.filterByStatus(ProjectModel.STATUS_CANCELED);
                projectPage.searchProject("ZZZ_NeverExist_999999");
                verifyElementVisible(projectPage.tableEmptyMessage, 5,
                                "Thông báo bảng rỗng không hiển thị khi kết hợp filter không có data.");
        }
}
