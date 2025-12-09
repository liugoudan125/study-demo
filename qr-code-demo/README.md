# 二维码/条形码识别工具

基于 **ZXing + OpenCV** 的二维码和条形码批量识别工具,专门用于处理 A4 纸质扫描件。

## 功能特点

- ✅ 支持多种条码格式:QR_CODE、CODE_128、EAN_13 等
- ✅ 自动处理旋转(0°、90°、180°、270°)
- ✅ 智能定位右上角区域
- ✅ OpenCV 图像预处理增强识别率
- ✅ 批量处理目录下所有图片
- ✅ JSON 格式输出结果

## 环境要求

- JDK 21
- Maven 3.6+
- IDEA (推荐)

## 使用方法 (IDEA)

### 1. 运行主程序

直接在 IDEA 中运行 `QRCodeScanner.java`:
- 右键点击 `QRCodeScanner.java`
- 选择 "Run 'QRCodeScanner.main()'"
- 根据控制台提示输入图像目录路径

### 2. 配置运行参数 (可选)

如果想直接传入目录路径,可以配置 Program arguments:
1. 点击 Run -> Edit Configurations
2. 在 Program arguments 中输入: `D:\images`
3. 点击 OK,然后运行

### 3. 查看结果

程序会在项目根目录的 `output` 目录下生成:
- `all_results.json` - 完整的识别结果
- `recognized.json` - 已识别的文件列表
- `unrecognized.json` - 未识别的文件列表

### 4. 查看处理过程 (可选)

程序会在 `E:/temp` 目录下为每个图片创建一个子目录,保存所有处理步骤的中间文件:
```
E:/temp/
├── scan001/          # 图片1的处理过程
│   ├── 00_original.png
│   ├── 01_angle0_direct_2_cropped.png
│   └── 01_angle0_direct_4_SUCCESS.txt
├── scan002/          # 图片2的处理过程
│   ├── 00_original.png
│   ├── 01_angle0_direct_2_cropped.png
│   ├── ...
│   └── 04_angle90_preprocess_4_SUCCESS.txt
└── ...
```

详细说明请参考: [处理过程文件说明](PROCESS_FILES.md)

## 输出格式示例

### all_results.json
```json
{
  "totalFiles": 10,
  "recognizedCount": 8,
  "unrecognizedCount": 2,
  "recognizedFiles": [
    {
      "fileName": "scan001.jpg",
      "filePath": "D:\\images\\scan001.jpg",
      "recognized": true,
      "content": "https://example.com/product/12345",
      "barcodeType": "QR_CODE",
      "rotationAngle": 0,
      "errorMessage": null
    }
  ],
  "unrecognizedFiles": [
    {
      "fileName": "scan002.jpg",
      "filePath": "D:\\images\\scan002.jpg",
      "recognized": false,
      "content": null,
      "barcodeType": null,
      "rotationAngle": 0,
      "errorMessage": "未识别到条码"
    }
  ]
}
```

## 识别策略

程序专门针对 A4 纸质扫描件进行优化,采用右上角定位策略:

### 核心流程
对每个图像文件,按以下顺序尝试识别(0° → 90° → 180° → 270°):

**对于每个旋转角度:**
1. **旋转原图** (如果角度非0°)
2. **截取右上角区域** (1/3 宽度 × 1/6 高度)
3. **高质量放大1.5倍** (使用三次插值,保持图像清晰度)
4. **直接识别截取的区域**
5. **如果失败,对截取的区域进行预处理** (灰度化、二值化、降噪)
6. **识别预处理后的区域**
7. **如果成功则返回,否则尝试下一个角度**

### 详细步骤
```
原始图像
  ↓
旋转 0° → 截取右上角 → 1.5倍放大 → 识别 ✓/✗
  ↓ (失败)                        ↓ (失败)
  ↓                             预处理 → 识别 ✓/✗
  ↓ (失败)
旋转 90° → 截取右上角 → 1.5倍放大 → 识别 ✓/✗
  ↓ (失败)                        ↓ (失败)
  ↓                             预处理 → 识别 ✓/✗
  ↓ (失败)
旋转 180° → 截取右上角 → 1.5倍放大 → 识别 ✓/✗
  ↓ (失败)                        ↓ (失败)
  ↓                             预处理 → 识别 ✓/✗
  ↓ (失败)
旋转 270° → 截取右上角 → 1.5倍放大 → 识别 ✓/✗
  ↓ (失败)                        ↓ (失败)
  ↓                             预处理 → 识别 ✓/✗
  ↓ (失败)
未识别到条码
```

### 优势
- ✅ **精确定位**: 每次都先定位到右上角,提高识别效率
- ✅ **多角度支持**: 自动处理扫描件旋转问题
- ✅ **高质量放大**: 使用三次插值算法放大1.5倍,保持图像清晰
- ✅ **智能预处理**: 对截取的区域进行有针对性的图像增强
- ✅ **资源优化**: 只处理右上角区域,速度更快

