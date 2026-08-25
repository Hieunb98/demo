package com.anhtester.common;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.anhtester.constants.FrameworkConstants;
import com.anhtester.driver.DriverManager;
import com.anhtester.driver.TargetFactory;
import com.anhtester.helpers.PropertiesHelpers;
import com.anhtester.keywords.WebUI;
import com.anhtester.listeners.TestListener;
import com.anhtester.projects.cms.CommonPageCMS;

/**
 * BaseTest là class nền tảng cho tất cả các Test class khác.
 * Bất kỳ file Test nào cũng phải "extends BaseTest".
 * Nhiệm vụ chính: Tự động khởi tạo WebDriver trước khi test chạy và tự động
 * đóng WebDriver sau khi test xong.
 */
@Listeners({ TestListener.class })
public class BaseTest extends CommonPageCMS {

   /**
    * Hàm này chạy TRƯỚC mỗi phương thức @Test.
    * Đọc tham số BROWSER từ file XML (nếu có), nếu không có sẽ mặc định là
    * "chrome".
    */
   @Parameters("BROWSER")
   @BeforeMethod
   public void createDriver(@Optional("chrome") String browser) {
      // Khởi tạo WebDriver thông qua TargetFactory và bảo vệ nó khỏi xung đột luồng
      // bằng ThreadGuard
      WebDriver driver = ThreadGuard.protect(new TargetFactory().createInstance(browser));

      // Cài đặt kích thước cửa sổ dựa vào chế độ chạy
      if (Boolean.valueOf(FrameworkConstants.HEADLESS) == true) {
         driver.manage().window().setSize(new Dimension(1920, 1080)); // ép Selenium resize khi chạy ngầm
         System.out.println("Actual window size: " + driver.manage().window().getSize());
      } else {
         driver.manage().window().maximize(); // Phóng to toàn màn hình khi chạy có giao diện
      }

      // Lưu WebDriver vừa tạo vào ThreadLocal để các class khác có thể sử dụng chung
      DriverManager.setDriver(driver);
   }

   /**
    * Hàm này chạy SAU mỗi phương thức @Test (dù test PASS hay FAIL nhờ alwaysRun =
    * true).
    * Mục đích: Dọn dẹp, giải phóng bộ nhớ.
    */
   @AfterMethod(alwaysRun = true)
   public void closeDriver() {
      // Yêu cầu TestNG đánh fail test nếu có bất kỳ lệnh SoftAssert nào bị lỗi trước
      // đó
      WebUI.stopSoftAssertAll();
      // Đóng trình duyệt và xóa driver khỏi ThreadLocal
      DriverManager.quit();
   }

   /**
    * Hàm hỗ trợ tạo browser thủ công (thường dùng cho việc debug hoặc gọi ngoài
    * luồng TestNG).
    */
   public WebDriver createBrowser(@Optional("chrome") String browser) {
      PropertiesHelpers.loadAllFiles();
      WebDriver driver = ThreadGuard.protect(new TargetFactory().createInstance(browser));
      if (Boolean.valueOf(FrameworkConstants.HEADLESS) == true) {
         driver.manage().window().setSize(new Dimension(1920, 1080)); // ép Selenium resize
         System.out.println("Actual window size: " + driver.manage().window().getSize());
      } else {
         driver.manage().window().maximize();
      }
      DriverManager.setDriver(driver);
      return DriverManager.getDriver();
   }

}
