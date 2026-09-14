package com.practiceCRM.projects.crm.testcases.projects;

import static com.practiceCRM.keywords.WebUI.*;

import java.util.List;

import com.practiceCRM.projects.crm.pages.Projects.ProjectPageCRM;
import com.practiceCRM.projects.crm.models.ProjectModel;
import com.practiceCRM.utils.LogUtils;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

/**
 * ProjectGridTest - Test class cho các tính năng:
 * - Giao diện mặc định & 8 cột chuẩn trong Grid
 * - Ẩn/Hiện cột (Column Visibility - Đơn lẻ, Kết hợp nhiều cột, Persistence sau
 * Reload)
 * - Sắp xếp cột trong bảng (ID, Title, Client, Price, Start Date, Deadline,
 * Progress, Status)
 * - Tiến độ & Phân trang (Progress Bar, Next/Previous, Nhảy trang, Footer info,
 * Disabled buttons, Reset khi search)
 * - Page Length (Mặc định 10, 25, 50, 100 dòng, Reset về 10)
 */
@Epic("Regression Test CRM")
@Feature("Projects Grid & Display")
public class ProjectGridTest extends BaseProjectTest {

        // =========================================================================
        // NHÓM 1: GIAO DIỆN & HIỂN THỊ MẶC ĐỊNH (TC_001 → TC_002)
        // =========================================================================

        @Test(priority = 1, groups = {
                        "ui_display" }, description = "CRM_PROJECTS_TC_001 - Kiểm tra hiển thị mặc định của trang All Projects")
        @Story("Page Display")
        public void testProjectsDefaultDisplay() {
                projectPage.verifyGridColumnsPresent();
                verifyElementVisible(projectPage.btnAddProject, 5, "Nút Add project không hiển thị.", soft);
                verifyElementVisible(projectPage.btnImportProjects, 5, "Nút Import projects không hiển thị.",
                                soft);
                verifyElementVisible(projectPage.btnManageLabels, 5, "Nút Manage labels không hiển thị.", soft);
                verifyElementVisible(projectPage.btnExcel, 5, "Nút Excel không hiển thị.", soft);
                verifyElementVisible(projectPage.btnPrint, 5, "Nút Print không hiển thị.", soft);
                verifyElementVisible(projectPage.inputSearch, 5, "Ô tìm kiếm không hiển thị.", soft);
                verifyElementVisible(projectPage.tableProjects, 5, "Bảng danh sách dự án không hiển thị.", soft);
                stopSoftAssertAll();
        }
        // =========================================================================
        // NHÓM 4: ẨN / HIỆN CỘT (COLUMN VISIBILITY) - ĐƠN LẺ & MA TRẬN KẾT HỢP (TC_013
        // → TC_025)
        // =========================================================================

        @Test(priority = 13, groups = {
                        "column_visibility" }, description = "CRM_PROJECTS_TC_013 - Kiểm tra ẩn và hiện lại từng cột trên bảng dự án (Gộp TC_013 → TC_020)")
        @Story("Column Visibility (Individual)")
        public void testToggleIndividualColumns() {
                List<String> columns = ProjectModel.TABLE_COLUMNS;
                for (String col : columns) {
                        projectPage.toggleColumn(col);
                        projectPage.verifyColumnHidden(col, soft);
                        projectPage.toggleColumn(col);
                        projectPage.verifyColumnPresent(col, soft);
                }
                stopSoftAssertAll();
        }

        @Test(priority = 14, groups = {
                        "column_visibility" }, description = "CRM_PROJECTS_TC_014 - Kết hợp ẩn đồng thời các cột phụ và bật lại toàn bộ (Gộp TC_021 → TC_024)")
        @Story("Column Visibility (Combine)")
        public void testCombineHideAllColumnsAndRestore() {

                String[] multiColumns = {
                                ProjectModel.COL_CLIENT,
                                ProjectModel.COL_PRICE,
                                ProjectModel.COL_DEADLINE,
                                ProjectModel.COL_PROGRESS,
                                ProjectModel.COL_STATUS
                };

                // 1. Ẩn toàn bộ các cột phụ
                for (String col : multiColumns) {
                        projectPage.toggleColumn(col);
                }
                for (String col : multiColumns) {
                        projectPage.verifyColumnHidden(col, soft);
                }

                // 2. Bật lại toàn bộ các cột phụ
                for (String col : multiColumns) {
                        projectPage.toggleColumn(col);
                }
                for (String col : multiColumns) {
                        projectPage.verifyColumnPresent(col, soft);
                }

                stopSoftAssertAll();
        }

        @Test(priority = 15, groups = {
                        "column_visibility" }, description = "CRM_PROJECTS_TC_015 - Kiểm tra lưu trạng thái ẩn cột sau khi Reload trang (Persistence - TC_025)")
        @Story("Column Visibility (Persistence)")
        public void testColumnVisibilityPersistenceOnReload() {
                projectPage.toggleColumn(ProjectModel.COL_DEADLINE);
                projectPage.verifyColumnHidden(ProjectModel.COL_DEADLINE, soft);

                openWebsite(getCurrentUrl());
                waitForPageLoaded();

                projectPage.verifyColumnHidden(ProjectModel.COL_DEADLINE, soft);

                // Khôi phục lại trạng thái cột
                projectPage.toggleColumn(ProjectModel.COL_DEADLINE);
                projectPage.verifyColumnPresent(ProjectModel.COL_DEADLINE, soft);

                stopSoftAssertAll();
        }

