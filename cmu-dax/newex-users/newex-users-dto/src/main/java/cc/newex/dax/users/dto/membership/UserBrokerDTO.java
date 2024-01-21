package cc.newex.dax.users.dto.membership;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 券商表
 *
 * @author newex-team
 * @date 2018-09-11 10:57:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserBrokerDTO {
    /**
     * 券商id
     */
    private Integer id;

    /**
     * 券商域名
     */
    private String brokerHosts;

    /**
     * 券商名称
     */
    private String brokerName;

    /**
     * 券商状态0为开启，1为禁用，其他保留，默认为0
     */
    private Integer status;
    /**
     * 签名
     */
    private String sign;
}