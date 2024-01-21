package cc.newex.dax.boss.web.model.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 后台系统ip白名单表
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IpWhiteVO {
    /**
     * 标识
     */
    private Integer id;
    /**
     * 创建管理员用户id
     */
    private Integer adminUserId;
    /**
     * ip地址
     */
    private String ipAddress;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;
}