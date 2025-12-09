package top.urfree.qr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 扫描结果模型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScanResult {
    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 是否识别成功
     */
    private boolean recognized;

    /**
     * 识别到的内容
     */
    private String content;

    /**
     * 条码类型 (QR_CODE, CODE_128, EAN_13等)
     */
    private String barcodeType;

    /**
     * 旋转角度 (0, 90, 180, 270)
     */
    private int rotationAngle;

    /**
     * 错误信息
     */
    private String errorMessage;
}
