package top.urfree.qr;

import lombok.extern.slf4j.Slf4j;
import top.urfree.qr.model.ScanResult;
import top.urfree.qr.service.BarcodeRecognizer;

import java.io.File;

/**
 * 使用示例
 */
@Slf4j
public class Example {

    public static void main(String[] args) {
        // 示例1: 识别单个文件
        singleFileExample();

        // 示例2: 批量识别需要运行主程序 QRCodeScanner
        log.info("\n批量识别请运行主程序:");
        log.info("java -cp \"target/qr-code-demo-1.0-SNAPSHOT.jar;target/lib/*\" top.urfree.qr.QRCodeScanner [目录路径]");
    }

    /**
     * 单个文件识别示例
     */
    private static void singleFileExample() {
        log.info("=== 单个文件识别示例 ===");

        // 替换为你的图像文件路径
        String imagePath = "test-images/sample-qrcode.png";
        File imageFile = new File(imagePath);

        if (!imageFile.exists()) {
            log.warn("示例文件不存在: {}", imagePath);
            log.info("请将图像文件放在项目根目录下的 test-images 文件夹中");
            return;
        }

        BarcodeRecognizer recognizer = new BarcodeRecognizer();
        ScanResult result = recognizer.recognize(imageFile);

        if (result.isRecognized()) {
            log.info("识别成功!");
            log.info("  文件名: {}", result.getFileName());
            log.info("  条码类型: {}", result.getBarcodeType());
            log.info("  内容: {}", result.getContent());
            log.info("  旋转角度: {}°", result.getRotationAngle());
        } else {
            log.warn("识别失败!");
            log.warn("  文件名: {}", result.getFileName());
            log.warn("  错误信息: {}", result.getErrorMessage());
        }
    }
}
