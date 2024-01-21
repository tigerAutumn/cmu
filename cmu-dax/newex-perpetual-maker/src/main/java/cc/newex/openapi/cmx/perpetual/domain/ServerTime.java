package cc.newex.openapi.cmx.perpetual.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cointobe-sdk-team
 * @date 2018/04/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ServerTime {
    /**
     * 时间戳
     */
    private Long timestamp;
    /**
     * 时间
     */
    private String iso;
}
