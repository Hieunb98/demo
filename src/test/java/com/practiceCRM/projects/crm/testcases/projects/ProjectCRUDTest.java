package com.practiceCRM.projects.crm.testcases.projects;

import com.practiceCRM.constants.FrameworkConstants;
import com.practiceCRM.helpers.ExcelHelpers;
import static com.practiceCRM.keywords.WebUI.*;
import com.practiceCRM.projects.crm.models.ProjectModel;
import com.practiceCRM.projects.crm.models.ProjectDTO;
import static com.practiceCRM.projects.crm.models.ProjectModel.*;

import com.practiceCRM.projects.crm.pages.Projects.ProjectPageCRM;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import java.util.List;

/**
 * ProjectCRUDTest - Test class cho các tính năng:
 * - Add Project (Modal, Validation, Tạo mới)
 * - Import / Export Projects (Modal, Download mẫu, Import Excel, Export Excel)
 * - Manage Labels (Modal, Validation, Tạo màu, Đồng bộ vị trí, Sửa, Xóa)
 * - Actions & Navigation (Click ID/Title/Client, Edit, Update, Delete
 * Confirm/Cancel, Print)
 */
@Epic("Regression Test CRM")
@Feature("Projects CRUD & Actions")
public class ProjectCRUDTest extends BaseProjectTest {

        // =========================================================================
        // NHÓM 2.1: CÁC NÚT THAO TÁC TRÊN ADD PROJECT (TC_003 → TC_005)
        // =========================================================================

        @Test(priority = 3, groups = {
                        "add_project" }, description = "CRM_PROJECTS_TC_003 - Kiểm tra mở và đóng modal Add Project bằng nút Close (Footer) và icon 'X' (Header)")
        @Story("Add Project")
        public void testOpenAndCloseAddProjectModal() {
                // 1. Mở modal Add Project lần 1 và kiểm tra hiển thị
                projectPage.clickAddProject();
                verifyElementVisible(projectPage.inputTitle, 5, "Input Title không hiển thị trong modal Add Project.",
                                soft);

                // 2. Đóng bằng nút Close ở Footer và kiểm tra modal đóng
                projectPage.closeModalByButton();
                verifyElementNotVisible(projectPage.modalTitle, 3,
                                "Modal Add Project vẫn còn hiển thị sau khi click nút Close ở Footer.", soft);

                // 3. Mở lại modal Add Project lần 2 và kiểm tra hiển thị lại
                projectPage.clickAddProject();
                verifyElementVisible(projectPage.inputTitle, 5,
                                "Input Title không hiển thị lại khi mở lại modal Add Project.", soft);

                // 4. Đóng bằng icon 'X' ở Header và kiểm tra modal đóng
                projectPage.closeModalByIcon();
                verifyElementNotVisible(projectPage.modalTitle, 3,
                                "Modal Add Project vẫn còn hiển thị sau khi click icon X ở Header.", soft);

                stopSoftAssertAll();
        }

        @Test(priority = 4, groups = {
                        "add_project" }, description = "CRM_PROJECTS_TC_004 - Thêm mới dự án thành công với đầy đủ thông tin hợp lệ")
        @Story("Add Project")
        public void testAddNewProjectSuccess() {
                // Data được tạo sẵn trong @BeforeClass → verify project đã có trong danh sách
                projectPage.searchProject(projectOpenWithLabel);
                verifyElementPresent(projectPage.getProjectLocatorByTitle(projectOpenWithLabel), 5,
                                "Dự án '" + projectOpenWithLabel + "' không xuất hiện sau khi tạo.");
                projectPage.clearSearch();
        }

        @Test(priority = 5, groups = {
                        "add_project" }, description = "CRM_PROJECTS_TC_005 - Thêm mới dự án thất bại khi để trống trường Title bắt buộc")
        @Story("Add Project")
        public void testAddNewProjectEmptyTitleValidation() {
                projectPage.clickAddProject();
                clickElementWithJs(projectPage.btnSaveProject);
                verifyEquals(getTextElement(projectPage.labelTitleError), ProjectModel.MSG_REQUIRED_THIS_FIELD,
                                "Thông báo lỗi bắt buộc nhập Title không đúng.");
                projectPage.closeModal();
        }

