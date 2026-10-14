package com.practiceCRM.config;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

/**
 * Interface cấu hình hệ thống sử dụng thư viện Owner.
 * Tự động hợp nhất (MERGE) cấu hình từ dòng lệnh (Maven/System properties),
 * biến môi trường (System env) và các file cấu hình properties.
 */
@LoadPolicy(LoadType.MERGE)
@Sources({
        "system:properties",
        "system:env",
        "file:./src/test/resources/config/config.properties",
        "file:./src/test/resources/config/data.properties"
})
public interface Configuration extends Config, Accessible {

    // ===== HỆ THỐNG & TRÌNH DUYỆT =====
    @Key("TARGET")
    @DefaultValue("local")
    String TARGET();

    @Key("BROWSER")
    @DefaultValue("chrome")
    String BROWSER();

    @Key("HEADLESS")
    @DefaultValue("false")
    Boolean HEADLESS();

    @Key("URL_CRM")
    String URL_CRM();

    @Key("URL_CMS_ADMIN")
    String URL_CMS_ADMIN();

    @Key("URL_CMS_USER")
    String URL_CMS_USER();

    @Key("REMOTE_URL")
    @DefaultValue("localhost")
    String REMOTE_URL();

    @Key("REMOTE_PORT")
    @DefaultValue("4444")
    String REMOTE_PORT();

    @Key("AUTHOR")
    @DefaultValue("Bat Hieu")
    String AUTHOR();

    // ===== TIMEOUT CẤU HÌNH (Tính bằng giây) =====
    @Key("WAIT_DEFAULT")
    @DefaultValue("5")
    int WAIT_DEFAULT();

    @Key("WAIT_IMPLICIT")
    @DefaultValue("10")
    int WAIT_IMPLICIT();

    @Key("WAIT_EXPLICIT")
    @DefaultValue("10")
    int WAIT_EXPLICIT();

    @Key("WAIT_PAGE_LOADED")
    @DefaultValue("40")
    int WAIT_PAGE_LOADED();

    @Key("ACTIVE_PAGE_LOADED")
    @DefaultValue("true")
    String ACTIVE_PAGE_LOADED();

    @Key("WAIT_SLEEP_STEP")
    @DefaultValue("0")
    int WAIT_SLEEP_STEP();

    // ===== BÁO CÁO & XUẤT DỮ LIỆU =====
    @Key("PROJECT_NAME")
    @DefaultValue("CRM Automation")
    String PROJECT_NAME();

    @Key("REPORT_TITLE")
    @DefaultValue("Report | CRM Automation")
    String REPORT_TITLE();

    @Key("EXTENT_REPORT_NAME")
    @DefaultValue("ExtentReports")
    String EXTENT_REPORT_NAME();

    @Key("EXTENT_REPORT_FOLDER")
    @DefaultValue("exports/ExtentReports")
    String EXTENT_REPORT_FOLDER();

    @Key("EXPORT_VIDEO_PATH")
    @DefaultValue("exports/ExportData/Videos")
    String EXPORT_VIDEO_PATH();

    @Key("EXPORT_CAPTURE_PATH")
    @DefaultValue("exports/ExportData/Images")
    String EXPORT_CAPTURE_PATH();

    @Key("ZIP_FOLDER")
    @DefaultValue("no")
    String ZIP_FOLDER();

    @Key("ZIP_FOLDER_PATH")
    @DefaultValue("exports/ExtentReports")
    String ZIP_FOLDER_PATH();

    @Key("ZIP_FOLDER_NAME")
    @DefaultValue("ExtentReports.zip")
    String ZIP_FOLDER_NAME();

    @Key("VIDEO_RECORD")
    @DefaultValue("no")
    String VIDEO_RECORD();

    @Key("OVERRIDE_REPORTS")
    @DefaultValue("yes")
    String OVERRIDE_REPORTS();

    @Key("OPEN_REPORTS_AFTER_EXECUTION")
    @DefaultValue("no")
    String OPEN_REPORTS_AFTER_EXECUTION();

    @Key("SEND_EMAIL_TO_USERS")
    @DefaultValue("no")
    String SEND_EMAIL_TO_USERS();

    @Key("SCREENSHOT_PASSED_TCS")
    @DefaultValue("yes")
    String SCREENSHOT_PASSED_TCS();

    @Key("SCREENSHOT_FAILED_TCS")
    @DefaultValue("yes")
    String SCREENSHOT_FAILED_TCS();

