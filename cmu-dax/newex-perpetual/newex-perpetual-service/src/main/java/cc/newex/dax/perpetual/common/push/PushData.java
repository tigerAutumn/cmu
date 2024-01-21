package cc.newex.dax.perpetual.common.push;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PushData {
  String biz;
  String type;
  String contractCode;
  String base;
  String quote;
  String granularity;
  Boolean zip;
  Object data;
}
