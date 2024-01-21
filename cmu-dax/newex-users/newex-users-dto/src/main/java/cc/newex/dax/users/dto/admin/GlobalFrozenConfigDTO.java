package cc.newex.dax.users.dto.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 系统业务全局冻结配置表
 *
 * @author newex-team
 * @date 2018-07-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GlobalFrozenConfigDTO {
    /**
     * 自增id
     */
    private Integer id;
    /**
     * 全局冻结配置项唯一代号
     */
    private String code;
    /**
     * 冻结状态0未冻结1冻结,默认为0
     */
    private Integer status;
    /**
     * 说明备注
     */
    private String memo;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;
    /**
     * 缓存中的状态
     */
    private Integer cacheStatus;
    /**
     * 券商Id
     */
    private Integer brokerId;
}