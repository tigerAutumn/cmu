package cc.newex.dax.perpetual.matching.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 推送push的格式
 *
 * @author xionghui
 * @date 2018/10/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class PushInfo {
  /**
   * 业务 spot/perpetual
   */
  private String biz;
  /**
   * 类型 depth/deal/asset
   */
  private String type;
  /**
   * 交易币种 p_btc-usdt
   */
  private String contractCode;
  /**
   * 交易币种 btc/ltc/eth
   */
  private String base;
  /**
   * 计价币种 cny/usd/btc
   */
  private String quote;
  /**
   * 是否压缩 默认不压缩
   */
  private boolean zip;
  /**
   * 数据
   */
  private Object data;

  /**
   * 压缩data
   */
  public String compress() {
    if (this.zip) {
      this.data = PushInfo.zip(this.data.toString());
    }
    return JSON.toJSONString(this);
  }

  /**
   * 使用zip进行压缩
   *
   * @param str 压缩前的文本
   * @return 返回压缩后的文本
   */
  private static String zip(final String str) {
    if (StringUtils.isEmpty(str)) {
      return str;
    }
    try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ZipOutputStream zout = new ZipOutputStream(out)) {
      zout.putNextEntry(new ZipEntry("0"));
      zout.write(str.getBytes());
      zout.closeEntry();
      return Base64.encodeBase64String(out.toByteArray());
    } catch (final IOException e) {
      log.error("PushInfo zip error: ", e);
    }
    return null;
  }
}
