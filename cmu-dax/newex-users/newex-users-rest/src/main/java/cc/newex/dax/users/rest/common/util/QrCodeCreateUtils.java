package cc.newex.dax.users.rest.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 二维码生成和读的工具类
 *
 * @author newex-team
 * @date 2018/8/27
 */

public class QrCodeCreateUtils {

    /**
     * 生成包含字符串信息的二维码图片
     *
     * @param outputStream 文件输出流
     * @param contents      二维码携带信息
     * @param width        二维码图片宽度
     * @param height        二维码图片长度
     * @param imageFormat  二维码的格式
     */
    public static void createQrCode(final OutputStream outputStream, final String contents, final int width,
                                    final int height, final String imageFormat) throws WriterException, IOException {
        //设置二维码纠错级别MAP
        final Map<EncodeHintType, Object> hintMap = new ConcurrentHashMap<>(16);
        // 矫错级别
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hintMap.put(EncodeHintType.MARGIN, 0);
        final Integer qrVersion = choiseVersion(contents, width, height);
        if (qrVersion != null) {
            hintMap.put(EncodeHintType.QR_VERSION, qrVersion);
        }

        final QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //创建比特矩阵(位矩阵)的QR码编码的字符串
        final BitMatrix byteMatrix = qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, width, height, hintMap);
        // 使BufferedImage勾画QRCode  (matrixWidth 是行二维码像素点)
        final int matrixWidth = byteMatrix.getWidth();
        final int matrixHeight = byteMatrix.getHeight();
        final BufferedImage image = new BufferedImage(matrixWidth, matrixHeight, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        final Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixHeight);
        // 使用比特矩阵画并保存图像
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixHeight; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        ImageIO.write(image, imageFormat, outputStream);
    }

    /**
     * outputStream转inputStream
     */
    public static ByteArrayInputStream parse(final ByteArrayOutputStream out) {
        return new ByteArrayInputStream(out.toByteArray());
    }

    /**
     * 计算出一个最合适的version，使得空白边框宽度最小
     */
    private static Integer choiseVersion(final String contents, final int width, final int height) throws WriterException {
        Integer version = null;
        int minPadding = Integer.MAX_VALUE;
        for (int versionNum = 1; versionNum <= 40; versionNum++) {
            //设置二维码纠错级别MAP
            final Map<EncodeHintType, Object> hintMap = new ConcurrentHashMap<>(16);
            // 矫错级别
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hintMap.put(EncodeHintType.MARGIN, 0);
            hintMap.put(EncodeHintType.QR_VERSION, versionNum);

            try {
                final QRCode code = Encoder.encode(contents, ErrorCorrectionLevel.L, hintMap);
                final int padding = countPadding(code, width, height, 0);
                if (padding < minPadding) {
                    if (padding == 0) {
                        return versionNum;
                    }
                    minPadding = padding;
                    version = versionNum;
                }
            } catch (final Exception e) {
                //ignore
                continue;
            }
        }
        return version;
    }

    private static int countPadding(final QRCode code, final int width, final int height, final int quietZone) {
        final ByteMatrix input = code.getMatrix();
        if (input == null) {
            throw new IllegalStateException();
        }
        final int inputWidth = input.getWidth();
        final int inputHeight = input.getHeight();
        final int qrWidth = inputWidth + (quietZone * 2);
        final int qrHeight = inputHeight + (quietZone * 2);
        final int outputWidth = Math.max(width, qrWidth);
        final int outputHeight = Math.max(height, qrHeight);

        final int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        // Padding includes both the quiet zone and the extra white pixels to accommodate the requested
        // dimensions. For example, if input is 25x25 the QR will be 33x33 including the quiet zone.
        // If the requested size is 200x160, the multiple will be 4, for a QR of 132x132. These will
        // handle all the padding from 100x100 (the actual QR) up to 200x160.
        final int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
        final int topPadding = (outputHeight - (inputHeight * multiple)) / 2;
        return Math.max(leftPadding, topPadding);
    }
}
