package top.urfree.qr.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * 输出结果模型
 */
@Data
public class OutputResult {
    /**
     * 总文件数
     */
    private int totalFiles;

    /**
     * 已识别文件数
     */
    private int recognizedCount;

    /**
     * 未识别文件数
     */
    private int unrecognizedCount;

    /**
     * 已识别的文件列表
     */
    private List<ScanResult> recognizedFiles = new ArrayList<>();

    /**
     * 未识别的文件列表
     */
    private List<ScanResult> unrecognizedFiles = new ArrayList<>();
}
