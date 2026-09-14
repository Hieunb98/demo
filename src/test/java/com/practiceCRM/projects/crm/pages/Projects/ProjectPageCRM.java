package com.practiceCRM.projects.crm.pages.Projects;

import static com.practiceCRM.keywords.WebUI.*;
import com.practiceCRM.projects.crm.models.ProjectModel;
import com.practiceCRM.projects.crm.models.ProjectDTO;
import com.practiceCRM.projects.crm.pages.CommonPageCRM;
import com.practiceCRM.helpers.ExcelHelpers;
import com.practiceCRM.enums.FailureHandling;
import com.practiceCRM.reports.AllureManager;
import com.practiceCRM.reports.ExtentReportManager;
import com.practiceCRM.reports.ExtentTestManager;
import com.practiceCRM.utils.LogUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ProjectPageCRM - Page Object Model cho màn hình Projects trong RISE CRM
 * Quản lý Locators và các hành vi tương tác trên trang Projects.
 */
public class ProjectPageCRM extends CommonPageCRM {

    private Object executeScript(String script, Object... args) {
        return ((org.openqa.selenium.JavascriptExecutor) com.practiceCRM.driver.DriverManager.getDriver())
                .executeScript(script, args);
    }

    // =====================================================================
    // 2. LOCATORS: TOP BAR / ACTION BUTTONS
    // =====================================================================
    public By btnAddProject = By.cssSelector(".title-button-group a[title='Add project']");
    public By btnImportProjects = By.cssSelector(".title-button-group a[title='Import projects']");
    public By btnManageLabels = By.cssSelector(".title-button-group a[title='Manage labels']");
    public By btnExcel = By.cssSelector("a.buttons-excel, button.buttons-excel, .buttons-excel");
    public By btnPrint = By.cssSelector("a.buttons-print, button.buttons-print, .buttons-print");
    public By btnColumnVisibility = By.cssSelector("button.column-show-hide-popover");
    public By popoverColumnVisibility = By.cssSelector(".popover.show");

    // =====================================================================
    // 3. LOCATORS: FILTERS & SEARCH
    // =====================================================================
    public By inputSearch = By.cssSelector("#project-table_filter input");
    public By dropdownStatus = By.cssSelector(".filter-multi-select > button.dropdown-toggle, .filter-multi-select > button");
    public By dropdownLabels = By.cssSelector(
            "select[name='project_label'] ~ .select2-container .select2-choice, .filter-item-box .select2-choice");
    public By labelDropdownItems = By.cssSelector("#select2-drop .select2-result-label");
    public By dropdownDeadline = By.cssSelector("button[name='deadline']");
    public By dropdownStartDate = By.cssSelector(".custom-date-range-dropdown .dropdown-toggle");
    public By btnStartDateFrom = By.cssSelector("button[name='start_date_from']");
    public By btnStartDateTo = By.cssSelector("button[name='start_date_to']");
    public By btnClearFilters = By.cssSelector(
            "button.cancel-filter-button, a.cancel-filter-button, .cancel-filter-button, a.clear-filter-button, button.clear-filter-button");
    public By statusFilterItemsActive = By
            .cssSelector("ul[data-act='multiselect'] li.active, .filter-multi-select li.active");

    // =====================================================================
    // 4. LOCATORS: TABLE / GRID & PAGINATION
    // =====================================================================
    public By tableProjects = By.id("project-table");
    public By tableHeaders = By.cssSelector("#project-table thead th");
    public By tableRows = By.cssSelector("#project-table tbody tr");
    // cell
    public By tableCellTitles = By.cssSelector("#project-table tbody tr td:nth-child(2) a");
    public By tableCellLabels = By.cssSelector("#project-table tbody tr td:nth-child(2) span.badge, #project-table tbody tr td:nth-child(2) span[class*='badge'], #project-table tbody tr td:nth-child(2) span[class*='label']");
    public By tableCellStartDates = By.cssSelector("#project-table tbody tr td:nth-child(5)");
    public By tableCellDeadlines = By.cssSelector("#project-table tbody tr td:nth-child(6)");
    public By tableCellStatuses = By.cssSelector("#project-table tbody tr td:nth-child(8)");
    public By tableCellBudget = By.cssSelector("#project-table tbody tr td:nth-child(4)");
    public By tableEmptyMessage = By.cssSelector("#project-table td.dataTables_empty");

    public By tableProcessing = By.id("project-table_processing");
    public By selectPageLength = By.name("project-table_length");
    public By choicePageLength = By.cssSelector(".dataTables_length .select2-choice");
    public By labelTableInfo = By.id("project-table_info");
    public By paginationContainer = By.id("project-table_paginate");
    public By btnPaginationNext = By.cssSelector("#project-table_next a");
    public By btnPaginationPrevious = By.cssSelector("#project-table_previous a");
    public By itemPaginationPrevious = By.id("project-table_previous");
    public By itemPaginationNext = By.id("project-table_next");
    public By btnLastPage = By.cssSelector("#project-table_paginate li:not(.next):not(.previous):last-child a");
    public By paginationActivePage = By.cssSelector("#project-table_paginate li.paginate_button.active a");
    public By paginationPageButtons = By
            .cssSelector("#project-table_paginate li.paginate_button:not(.previous):not(.next)");
    public By btnFirstEdit = By.cssSelector(
            "#project-table tbody tr:first-child a[title='Edit project'], #project-table tbody tr:first-child a.edit");
    public By btnFirstDelete = By.cssSelector(
            "#project-table tbody tr:first-child a[title='Delete project'], #project-table tbody tr:first-child a.delete");
    public By linkFirstRowId = By.cssSelector("#project-table tbody tr:first-child td:nth-child(1) a");
    public By linkFirstRowTitle = By.cssSelector("#project-table tbody tr:first-child td:nth-child(2) a");
    public By linkFirstRowClient = By.cssSelector("#project-table tbody tr:first-child td:nth-child(3) a");
    public By progressBar = By.cssSelector("#project-table .progress");

    // =====================================================================
    // 5. LOCATORS: ADD / EDIT PROJECT MODAL
    // =====================================================================
    public By modalAjax = By.cssSelector("#ajaxModal.show, #ajaxModal.in, .modal-backdrop");
    public By modalProjectDialog = By.cssSelector("#ajaxModal.show, #ajaxModal.in");
    public By modalProject = By.id("ajaxModalContent");
    public By modalTitle = By.id("ajaxModalTitle");
    public By inputTitle = By.id("title");
    public By dropdownProjectType = By.cssSelector("#ajaxModal [id*='project-type'] .select2-choice");
    public By dropdownClient = By.cssSelector("#s2id_client_id .select2-choice");
    public By inputClientSearch = By.cssSelector("#select2-drop input");
    public By textareaDescription = By.id("description");
    public By inputStartDate = By.id("start_date");
    public By inputDeadline = By.id("deadline");
    public By inputPrice = By.id("price");
    public By dropdownStatusModal = By.xpath(
            "//div[@id='ajaxModal']//select[@name='status_id']/parent::*//a[contains(@class,'select2-choice')] | //div[@id='s2id_status']//a[contains(@class,'select2-choice')]");
    public By inputLabels = By.cssSelector("#s2id_project_labels input, #project_labels");
    public By btnSaveProject = By.cssSelector("#ajaxModal button[type='submit']");
    public By btnCloseModalIcon = By
            .cssSelector("#ajaxModal .modal-header button.close, #ajaxModal .modal-header button.btn-close");
    public By btnCloseModalButton = By.cssSelector(
            "#ajaxModal .modal-footer button[data-bs-dismiss='modal'], #ajaxModal .modal-footer button[data-dismiss='modal']");
    public By btnCloseModal = By.cssSelector("#ajaxModal [data-bs-dismiss='modal'], #ajaxModal [data-dismiss='modal']");
    public By labelTitleError = By.id("title-error");

    // =====================================================================
    // 6. LOCATORS: IMPORT & MANAGE LABELS & CONFIRM MODALS
    // =====================================================================
    public By linkDownloadSampleFile = By
            .xpath("//a[contains(@href,'download_sample_excel_file') or contains(.,'Download sample')]");
    public By inputImportFile = By
            .xpath("(//input[contains(@class,'dz-hidden-input')])[last()] | //input[@type='file']");
    public By dropzoneContainer = By
            .cssSelector(".dropzone, #import-form .dz-default, #import-form .dropzone, .dz-clickable");
    public By btnImportNext = By.id("form-next");
    public By btnImportSubmit = By.id("form-submit");
    public By importFormContainer = By.cssSelector("#import-form, form#import-form, .import-form");
    // uploaded file preview in modal
    public By uploadedFilePreviews = By.id("uploaded-file-previews");
    public By uploadedFileName = By
            .cssSelector("#uploaded-file-previews [data-dz-name], #uploaded-file-previews .name");
    public By uploadedFileErrorMsg = By.cssSelector(
            "#uploaded-file-previews [data-dz-errormessage], #uploaded-file-previews .error.text-danger, #uploaded-file-previews strong.text-danger");
    public By btnDeleteUploadedFile = By.cssSelector(
            "#uploaded-file-previews [data-dz-remove], #uploaded-file-previews a[data-dz-remove], #uploaded-file-previews .dz-remove");

    // preview import
    public By previewImportArea = By.id("preview-area");
    public By tableImportPreview = By.cssSelector("#preview-area table");
    public By rowsImportPreviewTable = By.cssSelector("#preview-area table tr");
    public By cellImportPreviewErrorMsg = By.cssSelector("#preview-area td.text-danger");
    public By cellsImportFieldError = By.cssSelector("#preview-area td.error");
    public By btnImportPrevious = By.id("form-previous");

    public By inputLabelTitle = By.cssSelector("#labels-form #title, #labels-form input[name='title']");
    public By btnSaveLabel = By.cssSelector("#labels-form button[type='submit']");
    public By btnDeleteLabel = By.id("label-delete-btn");
    public By btnCancelEditLabel = By.id("cancel-edit-btn");

    public By modalConfirmDelete = By.id("confirmationModal");
    public By btnConfirmDelete = By.id("confirmDeleteButton");
    public By btnCancelDelete = By.cssSelector("#confirmationModal button.btn-default");

    // =====================================================================
    // 7. DYNAMIC LOCATOR FACTORY METHODS
    // =====================================================================
    public By getStatusFilterOptionLocator(String statusName) {
        return By.xpath("//ul[@data-act='multiselect']//li[normalize-space()='" + statusName
                + "'] | //ul[contains(@class,'dropdown-menu')]//*[self::a or self::li][contains(normalize-space(),'"
                + statusName + "')] | //select[@name='status_id']/option[contains(.,'" + statusName + "')]");
    }

    public By getStatusFilterActiveOptionLocator(String statusName) {
        return By.xpath("//ul[@data-act='multiselect']//li[contains(@class,'active') and normalize-space()='"
                + statusName + "']");
    }

    public By getLabelFilterOptionLocator(String labelName) {
        return By.xpath("//div[@id='select2-drop']//div[contains(@class,'select2-result-label') and normalize-space()='"
                + labelName + "']");
    }

    public By getDeadlineOptionLocator(String optionName) {
        return By.xpath(
                "//div[contains(@class,'datepicker-custom-list')]//div[contains(@class,'list-group-item') and normalize-space()='"
                        + optionName + "']");
    }

    public By getStartDateOptionLocator(String optionName) {
        return By.xpath(
                "//*[contains(@class,'custom-date-range-dropdown')]//a[contains(@class,'dropdown-item') and normalize-space()='"
                        + optionName + "']");
    }

    public By getPopoverColumnItemLocator(String columnName) {
        return By.xpath(
                "//div[contains(@class,'popover')]//li[contains(@class,'toggle-table-column') and (contains(normalize-space(),'"
                        + columnName + "') or contains(.,'" + columnName + "'))]");
    }

    public By getColumnHeaderLocator(String columnName) {
        return By.xpath("//table[@id='project-table']//th[contains(normalize-space(),'" + columnName + "')]");
    }

    public By getTableCellsByColumnIndexLocator(int colIndex) {
        return By.cssSelector("#project-table tbody tr td:nth-child(" + colIndex + ")");
    }

    public By getRowByProjectTitleLocator(String title) {
        return By.xpath("//table[@id='project-table']//tbody//tr[.//a[contains(normalize-space(),'" + title + "')]]");
    }

    public By getProjectRowCellLocator(String projectTitle, int colIndex) {
        return By.xpath("//table[@id='project-table']//tbody//tr[.//a[contains(normalize-space(),'" + projectTitle
                + "')]]//td[" + colIndex + "]");
    }

