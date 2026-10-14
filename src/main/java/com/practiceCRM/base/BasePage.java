package com.practiceCRM.base;

import com.practiceCRM.constants.FrameworkConstants;
import com.practiceCRM.driver.DriverManager;
import com.practiceCRM.enums.FailureHandling;
import com.practiceCRM.reports.AllureManager;
import com.practiceCRM.utils.FileUtils;
import com.practiceCRM.utils.LogUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * BasePage — Core Engine riêng cho tầng Page Object.
 * <p>
 * Mục tiêu: Thay thế dependency vào WebUI (third-party) bằng một lớp
 * wrapper độc lập, 100% thuộc sở hữu của project này.
 * <p>
 * Nguyên tắc Thread-safe:
 * - Mọi method đều gọi {@code DriverManager.getDriver()} tại thời điểm thực thi
 *   (Lazy Driver Pattern) — không lưu trữ WebDriver như biến instance.
 * - SoftAssert được quản lý theo từng thread qua {@code ThreadLocal<SoftAssert>}.
 * - Không bao giờ khai báo biến static cho driver hay softAssert.
 */
public abstract class BasePage {

    // =========================================================================
    // 1. THREAD-SAFE SOFT ASSERT
    // =========================================================================

    private static final ThreadLocal<SoftAssert> softAssertHolder = new ThreadLocal<>();

    /**
     * Lấy SoftAssert của thread hiện tại. Tự động tạo mới nếu chưa tồn tại.
     */
    protected SoftAssert softAssert() {
        if (softAssertHolder.get() == null) {
            softAssertHolder.set(new SoftAssert());
        }
        return softAssertHolder.get();
    }

    /**
     * Gọi ở cuối test method để flush toàn bộ lỗi SoftAssert.
     */
    public static void stopSoftAssertAll() {
        if (softAssertHolder.get() != null) {
            try {
                softAssertHolder.get().assertAll();
            } finally {
                softAssertHolder.remove();
            }
        }
    }

    /**
     * Reset SoftAssert cho thread hiện tại (gọi trong @BeforeMethod).
     */
    public static void clearSoftAssert() {
        softAssertHolder.remove();
    }

    // =========================================================================
    // 2. INTERNAL DRIVER & WAIT HELPERS
    // =========================================================================

    private WebDriver driver() {
        return DriverManager.getDriver();
    }

    private WebDriverWait getWait(int timeoutSeconds) {
        return new WebDriverWait(driver(), Duration.ofSeconds(timeoutSeconds));
    }

    private WebDriverWait getWait() {
        return getWait(FrameworkConstants.WAIT_EXPLICIT);
    }

    // =========================================================================
    // 3. NAVIGATION
    // =========================================================================

    @Step("Mở URL: {url}")
    protected void openWebsite(String url) {
        LogUtils.info("Mở URL: " + url);
        driver().get(url);
        waitForPageLoaded();
    }

    protected String getCurrentUrl() {
        return driver().getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver().getTitle();
    }

    protected void reloadPage() {
        driver().navigate().refresh();
        waitForPageLoaded();
    }

    // =========================================================================
    // 4. WAIT METHODS
    // =========================================================================

    protected void waitForPageLoaded() {
        waitForPageLoaded(FrameworkConstants.WAIT_PAGE_LOADED);
    }

    protected void waitForPageLoaded(int timeoutSeconds) {
        getWait(timeoutSeconds).until(
                d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete")
        );
    }

    protected WebElement waitForElementVisible(By by) {
        return waitForElementVisible(by, FrameworkConstants.WAIT_EXPLICIT);
    }

