package com.practiceCRM.reports;

import com.practiceCRM.constants.FrameworkConstants;
import com.practiceCRM.helpers.SystemHelpers;
import com.practiceCRM.utils.LogUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import java.io.File;

public class TelegramManager {
    private static String Token = FrameworkConstants.TELEGRAM_TOKEN;
    private static String ChatId = FrameworkConstants.TELEGRAM_CHATID;
    private static TelegramBot bot = new TelegramBot(Token);
    private static File input = new File(FrameworkConstants.EXTENT_REPORT_FILE_PATH);

    public static boolean sendFilePath(String filePath) {
        boolean success = false;
        try {
            File input = new File(SystemHelpers.getCurrentDir() + filePath);
            SendDocument request = new SendDocument(ChatId, input).parseMode(ParseMode.HTML).disableNotification(true);
            SendResponse sendResponse = bot.execute(request);
            boolean ok = sendResponse.isOk();
            success = ok;
            if (ok != true) {
                Message message = sendResponse.message();
                LogUtils.warn("Message response from Telegram: " + message);
            }
        } catch (Exception e) {
            LogUtils.error("Error Send Report HTML to Telegram: " + e.getMessage());
        }
        return success;
    }

    private static boolean isReportSent = false;

    public static synchronized void sendReportPath() {
        if (!FrameworkConstants.SEND_REPORT_TO_TELEGRAM.equalsIgnoreCase(FrameworkConstants.YES)) {
            return;
        }
        if (isReportSent) {
            return; // Đã gửi rồi thì không gửi lại nữa
        }

        try {
            if (input.exists()) {
                SendDocument request = new SendDocument(ChatId, input)
                        .caption("📊 <b>Báo cáo chi tiết ExtentReports</b>")
                        .parseMode(ParseMode.HTML);
                SendResponse sendResponse = bot.execute(request);
                if (sendResponse.isOk()) {
                    isReportSent = true;
                    LogUtils.info("Đã gửi file báo cáo ExtentReports lên Telegram thành công.");
                } else {
                    LogUtils.warn("Phản hồi từ Telegram: " + sendResponse.message());
                }
            } else {
                LogUtils.warn("Không tìm thấy file báo cáo để gửi Telegram: " + input.getAbsolutePath());
            }
        } catch (Exception e) {
            LogUtils.error("Lỗi khi gửi báo cáo HTML lên Telegram: " + e.getMessage());
        }
    }

    public static void sendSummaryReport(int total, int passed, int failed, int skipped) {
        if (!FrameworkConstants.SEND_REPORT_TO_TELEGRAM.equalsIgnoreCase(FrameworkConstants.YES)) {
            return;
        }

        String statusEmoji = (failed == 0) ? "✅ PASS TOÀN BỘ" : "❌ CÓ TEST FAIL";
        String message = "🚀 <b>KẾT QUẢ AUTOMATION TEST CRM</b> 🚀\n\n"
                + "Trạng thái: <b>" + statusEmoji + "</b>\n"
                + "━━━━━━━━━━━━━━━━━━━\n"
                + "📊 Tổng số test cases: <b>" + total + "</b>\n"
                + "✅ Passed: <b>" + passed + "</b>\n"
                + "❌ Failed: <b>" + failed + "</b>\n"
                + "⚠️ Skipped: <b>" + skipped + "</b>\n"
                + "━━━━━━━━━━━━━━━━━━━\n"
                + "🔗 <a href=\"https://hieunb98.github.io/demo\">Xem Allure Report trực tuyến</a>";

        sendMessageText(message);
        sendReportPath();
    }

    // chỗ này check nếu gửi thành công thì xóa file luôn
    // public static boolean deleteReportFile() {
    // boolean success = false;
    // success = sendReportPath();
    // if (success == true) {
    // input.delete();
    // }
    // return success;
    // }

    public static boolean sendMessageText(String messageText) {
        SendMessage request = new SendMessage(ChatId, messageText).parseMode(ParseMode.HTML);
        SendResponse sendResponse = bot.execute(request);
        boolean ok = sendResponse.isOk();
        if (ok) {
            LogUtils.info("Send message to Telegram: " + messageText);
        } else {
            LogUtils.warn("Send message to Telegram failed: " + sendResponse.description());
        }
        return ok;
    }

}
