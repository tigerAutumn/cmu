package cc.newex.dax.push.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

import org.apache.commons.codec.binary.Base64;

import lombok.extern.slf4j.Slf4j;

/**
 * 压缩处理类
 *
 * @author xionghui
 * @date 2018/09/19
 */
@Slf4j
public class ZipUtil {

  /**
   * 使用zip进行解压缩
   */
  public static String unzip(final String compressedStr) {
    if (compressedStr == null) {
      return null;
    }
    ByteArrayOutputStream out = null;
    ByteArrayInputStream in = null;
    ZipInputStream zin = null;
    String decompressed;
    try {
      final byte[] compressed = Base64.decodeBase64(compressedStr);
      out = new ByteArrayOutputStream();
      in = new ByteArrayInputStream(compressed);
      zin = new ZipInputStream(in);
      zin.getNextEntry();
      final byte[] buffer = new byte[1024];
      int offset = -1;
      while ((offset = zin.read(buffer)) != -1) {
        out.write(buffer, 0, offset);
      }
      decompressed = out.toString();
    } catch (final IOException e) {
      log.error("ZipUtil unzip error: ", e);
      decompressed = null;
    } finally {
      if (zin != null) {
        try {
          zin.close();
        } catch (final IOException ignored) {
        }
      }
      if (in != null) {
        try {
          in.close();
        } catch (final IOException ignored) {
        }
      }
      if (out != null) {
        try {
          out.close();
        } catch (final IOException ignored) {
        }
      }
    }
    return decompressed;
  }
}
