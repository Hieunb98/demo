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
    private static TelegramBot bot;
    private static File input = new File(FrameworkConstants.EXTENT_REPORT_FILE_PATH);
    private static final String TELEGRAM_SENT_FLAG_PATH = "target/.telegram_sent";

    private static synchronized TelegramBot getBot() {
        if (bot == null) {
            String token = FrameworkConstants.TELEGRAM_TOKEN;
            if (token != null && !token.trim().isEmpty()) {
                bot = new TelegramBot(token.trim());
            }
        }
        return bot;
    }

    private static String getChatId() {
        return FrameworkConstants.TELEGRAM_CHATID;
    }

    public static boolean sendFilePath(String filePath) {
        TelegramBot currentBot = getBot();
        String chatId = getChatId();
        if (currentBot == null || chatId == null || chatId.trim().isEmpty()) {
            LogUtils.warn("Chưa cấu hình TELEGRAM_TOKEN hoặc TELEGRAM_CHATID, bỏ qua gửi Telegram.");
            return false;
        }

        boolean success = false;
        try {
            File input = new File(SystemHelpers.getCurrentDir() + filePath);
            SendDocument request = new SendDocument(chatId, input).parseMode(ParseMode.HTML).disableNotification(true);
            SendResponse sendResponse = currentBot.execute(request);
            boolean ok = sendResponse.isOk();
            success = ok;
            if (!ok) {
                Message message = sendResponse.message();
                LogUtils.warn("Message response from Telegram: " + message);
            }
        } catch (Exception e) {
            LogUtils.error("Error Send Report HTML to Telegram: " + e.getMessage());
        }
        return success;
    }

    public static synchronized void sendReportPath() {
        if (!FrameworkConstants.SEND_REPORT_TO_TELEGRAM.equalsIgnoreCase(FrameworkConstants.YES)) {
            return;
        }

        File flagFile = new File(TELEGRAM_SENT_FLAG_PATH);
        if (flagFile.exists()) {
            return; // Đã gửi trong lượt chạy này rồi, bỏ qua
        }

        TelegramBot currentBot = getBot();
        String chatId = getChatId();
        if (currentBot == null || chatId == null || chatId.trim().isEmpty()) {
            LogUtils.warn("Chưa cấu hình TELEGRAM_TOKEN hoặc TELEGRAM_CHATID, không gửi file Telegram.");
            return;
        }

        boolean sent = sendReportFileInternal(currentBot, chatId);
        if (sent) {
            markAsSent();
        }
    }

    public static synchronized void sendSummaryReport(int total, int passed, int failed, int skipped) {
        if (!FrameworkConstants.SEND_REPORT_TO_TELEGRAM.equalsIgnoreCase(FrameworkConstants.YES)) {
            return;
        }

        File flagFile = new File(TELEGRAM_SENT_FLAG_PATH);
        if (flagFile.exists()) {
            return; // Đã gửi trong lượt chạy này rồi, không gửi lại
        }

        TelegramBot currentBot = getBot();
        String chatId = getChatId();
        if (currentBot == null || chatId == null || chatId.trim().isEmpty()) {
            LogUtils.warn("Chưa cấu hình TELEGRAM_TOKEN hoặc TELEGRAM_CHATID, không gửi tin nhắn Telegram.");
            return;
        }

        String runNumber = System.getenv("GITHUB_RUN_NUMBER");
        String actor = System.getenv("GITHUB_ACTOR");
        String branch = System.getenv("GITHUB_REF_NAME");
        String currentTime = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());

        String buildInfo;
        if (runNumber != null && !runNumber.isEmpty()) {
            buildInfo = "Build #" + runNumber + (branch != null ? " (nhánh " + branch + ")" : "");
        } else {
            buildInfo = "Local Run (" + System.getProperty("user.name") + ")";
        }

        String actorInfo = (actor != null && !actor.isEmpty()) ? actor : System.getProperty("user.name");
        String browserInfo = (FrameworkConstants.BROWSER != null ? FrameworkConstants.BROWSER : "Chrome")
                + ("true".equalsIgnoreCase(FrameworkConstants.HEADLESS) ? " (Headless)" : " (Headed)");

        String statusEmoji = (failed == 0) ? "✅ PASS TOÀN BỘ" : "❌ CÓ " + failed + " TEST FAIL";

        String message = "🚀 <b>KẾT QUẢ AUTOMATION TEST CRM</b> 🚀\n\n"
                + "🔖 Lần chạy: <b>" + buildInfo + "</b>\n"
                + "👤 Người chạy: <b>" + actorInfo + "</b>\n"
                + "🕒 Thời gian: <b>" + currentTime + "</b>\n"
                + "🌐 Môi trường: <b>" + browserInfo + "</b>\n"
                + "━━━━━━━━━━━━━━━━━━━\n"
                + "Trạng thái: <b>" + statusEmoji + "</b>\n"
                + "📊 Tổng số test cases: <b>" + total + "</b>\n"
                + "✅ Passed: <b>" + passed + "</b> | ❌ Failed: <b>" + failed + "</b> | ⚠️ Skipped: <b>" + skipped + "</b>\n"
                + "━━━━━━━━━━━━━━━━━━━\n"
                + "🔗 <a href=\"https://hieunb98.github.io/demo\">Xem Allure Report trực tuyến</a>";

        // 1. Gửi tin nhắn tóm tắt
        sendMessageText(message);

        // 2. Gửi kèm file báo cáo ExtentReports
        sendReportFileInternal(currentBot, chatId);

        // 3. Đánh dấu đã gửi xong cả tin nhắn lẫn file báo cáo
        markAsSent();
    }

    private static boolean sendReportFileInternal(TelegramBot currentBot, String chatId) {
        try {
            if (input.exists()) {
                SendDocument request = new SendDocument(chatId, input)
                        .caption("📊 <b>Báo cáo chi tiết ExtentReports</b>")
                        .parseMode(ParseMode.HTML);
                SendResponse sendResponse = currentBot.execute(request);
                if (sendResponse.isOk()) {
                    LogUtils.info("Đã gửi file báo cáo ExtentReports lên Telegram thành công.");
                    return true;
                } else {
                    LogUtils.warn("Phản hồi từ Telegram: " + sendResponse.message());
                }
            } else {
                LogUtils.warn("Không tìm thấy file báo cáo để gửi Telegram: " + input.getAbsolutePath());
            }
        } catch (Exception e) {
            LogUtils.error("Lỗi khi gửi báo cáo HTML lên Telegram: " + e.getMessage());
        }
        return false;
    }

    private static void markAsSent() {
        try {
            File flagFile = new File(TELEGRAM_SENT_FLAG_PATH);
            flagFile.getParentFile().mkdirs();
            flagFile.createNewFile();
        } catch (Exception ignored) {}
    }

    public static boolean sendMessageText(String messageText) {
        TelegramBot currentBot = getBot();
        String chatId = getChatId();
        if (currentBot == null || chatId == null || chatId.trim().isEmpty()) {
            LogUtils.warn("Chưa cấu hình TELEGRAM_TOKEN hoặc TELEGRAM_CHATID, không gửi tin nhắn Telegram.");
            return false;
        }

        SendMessage request = new SendMessage(chatId, messageText).parseMode(ParseMode.HTML);
        SendResponse sendResponse = currentBot.execute(request);
        boolean ok = sendResponse.isOk();
        if (ok) {
            LogUtils.info("Send message to Telegram: " + messageText);
        } else {
            LogUtils.warn("Send message to Telegram failed: " + sendResponse.description());
        }
        return ok;
    }

}
