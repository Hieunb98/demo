package com.practiceCRM.listeners;

import static com.practiceCRM.constants.FrameworkConstants.*;

import java.awt.GraphicsEnvironment;
import java.util.Hashtable;
import java.util.Set;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.practiceCRM.projects.crm.models.LogInModel;
import com.practiceCRM.annotations.FrameworkAnnotation;
import com.practiceCRM.constants.FrameworkConstants;
import com.practiceCRM.driver.DriverManager;
import com.practiceCRM.enums.AuthorType;
import com.practiceCRM.enums.CategoryType;
import com.practiceCRM.helpers.CaptureHelpers;
import com.practiceCRM.helpers.FileHelpers;
import com.practiceCRM.helpers.PropertiesHelpers;
import com.practiceCRM.helpers.ScreenRecorderHelpers;
import com.practiceCRM.keywords.WebUI;
import com.practiceCRM.reports.ExtentReportManager;
import com.practiceCRM.reports.TelegramManager;
import com.practiceCRM.utils.BrowserInfoUtils;
import com.practiceCRM.utils.EmailSendUtils;
import com.practiceCRM.utils.LogUtils;
import com.practiceCRM.utils.ZipUtils;
import com.aventstack.extentreports.Status;
import com.github.automatedowl.tools.AllureEnvironmentWriter;
import com.google.common.collect.ImmutableMap;

/**
 * TestListener là "Trái tim" của hệ thống báo cáo (Reporting).
 * Nó lắng nghe mọi sự kiện xảy ra trong quá trình chạy test của TestNG
 * (như: trước khi chạy suite, khi bắt đầu 1 test case, khi pass, khi fail...).
 * Tại mỗi sự kiện, nó sẽ thực hiện các hành động tương ứng: ghi log, chụp ảnh,
 * quay video.
 */
