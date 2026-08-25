package com.anhtester.driver;

import org.openqa.selenium.WebDriver;

/**
 * DriverManager là class cốt lõi để quản lý các phiên bản (instances) của WebDriver.
 * Class này sử dụng Design Pattern là Singleton (ẩn constructor) kết hợp với ThreadLocal.
 * Mục đích: Đảm bảo khi chạy test song song (parallel), mỗi luồng (thread) sẽ có một 
 * WebDriver riêng biệt, không bị xung đột (conflict) với nhau.
 */
public class DriverManager {

    // ThreadLocal lưu trữ WebDriver riêng biệt cho từng luồng (thread)
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Private constructor ngăn không cho khởi tạo đối tượng DriverManager từ bên ngoài
    private DriverManager() {
        super();
    }

    /**
     * Lấy WebDriver của luồng hiện tại.
     * Hàm này được gọi ở bất cứ đâu (Page Object, Listeners, Utilities) mà không cần truyền driver.
     * @return WebDriver đang chạy trong thread hiện tại.
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Thiết lập WebDriver cho luồng hiện tại.
     * Hàm này thường được gọi trong @BeforeMethod của BaseTest ngay sau khi tạo browser.
     * @param driver WebDriver instance cần gán cho thread này.
     */
    public static void setDriver(WebDriver driver) {
        DriverManager.driver.set(driver);
    }

    /**
     * Đóng trình duyệt của luồng hiện tại và dọn dẹp.
     * Thường được gọi trong @AfterMethod của BaseTest.
     */
    public static void quit() {
        if (DriverManager.getDriver() != null) {
            DriverManager.getDriver().quit();
            // Xóa driver khỏi ThreadLocal sau khi quit để tránh memory leak
            driver.remove();
        }
    }

//    public static String getInfo() {
//        Capabilities cap = ((RemoteWebDriver) DriverManager.getDriver()).getCapabilities();
//        String browserName = cap.getBrowserName();
//        String platform = cap.getPlatformName().toString();
//        String version = cap.getBrowserVersion();
//        return String.format("browser: %s v: %s platform: %s", browserName, version, platform);
//    }
}
