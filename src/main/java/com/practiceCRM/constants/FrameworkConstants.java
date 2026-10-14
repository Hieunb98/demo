/*
 * Copyright (c) 2022 Anh Tester
 * Automation Framework Selenium
 */

package com.practiceCRM.constants;

import com.practiceCRM.config.ConfigFactory;
import com.practiceCRM.config.Configuration;
import com.practiceCRM.helpers.SystemHelpers;
import com.practiceCRM.utils.ReportUtils;

import java.io.File;

/**
 * FrameworkConstants là nơi tập trung các hằng số (constants) cho toàn bộ hệ thống.
 * Cấu hình được nạp Type-safe thông qua thư viện Owner (Configuration & ConfigFactory).
 */
public final class FrameworkConstants {

        private FrameworkConstants() {
        }

        private static final Configuration CONFIG = ConfigFactory.getConfigs();

        // ===== ĐƯỜNG DẪN FILE DỮ LIỆU =====
        public static final String PROJECT_PATH = SystemHelpers.getCurrentDir();
        public static final String EXCEL_DATA_FILE_PATH = CONFIG.EXCEL_DATA_FILE_PATH();
        public static final String EXCEL_CRM_LOGIN = CONFIG.EXCEL_CRM_LOGIN();
        public static final String EXCEL_PROJECTS_IMPORT = CONFIG.EXCEL_PROJECTS_IMPORT();
        public static final String EXCEL_PROJECTS_IMPORT_MULTI = CONFIG.EXCEL_PROJECTS_IMPORT_MULTI();
        public static final String EXCEL_PROJECTS_IMPORT_ALL_FIELDS = CONFIG.EXCEL_PROJECTS_IMPORT_ALL_FIELDS();
        public static final String EXCEL_PROJECTS_IMPORT_REQUIRED_ONLY = CONFIG.EXCEL_PROJECTS_IMPORT_REQUIRED_ONLY();
        public static final String EXCEL_PROJECTS_IMPORT_TRIM_AND_UNICODE = CONFIG.EXCEL_PROJECTS_IMPORT_TRIM_AND_UNICODE();
        public static final String EXCEL_PROJECTS_IMPORT_MISSING_PROJECT_TYPE_AND_STATUS = CONFIG.EXCEL_PROJECTS_IMPORT_MISSING_PROJECT_TYPE_AND_STATUS();
        public static final String EXCEL_PROJECTS_IMPORT_MISSING_CLIENT_AND_STATUS = CONFIG.EXCEL_PROJECTS_IMPORT_MISSING_CLIENT_AND_STATUS();
        public static final String EXCEL_PROJECTS_IMPORT_INVALID_STATUS = CONFIG.EXCEL_PROJECTS_IMPORT_INVALID_STATUS();
        public static final String EXCEL_PROJECTS_IMPORT_INVALID_DATE_AND_PRICE = CONFIG.EXCEL_PROJECTS_IMPORT_INVALID_DATE_AND_PRICE();
        public static final String EXCEL_PROJECTS_IMPORT_DUPLICATE_NAME = CONFIG.EXCEL_PROJECTS_IMPORT_DUPLICATE_NAME();
        public static final String EXCEL_PROJECTS_IMPORT_EMPTY_HEADER_ONLY = CONFIG.EXCEL_PROJECTS_IMPORT_EMPTY_HEADER_ONLY();
        public static final String EXCEL_PROJECTS_IMPORT_MISSING_TITLE_COLUMN = CONFIG.EXCEL_PROJECTS_IMPORT_MISSING_TITLE_COLUMN();
        public static final String EXCEL_PROJECTS_IMPORT_SWAPPED_COLUMNS = CONFIG.EXCEL_PROJECTS_IMPORT_SWAPPED_COLUMNS();
        public static final String EXCEL_PROJECTS_IMPORT_EXTRA_COLUMNS = CONFIG.EXCEL_PROJECTS_IMPORT_EXTRA_COLUMNS();
        public static final String EXCEL_PROJECTS_IMPORT_MULTIPLE_SHEETS = CONFIG.EXCEL_PROJECTS_IMPORT_MULTIPLE_SHEETS();
        public static final String FILE_PROJECTS_INVALID_IMAGE = CONFIG.FILE_PROJECTS_INVALID_IMAGE();
        public static final String FILE_PROJECTS_INVALID_PPTX = CONFIG.FILE_PROJECTS_INVALID_PPTX();
        public static final String FILE_PROJECTS_INVALID_DOCX = CONFIG.FILE_PROJECTS_INVALID_DOCX();
        public static final String FILE_PROJECTS_INVALID_CSV = CONFIG.FILE_PROJECTS_INVALID_CSV();
        public static final String EXCEL_CRM_DATA = CONFIG.EXCEL_CRM_DATA();
        public static final String EXCEL_CRM_PRODUCTS_USER = CONFIG.EXCEL_CRM_PRODUCTS_USER();

        // ===== CẤU HÌNH MÔI TRƯỜNG & URL =====
        // Owner tự động ưu tiên system:properties nên không cần kiểm tra System.getProperty thủ công
        public static final String BROWSER = CONFIG.BROWSER();
        public static final String URL_CRM = CONFIG.URL_CRM();
        public static final String URL_CMS_ADMIN = CONFIG.URL_CMS_ADMIN();
        public static final String URL_CMS_USER = CONFIG.URL_CMS_USER();
        public static final String REMOTE_URL = CONFIG.REMOTE_URL();
        public static final String REMOTE_PORT = CONFIG.REMOTE_PORT();