public class TestListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

   static int count_totalTCs;
   static int count_passedTCs;
   static int count_skippedTCs;
   static int count_failedTCs;

   private ScreenRecorderHelpers screenRecorder;

   public TestListener() {
      try {
         boolean enableRecord = VIDEO_RECORD != null && VIDEO_RECORD.toLowerCase().trim().equals(YES);

         if (enableRecord && !GraphicsEnvironment.isHeadless()) {
            screenRecorder = new ScreenRecorderHelpers();
            LogUtils.info("Screen recorder initialized.");
         } else {
            LogUtils.info("Skip screen recorder: VIDEO_RECORD=" + VIDEO_RECORD +
                  ", Headless=" + GraphicsEnvironment.isHeadless());
         }
      } catch (Exception e) {
         LogUtils.error("Failed to init ScreenRecorder: " + e.getMessage());
      }
   }

   public String getTestName(ITestResult result) {
      // Ưu tiên lấy tên test case từ file Excel (DataProvider)
      Object[] parameters = result.getParameters();
      if (parameters != null && parameters.length > 0 && parameters[0] instanceof java.util.Hashtable) {
         try {
            Hashtable<String, String> data = (Hashtable<String, String>) parameters[0];
            if (data.containsKey(LogInModel.getTestCaseName())) {
               return data.get(LogInModel.getTestCaseName());
            }
         } catch (Exception e) {
            // Fallback nếu có lỗi
         }
      }
      return result.getTestName() != null ? result.getTestName()
            : result.getMethod().getConstructorOrMethod().getName();
   }

   public String getTestDescription(ITestResult result) {
      return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
   }

   @Override
   public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
      // Before every method in the Test Class
      // System.out.println(method.getTestMethod().getMethodName());
   }

   @Override
   public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
      // After every method in the Test Class
      // System.out.println(method.getTestMethod().getMethodName());
   }

   /**
    * Sự kiện: TRƯỚC KHI BẮT ĐẦU chạy toàn bộ các Test trong 1 file XML (Suite).
    */
   @Override
   public void onStart(ISuite iSuite) {
      LogUtils.info("********** RUN STARTED **********");
      LogUtils.info("========= INSTALLING CONFIGURATION DATA =========");

      // Tải cấu hình từ các file .properties
      PropertiesHelpers.loadAllFiles();
      // Khởi tạo file report HTML (ExtentReport)
      ExtentReportManager.initReports();
      // Tự động dọn sạch thư mục tải về trước khi chạy Suite
      WebUI.cleanDownloadDirectory();
      LogUtils.info("========= INSTALLED CONFIGURATION DATA =========");
      LogUtils.info("=====> Starting Suite: " + iSuite.getName());
   }

   /**
    * Sự kiện: SAU KHI CHẠY XONG toàn bộ các Test trong 1 file XML (Suite).
    */

   @Override
   public void onFinish(ISuite iSuite) {
      LogUtils.info("********** RUN FINISHED **********");
      LogUtils.info("=====> End Suite: " + iSuite.getName());

      // Bắt buộc gọi hàm flush() để ghi (lưu) kết quả vào file ExtentReport HTML
      ExtentReportManager.flushReports();

      // Nén thư mục report lại thành đuôi .zip
      ZipUtils.zipReportFolder();

      // Gửi đường dẫn file qua Telegram tự động
      TelegramManager.sendReportPath();

      // Gửi email báo cáo số lượng PASS/FAIL
      EmailSendUtils.sendEmail(count_totalTCs, count_passedTCs, count_failedTCs, count_skippedTCs);

      // Determine Browsers
      Set<String> browsers = new java.util.HashSet<>();
      for (org.testng.xml.XmlTest test : iSuite.getXmlSuite().getTests()) {
         String browser = test.getParameter("BROWSER");
         if (browser != null && !browser.isEmpty()) {
            browsers.add(browser.toUpperCase());
         }
      }
      if (browsers.isEmpty() && FrameworkConstants.BROWSER != null && !FrameworkConstants.BROWSER.isEmpty()) {
         browsers.add(String.valueOf(FrameworkConstants.BROWSER).toUpperCase());
      }
      String browserNames = String.join(", ", browsers);

      // Determine URLs dynamically
      ImmutableMap.Builder<String, String> builder = ImmutableMap.<String, String>builder();
      String suiteName = iSuite.getName().toLowerCase();
      if (suiteName.contains("crm")) {
         builder.put("URL CRM", FrameworkConstants.URL_CRM);
      } else if (suiteName.contains("cms")) {
         builder.put("URL CMS Admin", FrameworkConstants.URL_CMS_ADMIN);
         builder.put("URL CMS User", FrameworkConstants.URL_CMS_USER);
      } else {
         builder.put("URL CRM", FrameworkConstants.URL_CRM);
         builder.put("URL CMS Admin", FrameworkConstants.URL_CMS_ADMIN);
         builder.put("URL CMS User", FrameworkConstants.URL_CMS_USER);
      }

      builder.put("Operating System", BrowserInfoUtils.getOSInfo())
            .put("Java Version", System.getProperty("java.version"))
            .put("Target Execution", FrameworkConstants.TARGET)
            .put("Explicit Timeout", String.valueOf(FrameworkConstants.WAIT_EXPLICIT))
            .put("Page Load Timeout", String.valueOf(FrameworkConstants.WAIT_PAGE_LOADED))
            .put("Headless Mode", FrameworkConstants.HEADLESS)
            .put("Local Browser", browserNames)
            .put("Remote URL", FrameworkConstants.REMOTE_URL)
            .put("Remote Port", FrameworkConstants.REMOTE_PORT)
            .put("TCs Total", String.valueOf(count_totalTCs))
            .put("TCs Passed", String.valueOf(count_passedTCs))
            .put("TCs Skipped", String.valueOf(count_skippedTCs))
            .put("TCs Failed", String.valueOf(count_failedTCs));

      AllureEnvironmentWriter.allureEnvironmentWriter(builder.build());

      // FileHelpers.copyFile("src/test/resources/config/allure/environment.xml",
      // "target/allure-results/environment.xml");
      FileHelpers.copyFile("src/test/resources/config/allure/categories.json", "target/allure-results/categories.json");
      setAllureExecutorInformation();
   }

   /**
    * Đọc thông tin từ file config executor.json mẫu, cập nhật các trường động nếu chạy trên GitHub Actions CI/CD,
    * rồi ghi vào target/allure-results/executor.json.
    */
   /**
    * Tự động sinh file executor.json để hiển thị widget EXECUTORS trên Allure Report.
    * Tự động 100% nhận diện thông tin từ GitHub Actions CI/CD hoặc môi trường Local.
    */
   private void setAllureExecutorInformation() {
      try {
         String isGitHubActions = System.getenv("GITHUB_ACTIONS");
         String name;
         String type;
         String url = "";
         long buildOrder = 1;
         String buildName;
         String buildUrl = "";
         String reportUrl = "";
         String reportName = "Allure Report | CRM-Automation-Framework";

         if ("true".equalsIgnoreCase(isGitHubActions)) {
            name = "GitHub Actions";
            type = "github";

            String serverUrl = System.getenv("GITHUB_SERVER_URL");
            if (serverUrl == null || serverUrl.isEmpty()) {
               serverUrl = "https://github.com";
            }
            String repo = System.getenv("GITHUB_REPOSITORY"); // Ví dụ: owner/repository-name
            String runId = System.getenv("GITHUB_RUN_ID");
            String runNumber = System.getenv("GITHUB_RUN_NUMBER");
            String actor = System.getenv("GITHUB_ACTOR");

            if (repo != null && !repo.isEmpty()) {
               url = serverUrl + "/" + repo;
               buildUrl = (runId != null && !runId.isEmpty()) ? url + "/actions/runs/" + runId : url + "/actions";

               // Tự động suy ra URL của GitHub Pages: https://<owner>.github.io/<repo-name>
               String[] parts = repo.split("/");
               if (parts.length == 2) {
                  reportUrl = "https://" + parts[0].toLowerCase() + ".github.io/" + parts[1];
               }
            }

            if (runNumber != null && !runNumber.isEmpty()) {
               try {
                  buildOrder = Long.parseLong(runNumber.trim());
               } catch (NumberFormatException ignored) {}
               buildName = "Run #" + runNumber + (actor != null ? " (" + actor + ")" : "");
            } else {
               buildName = "GitHub Actions Run";
            }
         } else {
            name = "Local Machine";
            type = "custom";
            buildName = "Local Run (" + System.getProperty("user.name") + ")";
         }

         java.util.Map<String, Object> executorData = new java.util.LinkedHashMap<>();
         executorData.put("name", name);
         executorData.put("type", type);
         executorData.put("url", url);
         executorData.put("buildOrder", buildOrder);
         executorData.put("buildName", buildName);
         executorData.put("buildUrl", buildUrl);
         executorData.put("reportUrl", reportUrl);
         executorData.put("reportName", reportName);

         java.io.File targetDir = new java.io.File("target/allure-results");
         if (!targetDir.exists()) {
            targetDir.mkdirs();
         }
         java.io.File executorFile = new java.io.File(targetDir, "executor.json");
         new com.fasterxml.jackson.databind.ObjectMapper()
               .writerWithDefaultPrettyPrinter()
               .writeValue(executorFile, executorData);
         LogUtils.info("Đã sinh thông tin executor.json cho Allure Report thành công.");
      } catch (Exception e) {
         LogUtils.warn("Không thể tạo file executor.json: " + e.getMessage());
      }
   }

   public AuthorType[] getAuthorType(ITestResult iTestResult) {
      if (iTestResult.getMethod().getConstructorOrMethod().getMethod()
            .getAnnotation(FrameworkAnnotation.class) == null) {
         return null;
      }
      AuthorType authorType[] = iTestResult.getMethod().getConstructorOrMethod().getMethod()
            .getAnnotation(FrameworkAnnotation.class).author();
      return authorType;
   }

   public CategoryType[] getCategoryType(ITestResult iTestResult) {
      if (iTestResult.getMethod().getConstructorOrMethod().getMethod()
            .getAnnotation(FrameworkAnnotation.class) == null) {
         return null;
      }
      CategoryType categoryType[] = iTestResult.getMethod().getConstructorOrMethod().getMethod()
            .getAnnotation(FrameworkAnnotation.class).category();
      return categoryType;
   }

   /**
    * Sự kiện: TRƯỚC KHI BẮT ĐẦU 1 Test Case.
    */
   @Override
   public void onTestStart(ITestResult iTestResult) {
      LogUtils.info("Test case: " + getTestName(iTestResult) + " is starting...");
      count_totalTCs = count_totalTCs + 1;

      // Tạo một block (node) trong ExtentReport cho Test Case này
      ExtentReportManager.createTest(iTestResult.getName());
      ExtentReportManager.addAuthors(getAuthorType(iTestResult));
      ExtentReportManager.addCategories(getCategoryType(iTestResult));
      ExtentReportManager.addDevices();
      ExtentReportManager.info(BrowserInfoUtils.getOSInfo());

      // Kích hoạt quay video nếu cấu hình = yes
      if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
         screenRecorder.startRecording(getTestName(iTestResult));
      }
   }

   /**
    * Sự kiện: KHI 1 TEST CASE PASS (THÀNH CÔNG).
    */
   @Override
   public void onTestSuccess(ITestResult iTestResult) {
      LogUtils.info("Test case: " + getTestName(iTestResult) + " is passed.");
      count_passedTCs = count_passedTCs + 1;

      // Chụp ảnh màn hình nếu cấu hình chụp khi PASS là yes
      if (DriverManager.getDriver() != null && SCREENSHOT_PASSED_TCS.equals(YES)) {
         CaptureHelpers.captureScreenshot(DriverManager.getDriver(), getTestName(iTestResult), "Success");
         ExtentReportManager.addScreenShot(Status.PASS, getTestName(iTestResult));
      }

      ExtentReportManager.logMessage(Status.PASS, "Test case: " + getTestName(iTestResult) + " is passed.");

      if (VIDEO_RECORD.trim().toLowerCase().equals(YES)) {
         WebUI.sleep(2);
         screenRecorder.stopRecording(true);
      }
   }

   /**
    * Sự kiện: KHI 1 TEST CASE FAIL (THẤT BẠI).
    */
   @Override
   public void onTestFailure(ITestResult iTestResult) {
      LogUtils.error("FAILED !! Test case " + getTestName(iTestResult) + " is failed.");
      LogUtils.error(iTestResult.getThrowable()); // In lỗi stacktrace

      count_failedTCs = count_failedTCs + 1;

      // Luôn luôn nên chụp ảnh màn hình khi FAIL để debug
      if (DriverManager.getDriver() != null && SCREENSHOT_FAILED_TCS.equals(YES)) {
         CaptureHelpers.captureScreenshot(DriverManager.getDriver(), getTestName(iTestResult), "Failed");
         ExtentReportManager.addScreenShot(Status.FAIL, getTestName(iTestResult)); // Đính kèm ảnh vào Report
      }

      // Ghi lỗi vào ExtentReport
      ExtentReportManager.logMessage(Status.FAIL, iTestResult.getThrowable().toString());

      // Dừng quay video nếu cấu hình = yes
      if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
         WebUI.sleep(2);
         screenRecorder.stopRecording(true);
      }
   }

   @Override
   public void onTestSkipped(ITestResult iTestResult) {
      LogUtils.warn("WARNING!! Test case: " + getTestName(iTestResult) + " is skipped.");
      count_skippedTCs = count_skippedTCs + 1;

      if (DriverManager.getDriver() != null && SCREENSHOT_SKIPPED_TCS.equals(YES)) {
         CaptureHelpers.captureScreenshot(DriverManager.getDriver(), getTestName(iTestResult), "Skipped");
      }

      ExtentReportManager.logMessage(Status.SKIP, "Test case: " + getTestName(iTestResult) + " is skipped.");

      if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
         screenRecorder.stopRecording(true);
      }
   }

   @Override
   public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
      ExtentReportManager.logMessage("Test failed but it is in defined success ratio: " + getTestName(iTestResult));
   }

}