        // =========================================================================
        // NHÓM 7: SẮP XẾP CỘT TRONG BẢNG (TC_055 → TC_062)
        // =========================================================================

        @Test(priority = 55, groups = { "sorting" }, description = "CRM_PROJECTS_TC_055 - Sắp xếp cột")
        @Story("Sort All Column")
        public void testSortAllColumn() {
                List<String> columns = ProjectModel.TABLE_COLUMNS;
                for (String col : columns) {
                        projectPage.sortByColumn(col);
                        projectPage.verifyColumnSorted(col, soft);
                }
                stopSoftAssertAll();
        }
        // =========================================================================
        // NHÓM 9: PHÂN TRANG & SỐ BẢN GHI HIỂN THỊ (TC_075 → TC_077)
        // =========================================================================

        @Test(priority = 75, groups = {
                        "pagination" }, description = "CRM_PROJECTS_TC_075 - Luồng phân trang tổng hợp khi số bản ghi vượt qua số dòng 1 trang (kiểm tra số dòng mặc định, hiển thị phân trang, chia số lượng trang và hoạt động các nút Next/Previous/Jump)")
        @Story("Pagination")
        public void testPagination_WorkflowWhenRecordsExceedLimit() {
                // 1. Verify số dòng mặc định ban đầu (10 dòng) và footer info
                int defaultRowCount = projectPage.getTableRowsCount();
                verifyTrue(defaultRowCount <= 10,
                                "Số dòng hiển thị mặc định ban đầu vượt quá 10. Thực tế: " + defaultRowCount,
                                soft);
                String defaultInfo = projectPage.getTableInfoText();
                verifyTrue(defaultInfo.contains("1-10"),
                                "Footer không hiển thị đúng định dạng mặc định 10 dòng: " + defaultInfo,
                                soft);

                // 2. Lấy tổng số bản ghi thực tế
                int totalRecords = projectPage.getTotalRecordsCount();
                LogUtils.info("📊 [PAGINATION] Tổng số bản ghi thực tế trong bảng: " + totalRecords);

                // 3. Nếu số bản ghi vượt quá 10 dòng mặc định -> kiểm tra phân trang & chia
                // trang
                if (totalRecords > 10) {
                        // Verify 1: Có hiển thị phân trang hay không
                        verifyTrue(projectPage.isPaginationVisible(),
                                        "Bảng có " + totalRecords
                                                        + " bản ghi (> 10) nhưng thanh phân trang không xuất hiện.",
                                        soft);

                        // Verify 2: Số lượng trang phân trang có chia đúng hay không
                        int expectedPages = projectPage.getExpectedTotalPages(10);
                        int actualPages = projectPage.getPaginationPagesCount();
                        verifyTrue(actualPages == expectedPages,
                                        "Số lượng trang phân trang hiển thị không đúng công thức (Actual: "
                                                        + actualPages
                                                        + ", Expected: " + expectedPages + " từ Total " + totalRecords
                                                        + " / 10)",
                                        soft);

                        // Verify 3: Kiểm tra trạng thái các nút điều hướng ở Trang 1
                        verifyTrue(projectPage.isPreviousPageButtonDisabled(),
                                        "Nút Previous phải bị vô hiệu hóa khi đang ở trang 1.",
                                        soft);
                        verifyTrue(!projectPage.isNextPageButtonDisabled(),
                                        "Nút Next bị vô hiệu hóa dù bảng có nhiều hơn 1 trang.",
                                        soft);
                        verifyTrue(projectPage.getCurrentPageNumber() == 1,
                                        "Trang hiện tại ban đầu phải là trang 1.",
                                        soft);

                        // Verify 4: Kiểm tra hoạt động của nút Next
                        projectPage.clickNextPage();
                        verifyTrue(projectPage.getCurrentPageNumber() == 2,
                                        "Sau khi click Next, trang hiện tại không chuyển sang trang 2.",
                                        soft);
                        verifyTrue(!projectPage.isPreviousPageButtonDisabled(),
                                        "Nút Previous không được kích hoạt sau khi đã chuyển sang trang 2.",
                                        soft);

                        // Verify 5: Kiểm tra hoạt động của nút Previous
                        projectPage.clickPreviousPage();
                        verifyTrue(projectPage.getCurrentPageNumber() == 1,
                                        "Sau khi click Previous, trang hiện tại không quay trở lại trang 1.",
                                        soft);
                        verifyTrue(projectPage.isPreviousPageButtonDisabled(),
                                        "Nút Previous không bị vô hiệu hóa lại sau khi trở về trang 1.",
                                        soft);

                        // Verify 6: Kiểm tra click trực tiếp vào nút số trang (Jump to page)
                        if (actualPages >= 2) {
                                projectPage.jumpToPage("2");
                                verifyTrue(projectPage.getCurrentPageNumber() == 2,
                                                "Click vào nút số trang 2 không chuyển đúng đến trang 2.",
                                                soft);
                                projectPage.jumpToPage("1");
                                verifyTrue(projectPage.getCurrentPageNumber() == 1,
                                                "Click quay lại số trang 1 không thành công.",
                                                soft);
                        }
                } else {
                        LogUtils.warn("⚠️ Số bản ghi hiện tại (" + totalRecords
                                        + ") không vượt quá 10, không phát sinh phân trang.");
                }

                stopSoftAssertAll();
        }

