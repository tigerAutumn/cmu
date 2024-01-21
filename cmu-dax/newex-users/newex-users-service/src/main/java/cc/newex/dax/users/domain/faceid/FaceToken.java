package cc.newex.dax.users.domain.faceid;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liutiejun
 * @date 2018-12-04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FaceToken {

    private String bizNo;

    private Long timeUsed;

    private String token;

    private String bizId;

    private String requestId;

    private Long expiredTime;

}