## 支持的图像格式

- JPG/JPEG
- PNG
- BMP
- GIF
- TIFF/TIF

## 依赖库

- **ZXing 3.5.3** - 条码识别核心库
- **OpenCV 4.9.0** - 图像预处理
- **Gson 2.10.1** - JSON 处理
- **Lombok 1.18.30** - 代码简化
- **Logback 1.4.11** - 日志记录

## 日志

程序运行日志会保存在 `logs/qr-scanner.log` 文件中。

## 注意事项

1. 确保图像文件清晰,分辨率足够
2. 二维码/条形码应尽量在右上角位置
3. 对于识别率低的情况,可以手动调整图像质量
4. 大批量处理时注意内存占用

## 故障排除

如果识别率较低,可以尝试:

- 提高扫描分辨率(建议 300 DPI 以上)
- 确保扫描件对比度清晰
- 检查二维码/条形码是否损坏或模糊
- 调整 `ImagePreprocessor` 中的预处理参数

---

## 之前的工具集(基于 Zxing 和 BoofCV)

基于 **Zxing** 和 **BoofCV** 实现的高识别率二维码/条形码识别工具集。

## 📦 依赖库

- **Zxing 3.5.3** - 通用二维码/条形码识别库
- **BoofCV 1.1.5** - 计算机视觉库，提供高级图像处理和识别功能

## 🚀 功能特性

### 1. Zxing工具类 (`QrCodeReader`)

✅ 支持多种格式
- 二维码: QR_CODE, DATA_MATRIX, PDF_417, AZTEC
- 条形码: EAN_13, EAN_8, CODE_128, CODE_39, UPC_A, UPC_E

✅ 基础功能
- 从文件路径/File/InputStream/BufferedImage识别
- 获取详细识别结果（内容、格式、错误信息）
- 识别多个二维码/条形码

### 2. BoofCV基础工具类 (`BoofCvQrCodeReader`)

✅ 专注二维码识别
- 更好的识别率，特别是模糊、倾斜场景
- 支持多个二维码同时识别
- 提供详细信息（版本、纠错级别、角点坐标等）

### 3. BoofCV高级工具类 (`AdvancedQrCodeReader`)

✅ 智能识别模式
- 自动尝试多种图像增强策略
- 针对复杂场景优化（光照不均、模糊、噪点等）

✅ 多种增强策略
- 直方图均衡化 - 光照不均
- 自适应阈值 - 对比度低
- 锐化增强 - 图片模糊
- 降噪处理 - 有噪点干扰

✅ 图像质量分析
- 评估亮度、对比度
- 判断是否需要预处理

### 4. 性能对比工具 (`QrCodeReaderComparison`)

✅ 对比测试
- Zxing vs BoofCV标准 vs BoofCV智能
- 成功率和耗时统计
- 自动生成推荐方案

## 📖 使用示例

### 基础识别 (Zxing)

```java
// 简单识别
String content = QrCodeReader.decode("path/to/qrcode.png");
System.out.println("内容: " + content);

// 获取详细信息
QrCodeReader.DecodeResult result = QrCodeReader.decodeWithDetails("path/to/qrcode.png");
if (result.isSuccess()) {
    System.out.println("内容: " + result.getContent());
    System.out.println("格式: " + result.getFormat());
}

// 识别多个
List<String> contents = QrCodeReader.decodeMultiple("path/to/multiple.png");
```

### BoofCV识别（更高识别率）

```java
// 基础识别
String content = BoofCvQrCodeReader.decode("path/to/qrcode.png");

// 获取详细信息（版本、纠错级别、角点坐标）
BoofCvQrCodeReader.DecodeResult result =
    BoofCvQrCodeReader.decodeWithDetails("path/to/qrcode.png");

if (result.isSuccess()) {
    System.out.println("内容: " + result.getContent());
    System.out.println("版本: " + result.getVersion());
    System.out.println("纠错级别: " + result.getErrorCorrection());
    System.out.println("角点坐标: " + result.getCornerPoints());
}

// 识别多个二维码
List<String> contents = BoofCvQrCodeReader.decodeMultiple("path/to/multiple.png");
```

### 智能识别模式（复杂场景）

```java
// 自动尝试多种策略
String content = AdvancedQrCodeReader.decodeWithSmartMode("path/to/difficult.png");

// 针对性增强
BufferedImage image = ImageIO.read(new File("path/to/qrcode.png"));

// 光照不均 -> 直方图均衡化
String result1 = AdvancedQrCodeReader.decodeWithHistogramEqualization(image);

// 图片模糊 -> 锐化
String result2 = AdvancedQrCodeReader.decodeWithSharpening(image);

// 对比度低 -> 自适应阈值
String result3 = AdvancedQrCodeReader.decodeWithAdaptiveThreshold(image);

// 有噪点 -> 降噪
String result4 = AdvancedQrCodeReader.decodeWithDenoising(image);
```

