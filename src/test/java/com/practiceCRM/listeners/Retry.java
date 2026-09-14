package com.practiceCRM.listeners;

import com.practiceCRM.constants.FrameworkConstants;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Lớp Retry xử lý việc chạy lại các Test Case bị FAIL tự động.
 * Nó implement interface IRetryAnalyzer của TestNG.
 */
public class Retry implements IRetryAnalyzer {
    // Biến đếm số lần đã chạy lại
    private int count = 0;
    
    // Đọc số lần tối đa được phép chạy lại từ cấu hình (Ví dụ: RETRY_TEST_FAIL = 2)
    private static int maxTry = Integer.parseInt(FrameworkConstants.RETRY_TEST_FAIL);

    /**
     * Hàm này quyết định xem có chạy lại Test Case hay không.
     * @return true = chạy lại, false = không chạy lại.
     */
    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {                      // Nếu Test đang bị FAIL
            if (count < maxTry) {                            // Nếu chưa quá số lần chạy lại cho phép
                count++;                                     // Tăng biến đếm
                iTestResult.setStatus(ITestResult.FAILURE);  // Đánh dấu test là FAIL ở lần chạy hiện tại
                return true;                                 // Trả về true để TestNG chạy lại
            } else {
                iTestResult.setStatus(ITestResult.FAILURE);  // Nếu đã chạy lại hết số lần vẫn fail -> chốt là FAIL
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);      // Nếu test pass -> chốt là SUCCESS
        }
        return false;
    }
}