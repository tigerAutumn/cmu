package cc.newex.dax.users.dto.membership;

import lombok.*;

import java.io.Serializable;

/**
 * @author newex-team
 * @date 2018/7/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserStatisticsInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String channelCode;
    private String channelName;
    private String channelFullName;

    private Integer userCount;
    private Integer userKyc1Count;
    private Integer userKyc2Count;
}