    protected WebElement waitForElementVisible(By by, int timeoutSeconds) {
        return getWait(timeoutSeconds).until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected WebElement waitForElementPresent(By by) {
        return waitForElementPresent(by, FrameworkConstants.WAIT_EXPLICIT);
    }

    protected WebElement waitForElementPresent(By by, int timeoutSeconds) {
        return getWait(timeoutSeconds).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected WebElement waitForElementClickable(By by) {
        return waitForElementClickable(by, FrameworkConstants.WAIT_EXPLICIT);
    }

    protected WebElement waitForElementClickable(By by, int timeoutSeconds) {
        return getWait(timeoutSeconds).until(ExpectedConditions.elementToBeClickable(by));
    }

    protected WebElement waitForElementClickable(By by, long timeoutSeconds) {
        return waitForElementClickable(by, (int) timeoutSeconds);
    }

    protected boolean waitForElementInvisible(By by) {
        return waitForElementInvisible(by, FrameworkConstants.WAIT_EXPLICIT);
    }

    protected boolean waitForElementInvisible(By by, int timeoutSeconds) {
        return getWait(timeoutSeconds).until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    // =========================================================================
    // 5. INTERACTION METHODS
    // =========================================================================

    @Step("Click vào element: {by}")
    protected void clickElement(By by) {
        clickElement(by, FrameworkConstants.WAIT_EXPLICIT);
    }

    @Step("Click vào element (timeout {timeout}s): {by}")
    protected void clickElement(By by, int timeout) {
        try {
            waitForElementClickable(by, timeout).click();
        } catch (Throwable e) {
            LogUtils.error("Không thể click element: " + by);
            throw e;
        }
    }

    @Step("Click element bằng JavaScript: {by}")
    protected void clickElementWithJs(By by) {
        WebElement element = waitForElementVisible(by);
        clickElementWithJs(element);
    }

    @Step("Click element bằng JavaScript: {element}")
    protected void clickElementWithJs(WebElement element) {
        ((JavascriptExecutor) driver()).executeScript("arguments[0].click();", element);
    }

    @Step("Nhập '{value}' vào: {by}")
    protected void setText(By by, String value) {
        WebElement el = waitForElementVisible(by);
        el.clear();
        el.sendKeys(value);
    }

    @Step("Nhập '{value}' và nhấn phím {keys}: {by}")
    protected void setText(By by, String value, Keys keys) {
        WebElement el = waitForElementVisible(by);
        el.clear();
        el.sendKeys(value, keys);
    }

    @Step("Xóa nội dung field: {by}")
    protected void clearText(By by) {
        waitForElementVisible(by).clear();
    }

    @Step("Xóa và nhập '{value}' vào: {by}")
    protected void clearAndFillText(By by, String value) {
        clearText(by);
        setText(by, value);
    }

    @Step("Upload file '{filePath}' vào: {by}")
    protected void uploadFileWithSendKeys(By by, String filePath) {
        driver().findElement(by).sendKeys(filePath);
    }

    // =========================================================================
    // 6. READ METHODS
    // =========================================================================

    protected String getTextElement(By by) {
        return waitForElementVisible(by).getText().trim();
    }

    protected String getText(By by) {
        return getTextElement(by);
    }

    protected String getAttributeElement(By by, String attributeName) {
        return waitForElementVisible(by).getAttribute(attributeName);
    }

    protected String getCssValueElement(By by, String cssProperty) {
        return waitForElementVisible(by).getCssValue(cssProperty);
    }

    protected List<String> getListElementsText(By by) {
        List<WebElement> elements = getWebElements(by);
        List<String> texts = new ArrayList<>();
        for (WebElement el : elements) {
            texts.add(el.getText().trim());
        }
        return texts;
    }

    // =========================================================================
    // 7. ELEMENT STATE CHECKS (Boolean, không ném exception)
    // =========================================================================

    @Step("Kiểm tra element tồn tại: {by}")
    protected boolean checkElementExist(By by) {
        return !driver().findElements(by).isEmpty();
    }

    protected boolean isElementPresent(By by, int timeoutSeconds) {
        return verifyElementPresent(by, timeoutSeconds);
    }

    protected boolean isElementVisible(By by, int timeoutSeconds) {
        return verifyElementVisible(by, timeoutSeconds);
    }

    protected boolean isElementDisabled(By by, int timeoutSeconds) {
        try {
            WebElement el = waitForElementVisible(by, timeoutSeconds);
            String disabledAttr = el.getAttribute("disabled");
            String classAttr = el.getAttribute("class");
            return disabledAttr != null || (classAttr != null && classAttr.contains("disabled"));
        } catch (Exception e) {
            return false;
        }
    }

    // =========================================================================
    // 8. VERIFY — CONDITIONS & EQUALITY
    // =========================================================================

    @Step("Verify điều kiện đúng: {message}")
    protected boolean verifyTrue(boolean condition, String message) {
        return verifyTrue(condition, message, FailureHandling.CONTINUE_ON_FAILURE);
    }

    @Step("Verify điều kiện đúng: {message}")
    protected boolean verifyTrue(boolean condition, String message, FailureHandling flowControl) {
        if (flowControl == FailureHandling.CONTINUE_ON_FAILURE) {
            softAssert().assertTrue(condition, message);
        } else {
            Assert.assertTrue(condition, message);
        }
        return condition;
    }

    @Step("Verify bằng nhau: actual='{actual}' expected='{expected}'")
    protected boolean verifyEquals(Object actual, Object expected) {
        return verifyEquals(actual, expected, "Verify equals: [" + actual + "] == [" + expected + "]");
    }

    @Step("Verify bằng nhau: {message}")
    protected boolean verifyEquals(Object actual, Object expected, String message) {
        boolean result = actual != null && actual.equals(expected);
        AllureManager.saveTextLog(message + " | actual=[" + actual + "] expected=[" + expected + "] → " + result);
        softAssert().assertEquals(actual, expected, message);
        return result;
    }

    @Step("Verify chứa: actual='{actual}' chứa '{expected}'")
    protected boolean verifyContains(String actual, String expected) {
        return verifyContains(actual, expected, "Expected [" + actual + "] to contain [" + expected + "]");
    }

    @Step("Verify chứa: {message}")
    protected boolean verifyContains(String actual, String expected, String message) {
        boolean result = actual != null && actual.contains(expected);
        AllureManager.saveTextLog(message + " | actual=[" + actual + "] expected to contain=[" + expected + "] → " + result);
        softAssert().assertTrue(result, message + " | actual=[" + actual + "] expected to contain=[" + expected + "]");
        return result;
    }

    // =========================================================================
    // 9. VERIFY — ELEMENT PRESENCE (có trong DOM, kể cả đang hidden)
    // =========================================================================

    protected boolean verifyElementPresent(By by) {
        return verifyElementPresent(by, FrameworkConstants.WAIT_EXPLICIT);
    }

    protected boolean verifyElementPresent(By by, int timeoutSeconds) {
        try {
            getWait(timeoutSeconds).until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean verifyElementPresent(By by, String message) {
        return verifyElementPresent(by, FrameworkConstants.WAIT_EXPLICIT, message);
    }

    protected boolean verifyElementPresent(By by, int timeoutSeconds, String message) {
        return verifyElementPresent(by, timeoutSeconds, message, FailureHandling.CONTINUE_ON_FAILURE);
    }

    protected boolean verifyElementPresent(By by, int timeoutSeconds, String message, FailureHandling flowControl) {
        return verifyTrue(verifyElementPresent(by, timeoutSeconds), message, flowControl);
    }

    // =========================================================================
    // 10. VERIFY — ELEMENT NOT PRESENT (không có trong DOM)
    // =========================================================================

    protected boolean verifyElementNotPresent(By by) {
        return verifyElementNotPresent(by, FrameworkConstants.WAIT_EXPLICIT);
    }

    protected boolean verifyElementNotPresent(By by, int timeoutSeconds) {
        try {
            getWait(timeoutSeconds).until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(by)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean verifyElementNotPresent(By by, String message) {
        return verifyElementNotPresent(by, FrameworkConstants.WAIT_EXPLICIT, message);
    }

    protected boolean verifyElementNotPresent(By by, int timeoutSeconds, String message) {
        return verifyElementNotPresent(by, timeoutSeconds, message, FailureHandling.CONTINUE_ON_FAILURE);
    }

    protected boolean verifyElementNotPresent(By by, int timeoutSeconds, String message, FailureHandling flowControl) {
        return verifyTrue(verifyElementNotPresent(by, timeoutSeconds), message, flowControl);
    }

    // =========================================================================
    // 11. VERIFY — ELEMENT VISIBLE (có trong DOM VÀ đang hiển thị)
    // =========================================================================

    protected boolean verifyElementVisible(By by) {
        return verifyElementVisible(by, FrameworkConstants.WAIT_EXPLICIT);
    }

    protected boolean verifyElementVisible(By by, int timeoutSeconds) {
        try {
            getWait(timeoutSeconds).until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean verifyElementVisible(By by, String message) {
        return verifyElementVisible(by, FrameworkConstants.WAIT_EXPLICIT, message);
    }

    protected boolean verifyElementVisible(By by, int timeoutSeconds, String message) {
        return verifyElementVisible(by, timeoutSeconds, message, FailureHandling.CONTINUE_ON_FAILURE);
    }

    protected boolean verifyElementVisible(By by, int timeoutSeconds, String message, FailureHandling flowControl) {
        return verifyTrue(verifyElementVisible(by, timeoutSeconds), message, flowControl);
    }

    // =========================================================================
    // 12. VERIFY — ELEMENT NOT VISIBLE (hidden hoặc không có trong DOM)
    // =========================================================================

    protected boolean verifyElementNotVisible(By by) {
        return verifyElementNotVisible(by, FrameworkConstants.WAIT_EXPLICIT);
    }

    protected boolean verifyElementNotVisible(By by, int timeoutSeconds) {
        try {
            getWait(timeoutSeconds).until(ExpectedConditions.invisibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean verifyElementNotVisible(By by, String message) {
        return verifyElementNotVisible(by, FrameworkConstants.WAIT_EXPLICIT, message);
    }

    protected boolean verifyElementNotVisible(By by, int timeoutSeconds, String message) {
        return verifyElementNotVisible(by, timeoutSeconds, message, FailureHandling.CONTINUE_ON_FAILURE);
    }

    protected boolean verifyElementNotVisible(By by, int timeoutSeconds, String message, FailureHandling flowControl) {
        return verifyTrue(verifyElementNotVisible(by, timeoutSeconds), message, flowControl);
    }

    // =========================================================================
    // 13. VERIFY — ELEMENT STATE & TEXT
    // =========================================================================

    protected boolean verifyElementDisabled(By by, int timeoutSeconds, String message, FailureHandling flowControl) {
        return verifyTrue(isElementDisabled(by, timeoutSeconds), message, flowControl);
    }

    @Step("Verify text của element {by} bằng [{text}]")
    protected boolean verifyElementText(By by, String text) {
        return verifyElementText(by, text, "Element " + by + " text không khớp.");
    }

    @Step("Verify text của element {by} bằng [{text}]: {message}")
    protected boolean verifyElementText(By by, String text, String message) {
        waitForElementVisible(by);
        String actualText = getTextElement(by);
        boolean result = actualText.equals(text.trim());
        LogUtils.info("Verify text element " + by + ": [" + actualText + "] == [" + text.trim() + "] → " + result);
        softAssert().assertTrue(result, message + " | Expected: [" + text + "], Actual: [" + actualText + "]");
        return result;
    }

    @Step("Verify text của element {by} bằng [{text}]")
    protected boolean verifyElementTextEquals(By by, String text) {
        return verifyElementText(by, text);
    }

    @Step("Verify text của element {by} bằng [{text}]")
    protected boolean verifyElementTextEquals(By by, String text, FailureHandling flowControl) {
        boolean result = verifyElementText(by, text);
        if (!result && flowControl == FailureHandling.STOP_ON_FAILURE) {
            Assert.fail("Element " + by + " text không khớp. Expected: [" + text + "], Actual: [" + getTextElement(by) + "]");
        }
        return result;
    }

    @Step("Verify text của element {by} chứa [{text}]")
    protected boolean verifyElementTextContains(By by, String text) {
        return verifyElementTextContains(by, text, FailureHandling.CONTINUE_ON_FAILURE);
    }

    @Step("Verify text của element {by} chứa [{text}]")
    protected boolean verifyElementTextContains(By by, String text, FailureHandling flowControl) {
        waitForElementVisible(by);
        String actualText = getTextElement(by);
        boolean result = actualText.contains(text.trim());
        LogUtils.info("Verify text element " + by + " contains: [" + actualText + "] has [" + text.trim() + "] → " + result);
        return verifyTrue(result, "Element " + by + " text không chứa. Expected: [" + text + "], Actual: [" + actualText + "]", flowControl);
    }

    // =========================================================================
    // 14. FILE DOWNLOAD VERIFICATION
    // =========================================================================

    @Step("Verify file tải về chứa tên: {fileName}")
    protected boolean verifyDownloadFileContainsName(String fileName, int timeoutSeconds) {
        LogUtils.info("Chờ tải file chứa tên: [" + fileName + "] (timeout: " + timeoutSeconds + "s)");
        boolean downloaded = FileUtils.verifyDownloadFileContainsName(fileName, timeoutSeconds);
        LogUtils.info("File [" + fileName + "] downloaded → " + downloaded);
        return downloaded;
    }

    @Step("Verify file tải về khớp tên: {fileName}")
    protected boolean verifyDownloadFileEqualsName(String fileName, int timeoutSeconds) {
        LogUtils.info("Chờ tải file khớp tên: [" + fileName + "] (timeout: " + timeoutSeconds + "s)");
        boolean downloaded = FileUtils.verifyDownloadFileEqualsName(fileName, timeoutSeconds);
        LogUtils.info("File [" + fileName + "] downloaded → " + downloaded);
        return downloaded;
    }

    // =========================================================================
    // 15. DOM & SCROLL HELPERS
    // =========================================================================

    protected WebElement getWebElement(By by) {
        return waitForElementVisible(by);
    }

    protected List<WebElement> getWebElements(By by) {
        return driver().findElements(by);
    }

    protected void scrollToElementAtBottom(By by) {
        WebElement el = waitForElementPresent(by);
        ((JavascriptExecutor) driver()).executeScript("arguments[0].scrollIntoView(false);", el);
    }

    protected void scrollToElementAtTop(By by) {
        WebElement el = waitForElementPresent(by);
        ((JavascriptExecutor) driver()).executeScript("arguments[0].scrollIntoView(true);", el);
    }

    // =========================================================================
    // 16. SLEEP (Chỉ dùng khi bắt buộc)
    // =========================================================================

    protected void sleep(double seconds) {
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected void sleep(int seconds) {
        sleep((double) seconds);
    }
}
