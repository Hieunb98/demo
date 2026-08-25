/*
 * Copyright (c) 2022.
 * Automation Framework Selenium - Anh Tester
 */

package com.anhtester.driver;

import com.anhtester.constants.FrameworkConstants;
import com.anhtester.exceptions.HeadlessNotSupportedException;
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

import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.TRUE;

/**
 * BrowserFactory là một Enum đóng vai trò như một "nhà máy" (Factory Pattern)
 * chuyên sản xuất các WebDriver tương ứng với từng loại trình duyệt (Chrome,
 * Edge, Firefox, Safari).
 * Enum giúp định nghĩa các giá trị cố định, mỗi giá trị sẽ tự cài đặt
 * các cấu hình (Capabilities/Options) riêng biệt của nó (ví dụ: tắt thông báo,
 * chạy ẩn headless).
 */
public enum BrowserFactory {

   CHROME {
      @Override
      public WebDriver createDriver() {
         return new ChromeDriver(getOptions());
      }

      @Override
      public ChromeOptions getOptions() {
         ChromeOptions options = new ChromeOptions();

         // Tắt các popup lưu mật khẩu, thông báo mặc định của trình duyệt để không làm
         // gián đoạn test
         Map<String, Object> prefs = new HashMap<String, Object>();
         prefs.put("profile.default_content_setting_values.notifications", 2);
         prefs.put("profile.password_manager_leak_detection", false); // Turn off change your password
         prefs.put("credentials_enable_service", false);
         prefs.put("profile.password_manager_enabled", false);
         prefs.put("autofill.profile_enabled", false); // Turn off Save Address popup
         options.setExperimentalOption("prefs", prefs);

         options.addArguments("--disable-extensions");
         options.addArguments("--disable-infobars");
         options.addArguments("--disable-notifications");
         options.addArguments("--remote-allow-origins=*");

         options.setAcceptInsecureCerts(true); // Bỏ qua cảnh báo SSL/HTTPS khi truy cập trang web chưa có chứng chỉ

         // Đọc cấu hình HEADLESS từ FrameworkConstants
         // Nếu true: trình duyệt chạy ngầm (không hiện UI), cấu hình kích thước ảo là
         // 1920x1080
         if (Boolean.valueOf(FrameworkConstants.HEADLESS) == true) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--high-dpi-support=1");
            options.addArguments("--force-device-scale-factor=1");
         } else {
            // Nếu false: mở trình duyệt lên và phóng to hết cỡ
            options.addArguments(START_MAXIMIZED);
         }

         return options;
      }
   },
   EDGE {
      @Override
      public WebDriver createDriver() {
         return new EdgeDriver(getOptions());
      }

      @Override
      public EdgeOptions getOptions() {
         EdgeOptions options = new EdgeOptions();

         Map<String, Object> prefs = new HashMap<String, Object>();
         prefs.put("profile.default_content_setting_values.notifications", 2);
         prefs.put("profile.password_manager_leak_detection", false); // Turn off change your password
         prefs.put("credentials_enable_service", false);
         prefs.put("profile.password_manager_enabled", false);
         prefs.put("autofill.profile_enabled", false);
         options.setExperimentalOption("prefs", prefs);

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
    * Khởi tạo và trả về đối tượng WebDriver.
    * Hàm này được implement lại (ghi đè) ở từng giá trị Enum (CHROME, EDGE...).
    */
   public abstract WebDriver createDriver();

   /**
    * Khởi tạo cấu hình Options (ví dụ: headless, block popup) cho WebDriver.
    */
   public abstract MutableCapabilities getOptions();
}