        @Test(priority = 76, groups = {
                        "pagination" }, description = "CRM_PROJECTS_TC_076 - Thay đổi số lượng bản ghi hiển thị 1 trang (10, 25, 50, All) và kiểm tra phân trang cập nhật đúng")
        @Story("Page Length")
        public void testPagination_DynamicPageLengthUpdate() {
                int totalRecords = projectPage.getTotalRecordsCount();
                LogUtils.info("📊 [PAGE LENGTH] Tổng số bản ghi trong bảng: " + totalRecords);

                // 1. Đổi sang 25 dòng
                projectPage.changePageLength("25");
                int rowCount25 = projectPage.getTableRowsCount();
                String info25 = projectPage.getTableInfoText();
                verifyTrue(rowCount25 <= 25, "Số dòng thực tế (" + rowCount25 + ") vượt quá 25.", soft);
                verifyTrue(info25.contains("25") || info25.contains("1-"),
                                "Footer info không cập nhật khi đổi sang 25 dòng: " + info25, soft);
                int expectedPages25 = projectPage.getExpectedTotalPages(25);
                verifyTrue(projectPage.getPaginationPagesCount() == expectedPages25,
                                "Số lượng trang phân trang không cập nhật đúng khi chọn 25 dòng/trang.", soft);

                // 2. Đổi sang 50 dòng
                projectPage.changePageLength("50");
                int rowCount50 = projectPage.getTableRowsCount();
                String info50 = projectPage.getTableInfoText();
                verifyTrue(rowCount50 <= 50, "Số dòng thực tế (" + rowCount50 + ") vượt quá 50.", soft);
                verifyTrue(info50.contains("50") || info50.contains("1-"),
                                "Footer info không cập nhật khi đổi sang 50 dòng: " + info50, soft);
                int expectedPages50 = projectPage.getExpectedTotalPages(50);
                verifyTrue(projectPage.getPaginationPagesCount() == expectedPages50,
                                "Số lượng trang phân trang không cập nhật đúng khi chọn 50 dòng/trang.", soft);

                // 3. Đổi sang All (-1)
                projectPage.changePageLength("All");
                int rowCountAll = projectPage.getTableRowsCount();
                verifyTrue(rowCountAll == totalRecords,
                                "Bảng không hiển thị toàn bộ bản ghi khi chọn All (thực tế: " + rowCountAll + ", tổng: "
                                                + totalRecords + ").",
                                soft);
                verifyTrue(projectPage.getPaginationPagesCount() == 1,
                                "Khi chọn hiển thị All, phân trang chỉ được phép có 1 trang.", soft);

                // 4. Khôi phục lại về 10 dòng (mặc định)
                projectPage.changePageLength("10");
                int rowCount10 = projectPage.getTableRowsCount();
                verifyTrue(rowCount10 <= 10, "Số dòng thực tế sau khi reset về 10 vượt quá 10.", soft);
                int expectedPages10 = projectPage.getExpectedTotalPages(10);
                verifyTrue(projectPage.getPaginationPagesCount() == expectedPages10,
                                "Số lượng trang phân trang không khôi phục đúng khi đổi lại về 10.", soft);

                stopSoftAssertAll();
        }

        @Test(priority = 77, groups = {
                        "pagination" }, description = "CRM_PROJECTS_TC_077 - Kiểm tra tự động reset về Trang 1 khi thực hiện Search từ trang khác")
        @Story("Pagination")
        public void testPagination_AutoResetToPageOneOnSearch() {
                if (projectPage.getPaginationPagesCount() >= 2) {
                        projectPage.jumpToPage("2");
                        verifyTrue(projectPage.getCurrentPageNumber() == 2,
                                        "Chuyển sang trang 2 không thành công trước khi search.", soft);

                        projectPage.searchProject(projectOpenWithLabel);
                        verifyTrue(projectPage.getCurrentPageNumber() == 1,
                                        "Phân trang không tự động reset về trang 1 sau khi thực hiện tìm kiếm.",
                                        soft);
                        projectPage.clearSearch();
                } else {
                        LogUtils.warn("⚠️ Bảng hiện tại chỉ có 1 trang, bỏ qua bước nhảy sang trang 2 để test search reset.");
                }

                stopSoftAssertAll();
        }
}
