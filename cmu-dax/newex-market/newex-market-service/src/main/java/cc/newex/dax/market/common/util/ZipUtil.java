package cc.newex.dax.market.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 解压缩字符串工具类
 *
 * @author newex-team
 */
@Slf4j
public class ZipUtil {
    /**
     * 使用zip进行压缩
     *
     * @param str 压缩前的文本
     * @return 返回压缩后的文本
     */
    public static final String zip(final String str) {
        if (str == null) {
            return null;
        }
        final byte[] compressed;
        ByteArrayOutputStream out = null;
        ZipOutputStream zout = null;
        String compressedStr = null;
        try {
            out = new ByteArrayOutputStream();
            zout = new ZipOutputStream(out);
            zout.putNextEntry(new ZipEntry("0"));
            zout.write(str.getBytes());
            zout.closeEntry();
            compressed = out.toByteArray();
            compressedStr = new String(compressed);
        } catch (final IOException e) {
            ZipUtil.log.error("", e);
        } finally {
            if (zout != null) {
                try {
                    zout.close();
                } catch (final IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (final IOException e) {
                }
            }
        }
        return compressedStr;
    }

}