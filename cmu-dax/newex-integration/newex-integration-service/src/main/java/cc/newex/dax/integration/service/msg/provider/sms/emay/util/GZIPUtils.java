package cc.newex.dax.integration.service.msg.provider.sms.emay.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZIP 压缩工具
 *
 * @author Frank
 */
public class GZIPUtils {

    public static void main(final String[] args) throws IOException {
        final String sst = "hahahah";
        System.out.println(sst);
        System.out.println(System.currentTimeMillis());
        System.out.println("size:" + sst.length());
        final byte[] bytes = sst.getBytes();
        System.out.println("length:" + bytes.length);
        System.out.println(System.currentTimeMillis());
        final byte[] end = compress(bytes);
        System.out.println(System.currentTimeMillis());
        System.out.println("length:" + end.length);
        System.out.println(System.currentTimeMillis());
        final byte[] start = decompress(end);
        System.out.println(System.currentTimeMillis());
        System.out.println("length:" + start.length);
        System.out.println(new String(start));
    }

    /**
     * 数据压缩传输
     *
     * @param is
     * @param os
     * @throws Exception
     */
    public static void compressTransfe(final byte[] bytes, final OutputStream out) throws IOException {
        GZIPOutputStream gos = null;
        try {
            gos = new GZIPOutputStream(out);
            gos.write(bytes);
            gos.finish();
            gos.flush();
        } finally {
            if (gos != null) {
                gos.close();
            }
        }
    }

    /**
     * 数据压缩
     *
     * @param is
     * @param os
     * @throws Exception
     */
    public static byte[] compress(final byte[] bytes) throws IOException {
        ByteArrayOutputStream out = null;
        GZIPOutputStream gos = null;
        try {
            out = new ByteArrayOutputStream();
            gos = new GZIPOutputStream(out);
            gos.write(bytes);
            gos.finish();
            gos.flush();
        } finally {
            if (gos != null) {
                gos.close();
            }
            if (out != null) {
                out.close();
            }
        }
        return out.toByteArray();
    }

    /**
     * 数据解压
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] decompress(final byte[] bytes) throws IOException {
        final ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        final GZIPInputStream gin = new GZIPInputStream(in);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        int count;
        final byte[] data = new byte[1024];
        while ((count = gin.read(data, 0, 1024)) != -1) {
            out.write(data, 0, count);
        }
        out.flush();
        out.close();
        gin.close();
        return out.toByteArray();
    }

}
