package cc.newex.dax.extra.domain.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 币种wiki用户权限管理表
 *
 * @author mbg.generated
 * @date 2018-08-20 17:32:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyPermission {
    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 币种唯一代码（币种简称）
     */
    private String code;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 组织机构
     */
    private String organization;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 状态，0-禁用，1-启用
     */
    private Integer status;

    /**
     * 发布用户id
     */
    private Integer publisherId;

    /**
     * 排序，可用于置顶，越小排序越靠前
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;
}