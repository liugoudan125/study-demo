package top.urfree.qr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import top.urfree.qr.model.OutputResult;
import top.urfree.qr.model.ScanResult;
import top.urfree.qr.service.BarcodeRecognizer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 主程序 - 批量识别目录下的图像文件
 */
@Slf4j
public class QRCodeScanner {

    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList(
            ".jpg", ".jpeg", ".png", ".bmp", ".gif", ".tiff", ".tif"
    );

    private static final String OUTPUT_DIR = "output";

    public static void main(String[] args) {
        log.info("=== 二维码/条形码识别工具 ===");

        String inputDir = getInputDirectory(args);
        if (inputDir == null) {
            return;
        }

        File directory = new File(inputDir);
        if (!directory.exists() || !directory.isDirectory()) {
            log.error("目录不存在或不是有效目录: {}", inputDir);
            return;
        }

        // 创建输出目录
        createOutputDirectory();

        // 获取所有图像文件
        File[] imageFiles = directory.listFiles(file ->
                file.isFile() && isImageFile(file.getName()));

        if (imageFiles == null || imageFiles.length == 0) {
            log.warn("目录中没有找到图像文件: {}", inputDir);
            return;
        }

        log.info("找到 {} 个图像文件", imageFiles.length);

        // 执行识别
        OutputResult outputResult = processImages(imageFiles);

        // 保存结果
        saveResults(outputResult);

        // 打印统计信息
        printSummary(outputResult);
    }

    /**
     * 获取输入目录
     */
    private static String getInputDirectory(String[] args) {
        if (args.length > 0) {
            return args[0];
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入图像目录路径: ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            log.error("未提供目录路径");
            return null;
        }

        return input;
    }

    /**
     * 创建输出目录
     */
    private static void createOutputDirectory() {
        File outputDir = new File(OUTPUT_DIR);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
            log.info("创建输出目录: {}", outputDir.getAbsolutePath());
        }
    }

    /**
     * 判断是否为图像文件
     */
    private static boolean isImageFile(String filename) {
        String lowerName = filename.toLowerCase();
        return IMAGE_EXTENSIONS.stream().anyMatch(lowerName::endsWith);
    }

    /**
     * 处理所有图像文件
     */
    private static OutputResult processImages(File[] imageFiles) {
        OutputResult result = new OutputResult();
        result.setTotalFiles(imageFiles.length);

        BarcodeRecognizer recognizer = new BarcodeRecognizer();

        int processed = 0;
        for (File imageFile : imageFiles) {
            processed++;
            log.info("处理进度: {}/{}", processed, imageFiles.length);

            ScanResult scanResult = recognizer.recognize(imageFile);

            if (scanResult.isRecognized()) {
                result.getRecognizedFiles().add(scanResult);
            } else {
                result.getUnrecognizedFiles().add(scanResult);
            }
        }

        result.setRecognizedCount(result.getRecognizedFiles().size());
        result.setUnrecognizedCount(result.getUnrecognizedFiles().size());

        return result;
    }

    /**
     * 保存结果到JSON文件
     */
    private static void saveResults(OutputResult result) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();

        try {
            // 保存完整结果
            saveJsonFile("all_results.json", result, gson);

            // 保存已识别结果
            if (!result.getRecognizedFiles().isEmpty()) {
                saveJsonFile("recognized.json", result.getRecognizedFiles(), gson);
            }

            // 保存未识别结果
            if (!result.getUnrecognizedFiles().isEmpty()) {
                saveJsonFile("unrecognized.json", result.getUnrecognizedFiles(), gson);
            }

            log.info("结果已保存到 {} 目录", OUTPUT_DIR);

        } catch (IOException e) {
            log.error("保存结果失败", e);
        }
    }

    /**
     * 保存JSON文件
     */
    private static void saveJsonFile(String filename, Object data, Gson gson) throws IOException {
        Path filePath = Paths.get(OUTPUT_DIR, filename);
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            gson.toJson(data, writer);
            log.info("已保存: {}", filePath.toAbsolutePath());
        }
    }

    /**
     * 打印统计信息
     */
    private static void printSummary(OutputResult result) {
        log.info("=== 识别完成 ===");
        log.info("总文件数: {}", result.getTotalFiles());
        log.info("已识别: {} ({:.2f}%)",
                result.getRecognizedCount(),
                result.getTotalFiles() > 0 ?
                        (result.getRecognizedCount() * 100.0 / result.getTotalFiles()) : 0);
        log.info("未识别: {} ({:.2f}%)",
                result.getUnrecognizedCount(),
                result.getTotalFiles() > 0 ?
                        (result.getUnrecognizedCount() * 100.0 / result.getTotalFiles()) : 0);

        if (!result.getRecognizedFiles().isEmpty()) {
            log.info("\n已识别文件:");
            for (ScanResult scan : result.getRecognizedFiles()) {
                log.info("  - {} [{}] (旋转{}度): {}",
                        scan.getFileName(),
                        scan.getBarcodeType(),
                        scan.getRotationAngle(),
                        scan.getContent());
            }
        }

        if (!result.getUnrecognizedFiles().isEmpty()) {
            log.info("\n未识别文件:");
            for (ScanResult scan : result.getUnrecognizedFiles()) {
                log.info("  - {}: {}",
                        scan.getFileName(),
                        scan.getErrorMessage());
            }
        }
    }
}