        // ===== CẤU HÌNH REPORT & NOTIFICATION =====
        public static final String PROJECT_NAME = CONFIG.PROJECT_NAME();
        public static final String REPORT_TITLE = CONFIG.REPORT_TITLE();
        public static final String EXTENT_REPORT_NAME = CONFIG.EXTENT_REPORT_NAME();
        public static final String EXTENT_REPORT_FOLDER = CONFIG.EXTENT_REPORT_FOLDER();
        public static final String EXPORT_VIDEO_PATH = CONFIG.EXPORT_VIDEO_PATH();
        public static final String EXPORT_CAPTURE_PATH = CONFIG.EXPORT_CAPTURE_PATH();
        public static final String TARGET = (System.getProperty("TARGET") != null && !System.getProperty("TARGET").trim().isEmpty())
                ? System.getProperty("TARGET").trim()
                : CONFIG.TARGET();
        public static final String OVERRIDE_REPORTS = CONFIG.OVERRIDE_REPORTS();
        public static final String OPEN_REPORTS_AFTER_EXECUTION = CONFIG.OPEN_REPORTS_AFTER_EXECUTION();
        public static final String SEND_EMAIL_TO_USERS = CONFIG.SEND_EMAIL_TO_USERS();
        public static final String SCREENSHOT_PASSED_TCS = CONFIG.SCREENSHOT_PASSED_TCS();
        public static final String SCREENSHOT_FAILED_TCS = CONFIG.SCREENSHOT_FAILED_TCS();
        public static final String SCREENSHOT_SKIPPED_TCS = CONFIG.SCREENSHOT_SKIPPED_TCS();
        public static final String SCREENSHOT_ALL_STEPS = CONFIG.SCREENSHOT_ALL_STEPS();
        public static final String ZIP_FOLDER = CONFIG.ZIP_FOLDER();
        public static final String ZIP_FOLDER_PATH = CONFIG.ZIP_FOLDER_PATH();
        public static final String ZIP_FOLDER_NAME = CONFIG.ZIP_FOLDER_NAME();
        public static final String VIDEO_RECORD = CONFIG.VIDEO_RECORD();

        public static final String LOCATE = CONFIG.LOCATE();
        public static final String RETRY_TEST_FAIL = CONFIG.RETRY_TEST_FAIL();

        // ===== CẤU HÌNH TIMEOUT (Tính bằng giây) =====
        public static final int WAIT_DEFAULT = CONFIG.WAIT_DEFAULT();
        public static final int WAIT_IMPLICIT = CONFIG.WAIT_IMPLICIT();
        public static final int WAIT_EXPLICIT = CONFIG.WAIT_EXPLICIT();
        public static final int WAIT_PAGE_LOADED = CONFIG.WAIT_PAGE_LOADED();
        public static final int WAIT_SLEEP_STEP = CONFIG.WAIT_SLEEP_STEP();
        public static final String ACTIVE_PAGE_LOADED = CONFIG.ACTIVE_PAGE_LOADED();

        public static final String EXTENT_REPORT_FOLDER_PATH = PROJECT_PATH + EXTENT_REPORT_FOLDER;
        public static final String EXTENT_REPORT_FILE_NAME = EXTENT_REPORT_NAME + ".html";
        public static String EXTENT_REPORT_FILE_PATH = EXTENT_REPORT_FOLDER_PATH + File.separator
                        + EXTENT_REPORT_FILE_NAME;

        // Zip file for Report folder
        public static final String ZIPPED_EXTENT_REPORTS_FOLDER = EXTENT_REPORT_FOLDER + ".zip";

        public static final String YES = "yes";
        public static final String NO = "no";

        public static final String BOLD_START = "<b>";
        public static final String BOLD_END = "</b>";

        /* ICONS - START */

        public static final String ICON_SMILEY_PASS = "<i class='fa fa-smile-o' style='font-size:24px'></i>";
        public static final String ICON_SMILEY_SKIP = "<i class=\"fas fa-frown-open\"></i>";
        public static final String ICON_SMILEY_FAIL = "<i class='fa fa-frown-o' style='font-size:24px'></i>";

        public static final String ICON_OS_WINDOWS = "<i class='fa fa-windows' ></i>";
        public static final String ICON_OS_MAC = "<i class='fa fa-apple' ></i>";
        public static final String ICON_OS_LINUX = "<i class='fa fa-linux' ></i>";

        public static final String ICON_BROWSER_OPERA = "<i class=\"fa fa-opera\" aria-hidden=\"true\"></i>";
        public static final String ICON_BROWSER_EDGE = "<i class=\"fa fa-edge\" aria-hidden=\"true\"></i>";
        public static final String ICON_BROWSER_CHROME = "<i class=\"fa fa-chrome\" aria-hidden=\"true\"></i>";
        public static final String ICON_BROWSER_FIREFOX = "<i class=\"fa fa-firefox\" aria-hidden=\"true\"></i>";
        public static final String ICON_BROWSER_SAFARI = "<i class=\"fa fa-safari\" aria-hidden=\"true\"></i>";

        public static final String ICON_Navigate_Right = "<i class='fa fa-arrow-circle-right' ></i>";
        public static final String ICON_LAPTOP = "<i class='fa fa-laptop' style='font-size:18px'></i>";
        public static final String ICON_BUG = "<i class='fa fa-bug' ></i>";
        /* style="text-align:center;" */


        public static final String ICON_CAMERA = "<i class=\"fa fa-camera\" aria-hidden=\"true\"></i>";

        public static final String ICON_BROWSER_PREFIX = "<i class=\"fa fa-";
        public static final String ICON_BROWSER_SUFFIX = "\" aria-hidden=\"true\"></i>";
        /* ICONS - END */

        public static String getExtentReportFilePath() {
                if (EXTENT_REPORT_FILE_PATH.isEmpty()) {
                        EXTENT_REPORT_FILE_PATH = ReportUtils.createExtentReportPath();
                }
                return EXTENT_REPORT_FILE_PATH;
        }

}
