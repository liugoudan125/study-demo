# 处理过程文件说明

## 目录结构

程序会在 `E:/temp` 目录下为每个处理的图片创建一个子目录,目录名为图片的文件名(不含扩展名)。

例如,处理 `scan001.jpg` 时,会创建目录:
```
E:/temp/scan001/
```

## 文件命名规则

### 格式
```
[步骤编号]_angle[角度]_[处理方式]_[阶段].png
```

### 说明

- **步骤编号**: 两位数字,表示尝试的顺序
  - `00` - 原始文件
  - `01` - 0度,直接识别
  - `02` - 0度,预处理后识别
  - `03` - 90度,直接识别
  - `04` - 90度,预处理后识别
  - `05` - 180度,直接识别
  - `06` - 180度,预处理后识别
  - `07` - 270度,直接识别
  - `08` - 270度,预处理后识别

- **角度**: 图像旋转的角度 (0, 90, 180, 270)

- **处理方式**:
  - `direct` - 直接识别,不预处理
  - `preprocess` - 预处理后识别

- **阶段**:
  - `1_rotated` - 旋转后的图像
  - `2_cropped` - 截取右上角后的图像
  - `3_preprocessed` - 预处理后的图像(仅预处理模式)
  - `4_SUCCESS` - 识别成功的结果文件(txt格式)

## 示例

### 示例1: 0度直接识别成功

```
E:/temp/scan001/
├── 00_original.png                          # 原始文件
├── 01_angle0_direct_2_cropped.png          # 0度截取右上角
└── 01_angle0_direct_4_SUCCESS.txt          # 识别成功
```

### 示例2: 90度预处理后识别成功

```
E:/temp/scan002/
├── 00_original.png                          # 原始文件
├── 01_angle0_direct_2_cropped.png          # 0度直接识别(失败)
├── 02_angle0_preprocess_2_cropped.png      # 0度预处理(失败)
├── 02_angle0_preprocess_3_preprocessed.png
├── 03_angle90_direct_1_rotated.png         # 90度旋转
├── 03_angle90_direct_2_cropped.png         # 90度截取右上角(失败)
├── 04_angle90_preprocess_1_rotated.png     # 90度旋转
├── 04_angle90_preprocess_2_cropped.png     # 90度截取右上角
├── 04_angle90_preprocess_3_preprocessed.png # 90度预处理
└── 04_angle90_preprocess_4_SUCCESS.txt      # 识别成功!
```

### 示例3: 所有尝试都失败

```
E:/temp/scan003/
├── 00_original.png
├── 01_angle0_direct_2_cropped.png
├── 02_angle0_preprocess_2_cropped.png
├── 02_angle0_preprocess_3_preprocessed.png
├── 03_angle90_direct_1_rotated.png
├── 03_angle90_direct_2_cropped.png
├── 04_angle90_preprocess_1_rotated.png
├── 04_angle90_preprocess_2_cropped.png
├── 04_angle90_preprocess_3_preprocessed.png
├── 05_angle180_direct_1_rotated.png
├── 05_angle180_direct_2_cropped.png
├── 06_angle180_preprocess_1_rotated.png
├── 06_angle180_preprocess_2_cropped.png
├── 06_angle180_preprocess_3_preprocessed.png
├── 07_angle270_direct_1_rotated.png
├── 07_angle270_direct_2_cropped.png
├── 08_angle270_preprocess_1_rotated.png
├── 08_angle270_preprocess_2_cropped.png
└── 08_angle270_preprocess_3_preprocessed.png
```

## SUCCESS.txt 文件内容

识别成功时会生成一个文本文件,包含以下信息:

```
识别成功!
旋转角度: 90度
预处理: 是
条码类型: QR_CODE
识别内容: https://example.com/product/12345
```

## 用途

通过查看这些处理过程文件,你可以:

1. **调试识别失败的原因** - 查看每一步的处理结果
2. **优化参数** - 根据图像质量调整预处理参数
3. **分析识别成功的条件** - 了解哪种处理方式最有效
4. **验证识别结果** - 确认识别的二维码/条形码是否正确

## 注意事项

1. 每次处理同一图片时,会先清空之前的处理过程文件
2. 处理过程文件可能占用较大磁盘空间,建议定期清理
3. 文件按步骤编号排序,方便按顺序查看处理过程
