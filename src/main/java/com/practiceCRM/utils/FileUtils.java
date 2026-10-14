package com.practiceCRM.utils;

import java.io.File;

/**
 * FileUtils — Tiện ích xử lý file/thư mục của framework.
 * Tách biệt khỏi WebUI để Core Engine không phụ thuộc vào tầng keyword.
 */
public final class FileUtils {

    private FileUtils() {}

    /**
     * Lấy đường dẫn tuyệt đối tới thư mục tải về mặc định (target/downloads).
     * Tự động tạo thư mục nếu chưa tồn tại.
     */
    public static String getPathDownloadDirectory() {
        String downloadPath = System.getProperty("user.dir")
                + File.separator + "target"
                + File.separator + "downloads";
        File dir = new File(downloadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return downloadPath;
    }

    /**
     * Xóa toàn bộ file trong thư mục tải về (giữ nguyên thư mục).
     * Thường gọi trong onStart() của TestListener trước khi chạy Suite.
     */
    public static void cleanDownloadDirectory() {
        try {
            String path = getPathDownloadDirectory();
            File folder = new File(path);
            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File f : files) {
                        if (f.isFile()) {
                            f.delete();
                        }
                    }
                }
            }
            LogUtils.info("🧹 Cleaned download directory: " + path);
        } catch (Exception e) {
            LogUtils.warn("Cannot clean download directory: " + e.getMessage());
        }
    }

    /**
     * Kiểm tra có file nào trong thư mục tải về chứa chuỗi fileName hay không.
     */
    public static boolean verifyFileContainsInDownloadDirectory(String fileName) {
        try {
            String pathFolderDownload = getPathDownloadDirectory();
            File dir = new File(pathFolderDownload);
            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                return false;
            }
            for (File f : files) {
                if (f.isFile() && f.getName().contains(fileName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            LogUtils.warn("Error checking file contains in download directory: " + e.getMessage());
        }
        return false;
    }

    /**
     * Kiểm tra có file nào trong thư mục tải về khớp chính xác fileName hay không.
     */
    public static boolean verifyFileEqualsInDownloadDirectory(String fileName) {
        try {
            String pathFolderDownload = getPathDownloadDirectory();
            File dir = new File(pathFolderDownload);
            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                return false;
            }
            for (File f : files) {
                if (f.isFile() && f.getName().equals(fileName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            LogUtils.warn("Error checking file equals in download directory: " + e.getMessage());
        }
        return false;
    }

    /**
     * Polling kiểm tra file tải về chứa chuỗi fileName trong khoảng timeoutSeconds.
     */
    public static boolean verifyDownloadFileContainsName(String fileName, int timeoutSeconds) {
        long startTime = System.currentTimeMillis();
        long maxDuration = (long) timeoutSeconds * 1000;
        while (System.currentTimeMillis() - startTime < maxDuration) {
            if (verifyFileContainsInDownloadDirectory(fileName)) {
                return true;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }

    /**
     * Polling kiểm tra file tải về khớp tên fileName trong khoảng timeoutSeconds.
     */
    public static boolean verifyDownloadFileEqualsName(String fileName, int timeoutSeconds) {
        long startTime = System.currentTimeMillis();
        long maxDuration = (long) timeoutSeconds * 1000;
        while (System.currentTimeMillis() - startTime < maxDuration) {
            if (verifyFileEqualsInDownloadDirectory(fileName)) {
                return true;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }
}
