package com.practiceCRM.projects.crm.models;

import java.util.Arrays;
import java.util.List;

public class ProjectModel {

        public static int row;

        public static String testCaseId = "TESTCASEID";
        public static String featureModule = "FEATURE_MODULE";
        public static String searchKeyword = "SEARCH_KEYWORD";
        public static String status = "STATUS";
        public static String label = "LABEL";
        public static String deadline = "DEADLINE";
        public static String deadlineOption = "DEADLINE_OPTION";
        public static String deadlineFrom = "DEADLINE_FROM";
        public static String deadlineTo = "DEADLINE_TO";
        public static String startDate = "START_DATE";
        public static String startDateOption = "START_DATE_OPTION";
        public static String startDateFrom = "START_DATE_FROM";
        public static String startDateTo = "START_DATE_TO";
        public static String projectTitle = "PROJECT_TITLE";
        public static String expectedUrl = "EXPECTED_URL";
        public static String expectedResultNote = "EXPECTED_RESULT_NOTE";

        // =========================================================================
        // CONSTANTS: Page Metadata & Test Data Prefixes
        // =========================================================================
        public static final String PAGE_TEXT = "Projects";
        public static final String PAGE_URL = "/projects/all_projects";
        public static final String PAGE_TITLE = "Projects | RISE CRM | Anh Tester Demo";
        public static final String PREFIX_PROJECT = "Auto_";
        public static final String DATA_LABEL = "AI_automation";

        // 12 mã màu cố định của bảng màu Label trong RISE CRM
        public static final List<String> LABEL_COLORS = List.of(
                        "#83c340", "#29c2c2", "#2d9cdb", "#aab7b7",
                        "#f1c40f", "#e18a00", "#e74c3c", "#d43480",
                        "#ad159e", "#37b4e1", "#34495e", "#dbadff");

        public static String getRandomLabelColor() {
                return LABEL_COLORS.get(new java.util.Random().nextInt(LABEL_COLORS.size()));
        }

        // =========================================================================
        // IMPORT TEST DATA (Lưu trữ dữ liệu đọc từ Excel import - KHUYẾN NGHỊ DÙNG
        // ProjectDTO)
        // =========================================================================
        public static String nameProjectImportMultiPrefix = "Auto_Import_Multi_1788970446_"; // 5 dòng: _1 → _5
        public static String nameProjectImportAllFields = "Auto_Import_AllFields_1788970546";
        public static String nameProjectImportRequiredOnly = "Auto_Import_RequiredOnly_1788970646";

        // =========================================================================
        // CONSTANTS: Tên Sheet và Cột file Excel Import
        // =========================================================================
        public static final String SHEET_IMPORT_PROJECTS = "Projects";
        public static final String COL_IMPORT_TITLE = "Title";
        public static final String COL_IMPORT_TYPE = "Project Type";
        public static final String COL_IMPORT_CLIENT = "Client";
        public static final String COL_IMPORT_DESCRIPTION = "Description";
        public static final String COL_IMPORT_START_DATE = "Start Date";
        public static final String COL_IMPORT_DEADLINE = "Deadline";
        public static final String COL_IMPORT_PRICE = "Price";
        public static final String COL_IMPORT_LABELS = "Labels";
        public static final String COL_IMPORT_STATUS = "Status";

        // =========================================================================
        // CONSTANTS: Messages & Tên File Import/Export
        // =========================================================================
        public static final String NAME_SAMPLE_FILE_IMPORT = "projects-sample";
        public static final String NAME_EXPORT_FILE_PROJECTS = "Projects  RISE CRM  Anh Tester Demo";
        public static final String MSG_REQUIRED_THIS_FIELD = "This field is required.";
        public static final String MSG_REQUIRE_STATUS_PREVIEW = "This field is required.";
        public static final String MSG_REQUIRE_PROJECT_TYPE_PREVIEW = "Project type field is required.";
        public static final String MSG_REQUIRE_CLIENT_PREVIEW = "Since it's client project, client field is required.";
        public static final String MSG_INVALID_STATUS_PREVIEW = "Project status is invalid.";
        public static final String MSG_INVALID_EXCEL_EXTENSION = "Please upload a excel file. (.xlsx)";
        public static final String MSG_FILE_TYPE_NOT_ALLOWED = "File type is not allowed.";
        public static final String MSG_TABLE_SEARCH_EMPTY = "No record found.";

