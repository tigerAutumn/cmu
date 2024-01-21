package cc.newex.dax.integration.domain.msg;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 信息息发送成功率统计表
 *
 * @author newex-team
 * @date 2018-05-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageSuccessRatio {
    /**
     * 自增标识
     */
    private Integer id;
    /**
     * 发送渠道代号
     */
    private String channel;
    /**
     * 信息类型(MAIL|SMS)
     */
    private String type;
    /**
     * 服务地区(china|international)
     */
    private String region;
    /**
     * 成功率
     */
    private Double ratio;
    /**
     * 创建时间
     */
    private Date createdTime;

    public static MessageSuccessRatio getInstance() {
        return MessageSuccessRatio.builder()
                .id(0)
                .channel("")
                .type("")
                .region("")
                .ratio(1.0)
                .createdTime(new Date())
                .build();
    }
}