### 图像质量分析

```java
BufferedImage image = ImageIO.read(new File("path/to/qrcode.png"));
AdvancedQrCodeReader.ImageQualityInfo quality =
    AdvancedQrCodeReader.analyzeImageQuality(image);

System.out.println(quality);

if (quality.needsEnhancement()) {
    System.out.println("建议使用智能模式");
    String content = AdvancedQrCodeReader.decodeWithSmartMode(image);
} else {
    System.out.println("使用标准模式即可");
    String content = BoofCvQrCodeReader.decode(image);
}
```

### 性能对比

```java
// 单个文件对比
QrCodeReaderComparison.ComparisonResult result =
    QrCodeReaderComparison.compare("path/to/qrcode.png");
System.out.println(result);

// 批量对比
List<String> files = Arrays.asList("file1.png", "file2.png", "file3.png");
List<QrCodeReaderComparison.ComparisonResult> results =
    QrCodeReaderComparison.compareBatch(files);

// 生成统计报告
String report = QrCodeReaderComparison.generateReport(results);
System.out.println(report);

// 对比整个目录
List<QrCodeReaderComparison.ComparisonResult> dirResults =
    QrCodeReaderComparison.compareDirectory("path/to/images");
```

## 🎯 使用场景建议

| 场景 | 推荐方案 | 说明 |
|------|----------|------|
| 标准二维码/条形码 | `QrCodeReader` (Zxing) | 速度快，格式支持全面 |
| 纯二维码识别 | `BoofCvQrCodeReader` | 识别率更高 |
| 光照不均 | `AdvancedQrCodeReader.decodeWithHistogramEqualization()` | 直方图均衡化 |
| 图片模糊 | `AdvancedQrCodeReader.decodeWithSharpening()` | 锐化增强 |
| 对比度低 | `AdvancedQrCodeReader.decodeWithAdaptiveThreshold()` | 自适应阈值 |
| 有噪点 | `AdvancedQrCodeReader.decodeWithDenoising()` | 降噪处理 |
| 不确定/复杂 | `AdvancedQrCodeReader.decodeWithSmartMode()` | 自动尝试所有策略 |
| 性能评估 | `QrCodeReaderComparison` | 对比测试选最优 |

## 📊 性能特点

### Zxing
- ✅ 速度快（通常 < 50ms）
- ✅ 格式支持全面
- ⚠️ 复杂场景识别率一般

### BoofCV标准
- ✅ 二维码识别率高
- ✅ 速度适中（通常 50-100ms）
- ⚠️ 仅支持二维码

### BoofCV智能
- ✅ 识别率最高
- ✅ 适合复杂场景
- ⚠️ 耗时较长（100-300ms）

## 🔧 Maven依赖

```xml
<dependencies>
    <!-- Zxing -->
    <dependency>
        <groupId>com.google.zxing</groupId>
        <artifactId>core</artifactId>
        <version>3.5.3</version>
    </dependency>
    <dependency>
        <groupId>com.google.zxing</groupId>
        <artifactId>javase</artifactId>
        <version>3.5.3</version>
    </dependency>

    <!-- BoofCV -->
    <dependency>
        <groupId>org.boofcv</groupId>
        <artifactId>boofcv-core</artifactId>
        <version>1.1.5</version>
    </dependency>
    <dependency>
        <groupId>org.boofcv</groupId>
        <artifactId>boofcv-ip</artifactId>
        <version>1.1.5</version>
    </dependency>
    <dependency>
        <groupId>org.boofcv</groupId>
        <artifactId>boofcv-io</artifactId>
        <version>1.1.5</version>
    </dependency>
    <dependency>
        <groupId>org.boofcv</groupId>
        <artifactId>boofcv-recognition</artifactId>
        <version>1.1.5</version>
    </dependency>
</dependencies>
```

## 💡 最佳实践

1. **优先使用快速方法**: 先尝试 `QrCodeReader` 或 `BoofCvQrCodeReader.decode()`
2. **失败时升级策略**: 如果失败，再使用 `AdvancedQrCodeReader.decodeWithSmartMode()`
3. **批量处理先评估**: 使用 `QrCodeReaderComparison` 评估后选择最优方案
4. **质量预检**: 使用 `analyzeImageQuality()` 判断是否需要预处理
5. **针对性优化**: 根据具体问题（模糊/暗/噪点）选择对应增强方法

## 📝 注意事项

- BoofCV目前主要支持QR码，不支持条形码
- 智能模式会尝试多种策略，耗时较长，适合离线处理
- 图像预处理可能改变内容，建议先用原图尝试
- 大批量处理建议使用性能对比工具找到最优方案

## 🎓 示例代码

完整示例代码请参考:
- `QrCodeReaderExample.java` - Zxing使用示例
- `BoofCvQrCodeExample.java` - BoofCV使用示例

## 📄 License

MIT License
