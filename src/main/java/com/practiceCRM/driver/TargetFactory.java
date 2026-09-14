/*
 * Copyright (c) 2022.
 * Automation Framework Selenium - Anh Tester
 */

package com.practiceCRM.driver;

import com.practiceCRM.constants.FrameworkConstants;
import com.practiceCRM.enums.Target;
import com.practiceCRM.exceptions.TargetNotValidException;
import com.practiceCRM.utils.LogUtils;
import io.qameta.allure.Allure;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

/**
 * TargetFactory quyết định môi trường chạy (Local hay Remote/Selenium Grid).
 * Nó đọc cấu hình TARGET từ FrameworkConstants và trả về WebDriver tương ứng.
 */
public class TargetFactory {

    /**
     * Khởi tạo WebDriver dựa vào cấu hình BROWSER trong properties.
     */
    public WebDriver createInstance() {
        Target target = Target.valueOf(FrameworkConstants.TARGET.toUpperCase());
        WebDriver webdriver;

        switch (target) {
            case LOCAL:
                // Tạo driver chạy trực tiếp trên máy tính cá nhân (Local)
                webdriver = BrowserFactory.valueOf(FrameworkConstants.BROWSER.toUpperCase()).createDriver();
                break;
            case REMOTE:
                // Tạo driver chạy trên server (Selenium Grid, Docker, Cloud)
                webdriver = createRemoteInstance(
                        BrowserFactory.valueOf(FrameworkConstants.BROWSER.toUpperCase()).getOptions());
                break;
            default:
                throw new TargetNotValidException(target.toString());
        }
        return webdriver;
    }

    /**
     * Khởi tạo WebDriver với tham số truyền vào từ TestNG XML (ưu tiên tham số
     * browser truyền vào).
     * 
     * @param browser Tên trình duyệt (ví dụ: "chrome", "edge")
     */
    public WebDriver createInstance(String browser) {
        Target target = Target.valueOf(FrameworkConstants.TARGET.toUpperCase());
        WebDriver webdriver;

        // Ưu tiên: Nếu BROWSER trong config có giá trị thì dùng nó, nếu rỗng thì dùng
        // tham số từ XML
        String browserName = (FrameworkConstants.BROWSER != null && !FrameworkConstants.BROWSER.isEmpty())
                ? FrameworkConstants.BROWSER
                : browser;

        Allure.step("\uD83E\uDD16 Run on browser: " + browserName);

        switch (target) {
            case LOCAL:
                // Tạo driver Local theo Enum trong BrowserFactory
                webdriver = BrowserFactory.valueOf(browserName.toUpperCase()).createDriver();
                break;
            case REMOTE:
                // Tạo driver Remote (Grid) theo cấu hình truyền qua getOptions()
                webdriver = createRemoteInstance(BrowserFactory.valueOf(browserName.toUpperCase()).getOptions());
                break;
            default:
                throw new TargetNotValidException(target.toString());
        }
        return webdriver;
    }

    /**
     * Hàm cấu hình RemoteWebDriver để kết nối tới Selenium Grid Hub.
     */
    private RemoteWebDriver createRemoteInstance(MutableCapabilities capability) {
        RemoteWebDriver remoteWebDriver = null;
        try {
            // Nối chuỗi tạo đường dẫn Grid Hub (ví dụ: http://localhost:4444)
            String gridURL = String.format("http://%s:%s", FrameworkConstants.REMOTE_URL,
                    FrameworkConstants.REMOTE_PORT);
            LogUtils.info("Remote URL: " + gridURL);
            remoteWebDriver = new RemoteWebDriver(new URL(gridURL), capability);
        } catch (java.net.MalformedURLException e) {
            LogUtils.error("Grid URL is invalid or Grid Port is not available");
            LogUtils.error(String.format("Browser: %s", capability.getBrowserName()), e);
        } catch (IllegalArgumentException e) {
            LogUtils.error(String.format("Browser %s is not valid or recognized", capability.getBrowserName()), e);
        }

        return remoteWebDriver;
    }

}