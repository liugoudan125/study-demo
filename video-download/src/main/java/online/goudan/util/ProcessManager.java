package online.goudan.util;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 刘成龙
 * @date 2021/6/24 10:54
 * @desc ProcessManager
 */
public class ProcessManager {
    private String dirPath;
    private String fileName;
    private FileOutputStream outputStream;
    private int process;
    private final static int PROCESS_MAX = 100;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");


    private ProcessManager(String dirPath, String fileName) throws IOException {
        this.dirPath = dirPath;
        this.fileName = fileName;
        File processFile = new File(dirPath, fileName + ".log");
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!processFile.exists()) {
            if (!processFile.createNewFile()) {
                System.out.println("创建processManager失败");
                return;
            }
        }
        outputStream = new FileOutputStream(processFile, true);
    }

    @Nullable
    public static ProcessManager createProcessManager(String dirPath, String fileName) {
        try {
            return new ProcessManager(dirPath, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建processManager失败");
        }
        return null;
    }

    public synchronized void setProcess(int process) {
        if (this.process != Math.min(process, PROCESS_MAX)) {
            this.process = process;
            changeProcessFile();
        }
    }

    public synchronized void addMessage(String message) {
        try {
            outputStream.write(String.format("[%s] message: %s%n", simpleDateFormat.format(new Date()), message).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println("添加消息失败");
        }
    }

    private void changeProcessFile() {
        try {
            outputStream.write(String.format("[%s] 已下载: %3d%%%n", simpleDateFormat.format(new Date()), process).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println("process更新失败");
        }
    }


    public void close() {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
