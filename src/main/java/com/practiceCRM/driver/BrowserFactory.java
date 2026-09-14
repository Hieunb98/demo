/*
 * Copyright (c) 2022.
 * Automation Framework Selenium - Anh Tester
 */

package com.practiceCRM.driver;

import com.practiceCRM.constants.FrameworkConstants;
import com.practiceCRM.exceptions.HeadlessNotSupportedException;
import com.practiceCRM.keywords.WebUI;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import org.openqa.selenium.chromium.ChromiumDriver;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.TRUE;

/**
 * BrowserFactory là một Enum đóng vai trò như một "nhà máy" (Factory Pattern)
 * chuyên sản xuất các WebDriver tương ứng với từng loại trình duyệt (Chrome,
 * Edge, Firefox, Safari).
 * 
 * Tác dụng chính của file:
 * 1. Định nghĩa cấu hình tập trung (Options/Capabilities) cho từng trình duyệt:
 *    - Tắt thông báo, popup lưu mật khẩu phiền toái.
 *    - Cấu hình chạy ngầm (Headless) chuẩn độ phân giải desktop 1920x1080.
 *    - Tự động bỏ qua lỗi chứng chỉ bảo mật SSL/HTTPS.
 * 2. Cấu hình tính năng tải file (File Download):
 *    - Tự động định hướng thư mục tải về `target/downloads` đồng bộ với WebUI.
 *    - Dùng Chrome DevTools Protocol (CDP) mở khóa tính năng tải file khi chạy Headless.
 */
public enum BrowserFactory {

   CHROME {
      @Override
      public WebDriver createDriver() {
         // Khởi tạo ChromeDriver và kích hoạt tính năng tải file qua CDP
         return enableDownloadBehavior(new ChromeDriver(getOptions()));
      }

      @Override
      public ChromeOptions getOptions() {
         ChromeOptions options = new ChromeOptions();

         // Áp dụng cấu hình prefs chung (tắt popup mật khẩu + thư mục download)
         options.setExperimentalOption("prefs", getChromiumPrefs());

         options.addArguments("--disable-extensions");
         options.addArguments("--disable-infobars");
         options.addArguments("--disable-notifications");
         options.addArguments("--remote-allow-origins=*");
         options.setAcceptInsecureCerts(true); // Bỏ qua cảnh báo SSL/HTTPS

         // Đọc cấu hình HEADLESS từ FrameworkConstants
         if (Boolean.valueOf(FrameworkConstants.HEADLESS) == true) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--high-dpi-support=1");
            options.addArguments("--force-device-scale-factor=1");
         } else {
            options.addArguments(START_MAXIMIZED);
         }

         return options;
      }
   },
   EDGE {
      @Override
      public WebDriver createDriver() {
         // Khởi tạo EdgeDriver và kích hoạt tính năng tải file qua CDP
         return enableDownloadBehavior(new EdgeDriver(getOptions()));
      }

      @Override
      public EdgeOptions getOptions() {
         EdgeOptions options = new EdgeOptions();

         // Áp dụng cấu hình prefs chung (tắt popup mật khẩu + thư mục download)
         options.setExperimentalOption("prefs", getChromiumPrefs());

         options.addArguments("--disable-extensions");
         options.addArguments("--disable-infobars");
         options.addArguments("--disable-notifications");
         options.addArguments("--remote-allow-origins=*");
         options.setAcceptInsecureCerts(true);

         if (Boolean.valueOf(FrameworkConstants.HEADLESS) == true) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--high-dpi-support=1");
            options.addArguments("--force-device-scale-factor=1");
         } else {
            options.addArguments(START_MAXIMIZED);
         }

         return options;
      }
   },
   FIREFOX {
      @Override
      public WebDriver createDriver() {
         return new FirefoxDriver(getOptions());
      }

      @Override
      public FirefoxOptions getOptions() {
         FirefoxOptions options = new FirefoxOptions();

         options.setAcceptInsecureCerts(true);

         if (Boolean.valueOf(FrameworkConstants.HEADLESS) == true) {
            options.addArguments("-headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
         }

         return options;
      }
   },
   SAFARI {
      @Override
      public WebDriver createDriver() {
         return new SafariDriver(getOptions());
      }

      @Override
      public SafariOptions getOptions() {
         SafariOptions options = new SafariOptions();
         options.setAutomaticInspection(false);

         if (TRUE.equals(Boolean.valueOf(FrameworkConstants.HEADLESS)))
            throw new HeadlessNotSupportedException(options.getBrowserName());

         return options;
      }
   };

   private static final String START_MAXIMIZED = "--start-maximized";

   /**
    * Cấu hình preferences (prefs) dùng chung cho các trình duyệt nhân Chromium (Chrome & Edge):
    * - Tắt thông báo, popup lưu mật khẩu mặc định của trình duyệt để không làm gián đoạn test.
    * - Thiết lập thư mục lưu file tải về mặc định trỏ vào 'target/downloads'.
    * - Tắt cửa sổ hỏi vị trí lưu file (prompt for download).
    */
   private static Map<String, Object> getChromiumPrefs() {
      Map<String, Object> prefs = new HashMap<>();
      prefs.put("profile.default_content_setting_values.notifications", 2);
      prefs.put("profile.password_manager_leak_detection", false); // Tắt cảnh báo lộ mật khẩu
      prefs.put("credentials_enable_service", false);
      prefs.put("profile.password_manager_enabled", false); // Tắt popup lưu mật khẩu
      prefs.put("download.default_directory", WebUI.getPathDownloadDirectory()); // Thư mục tải về chuẩn
      prefs.put("download.prompt_for_download", false); // Tải tự động, không hiện cửa sổ Save As
      prefs.put("download.directory_upgrade", true);
      prefs.put("safebrowsing.enabled", true);
      return prefs;
   }

   /**
    * Dùng lệnh Chrome DevTools Protocol (CDP) để mở khóa tính năng tải file khi chạy ngầm (Headless mode).
    * Áp dụng được cho cả ChromeDriver và EdgeDriver (kế thừa từ ChromiumDriver).
    */
   private static <T extends ChromiumDriver> T enableDownloadBehavior(T driver) {
      try {
         Map<String, Object> params = new HashMap<>();
         params.put("behavior", "allow"); // Cho phép tải file
         params.put("downloadPath", WebUI.getPathDownloadDirectory());
         params.put("eventsEnabled", true);
         driver.executeCdpCommand("Browser.setDownloadBehavior", params);
      } catch (Exception ignored) {
      }
      return driver;
   }

   /**
    * Khởi tạo và trả về đối tượng WebDriver.
    * Hàm này được implement lại (ghi đè) ở từng giá trị Enum (CHROME, EDGE...).
    */
   public abstract WebDriver createDriver();

   /**
    * Khởi tạo cấu hình Options (ví dụ: headless, block popup) cho WebDriver.
    */
   public abstract MutableCapabilities getOptions();
}
