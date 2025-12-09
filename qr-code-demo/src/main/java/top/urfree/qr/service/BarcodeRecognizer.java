package top.urfree.qr.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import lombok.extern.slf4j.Slf4j;
import top.urfree.qr.model.ScanResult;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

/**
 * 条码识别器 - 使用ZXing识别二维码和条形码
 */
@Slf4j
public class BarcodeRecognizer {

    private final ImagePreprocessor preprocessor;
    private final MultiFormatReader reader;
    private static final String TEMP_BASE_DIR = "E:/temp";

    public BarcodeRecognizer() {
        this.preprocessor = new ImagePreprocessor();
        this.reader = new MultiFormatReader();

        // 创建临时目录
        new File(TEMP_BASE_DIR).mkdirs();

        // 配置识别参数
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        // 支持所有常见的条码格式
        hints.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(
                BarcodeFormat.QR_CODE,
                BarcodeFormat.DATA_MATRIX,
                BarcodeFormat.AZTEC,
                BarcodeFormat.PDF_417,
                BarcodeFormat.CODE_128,
                BarcodeFormat.CODE_39,
                BarcodeFormat.CODE_93,
                BarcodeFormat.EAN_13,
                BarcodeFormat.EAN_8,
                BarcodeFormat.UPC_A,
                BarcodeFormat.UPC_E,
                BarcodeFormat.ITF,
                BarcodeFormat.RSS_14,
                BarcodeFormat.RSS_EXPANDED
        ));
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        reader.setHints(hints);
    }

    /**
     * 识别图像中的条码
     * 策略: 每次都先截取右上角，然后进行识别和预处理，如果失败则旋转后重复
     */
    public ScanResult recognize(File imageFile) {
        ScanResult result = new ScanResult();
        result.setFileName(imageFile.getName());
        result.setFilePath(imageFile.getAbsolutePath());
        result.setRecognized(false);

        // 为当前图片创建处理过程目录
        String imageBaseName = getFileNameWithoutExtension(imageFile.getName());
        File processDir = new File(TEMP_BASE_DIR, imageBaseName);
        if (processDir.exists()) {
            // 清空已存在的目录
            deleteDirectory(processDir);
        }
        processDir.mkdirs();
        log.info("处理过程文件将保存到: {}", processDir.getAbsolutePath());

        try {
            log.info("开始处理文件: {}", imageFile.getName());

            // 复制原始文件
            copyOriginalFile(imageFile, processDir);

            // 尝试不同旋转角度: 0°, 90°, 180°, 270°
            int[] rotations = {0, 90, 180, 270};

            for (int angle : rotations) {
                log.debug("尝试旋转{}度", angle);

                // 对于每个角度，依次尝试：
                // 1. 截取右上角 -> 直接识别
                // 2. 截取右上角 -> 预处理 -> 识别

                Result decodeResult = tryRecognizeTopRightWithAngle(imageFile, angle, false, processDir);
                if (decodeResult != null) {
                    fillResult(result, decodeResult, angle);
                    return result;
                }

                // 尝试预处理后识别
                decodeResult = tryRecognizeTopRightWithAngle(imageFile, angle, true, processDir);
                if (decodeResult != null) {
                    fillResult(result, decodeResult, angle);
                    return result;
                }
            }

            result.setErrorMessage("未识别到条码");
            log.warn("未能识别文件: {}", imageFile.getName());

        } catch (Exception e) {
            result.setErrorMessage("处理异常: " + e.getMessage());
            log.error("处理文件失败: {}", imageFile.getName(), e);
        }

        return result;
    }

    /**
     * 尝试识别右上角区域（带旋转和可选预处理）
     *
     * @param imageFile 图像文件
     * @param angle 旋转角度 (0, 90, 180, 270)
     * @param preprocess 是否进行预处理
     * @param processDir 处理过程文件保存目录
     * @return 识别结果，失败返回null
     */
    private Result tryRecognizeTopRightWithAngle(File imageFile, int angle, boolean preprocess, File processDir) {
        try {
            BufferedImage image;
            String prefix = String.format("%02d_angle%d_%s_",
                    getStepNumber(angle, preprocess), angle, preprocess ? "preprocess" : "direct");

            // 步骤1: 如果需要旋转，先旋转原图
            File rotatedFile = null;
            if (angle != 0) {
                log.debug("  步骤1: 旋转图像{}度", angle);
                image = preprocessor.rotateImage(imageFile, angle);
                rotatedFile = new File(processDir, prefix + "1_rotated.png");
                javax.imageio.ImageIO.write(image, "png", rotatedFile);
            } else {
                rotatedFile = imageFile;
            }

            // 步骤2: 截取右上角区域
            log.debug("  步骤2: 截取右上角区域");
            BufferedImage croppedImage = preprocessor.cropTopRight(rotatedFile);
            File croppedFile = new File(processDir, prefix + "2_cropped.png");
            javax.imageio.ImageIO.write(croppedImage, "png", croppedFile);

            // 步骤3: 如果需要预处理，对截取的部分进行预处理
            BufferedImage finalImage;
            if (preprocess) {
                log.debug("  步骤3: 预处理截取的区域");
                finalImage = preprocessor.preprocessImage(croppedFile);
                File preprocessedFile = new File(processDir, prefix + "3_preprocessed.png");
                javax.imageio.ImageIO.write(finalImage, "png", preprocessedFile);
            } else {
                finalImage = croppedImage;
            }

            // 步骤4: 识别
            log.debug("  步骤4: 尝试识别 (angle={}, preprocess={})", angle, preprocess);
            Result result = decodeImage(finalImage);

            if (result != null) {
                log.info("  ✓ 识别成功! (旋转{}度, 预处理={})", angle, preprocess);
                // 保存识别结果说明
                saveRecognitionResult(processDir, prefix, result, angle, preprocess);
            }

            return result;

        } catch (Exception e) {
            log.debug("  识别失败 (angle={}, preprocess={}): {}", angle, preprocess, e.getMessage());
            return null;
        }
    }

    /**
     * 获取步骤编号
     */
    private int getStepNumber(int angle, boolean preprocess) {
        int base = 0;
        if (angle == 0) base = 0;
        else if (angle == 90) base = 2;
        else if (angle == 180) base = 4;
        else if (angle == 270) base = 6;
        return base + (preprocess ? 1 : 0);
    }

    /**
     * 复制原始文件
     */
    private void copyOriginalFile(File imageFile, File processDir) {
        try {
            BufferedImage original = javax.imageio.ImageIO.read(imageFile);
            File destFile = new File(processDir, "00_original.png");
            javax.imageio.ImageIO.write(original, "png", destFile);
            log.debug("已保存原始文件: {}", destFile.getName());
        } catch (Exception e) {
            log.warn("保存原始文件失败: {}", e.getMessage());
        }
    }

    /**
     * 保存识别结果
     */
    private void saveRecognitionResult(File processDir, String prefix, Result result, int angle, boolean preprocess) {
        try {
            File resultFile = new File(processDir, prefix + "4_SUCCESS.txt");
            String content = String.format(
                "识别成功!\n" +
                "旋转角度: %d度\n" +
                "预处理: %s\n" +
                "条码类型: %s\n" +
                "识别内容: %s\n",
                angle,
                preprocess ? "是" : "否",
                result.getBarcodeFormat(),
                result.getText()
            );
            java.nio.file.Files.write(resultFile.toPath(), content.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.warn("保存识别结果失败: {}", e.getMessage());
        }
    }

    /**
     * 获取不带扩展名的文件名
     */
    private String getFileNameWithoutExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }

    /**
     * 删除目录及其内容
     */
    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }

    /**
     * 解码BufferedImage
     */
    private Result decodeImage(BufferedImage image) {
        try {
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            return reader.decode(bitmap);
        } catch (NotFoundException e) {
            return null;
        }
    }

    /**
     * 填充识别结果
     */
    private void fillResult(ScanResult result, Result decodeResult, int angle) {
        result.setRecognized(true);
        result.setContent(decodeResult.getText());
        result.setBarcodeType(decodeResult.getBarcodeFormat().toString());
        result.setRotationAngle(angle);
        log.info("识别成功: {} - {} (旋转{}度)", result.getFileName(),
                result.getBarcodeType(), angle);
    }
}