    @Step("Lấy text ô tại cột {1} của dự án '{0}'")
    public String getProjectRowCellText(String projectTitle, int colIndex) {
        By cellLocator = getProjectRowCellLocator(projectTitle, colIndex);
        return isElementPresent(cellLocator, 3) ? getTextElement(cellLocator).trim() : "";
    }

    public By getBtnEditByProjectTitleLocator(String title) {
        return By.xpath("//table[@id='project-table']//tbody//tr[.//a[contains(normalize-space(),'" + title
                + "')]]//a[@title='Edit project' or contains(@class,'edit')]");
    }

    public By getBtnDeleteByProjectTitleLocator(String title) {
        return By.xpath("//table[@id='project-table']//tbody//tr[.//a[contains(normalize-space(),'" + title
                + "')]]//a[@title='Delete project' or contains(@class,'delete')]");
    }

    public By getProjectLocatorByTitle(String title) {
        return By.xpath("//table[@id='project-table']//a[contains(normalize-space(),'" + title + "')]");
    }

    public By getTableProjectLocatorByTitle(String title) {
        return getProjectLocatorByTitle(title);
    }

    public By getTableLabelBadgeLocator(String projectTitle, String labelName) {
        return By.xpath("//table[@id='project-table']//tbody//tr[.//a[contains(normalize-space(),'" + projectTitle
                + "')]]//span[contains(@class,'badge') and normalize-space()='" + labelName + "']");
    }

    public By getPageLengthOptionLocator(String length) {
        return By.xpath("//ul[contains(@class,'select2-results')]//div[normalize-space()='" + length
                + "'] | //ul[contains(@class,'select2-results')]//li[contains(.,'" + length + "')]");
    }

    public By getPaginationPageItemLocator(String pageNumber) {
        return By.xpath("//div[@id='project-table_paginate']//a[normalize-space()='" + pageNumber + "']");
    }

    public By getTableMatchingRowByKeywordLocator(String keyword) {
        return By.xpath(
                "//table[@id='project-table']//tbody//tr[.//a[contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'"
                        + keyword.toLowerCase().trim() + "')]]");
    }

    public By getTableRowByStatusLocator(String status) {
        return By.xpath("//table[@id='project-table']//tbody//tr[.//td[normalize-space()='" + status.trim() + "']]");
    }

    public By getTableRowByAnyOfStatusesLocator(List<String> statuses) {
        StringBuilder xpathBuilder = new StringBuilder("//table[@id='project-table']//tbody//tr[.//td[");
        for (int i = 0; i < statuses.size(); i++) {
            if (i > 0) {
                xpathBuilder.append(" or ");
            }
            xpathBuilder.append("normalize-space()='").append(statuses.get(i).trim()).append("'");
        }
        xpathBuilder.append("]]");
        return By.xpath(xpathBuilder.toString());
    }

    public By getSelect2OptionByTextLocator(String text) {
        return By.xpath("//div[@id='select2-drop']//div[contains(@class,'select2-result-label') and normalize-space()='"
                + text + "']");
    }

    public By getColorTagLocator(String colorCode) {
        return By.cssSelector(".color-palet span[data-color='" + colorCode + "']");
    }

    public By getManageLabelItemLocator(String labelName) {
        return By.xpath("//div[@id='label-show-area']//span[normalize-space()='" + labelName + "']");
    }

    // =====================================================================
    // 8. ACTIONS & VERIFICATIONS: TABLE GRID COLUMNS
    // =====================================================================
    @Step("Kiểm tra hiển thị đầy đủ 8 cột thông tin chuẩn trong Grid")
    public void verifyGridColumnsPresent() {
        waitForElementVisible(tableProjects, 10);
        FailureHandling softAssert = FailureHandling.CONTINUE_ON_FAILURE;
        List<String> expectedColumns = ProjectModel.TABLE_COLUMNS;
        for (String columnName : expectedColumns) {
            verifyElementVisible(getColumnHeaderLocator(columnName), 5, "Thiếu cột: " + columnName, softAssert);
        }
    }

    // =====================================================================
    // 9. ACTIONS: ADD / EDIT PROJECT
    // =====================================================================
    @Step("Click nút Add project")
    public void clickAddProject() {
        waitForElementInvisible(modalAjax, 5);
        waitForElementVisible(btnAddProject, 10);
        clickElementWithJs(btnAddProject);
        waitForElementVisible(modalProjectDialog, 10);
        waitForElementVisible(inputTitle, 15);
        waitForPageLoaded();
        verifyElementPresent(modalProject, 5, "Modal Add project không xuất hiện.");
    }

    @Step("Điền thông tin chi tiết vào form Add/Edit Project")
    public void fillProjectForm(String title, String projectType, String clientName, String price, String description,
            String startDate, String deadline, String labels) {
        waitForElementVisible(inputTitle, 10);
        if (title != null && !title.isEmpty()) {
            clearAndFillText(inputTitle, title);
        }
        if (projectType != null && !projectType.isEmpty()) {
            waitForElementVisible(dropdownProjectType, 5);
            clickElementWithJs(dropdownProjectType);
            By projectTypeOption = getSelect2OptionByTextLocator(projectType);
            waitForElementVisible(projectTypeOption, 5);
            clickElementWithJs(projectTypeOption);
        }
        if (clientName != null && !clientName.isEmpty()) {
            waitForElementVisible(dropdownClient, 5);
            clickElementWithJs(dropdownClient);
            waitForElementVisible(inputClientSearch, 5);
            setText(inputClientSearch, clientName, Keys.ENTER);
        }
        if (price != null && !price.isEmpty()) {
            clearAndFillText(inputPrice, price);
        }
        if (description != null && !description.isEmpty()) {
            clearAndFillText(textareaDescription, description);
        }
        if (startDate != null && !startDate.isEmpty()) {
            clearAndFillText(inputStartDate, startDate);
            executeScript("$('#start_date').datepicker('hide');");
        }
        if (deadline != null && !deadline.isEmpty()) {
            clearAndFillText(inputDeadline, deadline);
            executeScript("$('#deadline').datepicker('hide');");
        }
        if (labels != null && !labels.isEmpty()) {
            waitForElementVisible(inputLabels, 5);
            clickElementWithJs(inputLabels);
            setText(inputLabels, labels, Keys.ENTER);
            executeScript("$('#s2id_project_labels').select2('close');");
        }

        // Đóng các datepicker / select2 đang nổi đè lên form
        executeScript("$('.datepicker').hide(); $('.select2-drop').hide();");

        // Đảm bảo Title chắc chắn có giá trị trước khi lưu
        if (title != null && !title.isEmpty()) {
            String currentTitle = getAttributeElement(inputTitle, "value");
            if (currentTitle == null || currentTitle.trim().isEmpty() || !currentTitle.trim().equals(title.trim())) {
                clearAndFillText(inputTitle, title);
            }
        }
    }

