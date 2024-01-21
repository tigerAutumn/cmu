package cc.newex.dax.users.dto.subaccount;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 组织详情表
 *
 * @author newex-team
 * @date 2018-10-31 16:16:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrganizationInfoDTO {
    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 组织类型，11-团队，12-企业，19-其他组织，和UserInfo中的type字段对应
     */
    private Integer orgType;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系方式
     */
    private String contactInfo;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;
    
}
