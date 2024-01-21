package cc.newex.dax.asset.rest.params;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author newex-team
 * @data 16/04/2018
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignDataDto<T> implements Serializable {
    Long time;
    String signature;
    List<T> signData;
}
