package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liutiejun
 * @date 2018-11-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LastLoginResVO {

    /**
     * 登录时间
     */
    private String date;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 登录所在地
     */
    private String region;

}
