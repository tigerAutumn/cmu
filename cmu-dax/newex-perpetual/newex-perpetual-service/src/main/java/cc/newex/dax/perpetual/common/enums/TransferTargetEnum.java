package cc.newex.dax.perpetual.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author newex-team
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TransferTargetEnum {

  ASSET("ASSET", 1),//
  ;

  private final String name;

  private final int targetId;

}
