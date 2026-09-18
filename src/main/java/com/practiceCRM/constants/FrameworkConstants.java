/*
 * Copyright (c) 2022 Anh Tester
 * Automation Framework Selenium
 */

package com.practiceCRM.constants;

import com.practiceCRM.helpers.SystemHelpers;
import com.practiceCRM.helpers.PropertiesHelpers;
import com.practiceCRM.utils.ReportUtils;

import java.io.File;

/**
 * FrameworkConstants là nơi tập trung đọc và lưu trữ toàn bộ các hằng số
 * (constants)
 * từ các file cấu hình (như config.properties, data.properties) vào bộ nhớ RAM
 * một lần duy nhất.
 * Nhờ có static block, khi hệ thống khởi động, nó sẽ nạp tất cả biến này để
 * dùng chung cho mọi file.
 */
public final class FrameworkConstants {

        private FrameworkConstants() {
        }

        // Static block sẽ chạy ngay khi class này được gọi lần đầu tiên
        static {
                PropertiesHelpers.loadAllFiles();
        }

        // ===== ĐƯỜNG DẪN FILE DỮ LIỆU =====
        public static final String PROJECT_PATH = SystemHelpers.getCurrentDir();
        public static final String EXCEL_DATA_FILE_PATH = PropertiesHelpers.getValue("EXCEL_DATA_FILE_PATH");
        public static final String EXCEL_CRM_LOGIN = PropertiesHelpers.getValue("EXCEL_CRM_LOGIN");
        public static final String EXCEL_PROJECTS_IMPORT = PropertiesHelpers.getValue("EXCEL_PROJECTS_IMPORT");
        public static final String EXCEL_PROJECTS_IMPORT_MULTI = PropertiesHelpers
                        .getValue("EXCEL_PROJECTS_IMPORT_MULTI");
        public static final String EXCEL_PROJECTS_IMPORT_ALL_FIELDS = PropertiesHelpers
                        .getValue("EXCEL_PROJECTS_IMPORT_ALL_FIELDS");
        public static final String EXCEL_PROJECTS_IMPORT_REQUIRED_ONLY = PropertiesHelpers
                        .getValue("EXCEL_PROJECTS_IMPORT_REQUIRED_ONLY");
        public static final String EXCEL_PROJECTS_IMPORT_TRIM_AND_UNICODE = PropertiesHelpers
                        .getValue("EXCEL_PROJECTS_IMPORT_TRIM_AND_UNICODE");
        public static final String EXCEL_PROJECTS_IMPORT_MISSING_PROJECT_TYPE_AND_STATUS = PropertiesHelpers
                        .getValue("EXCEL_PROJECTS_IMPORT_MISSING_PROJECT_TYPE_AND_STATUS");
        public static final String EXCEL_PROJECTS_IMPORT_MISSING_CLIENT_AND_STATUS = PropertiesHelpers
                        .getValue("EXCEL_PROJECTS_IMPORT_MISSING_CLIENT_AND_STATUS");
        public static final String EXCEL_PROJECTS_IMPORT_INVALID_STATUS = PropertiesHelpers
                        .getValue("EXCEL_PROJECTS_IMPORT_INVALID_STATUS");
        public static final String EXCEL_PROJECTS_IMPORT_INVALID_DATE_AND_PRICE = PropertiesHelpers
                        .getValue("EXCEL_PROJECTS_IMPORT_INVALID_DATE_AND_PRICE");
        public static final String EXCEL_PROJECTS_IMPORT_DUPLICATE_NAME = PropertiesHelpers
                        .getValue("EXCEL_PROJECTS_IMPORT_DUPLICATE_NAME");
        public static final String EXCEL_PROJECTS_IMPORT_EMPTY_HEADER_ONLY = PropertiesHelpers
                        .getValue("EXCEL_PROJECTS_IMPORT_EMPTY_HEADER_ONLY");
        public static final String EXCEL_PROJECTS_IMPORT_MISSING_TITLE_COLUMN = PropertiesHelpers
                        .getValue("EXCEL_PROJECTS_IMPORT_MISSING_TITLE_COLUMN");
        public static final String EXCEL_PROJECTS_IMPORT_SWAPPED_COLUMNS = PropertiesHelpers
                        .getValue("EXCEL_PROJECTS_IMPORT_SWAPPED_COLUMNS");
        public static final String EXCEL_PROJECTS_IMPORT_EXTRA_COLUMNS = PropertiesHelpers
                        .getValue("EXCEL_PROJECTS_IMPORT_EXTRA_COLUMNS");
        public static final String EXCEL_PROJECTS_IMPORT_MULTIPLE_SHEETS = PropertiesHelpers
                        .getValue("EXCEL_PROJECTS_IMPORT_MULTIPLE_SHEETS");
        public static final String FILE_PROJECTS_INVALID_IMAGE = PropertiesHelpers
                        .getValue("FILE_PROJECTS_INVALID_IMAGE");
        public static final String FILE_PROJECTS_INVALID_PPTX = PropertiesHelpers
                        .getValue("FILE_PROJECTS_INVALID_PPTX");
        public static final String FILE_PROJECTS_INVALID_DOCX = PropertiesHelpers
                        .getValue("FILE_PROJECTS_INVALID_DOCX");
        public static final String FILE_PROJECTS_INVALID_CSV = PropertiesHelpers.getValue("FILE_PROJECTS_INVALID_CSV");
        public static final String EXCEL_CRM_DATA = PropertiesHelpers.getValue("EXCEL_CRM_DATA");
        public static final String EXCEL_CRM_PRODUCTS_USER = PropertiesHelpers.getValue("EXCEL_CRM_PRODUCTS_USER");