    @Key("SCREENSHOT_SKIPPED_TCS")
    @DefaultValue("no")
    String SCREENSHOT_SKIPPED_TCS();

    @Key("SCREENSHOT_ALL_STEPS")
    @DefaultValue("no")
    String SCREENSHOT_ALL_STEPS();

    @Key("LOCATE")
    @DefaultValue("vi")
    String LOCATE();

    @Key("RETRY_TEST_FAIL")
    @DefaultValue("1")
    String RETRY_TEST_FAIL();

    // ===== DATA PROPERTIES =====
    @Key("TXT_FILE_PATH")
    String TXT_FILE_PATH();

    @Key("EXCEL_DATA_FILE_PATH")
    String EXCEL_DATA_FILE_PATH();

    @Key("EXCEL_CRM_LOGIN")
    String EXCEL_CRM_LOGIN();

    @Key("EXCEL_PROJECTS_IMPORT")
    String EXCEL_PROJECTS_IMPORT();

    @Key("EXCEL_CRM_DATA")
    String EXCEL_CRM_DATA();

    @Key("EXCEL_CRM_PRODUCTS_USER")
    String EXCEL_CRM_PRODUCTS_USER();

    @Key("email")
    String email();

    @Key("password")
    String password();

    @Key("product_P01")
    String product_P01();

    @Key("product_P02")
    String product_P02();

    @Key("EXCEL_PROJECTS_IMPORT_MULTI")
    String EXCEL_PROJECTS_IMPORT_MULTI();

    @Key("EXCEL_PROJECTS_IMPORT_ALL_FIELDS")
    String EXCEL_PROJECTS_IMPORT_ALL_FIELDS();

    @Key("EXCEL_PROJECTS_IMPORT_REQUIRED_ONLY")
    String EXCEL_PROJECTS_IMPORT_REQUIRED_ONLY();

    @Key("EXCEL_PROJECTS_IMPORT_TRIM_AND_UNICODE")
    String EXCEL_PROJECTS_IMPORT_TRIM_AND_UNICODE();

    @Key("FILE_PROJECTS_INVALID_IMAGE")
    String FILE_PROJECTS_INVALID_IMAGE();

    @Key("FILE_PROJECTS_INVALID_PPTX")
    String FILE_PROJECTS_INVALID_PPTX();

    @Key("FILE_PROJECTS_INVALID_DOCX")
    String FILE_PROJECTS_INVALID_DOCX();

    @Key("FILE_PROJECTS_INVALID_CSV")
    String FILE_PROJECTS_INVALID_CSV();

    @Key("EXCEL_PROJECTS_IMPORT_EMPTY_HEADER_ONLY")
    String EXCEL_PROJECTS_IMPORT_EMPTY_HEADER_ONLY();

    @Key("EXCEL_PROJECTS_IMPORT_MISSING_TITLE_COLUMN")
    String EXCEL_PROJECTS_IMPORT_MISSING_TITLE_COLUMN();

    @Key("EXCEL_PROJECTS_IMPORT_SWAPPED_COLUMNS")
    String EXCEL_PROJECTS_IMPORT_SWAPPED_COLUMNS();

    @Key("EXCEL_PROJECTS_IMPORT_EXTRA_COLUMNS")
    String EXCEL_PROJECTS_IMPORT_EXTRA_COLUMNS();

    @Key("EXCEL_PROJECTS_IMPORT_MULTIPLE_SHEETS")
    String EXCEL_PROJECTS_IMPORT_MULTIPLE_SHEETS();

    @Key("EXCEL_PROJECTS_IMPORT_MISSING_PROJECT_TYPE_AND_STATUS")
    String EXCEL_PROJECTS_IMPORT_MISSING_PROJECT_TYPE_AND_STATUS();

    @Key("EXCEL_PROJECTS_IMPORT_MISSING_CLIENT_AND_STATUS")
    String EXCEL_PROJECTS_IMPORT_MISSING_CLIENT_AND_STATUS();

    @Key("EXCEL_PROJECTS_IMPORT_INVALID_STATUS")
    String EXCEL_PROJECTS_IMPORT_INVALID_STATUS();

    @Key("EXCEL_PROJECTS_IMPORT_INVALID_DATE_AND_PRICE")
    String EXCEL_PROJECTS_IMPORT_INVALID_DATE_AND_PRICE();

    @Key("EXCEL_PROJECTS_IMPORT_DUPLICATE_NAME")
    String EXCEL_PROJECTS_IMPORT_DUPLICATE_NAME();
}
