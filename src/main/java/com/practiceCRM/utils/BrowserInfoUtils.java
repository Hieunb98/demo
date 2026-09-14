/*
 * Copyright (c) 2022 Anh Tester
 * Automation Framework Selenium
 */

package com.practiceCRM.utils;

import org.testng.Reporter;

import static com.practiceCRM.constants.FrameworkConstants.BROWSER;

public final class BrowserInfoUtils {

    private BrowserInfoUtils() {
        super();
    }

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static String getBrowserInfo() {
        String browser = "";
        try {
            if (Reporter.getCurrentTestResult() != null
                    && Reporter.getCurrentTestResult().getTestContext() != null
                    && Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest() != null
                    && Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("BROWSER") != null) {
                browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("BROWSER").trim().toUpperCase();
            } else {
                browser = (BROWSER != null) ? BROWSER.toUpperCase() : "CHROME";
            }
        } catch (Exception e) {
            browser = (BROWSER != null) ? BROWSER.toUpperCase() : "CHROME";
        }
        return browser;
    }

    public static String getOSInfo() {
        return System.getProperty("os.name");
    }

    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }

    public static boolean isSolaris() {
        return (OS.contains("sunos"));
    }

}
