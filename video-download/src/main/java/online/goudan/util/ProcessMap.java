package online.goudan.util;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 刘成龙
 * @description
 * @date 2021/8/3 21:58
 */
public class ProcessMap {
    @Data
    public static class ProcessInfo {
        private Integer process;
        private String detail;
    }

    private static Map<String, ProcessInfo> downloadingMap = new ConcurrentHashMap<>();
    private static Map<String, ProcessInfo> waitDownloadMap = new ConcurrentHashMap<>();
    private static Map<String, ProcessInfo> downloadedMap = new ConcurrentHashMap<>();
    public static final String DOWNLOAD_WAIT = "等待下载";
    public static final String DOWNLOADING = "正在下载";
    public static final String DOWNLOADED = "下载完成";

    public static void addMap(String key) {
        ProcessInfo processInfo = new ProcessInfo();
        processInfo.setProcess(0);
        processInfo.setDetail("等待下载");
        waitDownloadMap.put(key, processInfo);
    }

    public static void changeDownloading(String key) {
        ProcessInfo processInfo = waitDownloadMap.remove(key);
        downloadingMap.put(key, processInfo);
    }

    public static void changeDownloaded(String key) {
        ProcessInfo processInfo = downloadingMap.remove(key);
        processInfo.setProcess(100);
        processInfo.setDetail("下载完成");
        downloadedMap.put(key, processInfo);
    }

    public static void downloading(String key, int process, String detail) {
        ProcessInfo processInfo = downloadingMap.get(key);
        processInfo.setProcess(process);
        processInfo.setDetail(detail);
    }

    public static Map<String, ProcessInfo> getDownloadingMap() {
        return downloadingMap;
    }

    public static Map<String, ProcessInfo> getWaitDownloadMap() {
        return waitDownloadMap;
    }

    public static Map<String, ProcessInfo> getDownloadedMap() {
        return downloadedMap;
    }

    public static boolean existDownload(String key) {
        return downloadedMap.containsKey(key);
    }
}
