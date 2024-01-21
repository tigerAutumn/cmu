package cc.newex.dax.users.domain;

import lombok.*;

/**
 * user，channel，level汇总统计信息
 *
 * @author newex-team
 * @date 2018/7/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserStatisticsInfo {

    private Long userId;
    private String channelCode;
    private String channelName;
    private String channelFullName;
    private Integer level;
}