        // =========================================================================
        // CONSTANTS: Trạng thái (Status)
        // =========================================================================
        public static final String STATUS_OPEN = "Open";
        public static final String STATUS_COMPLETED = "Completed";
        public static final String STATUS_HOLD = "Hold";
        public static final String STATUS_CANCELED = "Canceled";
        public static final String STATUS_DOING = "Doing";
        public static final String STATUS_ALL = "Status"; // Tên mặc định của dropdown khi reset

        public static final List<String> STATUS_OPTIONS = Arrays.asList(
                        STATUS_OPEN,
                        STATUS_COMPLETED,
                        STATUS_HOLD,
                        STATUS_CANCELED,
                        STATUS_DOING);
        // =========================================================================
        // CONSTANTS: Bộ lọc Deadline
        // =========================================================================
        public static final String DEADLINE_DEFAULT = "- Deadline -";
        public static final String DEADLINE_EXPIRED = "Expired";
        public static final String DEADLINE_TODAY = "Today";
        public static final String DEADLINE_TOMORROW = "Tomorrow";
        public static final String DEADLINE_IN_7_DAYS = "In 7 days";
        public static final String DEADLINE_IN_15_DAYS = "In 15 days";
        public static final String DEADLINE_CUSTOM = "Custom";
        public static final List<String> DEADLINE_OPTIONS = Arrays.asList(
                        DEADLINE_EXPIRED,
                        DEADLINE_TODAY,
                        DEADLINE_TOMORROW,
                        DEADLINE_IN_7_DAYS,
                        DEADLINE_IN_15_DAYS);

        // =========================================================================
        // CONSTANTS: Bộ lọc Start Date
        // =========================================================================
        public static final String START_DATE_DEFAULT = "Start date";
        public static final String START_DATE_THIS_MONTH = "This Month";
        public static final String START_DATE_LAST_MONTH = "Last Month";
        public static final String START_DATE_THIS_YEAR = "This Year";
        public static final String START_DATE_LAST_YEAR = "Last Year";
        public static final String START_DATE_NEXT_7_DAYS = "Next 7 Days";
        public static final String START_DATE_NEXT_MONTH = "Next Month";
        public static final List<String> START_DATE_OPTIONS = Arrays.asList(
                        START_DATE_THIS_MONTH,
                        START_DATE_LAST_MONTH,
                        START_DATE_THIS_YEAR,
                        START_DATE_LAST_YEAR,
                        START_DATE_NEXT_7_DAYS,
                        START_DATE_NEXT_MONTH);

        // =========================================================================
        // CONSTANTS: Loại dự án (Project Type)
        // =========================================================================
        public static final String TYPE_CLIENT = "Client Project";
        public static final String TYPE_INTERNAL = "Internal Project";

        // =========================================================================
        // CONSTANTS: Tên cột trong bảng (Columns)
        // =========================================================================
        public static final String COL_ID = "ID";
        public static final String COL_TITLE = "Title";
        public static final String COL_CLIENT = "Client";
        public static final String COL_PRICE = "Price";
        public static final String COL_START_DATE = "Start date";
        public static final String COL_DEADLINE = "Deadline";
        public static final String COL_PROGRESS = "Progress";
        public static final String COL_STATUS = "Status";
        public static final String COL_OPTIONS = "Options";
        public static final List<String> TABLE_COLUMNS = Arrays.asList(
                        ProjectModel.COL_ID,
                        ProjectModel.COL_TITLE,
                        ProjectModel.COL_CLIENT,
                        ProjectModel.COL_PRICE,
                        ProjectModel.COL_START_DATE,
                        ProjectModel.COL_DEADLINE,
                        ProjectModel.COL_PROGRESS,
                        ProjectModel.COL_STATUS);

        // =========================================================================
        // CONSTANTS: Tiêu đề Modal
        // =========================================================================
        public static final String MODAL_TITLE_ADD_PROJECT = "Add project";
        public static final String MODAL_TITLE_IMPORT = "Import projects";
        public static final String MODAL_TITLE_MANAGE_LABELS = "Manage labels";
}
