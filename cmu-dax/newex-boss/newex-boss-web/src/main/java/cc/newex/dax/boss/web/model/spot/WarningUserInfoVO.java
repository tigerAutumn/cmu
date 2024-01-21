package cc.newex.dax.boss.web.model.spot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author liutiejun
 * @date 2019-01-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WarningUserInfoVO {

    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * Email
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户信息
     */
    private String username;

    /**
     * 通知方式，0-短信、邮件两者皆通知，1-短信，2-邮件
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;

}