    @Step("Thêm mới dự án đầy đủ thông tin: Title={0}, Type={1}, Client={2}, Price={3}")
    public void addNewProject(String title, String projectType, String clientName, String price, String description,
            String startDate, String deadline, String labels) {
        clickAddProject();
        fillProjectForm(title, projectType, clientName, price, description, startDate, deadline, labels);

        clickElementWithJs(btnSaveProject);

        if (isElementPresent(labelTitleError, 2)) {
            clearAndFillText(inputTitle, title);
            clickElementWithJs(btnSaveProject);
        }

        waitForElementInvisible(modalAjax, 10);
        waitForPageLoaded();

        String infoLog = String.format(
                "➕ [ADD PROJECT] Đã tạo dự án: [Title: %s | Type: %s | Client: %s | Price: %s | StartDate: %s | Deadline: %s | Label: %s]",
                title,
                (projectType != null && !projectType.isEmpty()) ? projectType : "Default",
                (clientName != null && !clientName.isEmpty()) ? clientName : "None",
                (price != null && !price.isEmpty()) ? price : "0",
                (startDate != null && !startDate.isEmpty()) ? startDate : "None",
                (deadline != null && !deadline.isEmpty()) ? deadline : "None",
                (labels != null && !labels.isEmpty()) ? labels : "None");
        LogUtils.info(infoLog);
        AllureManager.saveTextLog(infoLog);
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info(infoLog);
        }
    }

    @Step("Thêm mới dự án với Title: {0}, Price: {1}")
    public void addNewProject(String title, String price) {
        addNewProject(title, "", "", price, "", "", "", "");
    }

    @Step("Đóng modal Add Project bằng icon 'X' trên Header")
    public void closeModalByIcon() {
        if (isElementPresent(btnCloseModalIcon, 3)) {
            clickElementWithJs(btnCloseModalIcon);
            waitForElementInvisible(modalAjax, 5);
            waitForPageLoaded();
        }
    }

    @Step("Đóng modal Add Project bằng nút 'Close' dưới Footer")
    public void closeModalByButton() {
        if (isElementPresent(btnCloseModalButton, 3)) {
            clickElementWithJs(btnCloseModalButton);
            waitForElementInvisible(modalAjax, 5);
            waitForPageLoaded();
        }
    }

    @Step("Đóng modal Add Project")
    public void closeModal() {
        executeScript("$('.select2-drop, .select2-drop-mask').remove(); if(window.$ && $.fn.select2) { $('select').select2('close'); }");
        if (isElementPresent(btnCloseModalButton, 3)) {
            closeModalByButton();
        } else if (isElementPresent(btnCloseModalIcon, 3)) {
            closeModalByIcon();
        } else if (isElementPresent(btnCloseModal, 3)) {
            clickElementWithJs(btnCloseModal);
            waitForElementInvisible(modalAjax, 5);
            waitForPageLoaded();
        }
        sleep(0.5);
    }

    @Step("Chỉnh sửa trạng thái của dự án {0} thành {1}")
    public void editProjectStatus(String projectTitle, String status) {
        // Search
        searchProject(projectTitle);

        // Open edit project
        openEditProject(projectTitle);

        // Fill status
        waitForElementVisible(dropdownStatusModal, 10);
        clickElement(dropdownStatusModal);

        By statusOption = getSelect2OptionByTextLocator(status);
        waitForElementVisible(statusOption, 5);
        clickElement(statusOption);

        clickElement(btnSaveProject);
        waitForElementInvisible(modalProject, 10);
        clearSearch();

        String statusLog = "🔄 [UPDATE STATUS] Đã cập nhật trạng thái dự án '" + projectTitle + "' thành '" + status
                + "'";
        LogUtils.info(statusLog);
        AllureManager.saveTextLog(statusLog);
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info(statusLog);
        }
    }

    // =====================================================================
    // 10. ACTIONS & VERIFICATIONS: IMPORT & EXPORT PROJECTS (TC_IMP_001 → TC_IMP_034)
    // =====================================================================

    // --- 10.1 Modal UI & Navigation ---
    @Step("Click nút Import projects")
    public void clickImportProjects() {
        waitForElementVisible(btnImportProjects, 10);
        clickElementWithJs(btnImportProjects);
        waitForElementVisible(modalTitle, 10);
        waitForElementVisible(linkDownloadSampleFile, 10);
        waitForElementVisible(dropzoneContainer, 10);
        waitForElementPresent(inputImportFile, 10);
        verifyElementPresent(modalProject, 5, "Modal Import projects không xuất hiện.");
    }

    @Step("[TC_IMP_003] Verify UI elements mặc định trong modal Import Projects")
    public void verifyImportModalUIElements(FailureHandling soft) {
        // 1. Tiêu đề modal
        String actualTitle = getTextElement(modalTitle);
        verifyTrue(actualTitle.equalsIgnoreCase(ProjectModel.MODAL_TITLE_IMPORT),
                "[IMP_003-1] Tiêu đề modal không đúng. Expected: '" + ProjectModel.MODAL_TITLE_IMPORT
                        + "' | Actual: '" + actualTitle + "'",
                soft);

        // 2. Link Download sample file
        verifyElementVisible(linkDownloadSampleFile, 5,
                "[IMP_003-2] Link 'Download sample file' không hiển thị trong modal.", soft);
        String sampleHref = getAttributeElement(linkDownloadSampleFile, "href");
        verifyTrue(sampleHref != null && !sampleHref.isEmpty(),
                "[IMP_003-2b] Link 'Download sample file' không có href hợp lệ.", soft);

        // 3. Vùng upload file (dropzone hoặc input file)
        boolean hasDropzone = isElementPresent(dropzoneContainer, 3);
        boolean hasInputFile = isElementPresent(inputImportFile, 3);
        verifyTrue(hasDropzone || hasInputFile,
                "[IMP_003-3] Vùng upload file (dropzone / input[type=file]) không hiển thị trong modal.", soft);

        // 4. Nút Next ở Footer
        verifyElementVisible(btnImportNext, 5,
                "[IMP_003-4] Nút 'Next' (form-next) không hiển thị ở Footer.", soft);

        // 5. Nút Close ở Footer
        verifyElementVisible(btnCloseModalButton, 5,
                "[IMP_003-5] Nút 'Close' không hiển thị ở Footer.", soft);

        // 6. Icon X ở Header
        verifyElementVisible(btnCloseModalIcon, 5,
                "[IMP_003-6] Icon 'X' không hiển thị ở Header của modal.", soft);

        // 7. Nút Next phải DISABLED khi chưa chọn file
        verifyElementDisabled(btnImportNext, 5,
                "[IMP_003-7] Nút 'Next' phải DISABLED trước khi chọn file.",
                soft);
    }

    @Step("Click tải file mẫu sample trong modal Import projects")
    public void downloadSampleFile() {
        waitForElementVisible(linkDownloadSampleFile, 5);
        clickElement(linkDownloadSampleFile);
        sleep(2);
    }

    @Step("Xóa file vừa upload trong modal import")
    public void removeUploadedFile() {
        waitForElementClickable(btnDeleteUploadedFile, 5);
        clickElement(btnDeleteUploadedFile);
    }

    @Step("Click nút Back trên màn hình Preview Import")
    public void clickImportBack() {
        waitForElementClickable(btnImportPrevious, 5);
        clickElement(btnImportPrevious);
        waitForElementVisible(dropzoneContainer, 5);
    }

    // --- 10.2 File Upload & Import Execution ---
    @Step("Upload file và xác nhận Import projects: {0}")
    public void importProjectsFileSuccess(String filePath, FailureHandling flowControl) {
        waitForElementVisible(dropzoneContainer, 10);
        uploadFileWithSendKeys(inputImportFile, filePath);
        verifyUploadedFileName(filePath, flowControl);
        waitForElementClickable(btnImportNext, 15);
        clickElement(btnImportNext);
        waitForElementClickable(btnImportSubmit, 15);
        clickElement(btnImportSubmit);
        waitForElementInvisible(modalProject, 10);
        waitForElementInvisible(tableProcessing, 10);
        waitForPageLoaded();
    }

    @Step("Upload file và xác nhận Import projects: {0}")
    public void importProjectsFileSuccess(String filePath) {
        importProjectsFileSuccess(filePath, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Upload file và chuyển sang màn hình Preview Import: {0}")
    public void importToPreviewPage(String filePath, FailureHandling flowControl) {
        waitForElementVisible(dropzoneContainer, 10);
        uploadFileWithSendKeys(inputImportFile, filePath);
        sleep(1);
        verifyUploadedFileName(filePath, flowControl);
        waitForElementClickable(btnImportNext, 15);
        clickElement(btnImportNext);
        waitForElementVisible(previewImportArea, 10);
    }

    @Step("Upload file không hợp lệ và xác thực thông báo lỗi: {1}")
    public void importFileAndVerifyError(String filePath, String expectedError, FailureHandling flowControl) {
        waitForElementVisible(dropzoneContainer, 10);
        uploadFileWithSendKeys(inputImportFile, filePath);
        sleep(1);
        verifyUploadedFileName(filePath, flowControl);
        verifyUploadedFileError(expectedError, flowControl);
    }

    // --- 10.3 Import Verifications & Errors ---
    @Step("Xác thực tên file hiển thị trong vùng preview upload: {0}")
    public void verifyUploadedFileName(String filePath, FailureHandling flowControl) {
        String fileName = new File(filePath).getName();
        waitForElementPresent(uploadedFileName, 10);
        try {
            waitForElementVisible(uploadedFileName, 5);
        } catch (Exception ignored) {
        }
        String actualFileName = getTextElement(uploadedFileName);
        verifyTrue(actualFileName.contains(fileName),
                "Tên file hiển thị trong modal không đúng. Expected chứa: '" + fileName + "' | Actual: '"
                        + actualFileName + "'",
                flowControl);
    }

    @Step("Xác thực thông báo lỗi khi tải file không hợp lệ: {0}")
    public void verifyUploadedFileError(String expectedError, FailureHandling flowControl) {
        waitForElementVisible(uploadedFileErrorMsg, 10);
        String actualError = getTextElement(uploadedFileErrorMsg).trim();
        verifyTrue(actualError.contains(expectedError),
                "Thông báo lỗi khi tải file không đúng. Expected: '" + expectedError + "' | Actual: '"
                        + actualError + "'",
                flowControl);
        // Khi file lỗi, verify hệ thống không cho phép chuyển sang màn hình Preview
        verifyTrue(!isElementVisible(previewImportArea, 1),
                "Không được chuyển sang màn hình Preview khi upload file không hợp lệ.", flowControl);
    }

    @Step("Xác thực thông báo lỗi khi tải file không hợp lệ: {0}")
    public void verifyUploadedFileError(String expectedError) {
        verifyUploadedFileError(expectedError, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Lấy nội dung thông báo lỗi trong màn hình Preview Import")
    public String getImportPreviewErrorText() {
        waitForElementVisible(cellImportPreviewErrorMsg, 5);
        return getTextElement(cellImportPreviewErrorMsg).trim();
    }

    @Step("Xác thực màn hình Preview Import hiển thị lỗi: {0}")
    public void verifyImportPreviewHasError(String expectedError, FailureHandling flowControl) {
        waitForElementVisible(cellImportPreviewErrorMsg, 10);
        String actualError = getTextElement(cellImportPreviewErrorMsg);
        verifyTrue(actualError.contains(expectedError),
                "Thông báo lỗi trong preview import không đúng. Expected chứa: '" + expectedError
                        + "' | Actual: '"
                        + actualError + "'",
                flowControl);
    }

    @Step("Xác thực nút Upload (Submit) bị vô hiệu hoá khi file import có lỗi: {0}")
    public void verifyImportSubmitButtonDisabled(String msg, FailureHandling flowControl) {
        verifyElementDisabled(btnImportSubmit, 5,
                "Nút Upload (Submit) vẫn click được trong " + msg, flowControl);
    }

    @Step("Lấy số lượng dòng dữ liệu (data rows) trong màn hình Preview Import")
    public int getImportPreviewDataRowCount() {
        waitForElementVisible(previewImportArea, 10);
        List<WebElement> rows = getWebElements(rowsImportPreviewTable);
        return Math.max(0, rows.size() - 1);
    }

    @Step("Xác thực màn hình Preview Import không có dòng dữ liệu nào")
    public void verifyImportPreviewHasNoDataRows(FailureHandling flowControl) {
        int dataRowCount = getImportPreviewDataRowCount();
        verifyTrue(dataRowCount == 0,
                "Màn hình Preview Import vẫn có " + dataRowCount + " dòng dữ liệu, mong đợi 0 dòng.", flowControl);
    }

    // --- 10.4 Post-Import Verification & Test Data (Thread-safe via ProjectDTO)
    // ---
    private ExcelHelpers excelHelpers;

    private ExcelHelpers getExcelHelpers() {
        if (excelHelpers == null) {
            excelHelpers = new ExcelHelpers();
        }
        return excelHelpers;
    }

    @Step("Đọc dữ liệu dự án từ file Excel: {0}, dòng {1} trả về đối tượng ProjectDTO độc lập")
    public ProjectDTO getImportProjectTestData(String excelFilePath, int rowIdx) {
        ExcelHelpers excel = getExcelHelpers();
        excel.setExcelFile(excelFilePath, ProjectModel.SHEET_IMPORT_PROJECTS);
        return ProjectDTO.builder()
                .title(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_TITLE))
                .projectType(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_TYPE))
                .client(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_CLIENT))
                .description(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_DESCRIPTION))
                .labels(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_LABELS))
                .startDate(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_START_DATE))
                .deadline(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_DEADLINE))
                .status(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_STATUS))
                .price(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_PRICE))
                .build();
    }

    @Step("Đọc toàn bộ danh sách dữ liệu dự án từ file Excel: {0}")
    public List<ProjectDTO> getImportProjectTestDataList(String excelFilePath) {
        ExcelHelpers excel = getExcelHelpers();
        excel.setExcelFile(excelFilePath, ProjectModel.SHEET_IMPORT_PROJECTS);
        int totalRows = excel.getRows();
        List<ProjectDTO> projectList = new ArrayList<>();
        for (int rowIdx = 1; rowIdx <= totalRows; rowIdx++) {
            projectList.add(ProjectDTO.builder()
                    .title(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_TITLE))
                    .projectType(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_TYPE))
                    .client(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_CLIENT))
                    .description(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_DESCRIPTION))
                    .labels(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_LABELS))
                    .startDate(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_START_DATE))
                    .deadline(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_DEADLINE))
                    .status(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_STATUS))
                    .price(excel.getCellData(rowIdx, ProjectModel.COL_IMPORT_PRICE))
                    .build());
        }
        return projectList;
    }

    @Step("Xác thực thông tin các trường của dự án (ProjectDTO) sau khi import trên bảng (Soft Assert)")
    public void verifyImportedProjectDetails(ProjectDTO project, FailureHandling flowControl) {
        if (project == null) {
            verifyTrue(false, "Đối tượng ProjectDTO truyền vào để verify không được để null.", flowControl);
            return;
        }

        String title = project.getTitle() != null ? project.getTitle().trim() : "";
        searchProject(title);
        verifyAllRowsHaveSearchResults(title, flowControl);

        if (project.getLabels() != null && !project.getLabels().trim().isEmpty()) {
            verifyAllRowsHaveLabel(project.getLabels().trim(), flowControl);
        }
        if (project.getDeadline() != null && !project.getDeadline().trim().isEmpty()) {
            List<String> rowDeadlines = getListElementsText(tableCellDeadlines);
            for (String d : rowDeadlines) {
                if (d != null && !d.trim().isEmpty() && !d.trim().equals("-")) {
                    verifyTrue(d.trim().equalsIgnoreCase(project.getDeadline().trim()),
                            "Bản ghi có Deadline=" + d + " không khớp ngày mong đợi=" + project.getDeadline(), flowControl);
                }
            }
        }
        if (project.getStartDate() != null && !project.getStartDate().trim().isEmpty()) {
            List<String> rowStartDates = getListElementsText(tableCellStartDates);
            for (String d : rowStartDates) {
                if (d != null && !d.trim().isEmpty() && !d.trim().equals("-")) {
                    verifyTrue(d.trim().equalsIgnoreCase(project.getStartDate().trim()),
                            "Bản ghi có StartDate=" + d + " không khớp ngày mong đợi=" + project.getStartDate(), flowControl);
                }
            }
        }
        if (project.getPrice() != null && !project.getPrice().trim().isEmpty()) {
            verifyAllRowsHaveBudget(project.getPrice().trim(), flowControl);
        }
        if (project.getStatus() != null && !project.getStatus().trim().isEmpty()) {
            verifyAllRowsHaveStatus(project.getStatus().trim(), flowControl);
        }
    }

    // --- 10.5 Export to Excel Actions ---
    @Step("Click nút Export Excel xuất danh sách dự án")
    public void exportProjectsToExcel() {
        waitForElementVisible(btnExcel, 10);
        clickElement(btnExcel);
        sleep(2);
    }

    @Step("Click nút Export Excel")
    public void clickExportExcel() {
        exportProjectsToExcel();
    }

    // =====================================================================
    // 11. ACTIONS & VERIFICATIONS: MANAGE LABELS
    // =====================================================================
    @Step("Click nút Manage labels")
    public void clickManageLabels() {
        waitForElementPresent(btnManageLabels, 10);
        clickElementWithJs(btnManageLabels);
        waitForPageLoaded();
        verifyElementPresent(modalProject, 5, "Modal Manage labels không xuất hiện.");
        waitForElementVisible(inputLabelTitle, 10);
    }

    @Step("Thêm Label mới: {0}")
    public void addNewLabel(String labelName) {
        clickManageLabels();
        waitForElementVisible(inputLabelTitle, 10);
        clearText(inputLabelTitle);
        setText(inputLabelTitle, labelName);
        clickElementWithJs(btnSaveLabel);
        waitForElementPresent(getManageLabelItemLocator(labelName), 10);
        closeModal();
    }

    @Step("Thêm Label mới kèm màu sắc: {0} - Màu: {1}")
    public void addNewLabelWithColor(String labelName, String colorCode) {
        clickManageLabels();
        waitForElementVisible(inputLabelTitle, 10);
        By colorTag = getColorTagLocator(colorCode);
        if (isElementPresent(colorTag, 3)) {
            clickElementWithJs(colorTag);
        }
        clearText(inputLabelTitle);
        setText(inputLabelTitle, labelName);
        clickElementWithJs(btnSaveLabel);
        waitForElementPresent(getManageLabelItemLocator(labelName), 10);
        closeModal();
    }

    @Step("Lấy mã màu của Label trong Manage modal: {0}")
    public String getLabelColorInManageModal(String labelName) {
        clickManageLabels();
        waitForElementVisible(inputLabelTitle, 10);
        By labelItem = getManageLabelItemLocator(labelName);
        String color = "";
        if (isElementPresent(labelItem, 5)) {
            color = getAttributeElement(labelItem, "data-color");
            if (color == null || color.isEmpty()) {
                color = getAttributeElement(labelItem, "style");
            }
        }
        closeModal();
        return color;
    }

    @Step("Lấy màu của Label hiển thị trên bảng Projects: Project={0}, Label={1}")
    public String getLabelColorOnProjectsTable(String projectTitle, String labelName) {
        searchProject(projectTitle);
        By badge = getTableLabelBadgeLocator(projectTitle, labelName);
        String color = "";
        if (isElementPresent(badge, 5)) {
            color = getAttributeElement(badge, "style");
            if (color == null || color.isEmpty()) {
                color = getCssValueElement(badge, "background-color");
            }
        }
        clearSearch();
        return color;
    }

    @Step("Xác thực màu của Label trong modal Manage Labels: {0} - Màu mong đợi: {1}")
    public void verifyLabelColorInManageModal(String labelName, String expectedColor) {
        verifyLabelColorInManageModal(labelName, expectedColor, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Xác thực màu của Label trong modal Manage Labels: {0} - Màu mong đợi: {1} với FailureHandling")
    public void verifyLabelColorInManageModal(String labelName, String expectedColor, FailureHandling flowControl) {
        String modalColor = getLabelColorInManageModal(labelName);
        verifyTrue(isColorMatching(modalColor, expectedColor),
                "Màu của label '" + labelName + "' trong Manage modal không khớp (" + expectedColor + "). Actual: "
                        + modalColor,
                flowControl);
    }

    @Step("Xác thực màu của Label badge trên bảng Projects: Project={0}, Label={1} - Màu mong đợi: {2}")
    public void verifyLabelColorOnProjectsTable(String projectTitle, String labelName, String expectedColor) {
        verifyLabelColorOnProjectsTable(projectTitle, labelName, expectedColor, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Xác thực màu của Label badge trên bảng Projects: Project={0}, Label={1} - Màu mong đợi: {2} với FailureHandling")
    public void verifyLabelColorOnProjectsTable(String projectTitle, String labelName, String expectedColor,
            FailureHandling flowControl) {
        String tableBadgeColor = getLabelColorOnProjectsTable(projectTitle, labelName);
        verifyTrue(isColorMatching(tableBadgeColor, expectedColor),
                "Màu của label badge '" + labelName + "' trên bảng Projects không khớp (" + expectedColor
                        + "). Actual: " + tableBadgeColor,
                flowControl);
    }

    public static String hexToRgbString(String hex) {
        if (hex == null || !hex.startsWith("#") || hex.length() != 7) {
            return hex;
        }
        try {
            int r = Integer.parseInt(hex.substring(1, 3), 16);
            int g = Integer.parseInt(hex.substring(3, 5), 16);
            int b = Integer.parseInt(hex.substring(5, 7), 16);
            return "rgb(" + r + ", " + g + ", " + b + ")";
        } catch (Exception e) {
            return hex;
        }
    }

    public boolean isColorMatching(String actualColor, String expectedHex) {
        if (actualColor == null || expectedHex == null) {
            return false;
        }
        String lowerActual = actualColor.toLowerCase();
        String lowerHex = expectedHex.toLowerCase();
        if (lowerActual.contains(lowerHex)) {
            return true;
        }
        String rgb = hexToRgbString(expectedHex);
        return lowerActual.replaceAll("\\s+", "").contains(rgb.replaceAll("\\s+", ""));
    }

    @Step("Xóa Label: {0}")
    public void deleteLabel(String labelName) {
        if (labelName == null || labelName.trim().isEmpty()) {
            return;
        }
        executeScript("$('.select2-drop, .select2-drop-mask').remove();");
        clickManageLabels();
        waitForElementVisible(inputLabelTitle, 10);

        By labelItem = getManageLabelItemLocator(labelName);
        waitForElementVisible(labelItem, 5);
        clickElementWithJs(labelItem);

        waitForElementVisible(btnDeleteLabel, 5);
        clickElementWithJs(btnDeleteLabel);

        waitForElementInvisible(labelItem, 5);
        closeModal();
    }

    @Step("Sửa tên Label từ '{0}' thành '{1}'")
    public void editLabel(String oldLabelName, String newLabelName) {
        executeScript("$('.select2-drop, .select2-drop-mask').remove(); if(window.$ && $.fn.select2) { $('select').select2('close'); }");
        clickManageLabels();
        waitForElementVisible(inputLabelTitle, 10);
        By labelItem = getManageLabelItemLocator(oldLabelName);
        waitForElementVisible(labelItem, 5);
        clickElementWithJs(labelItem);
        waitForElementVisible(btnDeleteLabel, 5);
        waitForElementVisible(btnCancelEditLabel, 5);
        executeScript("$('#labels-form #title').val('').focus();");
        clearText(inputLabelTitle);
        setText(inputLabelTitle, newLabelName);
        clickElementWithJs(btnSaveLabel);
        waitForElementPresent(getManageLabelItemLocator(newLabelName), 10);
        waitForElementInvisible(labelItem, 5);
        closeModal();
    }

    @Step("Xác thực Label hiển thị tại cả 4 vị trí (Manage modal, Filter Top bar, Add modal, Edit modal) bằng Soft Assert: {0}")
    public void verifyLabelPresentAcrossAllComponents(String labelName) {
        FailureHandling soft = FailureHandling.CONTINUE_ON_FAILURE;

        // 1. Manage labels modal
        clickManageLabels();
        verifyElementPresent(getManageLabelItemLocator(labelName), 5,
                "❌ Label '" + labelName + "' không hiển thị trong Manage labels modal!", soft);
        closeModal();

        // 2. Dropdown Filter Labels trên Top bar (cần refresh để top bar đồng bộ label mới từ server)
        reloadPage();
        waitForPageLoaded();
        waitForElementVisible(tableProjects, 10);
        if (isElementPresent(dropdownLabels, 3)) {
            clickElementWithJs(dropdownLabels);
            verifyElementPresent(getLabelFilterOptionLocator(labelName), 5,
                    "❌ Label '" + labelName + "' không hiển thị trong dropdown Filter Labels!", soft);
            executeScript("$('.select2-drop, .select2-drop-mask').remove(); if(window.$ && $.fn.select2) { $('select').select2('close'); }");
        }

        // 3. Trường Labels trong modal Add Project
        clickAddProject();
        waitForElementVisible(inputTitle, 10);
        clickElementWithJs(inputLabels);
        setText(inputLabels, labelName);
        verifyElementPresent(getSelect2OptionByTextLocator(labelName), 3,
                "❌ Label '" + labelName + "' không hiển thị trong trường Labels của modal Add Project!", soft);
        closeModal();

        // 4. Trường Labels trong modal Edit Project
        waitForElementVisible(btnFirstEdit, 10);
        clickElementWithJs(btnFirstEdit);
        waitForElementVisible(inputTitle, 10);
        clickElementWithJs(inputLabels);
        setText(inputLabels, labelName);
        verifyElementPresent(getSelect2OptionByTextLocator(labelName), 3,
                "❌ Label '" + labelName + "' không hiển thị trong trường Labels của modal Edit Project!", soft);
        closeModal();

        stopSoftAssertAll();
    }

    @Step("Xác thực Label đã biến mất khỏi cả 4 vị trí (Manage modal, Filter Top bar, Add modal, Edit modal) bằng Soft Assert: {0}")
    public void verifyLabelNotPresentAcrossAllComponents(String labelName) {
        FailureHandling soft = FailureHandling.CONTINUE_ON_FAILURE;

        // 1. Manage labels modal
        clickManageLabels();
        verifyElementNotPresent(getManageLabelItemLocator(labelName), 2,
                "❌ Label '" + labelName + "' vẫn còn hiển thị trong Manage labels modal!", soft);
        closeModal();

        // 2. Dropdown Filter Labels trên Top bar
        reloadPage();
        waitForPageLoaded();
        waitForElementVisible(tableProjects, 10);
        if (isElementPresent(dropdownLabels, 3)) {
            clickElementWithJs(dropdownLabels);
            verifyElementNotPresent(getLabelFilterOptionLocator(labelName), 2,
                    "❌ Label '" + labelName + "' vẫn còn hiển thị trong dropdown Filter Labels!", soft);
            clickElementWithJs(dropdownLabels);
        }

        // 3. Trường Labels trong modal Add Project
        clickAddProject();
        waitForElementVisible(inputTitle, 10);
        clickElementWithJs(inputLabels);
        setText(inputLabels, labelName);
        verifyElementNotPresent(getSelect2OptionByTextLocator(labelName), 2,
                "❌ Label '" + labelName + "' vẫn còn hiển thị trong modal Add Project!", soft);
        closeModal();

        // 4. Trường Labels trong modal Edit Project
        waitForElementVisible(btnFirstEdit, 10);
        clickElementWithJs(btnFirstEdit);
        waitForElementVisible(inputTitle, 10);
        clickElementWithJs(inputLabels);
        setText(inputLabels, labelName);
        verifyElementNotPresent(getSelect2OptionByTextLocator(labelName), 2,
                "❌ Label '" + labelName + "' vẫn còn hiển thị trong modal Edit Project!", soft);
        closeModal();

        stopSoftAssertAll();
    }

    // =====================================================================
    // 12. ACTIONS & VERIFICATIONS: COLUMN VISIBILITY
    // =====================================================================
    @Step("Toggle ẩn/hiện cột: {0}")
    public void toggleColumn(String columnName) {
        waitForElementPresent(tableProjects, 10);
        if (!isElementPresent(popoverColumnVisibility, 1)) {
            clickElement(btnColumnVisibility);
            waitForElementVisible(popoverColumnVisibility, 5);
        }
        By colItem = getPopoverColumnItemLocator(columnName);
        waitForElementVisible(colItem, 5);
        clickElement(colItem);
        // Nếu popover đóng thì chờ đóng, nếu popover giữ nguyên thì click lại nút đóng hoặc bấm tiếp cột kế tiếp
        if (isElementPresent(popoverColumnVisibility, 1)) {
            clickElement(btnColumnVisibility);
            waitForElementInvisible(popoverColumnVisibility, 5);
        }
        waitForPageLoaded();
    }

    @Step("Kiểm tra cột '{0}' đã bị ẩn khỏi bảng")
    public void verifyColumnHidden(String columnName) {
        verifyColumnHidden(columnName, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Kiểm tra cột '{0}' đã bị ẩn khỏi bảng (Soft Assert)")
    public void verifyColumnHidden(String columnName, FailureHandling flowControl) {
        waitForElementPresent(tableProjects, 10);
        verifyElementNotVisible(getColumnHeaderLocator(columnName), 5,
                "Cột '" + columnName + "' vẫn còn hiển thị trên bảng!", flowControl);
    }

    @Step("Kiểm tra cột '{0}' đang hiển thị trên bảng")
    public void verifyColumnPresent(String columnName) {
        verifyColumnPresent(columnName, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Kiểm tra cột '{0}' đang hiển thị trên bảng (Soft Assert)")
    public void verifyColumnPresent(String columnName, FailureHandling flowControl) {
        waitForElementPresent(tableProjects, 10);
        verifyElementVisible(getColumnHeaderLocator(columnName), 5,
                "Cột '" + columnName + "' không hiển thị trên bảng!", flowControl);
    }

    // =====================================================================
    // 13. ACTIONS & VERIFICATIONS: SEARCH & FILTER
    // =====================================================================
    @Step("Tìm kiếm dự án với từ khóa: {0}")
    public void searchProject(String keyword) {
        executeScript("window.scrollTo(0, 0); document.querySelectorAll('.page-container, #content, body, html').forEach(function(el){ el.scrollTop = 0; });");
        waitForElementPresent(inputSearch, 10);
        scrollToElementAtTop(inputSearch);
        waitForElementVisible(inputSearch, 10);
        clearText(inputSearch);
        setText(inputSearch, keyword);
        executeScript(
                "if(window.$ && $.fn.DataTable && $('#project-table').length) { $('#project-table').DataTable().search(arguments[0]).draw(); } else { var input = document.querySelector('#project-table_filter input'); if(input) { $(input).trigger('input'); } }",
                keyword);
        waitForElementInvisible(tableProcessing, 5);
        waitForElementInvisible(tableEmptyMessage, 5);
        waitForPageLoaded();
    }

    @Step("Xóa trắng ô tìm kiếm")
    public void clearSearch() {
        if (isElementPresent(inputSearch, 2)) {
            clearText(inputSearch);
            executeScript(
                    "if(window.$ && $.fn.DataTable && $('#project-table').length) { $('#project-table').DataTable().search('').draw(); } else { var input = document.querySelector('#project-table_filter input'); if(input) { input.value = ''; $(input).trigger('input'); } }");
            waitForElementInvisible(tableProcessing, 5);
            waitForElementInvisible(tableEmptyMessage, 5);
            waitForPageLoaded();
        }
    }

    @Step("Xác thực tất cả các dòng trên bảng chứa từ khóa: {0}")
    public void verifySearchResultsContainKeyword(String keyword) {
        verifyAllRowsHaveSearchResults(keyword, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Xác thực tất cả các dòng trên bảng chứa từ khóa: {0}")
    public void verifyAllRowsHaveSearchResults(String keyword) {
        verifyAllRowsHaveSearchResults(keyword, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Xác thực tất cả các dòng trên bảng chứa từ khóa: {0} (Soft Assert)")
    public void verifyAllRowsHaveSearchResults(String keyword, FailureHandling flowControl) {
        waitForElementPresent(getTableMatchingRowByKeywordLocator(keyword), 5);
        if (getTotalRecordsCount() > 10) {
            changePageLength("All");
            waitForElementInvisible(tableProcessing, 5);
            waitForPageLoaded();
        }

        List<String> rowTitles = getListElementsText(tableCellTitles);
        verifyTrue(!rowTitles.isEmpty(), "Bảng không có bản ghi nào sau khi search từ khóa: '" + keyword + "'",
                flowControl);

        for (String title : rowTitles) {
            verifyTrue(title.toLowerCase().trim().contains(keyword.toLowerCase().trim()),
                    "Bản ghi '" + title + "' trên bảng không chứa từ khóa tìm kiếm: '" + keyword + "'", flowControl);
        }
    }

    @Step("Lọc theo Status: {0}")
    public void filterByStatus(String statusName) {
        waitForElementVisible(dropdownStatus, 10);
        clickElement(dropdownStatus);
        List<WebElement> activeList = getWebElements(statusFilterItemsActive);
        for (WebElement activeItem : activeList) {
            if (!activeItem.getText().trim().equalsIgnoreCase(statusName)) {
                clickElementWithJs(activeItem);
            }
        }
        By activeTarget = getStatusFilterActiveOptionLocator(statusName);
        if (!isElementPresent(activeTarget, 1)) {
            By statusOption = getStatusFilterOptionLocator(statusName);
            waitForElementVisible(statusOption, 5);
            clickElement(statusOption);
        }
        clickElement(dropdownStatus);
        waitForElementInvisible(tableProcessing, 5);
        waitForElementInvisible(tableEmptyMessage, 5);
        waitForPageLoaded();
    }

    @Step("Lọc theo nhiều Status đồng thời: {0}")
    public void filterByMultipleStatuses(List<String> statusNames) {
        waitForElementVisible(dropdownStatus, 10);
        clickElement(dropdownStatus);
        List<WebElement> activeList = getWebElements(statusFilterItemsActive);
        for (WebElement activeItem : activeList) {
            String activeText = activeItem.getText().trim();
            boolean keep = false;
            for (String s : statusNames) {
                if (s.equalsIgnoreCase(activeText)) {
                    keep = true;
                    break;
                }
            }
            if (!keep) {
                clickElementWithJs(activeItem);
            }
        }
        for (String statusName : statusNames) {
            By activeTarget = getStatusFilterActiveOptionLocator(statusName);
            if (!isElementPresent(activeTarget, 1)) {
                By statusOption = getStatusFilterOptionLocator(statusName);
                waitForElementVisible(statusOption, 5);
                clickElement(statusOption);
            }
        }
        String ariaExpanded = getAttributeElement(dropdownStatus, "aria-expanded");
        if ("true".equalsIgnoreCase(ariaExpanded)) {
            clickElementWithJs(dropdownStatus);
        }
        waitForElementInvisible(tableProcessing, 5);
        waitForElementInvisible(tableEmptyMessage, 5);
        waitForPageLoaded();
    }

    @Step("Reset bộ lọc Status về Tất cả (All)")
    public void resetStatusFilterToAll() {
        if (isElementPresent(dropdownStatus, 5)) {
            clickElementWithJs(dropdownStatus);
            List<WebElement> activeList = getWebElements(statusFilterItemsActive);
            for (WebElement activeItem : activeList) {
                clickElementWithJs(activeItem);
            }
            clickElementWithJs(dropdownStatus);
            waitForElementInvisible(tableProcessing, 5);
            waitForElementInvisible(tableEmptyMessage, 5);
            waitForPageLoaded();
        }
    }

    @Step("Xác thực tất cả các dòng trên bảng có Status: {0}")
    public void verifyAllRowsHaveStatus(String expectedStatus) {
        verifyAllRowsHaveStatus(expectedStatus, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Xác thực tất cả các dòng trên bảng có Status: {0} (Soft Assert)")
    public void verifyAllRowsHaveStatus(String expectedStatus, FailureHandling flowControl) {
        changePageLength("All");
        waitForElementInvisible(tableProcessing, 5);
        waitForPageLoaded();
        if (isElementVisible(tableCellStatuses, 5)) {
            List<String> rowStatuses = getListElementsText(tableCellStatuses);
            for (String status : rowStatuses) {
                if (status.trim().isEmpty()) {
                    continue;
                }
                verifyTrue(status.trim().equalsIgnoreCase(expectedStatus),
                        "Bản ghi có Status='" + status.trim() + "' không khớp với filter Status='" + expectedStatus
                                + "'",
                        flowControl);
            }
        }
    }

    @Step("Xác thực tất cả các dòng trên bảng có Budget: {0}")
    public void verifyAllRowsHaveBudget(String expectedBudget) {
        verifyAllRowsHaveBudget(expectedBudget, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Xác thực tất cả các dòng trên bảng có Budget: {0} (Soft Assert)")
    public void verifyAllRowsHaveBudget(String expectedBudget, FailureHandling flowControl) {
        changePageLength("All");
        waitForElementInvisible(tableProcessing, 5);
        waitForPageLoaded();

        // Tự động chuyển đổi sang $1,000.00 nếu truyền vào dạng số thô (ví dụ: "1000")
        String formattedExpected = expectedBudget;
        try {
            double val = Double.parseDouble(expectedBudget.replaceAll("[^0-9.]", ""));
            formattedExpected = String.format(Locale.US, "$%,.2f", val);
        } catch (Exception ignored) {
        }
        waitForElementVisible(tableCellBudget, 5);
        List<String> rowBudgets = getListElementsText(tableCellBudget);
        for (String budget : rowBudgets) {
            if (budget.trim().isEmpty()) {
                continue;
            }
            boolean isMatch = budget.trim().equalsIgnoreCase(formattedExpected)
                    || budget.replaceAll("[^0-9]", "").equals(expectedBudget.replaceAll("[^0-9]", ""));
            verifyTrue(isMatch,
                    "Bản ghi có Budget='" + budget.trim() + "' không khớp với expected Budget='" + formattedExpected
                            + "'",
                    flowControl);
        }

    }

    @Step("Xác thực tất cả các dòng trên bảng có Status thuộc danh sách: {0}")
    public void verifyAllRowsHaveAnyOfStatuses(List<String> expectedStatuses) {
        verifyAllRowsHaveAnyOfStatuses(expectedStatuses, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Xác thực tất cả các dòng trên bảng có Status thuộc danh sách: {0} (Soft Assert)")
    public void verifyAllRowsHaveAnyOfStatuses(List<String> expectedStatuses, FailureHandling flowControl) {
        waitForElementVisible(tableProjects, 10);
        if (getTotalRecordsCount() > 10) {
            changePageLength("All");
        }
        waitForElementInvisible(tableProcessing, 5);
        waitForPageLoaded();

        if (isElementVisible(tableCellStatuses, 5)) {
            List<String> rowStatuses = getListElementsText(tableCellStatuses);
            for (String status : rowStatuses) {
                if (status.trim().isEmpty()) {
                    continue;
                }
                boolean matched = false;
                for (String expected : expectedStatuses) {
                    if (expected.equalsIgnoreCase(status.trim())) {
                        matched = true;
                        break;
                    }
                }
                verifyTrue(matched,
                        "Bản ghi có Status='" + status.trim() + "' không thuộc tập hợp Status lọc: " + expectedStatuses,
                        flowControl);
            }
        }
    }

    @Step("Xác thực bộ lọc Status đã được reset về Tất cả (All)")
    public void verifyFilterStatusResetToAll(String previousFilteredStatus) {
        waitForElementPresent(tableProjects, 10);
        waitForElementInvisible(tableEmptyMessage, 5);

        List<WebElement> rows = getWebElements(tableRows);
        verifyTrue(rows.size() > 1, "Bảng không hiển thị lại danh sách dự án sau khi reset filter về All.");

        List<String> currentStatuses = getListElementsText(tableCellStatuses);
        boolean hasDifferentStatus = false;
        for (String s : currentStatuses) {
            if (!s.trim().equalsIgnoreCase(previousFilteredStatus)) {
                hasDifferentStatus = true;
                break;
            }
        }
        verifyTrue(hasDifferentStatus,
                "Bảng vẫn chỉ hiển thị status đã lọc ('" + previousFilteredStatus
                        + "'), bộ lọc chưa được reset về All.");

        By activeTarget = getStatusFilterActiveOptionLocator(previousFilteredStatus);
        verifyTrue(!isElementPresent(activeTarget, 1),
                "Status '" + previousFilteredStatus + "' vẫn đang ở trạng thái active trong dropdown sau khi reset.");
    }

    @Step("Lọc theo Label: {0}")
    public void filterByLabel(String labelName) {
        waitForElementVisible(dropdownLabels, 10);
        clickElement(dropdownLabels);
        By labelOption = getLabelFilterOptionLocator(labelName);
        waitForElementVisible(labelOption, 5);
        clickElement(labelOption);
        waitForElementInvisible(tableProcessing, 5);
        waitForElementInvisible(tableEmptyMessage, 5);
        waitForPageLoaded();
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(com.practiceCRM.driver.DriverManager.getDriver(),
                    java.time.Duration.ofSeconds(5))
                    .until(driver -> (Boolean) ((org.openqa.selenium.JavascriptExecutor) driver)
                            .executeScript("return (window.jQuery != null) && (jQuery.active === 0);"));
        } catch (Exception ignored) {}
    }

    @Step("Xác thực tất cả các dòng trên bảng có Label: {0}")
    public void verifyAllRowsHaveLabel(String expectedLabel) {
        verifyAllRowsHaveLabel(expectedLabel, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Xác thực tất cả các dòng trên bảng có Label: {0} (Soft Assert)")
    public void verifyAllRowsHaveLabel(String expectedLabel, FailureHandling flowControl) {
        if (getTotalRecordsCount() > 10) {
            changePageLength("All");
        }
        waitForElementInvisible(tableProcessing, 5);
        waitForPageLoaded();
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(com.practiceCRM.driver.DriverManager.getDriver(),
                    java.time.Duration.ofSeconds(5))
                    .until(driver -> (Boolean) ((org.openqa.selenium.JavascriptExecutor) driver)
                            .executeScript("return (window.jQuery != null) && (jQuery.active === 0);"));
        } catch (Exception ignored) {}

        List<WebElement> rows = getWebElements(tableRows);
        for (WebElement row : rows) {
            if (!row.findElements(tableEmptyMessage).isEmpty()) {
                continue;
            }
            List<WebElement> badges = row.findElements(By.cssSelector("td:nth-child(2) span.badge, td:nth-child(2) span[class*='badge'], td:nth-child(2) span[class*='label']"));
            boolean hasExpectedLabel = false;
            for (WebElement badge : badges) {
                String labelText = badge.getAttribute("textContent");
                if (labelText == null || labelText.trim().isEmpty()) {
                    labelText = badge.getText();
                }
                if (labelText != null && labelText.trim().equalsIgnoreCase(expectedLabel)) {
                    hasExpectedLabel = true;
                    break;
                }
            }
            verifyTrue(hasExpectedLabel,
                    "Dòng dự án trên bảng không chứa nhãn Label='" + expectedLabel + "'",
                    flowControl);
        }
    }

    @Step("Xác thực kết quả tìm kiếm không có Label")
    public void verifySearchResultNoLabel(String projcetNoLabel) {
        verifyElementNotVisible(tableCellLabels, 5);
    }

    // =====================================================================
    // 13. ACTIONS & VERIFICATIONS: DEADLINE FILTER
    // =====================================================================
    // --- 13.1 Thao tác chọn Dropdown Deadline ---
    @Step("Lọc theo Deadline: {0}")
    public void filterByDeadline(String deadlineOption) {
        waitForElementVisible(dropdownDeadline, 10);
        clickElement(dropdownDeadline);
        By optionLocator = getDeadlineOptionLocator(deadlineOption);
        waitForElementVisible(optionLocator, 5);
        clickElement(optionLocator);
        waitForElementInvisible(tableProcessing, 5);
        waitForElementInvisible(tableEmptyMessage, 5);
        waitForPageLoaded();
    }

    @Step("Lọc theo Deadline Custom với ngày cụ thể: {0}")
    public void filterByDeadlineCustom(String customDate) {
        waitForElementVisible(dropdownDeadline, 10);
        clickElement(dropdownDeadline);
        By optionLocator = getDeadlineOptionLocator(ProjectModel.DEADLINE_CUSTOM);
        waitForElementVisible(optionLocator, 5);
        clickElement(optionLocator);
        executeScript(
                "var dateStr = arguments[0];" +
                        "var parts = dateStr.split('-');" +
                        "var d = new Date(parseInt(parts[0], 10), parseInt(parts[1], 10) - 1, parseInt(parts[2], 10));"
                        +
                        "var u = $('button[name=\"deadline\"]');" +
                        "u.datepicker('setDate', d);" +
                        "u.trigger({type: 'changeDate', date: d});" +
                        "if (window.Filters && window.Filters['all_projects_list']) {" +
                        "    window.Filters['all_projects_list'].settings.filterParams['deadline'] = dateStr;" +
                        "    window.Filters['all_projects_list'].reloadInstance();" +
                        "}",
                customDate);
        waitForElementInvisible(tableProcessing, 5);
        waitForElementInvisible(tableEmptyMessage, 5);
        waitForPageLoaded();
    }

    // --- 13.2 Verify kết quả lọc Deadline ---
    @Step("Xác thực tất cả các dòng trên bảng thỏa mãn bộ lọc Deadline: {0}")
    public void verifyAllRowsHaveDeadline(String expectedDeadline) {
        verifyAllRowsHaveDeadline(expectedDeadline, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Xác thực tất cả các dòng trên bảng thỏa mãn bộ lọc Deadline: {0} (Soft Assert)")
    public void verifyAllRowsHaveDeadline(String expectedDeadline, FailureHandling flowControl) {
        changePageLength("All");
        waitForElementInvisible(tableProcessing, 5);
        waitForPageLoaded();

        String buttonText = getTextElement(dropdownDeadline);
        verifyTrue(buttonText.contains(expectedDeadline),
                "Dropdown Deadline hiển thị text không khớp: '" + buttonText + "', mong đợi chứa: '" + expectedDeadline
                        + "'",
                flowControl);

        List<String> rowDeadlines = getListElementsText(tableCellDeadlines);
        verifyTrue(!rowDeadlines.isEmpty(), "Bảng không có bản ghi nào sau khi lọc theo Deadline: " + expectedDeadline,
                flowControl);

        LocalDate today = LocalDate.now();
        for (String d : rowDeadlines) {
            if (d == null || d.trim().isEmpty() || d.trim().equals("-")) {
                continue;
            }
            try {
                LocalDate rowDate = LocalDate.parse(d.trim());
                boolean matches = false;
                String errorMsg = "";

                if (expectedDeadline.equalsIgnoreCase(ProjectModel.DEADLINE_EXPIRED)) {
                    matches = rowDate.isBefore(today);
                    errorMsg = "Bản ghi có Deadline=" + d + " không hết hạn (phải trước " + today + ")";
                } else if (expectedDeadline.equalsIgnoreCase(ProjectModel.DEADLINE_TODAY)) {
                    matches = !rowDate.isAfter(today);
                    errorMsg = "Bản ghi có Deadline=" + d + " vượt quá ngày hôm nay (" + today + ")";
                } else if (expectedDeadline.equalsIgnoreCase(ProjectModel.DEADLINE_TOMORROW)) {
                    LocalDate tomorrow = today.plusDays(1);
                    matches = !rowDate.isAfter(tomorrow);
                    errorMsg = "Bản ghi có Deadline=" + d + " vượt quá ngày mai (" + tomorrow + ")";
                } else if (expectedDeadline.equalsIgnoreCase(ProjectModel.DEADLINE_IN_7_DAYS)) {
                    LocalDate next7 = today.plusDays(7);
                    matches = !rowDate.isAfter(next7);
                    errorMsg = "Bản ghi có Deadline=" + d + " vượt quá 7 ngày tới (" + next7 + ")";
                } else if (expectedDeadline.equalsIgnoreCase(ProjectModel.DEADLINE_IN_15_DAYS)) {
                    LocalDate next15 = today.plusDays(15);
                    matches = !rowDate.isAfter(next15);
                    errorMsg = "Bản ghi có Deadline=" + d + " vượt quá 15 ngày tới (" + next15 + ")";
                } else {
                    matches = d.trim().equalsIgnoreCase(expectedDeadline.trim());
                    errorMsg = "Bản ghi có Deadline=" + d + " không khớp ngày mong đợi=" + expectedDeadline;
                }
                verifyTrue(matches, errorMsg, flowControl);
            } catch (Exception e) {
                LogUtils.warn("Không parse được ngày deadline: " + d + " (" + e.getMessage() + ")");
            }
        }
    }

    public static String formatDeadlineButtonDate(LocalDate date) {
        int day = date.getDayOfMonth();
        String suffix;
        if (day >= 11 && day <= 13) {
            suffix = "th";
        } else {
            switch (day % 10) {
                case 1:
                    suffix = "st";
                    break;
                case 2:
                    suffix = "nd";
                    break;
                case 3:
                    suffix = "rd";
                    break;
                default:
                    suffix = "th";
                    break;
            }
        }
        String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        return day + suffix + " " + month + " " + date.getYear();
    }

    @Step("Xác thực tất cả các dòng trên bảng thỏa mãn bộ lọc Deadline Custom: {0}")
    public void verifyAllRowsHaveDeadlineCustom(String expectedCustomDate) {
        verifyAllRowsHaveDeadlineCustom(expectedCustomDate, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Xác thực tất cả các dòng trên bảng thỏa mãn bộ lọc Deadline Custom: {0} (Soft Assert)")
    public void verifyAllRowsHaveDeadlineCustom(String expectedCustomDate, FailureHandling flowControl) {
        changePageLength("All");
        waitForElementInvisible(tableProcessing, 5);
        waitForPageLoaded();

        LocalDate customTarget = LocalDate.parse(expectedCustomDate.trim());
        String expectedButtonDate = formatDeadlineButtonDate(customTarget);
        String buttonText = getTextElement(dropdownDeadline);
        verifyTrue(buttonText.contains(expectedButtonDate),
                "Dropdown Deadline hiển thị text không khớp ngày đã chọn: '" + buttonText + "', mong đợi chứa: '"
                        + expectedButtonDate + "'",
                flowControl);

        List<String> rowDeadlines = getListElementsText(tableCellDeadlines);
        verifyTrue(!rowDeadlines.isEmpty(),
                "Bảng không có bản ghi nào sau khi lọc theo Deadline Custom: " + expectedCustomDate, flowControl);

        for (String d : rowDeadlines) {
            try {
                LocalDate rowDate = LocalDate.parse(d.trim());
                boolean matches = !rowDate.isAfter(customTarget);
                verifyTrue(matches,
                        "Bản ghi có Deadline=" + d + " vượt quá ngày Custom đã chọn (" + customTarget + ")",
                        flowControl);
            } catch (Exception e) {
                LogUtils.warn("Không parse được ngày deadline: " + d + " (" + e.getMessage() + ")");
            }
        }
    }

    // =====================================================================
    // 14. ACTIONS & VERIFICATIONS: START DATE FILTER
    // =====================================================================
    // --- 14.1 Thao tác chọn Dropdown Start date ---
    @Step("Lọc theo Start date: {0}")
    public void filterByStartDate(String optionName) {
        waitForElementVisible(dropdownStartDate, 10);
        clickElement(dropdownStartDate);
        By optionLocator = getStartDateOptionLocator(optionName);
        waitForElementVisible(optionLocator, 5);
        clickElement(optionLocator);
        waitForElementInvisible(tableProcessing, 5);
        waitForElementInvisible(tableEmptyMessage, 5);
        waitForPageLoaded();
    }

    @Step("Lọc theo Start date Custom với khoảng ngày: from={0}, to={1}")
    public void filterByStartDateCustom(String fromDate, String toDate) {
        new org.openqa.selenium.support.ui.WebDriverWait(com.practiceCRM.driver.DriverManager.getDriver(),
                java.time.Duration.ofSeconds(10))
                .until(driver -> (Boolean) ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("return (window.jQuery != null) && (jQuery.active === 0);"));
        executeScript(
                "window.__list_data_done = false;" +
                        "$(document).on('ajaxComplete.customFilter', function(e, xhr, settings) {" +
                        "    if (settings.url && settings.url.indexOf('/projects/list_data') !== -1) {" +
                        "        window.__list_data_done = true;" +
                        "        $(document).off('ajaxComplete.customFilter');" +
                        "    }" +
                        "});" +
                        "var fromStr = arguments[0] || '';" +
                        "var toStr = arguments[1] || '';" +
                        "var f = window.Filters ? window.Filters['all_projects_list'] : null;" +
                        "if (f && f.filterElements) {" +
                        "    if (f.filterElements['start_date_to']) f.filterElements['start_date_to'].setValue(toStr);"
                        +
                        "    if (f.filterElements['start_date_from']) f.filterElements['start_date_from'].setValue(fromStr);"
                        +
                        "    f.reloadInstance();" +
                        "}",
                fromDate, toDate);
        new org.openqa.selenium.support.ui.WebDriverWait(com.practiceCRM.driver.DriverManager.getDriver(),
                java.time.Duration.ofSeconds(10))
                .until(driver -> (Boolean) ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("return window.__list_data_done === true;"));
        waitForElementInvisible(tableProcessing, 5);
        waitForElementInvisible(tableEmptyMessage, 5);
        waitForPageLoaded();
    }

    public void filterByStartDateCustom(String fromDate) {
        filterByStartDateCustom(fromDate, null);
    }

    // --- 14.2 Verify kết quả lọc Start date ---
    @Step("Xác thực tất cả các dòng trên bảng thỏa mãn bộ lọc Start date: {0}")
    public void verifyAllRowsHaveStartDate(String expectedOption) {
        verifyAllRowsHaveStartDate(expectedOption, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Xác thực tất cả các dòng trên bảng thỏa mãn bộ lọc Start date: {0} (Soft Assert)")
    public void verifyAllRowsHaveStartDate(String expectedOption, FailureHandling flowControl) {
        changePageLength("All");
        waitForElementInvisible(tableProcessing, 5);
        waitForPageLoaded();

        LocalDate today = LocalDate.now();
        LocalDate expectedFrom;
        LocalDate expectedTo;

        if (expectedOption.equalsIgnoreCase(ProjectModel.START_DATE_THIS_MONTH)) {
            expectedFrom = today.withDayOfMonth(1);
            expectedTo = today.withDayOfMonth(today.lengthOfMonth());
        } else if (expectedOption.equalsIgnoreCase(ProjectModel.START_DATE_LAST_MONTH)) {
            LocalDate lastMonth = today.minusMonths(1);
            expectedFrom = lastMonth.withDayOfMonth(1);
            expectedTo = lastMonth.withDayOfMonth(lastMonth.lengthOfMonth());
        } else if (expectedOption.equalsIgnoreCase(ProjectModel.START_DATE_THIS_YEAR)) {
            expectedFrom = LocalDate.of(today.getYear(), 1, 1);
            expectedTo = LocalDate.of(today.getYear(), 12, 31);
        } else if (expectedOption.equalsIgnoreCase(ProjectModel.START_DATE_LAST_YEAR)) {
            expectedFrom = LocalDate.of(today.getYear() - 1, 1, 1);
            expectedTo = LocalDate.of(today.getYear() - 1, 12, 31);
        } else if (expectedOption.equalsIgnoreCase(ProjectModel.START_DATE_NEXT_7_DAYS)) {
            expectedFrom = today;
            expectedTo = today.plusDays(6);
        } else if (expectedOption.equalsIgnoreCase(ProjectModel.START_DATE_NEXT_MONTH)) {
            LocalDate nextMonth = today.plusMonths(1);
            expectedFrom = nextMonth.withDayOfMonth(1);
            expectedTo = nextMonth.withDayOfMonth(nextMonth.lengthOfMonth());
        } else {
            throw new IllegalArgumentException("Không hỗ trợ tùy chọn Start date: " + expectedOption);
        }

        String fromText = getTextElement(btnStartDateFrom);
        String toText = getTextElement(btnStartDateTo);
        String expectedFromStr = formatDeadlineButtonDate(expectedFrom);
        String expectedToStr = formatDeadlineButtonDate(expectedTo);

        verifyTrue(fromText.contains(expectedFromStr),
                "Nút Start date from hiển thị không đúng: '" + fromText + "', mong đợi chứa: '" + expectedFromStr + "'",
                flowControl);
        verifyTrue(toText.contains(expectedToStr),
                "Nút Start date to hiển thị không đúng: '" + toText + "', mong đợi chứa: '" + expectedToStr + "'",
                flowControl);

        List<String> rowStartDates = getListElementsText(tableCellStartDates);
        verifyTrue(!rowStartDates.isEmpty(),
                "Bảng không có bản ghi nào sau khi lọc theo Start date: " + expectedOption, flowControl);

        for (String d : rowStartDates) {
            try {
                LocalDate rowDate = LocalDate.parse(d.trim());
                boolean matches = !rowDate.isBefore(expectedFrom) && !rowDate.isAfter(expectedTo);
                verifyTrue(matches,
                        "Bản ghi có Start date=" + d + " nằm ngoài khoảng [" + expectedFrom + " -> " + expectedTo
                                + "] của tùy chọn " + expectedOption,
                        flowControl);
            } catch (Exception e) {
                LogUtils.warn("Không parse được ngày Start date: " + d + " (" + e.getMessage() + ")");
            }
        }
    }

    @Step("Xác thực tất cả các dòng trên bảng thỏa mãn bộ lọc Start date Custom: from={0}, to={1}")
    public void verifyAllRowsHaveStartDateCustom(String expectedFromDate, String expectedToDate) {
        verifyAllRowsHaveStartDateCustom(expectedFromDate, expectedToDate, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Xác thực tất cả các dòng trên bảng thỏa mãn bộ lọc Start date Custom: from={0}, to={1} (Soft Assert)")
    public void verifyAllRowsHaveStartDateCustom(String expectedFromDate, String expectedToDate,
            FailureHandling flowControl) {
        changePageLength("All");
        waitForPageLoaded();

        LocalDate fromTarget = LocalDate.parse(expectedFromDate.trim());
        LocalDate toTarget = LocalDate.parse(expectedToDate.trim());

        String expectedFromStr = formatDeadlineButtonDate(fromTarget);
        String fromText = getTextElement(btnStartDateFrom);
        verifyTrue(fromText.contains(expectedFromStr),
                "Nút Start date from hiển thị text không khớp ngày đã chọn: '" + fromText + "', mong đợi chứa: '"
                        + expectedFromStr + "'",
                flowControl);

        String expectedToStr = formatDeadlineButtonDate(toTarget);
        String toText = getTextElement(btnStartDateTo);
        verifyTrue(toText.contains(expectedToStr),
                "Nút Start date to hiển thị text không khớp ngày đã chọn: '" + toText + "', mong đợi chứa: '"
                        + expectedToStr + "'",
                flowControl);

        List<String> rowStartDates = getListElementsText(tableCellStartDates);
        LogUtils.info(
                "📊 Danh sách Start date đọc được trên bảng (" + rowStartDates.size() + " dòng): " + rowStartDates);
        verifyTrue(!rowStartDates.isEmpty(),
                "Bảng không có bản ghi nào sau khi lọc theo Start date Custom: [" + expectedFromDate + " -> "
                        + expectedToDate + "]",
                flowControl);

        for (String d : rowStartDates) {
            try {
                LocalDate rowDate = LocalDate.parse(d.trim());
                boolean matches = !rowDate.isBefore(fromTarget) && !rowDate.isAfter(toTarget);
                verifyTrue(matches,
                        "Bản ghi có Start date=" + d + " nằm ngoài khoảng Custom [" + expectedFromDate + " -> "
                                + expectedToDate + "]",
                        flowControl);
            } catch (Exception e) {
                LogUtils.warn("Không parse được ngày Start date: " + d + " (" + e.getMessage() + ")");
            }
        }
    }

    @Step("Xác thực bảng có hiển thị dự án: {0}")
    public void verifyTableContainsProject(String projectTitle, FailureHandling flowControl) {
        waitForElementPresent(tableProjects, 10);
        if (getTotalRecordsCount() > 10) {
            changePageLength("All");
        }
        waitForElementInvisible(tableProcessing, 5);
        waitForPageLoaded();
        By projectRow = getProjectLocatorByTitle(projectTitle);
        verifyTrue(isElementPresent(projectRow, 5),
                "Không tìm thấy dự án '" + projectTitle + "' trên bảng sau khi lọc.", flowControl);
    }

    @Step("Click Sort cột: {0}")
    public void sortByColumn(String columnName) {
        waitForElementPresent(tableProjects, 10);
        By colHeader = getColumnHeaderLocator(columnName);
        waitForElementVisible(colHeader, 5);
        clickElement(colHeader);
        waitForElementVisible(tableProjects, 10);
        waitForElementInvisible(tableProcessing, 5);
        waitForPageLoaded();
    }

    public int getColumnIndex(String columnName) {
        List<WebElement> headers = getWebElements(tableHeaders);
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().trim().toLowerCase().contains(columnName.toLowerCase().trim())) {
                return i + 1;
            }
        }
        return -1;
    }

    @Step("Xác thực cột '{0}' trong bảng được sắp xếp đúng thứ tự")
    public void verifyColumnSorted(String columnName) {
        verifyColumnSorted(columnName, FailureHandling.STOP_ON_FAILURE);
    }

    @Step("Xác thực cột '{0}' trong bảng được sắp xếp đúng thứ tự (Soft Assert)")
    public void verifyColumnSorted(String columnName, FailureHandling flowControl) {
        By colHeader = getColumnHeaderLocator(columnName);
        waitForElementVisible(colHeader, 5);
        String headerClass = getAttributeElement(colHeader, "class");
        boolean isAscending = headerClass.contains("sorting_asc");
        boolean isDescending = headerClass.contains("sorting_desc");

        verifyTrue(isAscending || isDescending,
                "Cột '" + columnName + "' không ở trạng thái sorting_asc hoặc sorting_desc sau khi click.",
                flowControl);

        int colIndex = getColumnIndex(columnName);
        verifyTrue(colIndex > 0, "Không tìm thấy index của cột: " + columnName, flowControl);

        List<WebElement> cells = getWebElements(getTableCellsByColumnIndexLocator(colIndex));
        if (cells.size() <= 1) {
            LogUtils.info("Bảng có ít hơn 2 dòng dữ liệu, bỏ qua bước kiểm tra thứ tự sắp xếp của cột: " + columnName);
            return;
        }

        List<String> values = new ArrayList<>();
        for (WebElement cell : cells) {
            String text = cell.getAttribute("textContent");
            if (text == null) {
                text = cell.getText();
            }
            values.add(text);
        }

        boolean isSorted = checkListIsSorted(columnName, values, isAscending);
        String direction = isAscending ? "tăng dần (ASC)" : "giảm dần (DESC)";
        verifyTrue(isSorted,
                "Dữ liệu cột '" + columnName + "' không được sắp xếp đúng thứ tự " + direction + ". Dữ liệu thực tế: "
                        + values,
                flowControl);
    }

    private boolean checkListIsSorted(String columnName, List<String> values, boolean isAscending) {
        String col = columnName.toLowerCase().trim();

        // 1. Cột Số nguyên (ID)
        if (col.equals("id")) {
            List<Long> numbers = new ArrayList<>();
            for (String v : values) {
                try {
                    String clean = v.replaceAll("[^0-9]", "");
                    if (!clean.isEmpty())
                        numbers.add(Long.parseLong(clean));
                } catch (Exception ignored) {
                }
            }
            for (int i = 0; i < numbers.size() - 1; i++) {
                if (isAscending && numbers.get(i) > numbers.get(i + 1))
                    return false;
                if (!isAscending && numbers.get(i) < numbers.get(i + 1))
                    return false;
            }
            return true;
        }

        // 2. Cột Tiền tệ (Price)
        if (col.equals("price")) {
            List<Double> prices = new ArrayList<>();
            for (String v : values) {
                try {
                    String clean = v.replaceAll("[^0-9.]", "");
                    if (!clean.isEmpty())
                        prices.add(Double.parseDouble(clean));
                } catch (Exception ignored) {
                }
            }
            for (int i = 0; i < prices.size() - 1; i++) {
                if (isAscending && prices.get(i) > prices.get(i + 1))
                    return false;
                if (!isAscending && prices.get(i) < prices.get(i + 1))
                    return false;
            }
            return true;
        }

        // 3. Cột Phần trăm (Progress)
        if (col.equals("progress")) {
            List<Integer> progresses = new ArrayList<>();
            for (String v : values) {
                try {
                    String clean = v.replaceAll("[^0-9]", "");
                    if (!clean.isEmpty())
                        progresses.add(Integer.parseInt(clean));
                } catch (Exception ignored) {
                }
            }
            for (int i = 0; i < progresses.size() - 1; i++) {
                if (isAscending && progresses.get(i) > progresses.get(i + 1))
                    return false;
                if (!isAscending && progresses.get(i) < progresses.get(i + 1))
                    return false;
            }
            return true;
        }

        // 4. Cột Ngày tháng (Start date, Deadline)
        if (col.contains("date") || col.contains("deadline")) {
            List<LocalDate> dates = new ArrayList<>();
            for (String v : values) {
                try {
                    String clean = v.trim();
                    if (!clean.isEmpty() && !clean.equals("-")) {
                        dates.add(LocalDate.parse(clean));
                    }
                } catch (Exception ignored) {
                }
            }
            for (int i = 0; i < dates.size() - 1; i++) {
                if (isAscending && dates.get(i).isAfter(dates.get(i + 1)))
                    return false;
                if (!isAscending && dates.get(i).isBefore(dates.get(i + 1)))
                    return false;
            }
            return true;
        }

        // 5. Cột Văn bản (Title, Client, Status)
        List<String> texts = new ArrayList<>();
        for (String v : values) {
            if (v != null && !v.trim().isEmpty() && !v.trim().equals("-")) {
                String clean = v.replace("\r", "").replace("\n", " ");
                texts.add(clean);
            }
        }
        for (int i = 0; i < texts.size() - 1; i++) {
            int cmp = texts.get(i).compareToIgnoreCase(texts.get(i + 1));
            if (isAscending && cmp > 0)
                return false;
            if (!isAscending && cmp < 0)
                return false;
        }
        return true;
    }

    // =====================================================================
    // 14. ACTIONS: ROW LEVEL & MODAL OPERATIONS
    // =====================================================================

    @Step("Mở modal Edit cho dự án: {0}")
    public void openEditProject(String title) {
        waitForElementPresent(tableProjects, 10);

        List<WebElement> matchingRows = getWebElements(getRowByProjectTitleLocator(title));
        if (matchingRows.size() > 1) {
            String warnMsg = "⚠️ Cảnh báo: Tìm thấy " + matchingRows.size() + " dự án có tên khớp với '" + title
                    + "'. Đang thực hiện Edit trên dòng đầu tiên.";
            LogUtils.warn(warnMsg);
            AllureManager.saveTextLog(warnMsg);
            if (ExtentTestManager.getExtentTest() != null) {
                ExtentReportManager.warning(warnMsg);
            }
        }
        By editBtn = getBtnEditByProjectTitleLocator(title);
        clickElementWithJs(editBtn);

        waitForElementVisible(modalProject, 10);

    }

    @Step("Click Delete dự án: {0}")
    public void clickDeleteProject(String title) {
        waitForElementPresent(tableProjects, 10);
        if (title != null && !title.trim().isEmpty()) {
            List<WebElement> matchingRows = getWebElements(getRowByProjectTitleLocator(title));
            if (matchingRows.size() > 1) {
                String warnMsg = "⚠️ Cảnh báo: Tìm thấy " + matchingRows.size() + " dự án có tên khớp với '" + title
                        + "'. Đang thực hiện Delete trên dòng đầu tiên.";
                LogUtils.warn(warnMsg);
                AllureManager.saveTextLog(warnMsg);
                if (ExtentTestManager.getExtentTest() != null) {
                    ExtentReportManager.warning(warnMsg);
                }
            }
            By deleteBtn = getBtnDeleteByProjectTitleLocator(title);
            waitForElementVisible(deleteBtn, 5);
            clickElement(deleteBtn);
        } else {
            waitForElementVisible(btnFirstDelete, 5);
            clickElement(btnFirstDelete);
        }
        waitForElementVisible(modalConfirmDelete, 5);
        waitForPageLoaded();
    }

    @Step("Xác nhận xóa dự án trên popup")
    public void confirmDelete() {
        waitForElementVisible(btnConfirmDelete, 5);
        clickElement(btnConfirmDelete);
        waitForElementInvisible(modalConfirmDelete, 5);
    }

    @Step("Hủy xóa dự án trên popup")
    public void cancelDelete() {
        waitForElementVisible(btnCancelDelete, 5);
        clickElement(btnCancelDelete);
        waitForElementInvisible(modalConfirmDelete, 5);
    }

    // =====================================================================
    // 15. ACTIONS: PAGINATION & TABLE LENGTH
    // =====================================================================
    @Step("Thay đổi số lượng dòng hiển thị: {0}")
    public void changePageLength(String length) {
        waitForElementVisible(tableProjects, 10);
        executeScript(
                "if(window.$ && $.fn.DataTable && $('#project-table').length) { var len = (arguments[0] === 'All' || arguments[0] === '-1') ? -1 : parseInt(arguments[0]); $('#project-table').DataTable().page.len(len).draw(); if($('.dataTables_length select').length) { $('.dataTables_length select').val(len); } }",
                length);
        waitForElementInvisible(tableProcessing, 5);
        waitForElementInvisible(tableEmptyMessage, 5);
        waitForPageLoaded();
    }

    @Step("Click nút Next trang")
    public void clickNextPage() {
        waitForElementVisible(tableProjects, 10);
        scrollToElementAtBottom(paginationContainer);
        waitForElementVisible(btnPaginationNext, 5);
        clickElement(btnPaginationNext);
        waitForElementVisible(tableProjects, 10);
        waitForElementInvisible(tableProcessing, 5);
        waitForPageLoaded();
    }

    @Step("Click nút Previous trang")
    public void clickPreviousPage() {
        waitForElementVisible(tableProjects, 10);
        scrollToElementAtBottom(paginationContainer);
        waitForElementVisible(btnPaginationPrevious, 5);
        clickElement(btnPaginationPrevious);
        waitForElementVisible(tableProjects, 10);
        waitForElementInvisible(tableProcessing, 5);
        waitForPageLoaded();
    }

    @Step("Nhảy trực tiếp đến số trang: {0}")
    public void jumpToPage(String pageNumber) {
        waitForElementVisible(tableProjects, 10);
        scrollToElementAtBottom(paginationContainer);
        By pageItem = getPaginationPageItemLocator(pageNumber);
        waitForElementVisible(pageItem, 5);
        clickElement(pageItem);
        waitForElementInvisible(tableProcessing, 5);
        waitForPageLoaded();
        executeScript("window.scrollTo(0, 0); document.querySelectorAll('.page-container, #content, body, html').forEach(function(el){ el.scrollTop = 0; });");
    }

    @Step("Lấy tổng số bản ghi từ thông tin phân trang")
    public int getTotalRecordsCount() {
        waitForElementInvisible(tableProcessing, 5);
        Object dtTotal = executeScript(
                "return (window.$ && $.fn.DataTable && $('#project-table').length) ? $('#project-table').DataTable().page.info().recordsDisplay : -1;");
        if (dtTotal instanceof Number && ((Number) dtTotal).intValue() >= 0) {
            return ((Number) dtTotal).intValue();
        }
        try {
            String text = getWebElement(labelTableInfo).getText().trim();
            Pattern pattern = Pattern.compile("(?:of|/|trong)\\s*(\\d+)|\\b(\\d+)\\s*(?:entries|bản ghi)",
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String val = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
                if (val != null) {
                    return Integer.parseInt(val);
                }
            }
            Matcher lastNumberMatcher = Pattern.compile("(\\d+)(?!.*\\d)").matcher(text);
            if (lastNumberMatcher.find()) {
                return Integer.parseInt(lastNumberMatcher.group(1));
            }
        } catch (Throwable ignored) {
        }
        return 0;
    }

    @Step("Tính số lượng trang mong đợi dựa trên số bản ghi hiển thị: {0}")
    public int getExpectedTotalPages(int pageLength) {
        int totalRecords = getTotalRecordsCount();
        if (pageLength <= 0 || totalRecords == 0) {
            return 1;
        }
        return (int) Math.ceil((double) totalRecords / pageLength);
    }

    @Step("Lấy tổng số trang hiện tại của bảng")
    public int getPaginationPagesCount() {
        Object pages = executeScript(
                "return (window.$ && $.fn.DataTable && $('#project-table').length) ? $('#project-table').DataTable().page.info().pages : 0;");
        if (pages instanceof Number && ((Number) pages).intValue() > 0) {
            return ((Number) pages).intValue();
        }
        if (isElementVisible(paginationPageButtons, 3)) {
            List<WebElement> pageButtons = getWebElements(paginationPageButtons);
            if (!pageButtons.isEmpty()) {
                String lastText = pageButtons.get(pageButtons.size() - 1).getText().trim();
                if (lastText.matches("\\d+")) {
                    return Integer.parseInt(lastText);
                }
                return pageButtons.size();
            }
        }
        return 1;
    }

    @Step("Lấy số trang hiện tại đang active")
    public int getCurrentPageNumber() {
        waitForElementVisible(paginationActivePage);
        String activeText = getTextElement(paginationActivePage).trim();
        if (activeText.matches("\\d+")) {
            return Integer.parseInt(activeText);
        }
        Object currentPage = executeScript(
                "return (window.$ && $.fn.DataTable && $('#project-table').length) ? $('#project-table').DataTable().page.info().page + 1 : 1;");
        if (currentPage instanceof Number) {
            return ((Number) currentPage).intValue();
        }
        return 1;
    }

    @Step("Kiểm tra thanh phân trang có hiển thị không")
    public boolean isPaginationVisible() {
        return isElementVisible(paginationContainer, 3);
    }

    @Step("Kiểm tra nút Previous trang có bị vô hiệu hóa không")
    public boolean isPreviousPageButtonDisabled() {
        return isElementDisabled(itemPaginationPrevious, 3);
    }

    @Step("Kiểm tra nút Next trang có bị vô hiệu hóa không")
    public boolean isNextPageButtonDisabled() {
        return isElementDisabled(itemPaginationNext, 3);
    }

    @Step("Lấy nội dung text thông tin bảng ở footer")
    public String getTableInfoText() {
        waitForElementInvisible(tableProcessing, 5);
        Object text = executeScript("return (window.$ && $('#project-table_info').length) ? $('#project-table_info').text() : '';");
        if (text != null && !text.toString().trim().isEmpty()) {
            return text.toString().trim();
        }
        try {
            return getWebElement(labelTableInfo).getText().trim();
        } catch (Throwable t) {
            return "";
        }
    }

    @Step("Lấy số lượng dòng thực tế đang hiển thị trong bảng")
    public int getTableRowsCount() {
        return getWebElements(tableRows).size();
    }

    // =====================================================================
    // 16. UTILITY: FAST DATA CLEANUP VIA AJAX API
    // =====================================================================
    @Step("Xóa tất cả các dự án có tiền tố: {0}")
    public void deleteProjectsByPrefix(String prefix) {
        if (prefix == null || prefix.trim().isEmpty()) {
            return;
        }
        waitForElementPresent(tableProjects, 10);
        waitForPageLoaded();

        // 1. Cho DataTables search tiền tố và lấy toàn bộ dòng (len=-1)
        executeScript(
                "if (window.$ && $.fn.DataTable && $('#project-table').length) {" +
                        "    $('#project-table').DataTable().search(arguments[0]).page.len(-1).draw();" +
                        "}",
                prefix);
        waitForPageLoaded();
        sleep(1.5);

        // 2. Thu thập data-id và xóa hàng loạt bằng AJAX API
        String script = "var prefix = arguments[0];" +
                "var rows = Array.from(document.querySelectorAll('#project-table tbody tr')).filter(function(r) {" +
                "    var titleLink = r.querySelector('td:nth-child(2) a');" +
                "    return titleLink && titleLink.textContent.trim().indexOf(prefix) === 0;" +
                "});" +
                "var ids = [];" +
                "rows.forEach(function(r) {" +
                "    var deleteBtn = r.querySelector('a.delete, a[title=\"Delete project\"]');" +
                "    if (deleteBtn && deleteBtn.getAttribute('data-id')) {" +
                "        ids.push(deleteBtn.getAttribute('data-id'));" +
                "    }" +
                "});" +
                "var csrf = (typeof AppHelper !== 'undefined' && AppHelper.csrfTokenName) ? AppHelper.csrfTokenName : null;"
                +
                "var hash = (typeof AppHelper !== 'undefined' && AppHelper.csrfHash) ? AppHelper.csrfHash : null;" +
                "var deleted = 0;" +
                "for (var i = 0; i < ids.length; i++) {" +
                "    var data = { id: ids[i] };" +
                "    if (csrf) { data[csrf] = hash; }" +
                "    $.ajax({" +
                "        url: 'https://rise.anhtester.com/projects/delete'," +
                "        type: 'POST'," +
                "        async: false," +
                "        dataType: 'json'," +
                "        data: data," +
                "        success: function() { deleted++; }" +
                "    });" +
                "}" +
                "if (window.$ && $.fn.DataTable && $('#project-table').length) {" +
                "    $('#project-table').DataTable().search('').page.len(10).draw();" +
                "}" +
                "return { deleted: deleted, found: ids.length };";

        try {
            Object result = executeScript(script, prefix.trim());
            LogUtils.info("🗑️ [PROJECT CLEANUP] Kết quả xóa dự án tiền tố '" + prefix + "': " + result);
        } catch (Exception e) {
            LogUtils.warn("Không thể xóa dự án qua API: " + e.getMessage());
        }

        waitForPageLoaded();
    }

    @Step("Dọn dẹp data Label: {0}")
    public void cleanupLabelData(String labelName) {
        if (labelName == null || labelName.trim().isEmpty()) {
            return;
        }
        clickManageLabels();
        waitForElementVisible(inputLabelTitle, 10);

        String script = "var labelName = arguments[0];" +
                "var spans = Array.from(document.querySelectorAll('#label-show-area span')).filter(function(s) {" +
                "    return s.textContent.trim() === labelName;" +
                "});" +
                "var deleted = 0;" +
                "var inUse = 0;" +
                "spans.forEach(function(s) {" +
                "    var id = s.getAttribute('data-id');" +
                "    if (id) {" +
                "        $.ajax({" +
                "            url: 'https://rise.anhtester.com/labels/delete'," +
                "            type: 'POST'," +
                "            async: false," +
                "            dataType: 'json'," +
                "            data: { id: id, type: 'project' }," +
                "            success: function(res) {" +
                "                if (res && res.success) { deleted++; $(s).remove(); }" +
                "                else if (res && res.label_exists) { inUse++; }" +
                "            }" +
                "        });" +
                "    }" +
                "});" +
                "return { deleted: deleted, inUse: inUse, found: spans.length };";

        try {
            Object res = executeScript(script, labelName.trim());
            LogUtils.info("🏷️ [LABEL CLEANUP] Kết quả dọn dẹp data nhãn '" + labelName + "': " + res);
        } catch (Exception e) {
            LogUtils.warn("Không thể dọn dẹp label qua API: " + e.getMessage());
        }
        closeModal();
    }

    @Step("Dọn dẹp data Label theo tiền tố: {0}")
    public void cleanupLabelsByPrefix(String prefix) {
        if (prefix == null || prefix.trim().isEmpty()) {
            return;
        }
        clickManageLabels();
        waitForElementVisible(inputLabelTitle, 10);

        String script = "var prefix = arguments[0];" +
                "var spans = Array.from(document.querySelectorAll('#label-show-area span')).filter(function(s) {" +
                "    return s.textContent.trim().indexOf(prefix) === 0;" +
                "});" +
                "var deleted = 0;" +
                "var inUse = 0;" +
                "spans.forEach(function(s) {" +
                "    var id = s.getAttribute('data-id');" +
                "    if (id) {" +
                "        $.ajax({" +
                "            url: 'https://rise.anhtester.com/labels/delete'," +
                "            type: 'POST'," +
                "            async: false," +
                "            dataType: 'json'," +
                "            data: { id: id, type: 'project' }," +
                "            success: function(res) {" +
                "                if (res && res.success) { deleted++; $(s).remove(); }" +
                "                else if (res && res.label_exists) { inUse++; }" +
                "            }" +
                "        });" +
                "    }" +
                "});" +
                "return { deleted: deleted, inUse: inUse, found: spans.length };";

        try {
            Object res = executeScript(script, prefix.trim());
            LogUtils.info("🏷️ [LABEL CLEANUP] Kết quả dọn dẹp data nhãn theo tiền tố '" + prefix + "': " + res);
        } catch (Exception e) {
            LogUtils.warn("Không thể dọn dẹp label theo prefix: " + e.getMessage());
        }
        closeModal();
    }
}
