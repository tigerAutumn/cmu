package cc.newex.dax.users.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

@Slf4j
public class VerificationCodeUtils {
    private static final Random RANDOM = new Random();

    public VerificationCodeUtils() {
    }

    /**
     * 获取4位长度的随机码
     *
     * @return
     */

    public static String generate4RandomCode() {
        return generateRandomCode(4);
    }

    /**
     * 获取6位长度的随机码
     *
     * @return
     */

    public static String generate6RandomCode() {
        return generateRandomCode(6);
    }

    public static String generate8RandomCode() {
        return generate8RandomCode(8);
    }

    public static String generate8RandomCode(final int length) {
        final String[] codeStr = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(codeStr[RANDOM.nextInt(codeStr.length)]);
        }
        return sb.toString();
    }

    public static String generateRandomCode(final int length) {
        final String[] codeStr = new String[]{
                "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
        };

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(codeStr[RANDOM.nextInt(codeStr.length)]);
        }
        return sb.toString();
    }

    public static Color createsRandomColor() {
        final int r = (new Double(Math.random() * 256.0D)).intValue();
        final int g = (new Double(Math.random() * 256.0D)).intValue();
        final int b = (new Double(Math.random() * 256.0D)).intValue();
        return new Color(r, g, b);
    }

    public static Object[] getImageCode(final int length) {
        final int width = 120;
        final int height = 40;
        final BufferedImage image = new BufferedImage(width, height, 1);
        final Graphics2D g = (Graphics2D) image.getGraphics();
        final String code = generateRandomCode(length);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final Color[] colors = new Color[5];
        final Color[] colorSpaces = new Color[]{
                Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color
                .MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW
        };
        final float[] fractions = new float[colors.length];

        for (int i = 0; i < colors.length; ++i) {
            colors[i] = colorSpaces[RANDOM.nextInt(colorSpaces.length)];
            fractions[i] = RANDOM.nextFloat();
        }

        Arrays.sort(fractions);
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);
        final Color c = getRandColor(200, 250);
        g.setColor(c);
        g.fillRect(0, 2, width, height - 4);
        g.setColor(getRandColor(160, 200));

        int area;
        int fontSize;
        int x;
        int y;
        for (int i = 0; i < 20; ++i) {
            area = RANDOM.nextInt(width - 1);
            fontSize = RANDOM.nextInt(height - 1);
            x = RANDOM.nextInt(6) + 1;
            y = RANDOM.nextInt(12) + 1;
            g.drawLine(area, fontSize + 5, area + x + 40, fontSize + y + 15);
        }

        final float yawpRate = 0.05F;
        area = (int) (yawpRate * (float) width * (float) height);

        int i;
        for (fontSize = 0; fontSize < area; ++fontSize) {
            x = RANDOM.nextInt(width);
            y = RANDOM.nextInt(height);
            i = getRandomIntColor();
            image.setRGB(x, y, i);
        }

        shear(g, width, height, c);
        g.setColor(getRandColor(100, 160));
        fontSize = height - 4;
        final Font font = new Font("Algerian", 2, fontSize);
        g.setFont(font);
        final char[] chars = code.toCharArray();

        for (i = 0; i < 4; ++i) {
            final AffineTransform affine = new AffineTransform();
            affine.setToRotation(
                    0.7853981633974483D * RANDOM.nextDouble() * (double) (RANDOM.nextBoolean() ? 1 : -1),
                    (double) (width / 4 * i + fontSize / 2), (double) (height / 2)
            );
            g.setTransform(affine);
            g.drawChars(chars, i, 1, (width - 10) / 4 * i + 5, height / 2 + fontSize / 2 - 10);
        }

        g.dispose();
        return new Object[]{image, code};
    }

    private static Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }

        if (bc > 255) {
            bc = 255;
        }

        final int r = fc + RANDOM.nextInt(bc - fc);
        final int g = fc + RANDOM.nextInt(bc - fc);
        final int b = fc + RANDOM.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private static int getRandomIntColor() {
        final int[] rgb = getRandomRgb();
        int color = 0;
        final int[] var2 = rgb;
        final int var3 = rgb.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            final int c = var2[var4];
            color <<= 8;
            color |= c;
        }

        return color;
    }

    private static int[] getRandomRgb() {
        final int[] rgb = new int[3];

        for (int i = 0; i < 3; ++i) {
            rgb[i] = RANDOM.nextInt(255);
        }

        return rgb;
    }

    private static void shear(final Graphics g, final int w1, final int h1, final Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private static void shearX(final Graphics g, final int w1, final int h1, final Color color) {
        final int period = RANDOM.nextInt(2);
        final boolean borderGap = true;
        final int frames = 1;
        final int phase = RANDOM.nextInt(2);

        for (int i = 0; i < h1; ++i) {
            final double d = (double) (period >> 1) * Math.sin((double) i / (double) period + 6.283185307179586D * (double) phase / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    private static void shearY(final Graphics g, final int w1, final int h1, final Color color) {
        final int period = RANDOM.nextInt(40) + 10;
        final boolean borderGap = true;
        final int frames = 20;
        final int phase = 7;

        for (int i = 0; i < w1; ++i) {
            final double d = (double) (period >> 1) * Math.sin((double) i / (double) period + 6.283185307179586D * (double) phase / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }

    /**
     * 获取n位随机验证码
     *
     * @param len
     * @return
     */
    public static String getRandNumber(final int len) {
        final StringBuilder sb = new StringBuilder();

        // 首位不能是0
        sb.append(RandomUtils.nextInt(1, 10));

        for (int i = 1; i < len; i++) {
            sb.append(RandomUtils.nextInt(0, 10));
        }

        return sb.toString();
    }

    public static void main(final String[] args) {
        System.out.println(generate8RandomCode());
    }
}