        // =========================================================================
        // NHÓM 2.2: IMPORT / EXPORT PROJECTS (TC_IMP_001 → TC_IMP_014)
        // =========================================================================

        @Test(priority = 6, groups = {
                        "import_projects" }, description = "CRM_PROJECTS_TC_IMP_001+002+003+004 - Verify UI modal Import Projects, nút Next disabled khi chưa chọn file, đóng bằng Close (Footer) và icon X (Header)")
        @Story("Import Projects - A. UI Modal")
        public void testImportModal_VerifyUIAndCloseActions() {
                // === TC_IMP_003+004: Verify tất cả UI elements mặc định & nút Next disabled
                // khi chưa chọn file ===
                projectPage.clickImportProjects();
                projectPage.verifyImportModalUIElements(soft);

                // === TC_IMP_001: Đóng modal bằng nút Close ở Footer ===
                projectPage.closeModalByButton();
                verifyElementNotVisible(projectPage.modalProject, 3,
                                "[IMP_001] Modal vẫn hiển thị sau khi click nút Close ở Footer.", soft);

                // === TC_IMP_002: Mở lại → Đóng modal bằng icon X ở Header ===
                projectPage.clickImportProjects();
                verifyElementVisible(projectPage.linkDownloadSampleFile, 5,
                                "[IMP_002] Link 'Download sample file' không hiển thị khi mở lại modal.", soft);
                projectPage.closeModalByIcon();
                verifyElementNotVisible(projectPage.modalProject, 3,
                                "[IMP_002] Modal vẫn hiển thị sau khi click icon X ở Header.", soft);

                stopSoftAssertAll();
        }

        @Test(priority = 7, groups = {
                        "import_projects" }, description = "CRM_PROJECTS_TC_IMP_005 - Chọn lại file khác trước khi click Import, xác thực file mới thay thế file cũ và import thành công")
        @Story("Import Projects - A. UI Modal")
        public void testImportProject_ReselectDifferentFileBeforeImport() {
                // 1. Đọc test data của file B độc lập qua ProjectDTO
                ProjectDTO projectB = projectPage.getImportProjectTestData(FrameworkConstants.EXCEL_PROJECTS_IMPORT, 1);

                // 2. Mở modal import
                projectPage.clickImportProjects();
                try {
                        // 3. Upload file A (file ban đầu) và verify tên hiển thị
                        uploadFileWithSendKeys(projectPage.inputImportFile,
                                        FrameworkConstants.EXCEL_PROJECTS_IMPORT_MISSING_PROJECT_TYPE_AND_STATUS);
                        projectPage.verifyUploadedFileName(
                                        FrameworkConstants.EXCEL_PROJECTS_IMPORT_MISSING_PROJECT_TYPE_AND_STATUS,
                                        soft);

                        // 4. Xóa file A
                        projectPage.removeUploadedFile();

                        // 5. Upload file B và submit import bằng method có sẵn
                        projectPage.importProjectsFileSuccess(FrameworkConstants.EXCEL_PROJECTS_IMPORT);

                        // 6. Verify dữ liệu của file B được import thành công vào danh sách
                        projectPage.verifyImportedProjectDetails(projectB, soft);
                } finally {
                        if (checkElementExist(projectPage.modalTitle)) {
                                projectPage.closeModal();
                        }
                }
                stopSoftAssertAll();
        }

        @Test(priority = 8, groups = {
                        "import_projects" }, description = "CRM_PROJECTS_TC_IMP_006 - Kiểm tra tải file mẫu (Download sample file) trong modal Import Projects")
        @Story("Import Projects - B. Download Sample")
        public void testDownloadSampleFileInImportModal() {
                projectPage.clickImportProjects();
                try {
                        projectPage.downloadSampleFile();
                        boolean isDownloaded = verifyDownloadFileContainsName(ProjectModel.NAME_SAMPLE_FILE_IMPORT, 10);
                        verifyTrue(isDownloaded, "File mẫu sample import không được tải xuống thành công.");
                } finally {
                        projectPage.closeModal();
                }
        }