        // ===== CẤU HÌNH MÔI TRƯỜNG & URL =====
        public static final String BROWSER = System.getProperty("BROWSER") != null
                        ? System.getProperty("BROWSER")
                        : PropertiesHelpers.getValue("BROWSER");
        public static final String URL_CRM = PropertiesHelpers.getValue("URL_CRM");
        public static final String URL_CMS_ADMIN = PropertiesHelpers.getValue("URL_CMS_ADMIN");
        public static final String URL_CMS_USER = PropertiesHelpers.getValue("URL_CMS_USER");
        public static final String REMOTE_URL = PropertiesHelpers.getValue("REMOTE_URL");
        public static final String REMOTE_PORT = PropertiesHelpers.getValue("REMOTE_PORT");

        // ===== CẤU HÌNH REPORT & NOTIFICATION =====
        public static final String PROJECT_NAME = PropertiesHelpers.getValue("PROJECT_NAME");
        public static final String REPORT_TITLE = PropertiesHelpers.getValue("REPORT_TITLE");
        public static final String EXTENT_REPORT_NAME = PropertiesHelpers.getValue("EXTENT_REPORT_NAME");
        public static final String EXTENT_REPORT_FOLDER = PropertiesHelpers.getValue("EXTENT_REPORT_FOLDER");
        public static final String EXPORT_VIDEO_PATH = PropertiesHelpers.getValue("EXPORT_VIDEO_PATH");
        public static final String EXPORT_CAPTURE_PATH = PropertiesHelpers.getValue("EXPORT_CAPTURE_PATH");
        public static final String AUTHOR = PropertiesHelpers.getValue("AUTHOR");
        public static final String TARGET = PropertiesHelpers.getValue("TARGET");
        public static final String HEADLESS = System.getProperty("HEADLESS") != null
                        ? System.getProperty("HEADLESS")
                        : PropertiesHelpers.getValue("HEADLESS");
        public static final String OVERRIDE_REPORTS = PropertiesHelpers.getValue("OVERRIDE_REPORTS");
        public static final String OPEN_REPORTS_AFTER_EXECUTION = PropertiesHelpers
                        .getValue("OPEN_REPORTS_AFTER_EXECUTION");
        public static final String SEND_EMAIL_TO_USERS = PropertiesHelpers.getValue("SEND_EMAIL_TO_USERS");
        public static final String SCREENSHOT_PASSED_TCS = PropertiesHelpers.getValue("SCREENSHOT_PASSED_TCS");
        public static final String SCREENSHOT_FAILED_TCS = PropertiesHelpers.getValue("SCREENSHOT_FAILED_TCS");
        public static final String SCREENSHOT_SKIPPED_TCS = PropertiesHelpers.getValue("SCREENSHOT_SKIPPED_TCS");
        public static final String SCREENSHOT_ALL_STEPS = PropertiesHelpers.getValue("SCREENSHOT_ALL_STEPS");
        public static final String ZIP_FOLDER = PropertiesHelpers.getValue("ZIP_FOLDER");
        public static final String ZIP_FOLDER_PATH = PropertiesHelpers.getValue("ZIP_FOLDER_PATH");
        public static final String ZIP_FOLDER_NAME = PropertiesHelpers.getValue("ZIP_FOLDER_NAME");
        public static final String VIDEO_RECORD = PropertiesHelpers.getValue("VIDEO_RECORD");

        public static final String LOCATE = PropertiesHelpers.getValue("LOCATE");
        public static final String RETRY_TEST_FAIL = PropertiesHelpers.getValue("RETRY_TEST_FAIL");

        // ===== CẤU HÌNH TIMEOUT (Tính bằng giây) =====
        public static final int WAIT_DEFAULT = Integer.parseInt(PropertiesHelpers.getValue("WAIT_DEFAULT"));
        public static final int WAIT_IMPLICIT = Integer.parseInt(PropertiesHelpers.getValue("WAIT_IMPLICIT"));
        public static final int WAIT_EXPLICIT = Integer.parseInt(PropertiesHelpers.getValue("WAIT_EXPLICIT"));
        public static final int WAIT_PAGE_LOADED = Integer.parseInt(PropertiesHelpers.getValue("WAIT_PAGE_LOADED"));
        public static final int WAIT_SLEEP_STEP = Integer.parseInt(PropertiesHelpers.getValue("WAIT_SLEEP_STEP"));
        public static final String ACTIVE_PAGE_LOADED = PropertiesHelpers.getValue("ACTIVE_PAGE_LOADED");

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

        public static final String ICON_SOCIAL_GITHUB_PAGE_URL = "https://anhtester.com/";
        public static final String ICON_SOCIAL_LINKEDIN_URL = "https://www.linkedin.com/in/anhtester/";
        public static final String ICON_SOCIAL_GITHUB_URL = "https://github.com/anhtester";
        public static final String ICON_SOCIAL_LINKEDIN = "<a href='" + ICON_SOCIAL_LINKEDIN_URL
                        + "'><i class='fa fa-linkedin-square' style='font-size:24px'></i></a>";
        public static final String ICON_SOCIAL_GITHUB = "<a href='" + ICON_SOCIAL_GITHUB_URL
                        + "'><i class='fa fa-github-square' style='font-size:24px'></i></a>";

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
