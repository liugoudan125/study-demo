package top.urfree.qr.service;

import lombok.extern.slf4j.Slf4j;
import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

/**
 * 图像预处理器 - 使用OpenCV增强图像质量
 */
@Slf4j
public class ImagePreprocessor {

    static {
        // 加载OpenCV本地库
        OpenCV.loadLocally();
    }

    /**
     * 预处理图像以提高识别率
     */
    public BufferedImage preprocessImage(File imageFile) throws IOException {
        // 读取图像
        Mat src = Imgcodecs.imread(imageFile.getAbsolutePath());
        if (src.empty()) {
            throw new IOException("无法读取图像: " + imageFile.getName());
        }

        // 转换为灰度图
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        // 自适应二值化
        Mat binary = new Mat();
        Imgproc.adaptiveThreshold(gray, binary, 255,
                Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                Imgproc.THRESH_BINARY, 11, 2);

        // 降噪
        Mat denoised = new Mat();
        Imgproc.medianBlur(binary, denoised, 3);

        // 转换回BufferedImage
        BufferedImage result = matToBufferedImage(denoised);

        // 释放资源
        src.release();
        gray.release();
        binary.release();
        denoised.release();

        return result;
    }

    /**
     * 旋转图像
     */
    public BufferedImage rotateImage(File imageFile, int angle) throws IOException {
        Mat src = Imgcodecs.imread(imageFile.getAbsolutePath());
        if (src.empty()) {
            throw new IOException("无法读取图像: " + imageFile.getName());
        }

        Mat rotated = new Mat();

        switch (angle) {
            case 90:
                Core.rotate(src, rotated, Core.ROTATE_90_CLOCKWISE);
                break;
            case 180:
                Core.rotate(src, rotated, Core.ROTATE_180);
                break;
            case 270:
                Core.rotate(src, rotated, Core.ROTATE_90_COUNTERCLOCKWISE);
                break;
            default:
                rotated = src.clone();
        }

        BufferedImage result = matToBufferedImage(rotated);

        src.release();
        rotated.release();

        return result;
    }

    /**
     * 裁剪右上角区域（二维码通常在此位置）并放大1.5倍
     */
    public BufferedImage cropTopRight(File imageFile) throws IOException {
        Mat src = Imgcodecs.imread(imageFile.getAbsolutePath(), Imgcodecs.IMREAD_COLOR);
        if (src.empty()) {
            throw new IOException("无法读取图像: " + imageFile.getName());
        }

        // 裁剪右上角 1/3 区域
        int width = src.cols();
        int height = src.rows();
        int cropWidth = width / 3;
        int cropHeight = height / 6;
        int x = width - cropWidth;
        int y = 0;

        Rect roi = new Rect(x, y, cropWidth, cropHeight);
        Mat cropped = new Mat(src, roi).clone(); // clone 确保内存独立

        // 1.5倍放大，使用高质量插值
        Mat enlarged = new Mat();
        Size newSize = new Size(cropped.cols() * 1.5, cropped.rows() * 1.5);
        // 使用 INTER_CUBIC 三次插值，保证图像质量
        Imgproc.resize(cropped, enlarged, newSize, 0, 0, Imgproc.INTER_CUBIC);

        BufferedImage result = matToBufferedImage(enlarged);

        src.release();
        cropped.release();
        enlarged.release();

        return result;
    }

    /**
     * Mat转BufferedImage (保持高质量)
     */
    private BufferedImage matToBufferedImage(Mat mat) {
        // 根据通道数选择合适的图像类型
        int type;
        if (mat.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (mat.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        } else if (mat.channels() == 4) {
            type = BufferedImage.TYPE_4BYTE_ABGR;
        } else {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] buffer = new byte[bufferSize];
        mat.get(0, 0, buffer);

        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);

        return image;
    }
}