        @Test(priority = 9, groups = {
                        "import_projects" }, description = "CRM_PROJECTS_TC_IMP_008+010+033+034 - Import dự án thành công với đầy đủ trường (kể cả optional), verify bảng refresh và data chi tiết")
        @Story("Import Projects - C. Import Thành Công")
        public void testImportProjectsSuccess() {
                ProjectDTO project = projectPage.getImportProjectTestData(FrameworkConstants.EXCEL_PROJECTS_IMPORT, 1);
                projectPage.clickImportProjects();
                projectPage.importProjectsFileSuccess(FrameworkConstants.EXCEL_PROJECTS_IMPORT);
                projectPage.verifyImportedProjectDetails(project, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 10, groups = {
                        "import_projects" }, description = "CRM_PROJECTS_TC_IMP_009 - Import nhiều dự án cùng lúc từ 1 file Excel (multi-row)")
        @Story("Import Projects - C. Import Thành Công")
        public void testImportMultipleProjectsSuccess() {
                projectPage.clickImportProjects();
                projectPage.importProjectsFileSuccess(FrameworkConstants.EXCEL_PROJECTS_IMPORT_MULTI);

                // Đọc động toàn bộ danh sách dự án từ file Excel và verify từng dự án
                List<ProjectDTO> projects = projectPage
                                .getImportProjectTestDataList(FrameworkConstants.EXCEL_PROJECTS_IMPORT_MULTI);
                for (ProjectDTO project : projects) {
                        projectPage.verifyImportedProjectDetails(project, soft);
                }
                stopSoftAssertAll();
        }

        @Test(priority = 11, groups = {
                        "import_projects" }, description = "CRM_PROJECTS_TC_IMP_011 - Import dự án thành công chỉ với các trường bắt buộc (Title, Project Type, Status), các trường optional để trống")
        @Story("Import Projects - C. Import Thành Công")
        public void testImportProject_RequiredFieldsOnlySuccess() {
                ProjectDTO project = projectPage
                                .getImportProjectTestData(FrameworkConstants.EXCEL_PROJECTS_IMPORT_REQUIRED_ONLY, 1);
                projectPage.clickImportProjects();
                projectPage.importProjectsFileSuccess(FrameworkConstants.EXCEL_PROJECTS_IMPORT_REQUIRED_ONLY);
                projectPage.verifyImportedProjectDetails(project, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 12, groups = {
                        "import_projects" }, description = "CRM_PROJECTS_TC_IMP_012+013 - Import dự án với dữ liệu chuẩn hóa: Trim khoảng trắng thừa đầu cuối và hiển thị đúng ký tự Tiếng Việt Unicode có dấu")
        @Story("Import Projects - C. Import Thành Công")
        public void testImportProject_TrimSpaceAndUnicodeData() {
                // 1. Mở modal và import file chứa dữ liệu có khoảng trắng thừa (row 1) và Tiếng
                // Việt Unicode (row 2)
                projectPage.clickImportProjects();
                projectPage.importProjectsFileSuccess(FrameworkConstants.EXCEL_PROJECTS_IMPORT_TRIM_AND_UNICODE);

                // 2. Verify TC_IMP_012: Đọc dữ liệu dòng 1 từ file Excel, verify hệ thống tự
                // động trim khoảng trắng thừa đầu cuối
                ProjectDTO projectTrim = projectPage
                                .getImportProjectTestData(FrameworkConstants.EXCEL_PROJECTS_IMPORT_TRIM_AND_UNICODE, 1);
                projectPage.verifyImportedProjectDetails(projectTrim, soft);

                // 3. Verify TC_IMP_013: Đọc dữ liệu dòng 2 từ file Excel, verify hiển thị chính
                // xác ký tự Tiếng Việt Unicode có dấu
                ProjectDTO projectUnicode = projectPage
                                .getImportProjectTestData(FrameworkConstants.EXCEL_PROJECTS_IMPORT_TRIM_AND_UNICODE, 2);
                projectPage.verifyImportedProjectDetails(projectUnicode, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 13, groups = {
                        "import_projects" }, description = "CRM_PROJECTS_TC_IMP_014+015+016 - Import file sai định dạng (.png, .pptx, .docx, .csv) hiển thị lỗi và không chuyển sang Preview")
        @Story("Import Projects - D. File Không Hợp Lệ")
        public void testImportProject_InvalidFileFormats() {
                String[][] invalidFileCases = {
                                { FrameworkConstants.FILE_PROJECTS_INVALID_IMAGE,
                                                ProjectModel.MSG_INVALID_EXCEL_EXTENSION },
                                { FrameworkConstants.FILE_PROJECTS_INVALID_PPTX,
                                                ProjectModel.MSG_FILE_TYPE_NOT_ALLOWED },
                                { FrameworkConstants.FILE_PROJECTS_INVALID_DOCX,
                                                ProjectModel.MSG_INVALID_EXCEL_EXTENSION },
                                { FrameworkConstants.FILE_PROJECTS_INVALID_CSV, ProjectModel.MSG_FILE_TYPE_NOT_ALLOWED }
                };

                projectPage.clickImportProjects();
                for (String[] testCase : invalidFileCases) {
                        String filePath = testCase[0];
                        String expectedError = testCase[1];

                        projectPage.importFileAndVerifyError(filePath, expectedError, soft);
                        projectPage.removeUploadedFile();
                }
                projectPage.closeModal();
                stopSoftAssertAll();
        }

        @Test(priority = 14, groups = {
                        "import_projects" }, description = "CRM_PROJECTS_TC_IMP_017 - Import file Excel rỗng (chỉ chứa dòng tiêu đề, không có dữ liệu) chuyển sang Preview không có dòng dữ liệu nào")
        @Story("Import Projects - D. File Không Hợp Lệ")
        public void testImportProject_EmptyExcelFile() {
                projectPage.clickImportProjects();
                projectPage.importToPreviewPage(FrameworkConstants.EXCEL_PROJECTS_IMPORT_EMPTY_HEADER_ONLY, soft);
                projectPage.verifyImportPreviewHasNoDataRows(soft);
                projectPage.closeModal();
                stopSoftAssertAll();
        }

        @Test(priority = 15, groups = {
                        "import_projects" }, description = "CRM_PROJECTS_TC_IMP_019+020+021 - Import file Excel sai cấu trúc cột (thiếu cột bắt buộc, đảo thứ tự cột, hoặc thừa cột ngoài spec) thì nút Upload bị vô hiệu hoá")
        @Story("Import Projects - D. File Không Hợp Lệ")
        public void testImportProject_InvalidColumnStructures() {
                String[][] invalidStructureCases = {
                                { FrameworkConstants.EXCEL_PROJECTS_IMPORT_MISSING_TITLE_COLUMN,
                                                "TC_IMP_019: Thiếu cột bắt buộc Title" },
                                { FrameworkConstants.EXCEL_PROJECTS_IMPORT_SWAPPED_COLUMNS,
                                                "TC_IMP_020: Đảo thứ tự các cột header" },
                                { FrameworkConstants.EXCEL_PROJECTS_IMPORT_EXTRA_COLUMNS,
                                                "TC_IMP_021: Có thêm các cột dư ngoài spec" }
                };

                for (String[] testCase : invalidStructureCases) {
                        String filePath = testCase[0];
                        String testDesc = testCase[1];

                        projectPage.clickImportProjects();
                        projectPage.importToPreviewPage(filePath, soft);
                        projectPage.verifyImportSubmitButtonDisabled(testDesc, soft);
                        projectPage.closeModal();
                        reloadPage();
                        waitForPageLoaded();
                }
                stopSoftAssertAll();
        }

        @Test(priority = 16, groups = {
                        "import_projects" }, description = "CRM_PROJECTS_TC_IMP_022 - Import file Excel có nhiều sheets, hệ thống chỉ đọc và import dữ liệu từ sheet đầu tiên")
        @Story("Import Projects - D. File Không Hợp Lệ")
        public void testImportProject_MultipleSheetsOnlyFirstSheetImported() {
                ProjectDTO project = projectPage.getImportProjectTestData(FrameworkConstants.EXCEL_PROJECTS_IMPORT_MULTIPLE_SHEETS, 1);
                projectPage.clickImportProjects();
                projectPage.importProjectsFileSuccess(FrameworkConstants.EXCEL_PROJECTS_IMPORT_MULTIPLE_SHEETS);
                projectPage.verifyImportedProjectDetails(project, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 17, groups = {
                        "import_projects" }, description = "CRM_PROJECTS_TC_IMP_023a - Validation hiển thị lỗi trên Preview khi dữ liệu trong file thiếu trường bắt buộc (Project Type và Status)")
        @Story("Import Projects - E. Data Trong File")
        public void testImportProject_ValidationMissingProjectTypeAndStatus() {
                projectPage.clickImportProjects();
                projectPage.importToPreviewPage(
                                FrameworkConstants.EXCEL_PROJECTS_IMPORT_MISSING_PROJECT_TYPE_AND_STATUS, soft);
                projectPage.verifyImportPreviewHasError(ProjectModel.MSG_REQUIRE_PROJECT_TYPE_PREVIEW, soft);
                projectPage.verifyImportPreviewHasError(ProjectModel.MSG_REQUIRE_STATUS_PREVIEW, soft);
                projectPage.verifyImportSubmitButtonDisabled("TC_IMP_023a", soft);
                projectPage.closeModal();
                stopSoftAssertAll();
        }

        @Test(priority = 18, groups = {
                        "import_projects" }, description = "CRM_PROJECTS_TC_IMP_023b - Validation hiển thị lỗi trên Preview khi Project Type là Client Project nhưng thiếu Client và Status")
        @Story("Import Projects - E. Data Trong File")
        public void testImportProject_ValidationMissingClientAndStatus() {
                projectPage.clickImportProjects();
                projectPage.importToPreviewPage(FrameworkConstants.EXCEL_PROJECTS_IMPORT_MISSING_CLIENT_AND_STATUS,
                                soft);
                projectPage.verifyImportPreviewHasError(ProjectModel.MSG_REQUIRE_CLIENT_PREVIEW, soft);
                projectPage.verifyImportPreviewHasError(ProjectModel.MSG_REQUIRE_STATUS_PREVIEW, soft);
                projectPage.verifyImportSubmitButtonDisabled("TC_IMP_023b", soft);
                projectPage.closeModal();
                stopSoftAssertAll();
        }

        @Test(priority = 19, groups = {
                        "import_projects" }, description = "CRM_PROJECTS_TC_IMP_031 - Validation hiển thị lỗi trên Preview khi giá trị cột Status không hợp lệ")
        @Story("Import Projects - E. Data Trong File")
        public void testImportProject_ValidationInvalidStatus() {
                projectPage.clickImportProjects();
                projectPage.importToPreviewPage(FrameworkConstants.EXCEL_PROJECTS_IMPORT_INVALID_STATUS, soft);
                projectPage.verifyImportPreviewHasError(ProjectModel.MSG_INVALID_STATUS_PREVIEW, soft);
                projectPage.verifyImportSubmitButtonDisabled("TC_IMP_031", soft);
                projectPage.closeModal();
                stopSoftAssertAll();
        }

        @Test(priority = 20, groups = {
                        "import_projects" }, description = "CRM_PROJECTS_TC_IMP_024_025 - Hệ thống tự động sanitize loại bỏ định dạng ngày và giá không hợp lệ khi import")
        @Story("Import Projects - E. Data Trong File")
        public void testImportProject_SanitizationInvalidDateAndPrice() {
                ProjectDTO project = projectPage.getImportProjectTestData(
                                FrameworkConstants.EXCEL_PROJECTS_IMPORT_INVALID_DATE_AND_PRICE, 1);
                projectPage.clickImportProjects();
                projectPage.importProjectsFileSuccess(FrameworkConstants.EXCEL_PROJECTS_IMPORT_INVALID_DATE_AND_PRICE);
                projectPage.searchProject(project.getTitle());
                projectPage.verifyAllRowsHaveSearchResults(project.getTitle(), soft);
                stopSoftAssertAll();
        }

        @Test(priority = 21, groups = {
                        "import_projects" }, description = "CRM_PROJECTS_TC_IMP_027 - Cho phép import các dự án trùng tên do hệ thống phân biệt bằng ID tự tăng")
        @Story("Import Projects - E. Data Trong File")
        public void testImportProject_DuplicateProjectNameAllowed() {
                ProjectDTO project = projectPage.getImportProjectTestData(
                                FrameworkConstants.EXCEL_PROJECTS_IMPORT_DUPLICATE_NAME, 1);

                // Import lần 1
                projectPage.clickImportProjects();
                projectPage.importProjectsFileSuccess(FrameworkConstants.EXCEL_PROJECTS_IMPORT_DUPLICATE_NAME);

                // Import lần 2 cùng tên dự án
                projectPage.clickImportProjects();
                projectPage.importProjectsFileSuccess(FrameworkConstants.EXCEL_PROJECTS_IMPORT_DUPLICATE_NAME);

                // Search theo tên, kiểm tra có ít nhất 2 bản ghi cùng tên hiển thị
                projectPage.searchProject(project.getTitle());
                projectPage.verifyAllRowsHaveSearchResults(project.getTitle(), soft);
                int totalFound = projectPage.getTotalRecordsCount();
                verifyTrue(totalFound >= 2,
                                "Kỳ vọng tìm thấy ít nhất 2 dự án trùng tên '" + project.getTitle()
                                                + "', thực tế: " + totalFound,
                                soft);
                stopSoftAssertAll();
        }

        @Test(priority = 22, groups = {
                        "import_projects",
                        "export_projects" }, description = "CRM_PROJECTS_TC_009c - Kiểm tra xuất danh sách dự án ra file Excel (Export Excel)")
        @Story("Import & Export Projects")
        public void testExportProjectsToExcel() {
                projectPage.exportProjectsToExcel();
                boolean isDownloaded = verifyDownloadFileContainsName(ProjectModel.NAME_EXPORT_FILE_PROJECTS, 10);
                verifyTrue(isDownloaded, "File danh sách dự án Projects không được xuất thành công.");
        }

        // =========================================================================
        // NHÓM 2.3: CÁC THAO TÁC VÀ KIỂM TRA TRÊN MANAGE LABELS (TC_010 → TC_015)
        // =========================================================================

        @Test(priority = 10, groups = {
                        "manage_labels" }, description = "CRM_PROJECTS_TC_010 - Kiểm tra mở và đóng modal Manage Labels bằng nút Close (Footer) và icon 'X' (Header)")
        @Story("Manage Labels")
        public void testOpenAndCloseManageLabelsModal() {
                // 1. Mở modal Manage Labels lần 1 và kiểm tra hiển thị
                projectPage.clickManageLabels();
                verifyElementVisible(projectPage.inputLabelTitle, 5,
                                "Input Label Title không hiển thị trong modal Manage labels.", soft);

                // 2. Đóng bằng nút Close ở Footer và kiểm tra modal đóng
                projectPage.closeModalByButton();
                verifyElementNotVisible(projectPage.modalTitle, 3,
                                "Modal Manage Labels vẫn còn hiển thị sau khi click nút Close ở Footer.", soft);

                // 3. Mở lại modal Manage Labels lần 2 và kiểm tra hiển thị lại
                projectPage.clickManageLabels();
                verifyElementVisible(projectPage.inputLabelTitle, 5,
                                "Input Label Title không hiển thị lại khi mở lại modal Manage labels.", soft);

                // 4. Đóng bằng icon 'X' ở Header và kiểm tra modal đóng
                projectPage.closeModalByIcon();
                verifyElementNotVisible(projectPage.modalTitle, 3,
                                "Modal Manage Labels vẫn còn hiển thị sau khi click icon 'X' ở Header.", soft);

                stopSoftAssertAll();
        }

        @Test(priority = 14, groups = {
                        "manage_labels" }, description = "CRM_PROJECTS_TC_012 - Kiểm tra báo lỗi khi để trống Title lúc thêm mới Label")
        @Story("Manage Labels")
        public void testAddNewLabelEmptyTitleValidation() {
                projectPage.clickManageLabels();
                clearText(projectPage.inputLabelTitle);
                clickElementWithJs(projectPage.btnSaveLabel);
                verifyElementVisible(projectPage.labelTitleError, 5,
                                "Thông báo lỗi bắt buộc nhập Title không hiển thị khi để trống.");
                verifyElementText(projectPage.labelTitleError, ProjectModel.MSG_REQUIRED_THIS_FIELD);
                projectPage.closeModal();
        }

        @Test(priority = 15, groups = {
                        "manage_labels" }, description = "CRM_PROJECTS_TC_013 - Xác thực Label tạo từ Data Setup có đúng màu sắc đã chọn cả trong Modal và trên Bảng Projects")
        @Story("Manage Labels")
        public void testVerifyCreatedLabelWithColor() {
                projectPage.verifyLabelColorInManageModal(ProjectModel.DATA_LABEL, setupLabelColor, soft);
                projectPage.verifyLabelColorOnProjectsTable(projectOpenWithLabel, ProjectModel.DATA_LABEL,
                                setupLabelColor, soft);
                stopSoftAssertAll();
        }

        @Test(priority = 16, groups = {
                        "manage_labels" }, description = "CRM_PROJECTS_TC_014 - Kiểm tra Label hiển thị đồng bộ ở Manage Modal, Filter Top bar, Add Modal, Edit Modal")
        @Story("Manage Labels")
        public void testVerifyLabelDisplayedAcrossAllComponents() {
                projectPage.verifyLabelPresentAcrossAllComponents(ProjectModel.DATA_LABEL);
        }

        @Test(priority = 17, groups = {
                        "manage_labels" }, description = "CRM_PROJECTS_TC_015 - Thêm Label không màu, sửa tên và kiểm tra biến mất tên cũ / hiện diện tên mới tại cả 4 vị trí")
        @Story("Manage Labels")
        public void testAddNewLabelWithoutColorAndEditAcrossAllComponents() {
                String tempLabelOld = "Temp_NoColor_" + System.currentTimeMillis();
                String tempLabelNew = tempLabelOld + "_Edited";
                try {
                        // 1. Thêm label không chọn màu (dùng màu mặc định)
                        projectPage.addNewLabel(tempLabelOld);
                        projectPage.verifyLabelPresentAcrossAllComponents(tempLabelOld);

                        // 2. Chỉnh sửa tên label
                        projectPage.editLabel(tempLabelOld, tempLabelNew);

                        // 3. Kiểm tra tên cũ biến mất và tên mới hiện diện tại cả 4 vị trí
                        projectPage.verifyLabelNotPresentAcrossAllComponents(tempLabelOld);
                        projectPage.verifyLabelPresentAcrossAllComponents(tempLabelNew);

                        // 4. Dọn dẹp label sau test case
                        projectPage.deleteLabel(tempLabelNew);
                        projectPage.verifyLabelNotPresentAcrossAllComponents(tempLabelNew);
                } finally {
                        // Đảm bảo luôn dọn dẹp sạch nhãn kể cả khi assertion thất bại
                        projectPage.cleanupLabelData(tempLabelOld);
                        projectPage.cleanupLabelData(tempLabelNew);
                }
        }

        // =========================================================================
        // NHÓM 8: ĐIỀU HƯỚNG LIÊN KẾT & THAO TÁC HÀNG (TC_063 → TC_072)
        // =========================================================================

        @Test(priority = 63, groups = {
                        "actions" }, description = "CRM_PROJECTS_TC_063 - Click link ID dự án chuyển hướng đến trang chi tiết")
        @Story("Project Navigation")
        public void testClickProjectIdNavigation() {
                if (isElementPresent(projectPage.linkFirstRowId, 3)) {
                        clickElementWithJs(projectPage.linkFirstRowId);
                        waitForPageLoaded();
                        verifyContains(getCurrentUrl(), "/projects/view/",
                                        "Không điều hướng đúng sang trang Project Details.");
                }
        }

        @Test(priority = 64, groups = {
                        "actions" }, description = "CRM_PROJECTS_TC_064 - Click link Title dự án chuyển hướng đến trang chi tiết")
        @Story("Project Navigation")
        public void testClickProjectTitleNavigation() {
                if (isElementPresent(projectPage.linkFirstRowTitle, 3)) {
                        clickElementWithJs(projectPage.linkFirstRowTitle);
                        waitForPageLoaded();
                        verifyContains(getCurrentUrl(), "/projects/view/",
                                        "Không điều hướng đúng sang trang Project Details.");
                }
        }

        @Test(priority = 65, groups = {
                        "actions" }, description = "CRM_PROJECTS_TC_065 - Click link Client chuyển hướng đến trang chi tiết Client")
        @Story("Client Navigation")
        public void testClickClientLinkNavigation() {
                if (isElementPresent(projectPage.linkFirstRowClient, 3)) {
                        clickElementWithJs(projectPage.linkFirstRowClient);
                        waitForPageLoaded();
                        verifyContains(getCurrentUrl(), "/clients/view/",
                                        "Không điều hướng đúng sang trang Client Details.");
                }
        }

        @Test(priority = 66, groups = {
                        "actions" }, description = "CRM_PROJECTS_TC_066 - Mở modal Edit Project từ nút Action")
        @Story("Edit Project")
        public void testOpenEditProjectModal() {
                // Search đúng project mình tạo → đảm bảo không phụ thuộc thứ tự dòng trên bảng
                projectPage.searchProject(projectOpenWithLabel);
                projectPage.openEditProject(projectOpenWithLabel);
                verifyElementPresent(projectPage.inputTitle, "Modal Edit project không hiển thị input Title.");
                projectPage.closeModal();
                projectPage.clearSearch();
        }

        @Test(priority = 67, groups = {
                        "actions" }, description = "CRM_PROJECTS_TC_067 - Cập nhật thông tin dự án thành công")
        @Story("Edit Project")
        public void testUpdateProjectSuccess() {
                // Search đúng project → mở Edit → cập nhật Price → verify không lỗi
                projectPage.searchProject(projectOpenWithLabel);
                projectPage.openEditProject(projectOpenWithLabel);
                clearAndFillText(projectPage.inputPrice, "9999");
                clickElementWithJs(projectPage.btnSaveProject);
                waitForPageLoaded();
                projectPage.clearSearch();
        }

        @Test(priority = 68, groups = {
                        "actions" }, description = "CRM_PROJECTS_TC_068 - Mở pop-up xác nhận xóa dự án khi click nút Delete")
        @Story("Delete Project")
        public void testOpenDeleteProjectModal() {
                // Search đúng project → mở popup Delete → Cancel (không xóa thật)
                projectPage.searchProject(projectOpenNoLabel);
                projectPage.clickDeleteProject(projectOpenNoLabel);
                verifyElementPresent(projectPage.modalConfirmDelete, "Pop-up xác nhận xóa không hiển thị.");
                projectPage.cancelDelete();
                projectPage.clearSearch();
        }

        @Test(priority = 69, groups = {
                        "actions" }, description = "CRM_PROJECTS_TC_069 - Xác nhận xóa dự án (Click Confirm/Yes)")
        @Story("Delete Project")
        public void testConfirmDeleteProject() {
                // Dùng project tạo sẵn trong @BeforeClass cho case Delete
                projectPage.searchProject(projectToDelete);
                projectPage.clickDeleteProject(projectToDelete);
                projectPage.confirmDelete();
                projectPage.clearSearch();
        }

        @Test(priority = 70, groups = {
                        "actions" }, description = "CRM_PROJECTS_TC_070 - Hủy xóa dự án (Click Cancel/No)")
        @Story("Delete Project")
        public void testCancelDeleteProject() {
                // Search đúng project → mở popup → Cancel → verify project vẫn còn
                projectPage.searchProject(projectOpenNoLabel);
                projectPage.clickDeleteProject(projectOpenNoLabel);
                projectPage.cancelDelete();
                verifyElementPresent(projectPage.getProjectLocatorByTitle(projectOpenNoLabel), 5,
                                "Project đã bị xóa dù đã nhấn Cancel.");
                projectPage.clearSearch();
        }

        @Test(priority = 71, groups = {
                        "export" }, description = "CRM_PROJECTS_TC_071 - Xuất danh sách dự án ra file Excel")
        @Story("Export Excel")
        public void testExportExcel() {
                projectPage.clickExportExcel();
        }

        @Test(priority = 72, groups = { "export" }, description = "CRM_PROJECTS_TC_072 - In danh sách dự án (Print)")
        @Story("Print")
        public void testPrintProjectsList() {
                verifyElementPresent(projectPage.btnPrint, "Nút Print không hiển thị trên thanh công cụ.");
        }
}
