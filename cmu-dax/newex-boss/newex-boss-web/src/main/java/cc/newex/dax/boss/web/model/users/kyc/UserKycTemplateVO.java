package cc.newex.dax.boss.web.model.users.kyc;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * kyc发送短信、邮件的模板信息
 *
 * @author liutiejun
 * @date 2018-07-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserKycTemplateVO {

    /**
     * 0-拒绝，1-通过
     */
    private Integer pass;

    /**
     * mail，sms
     */
    private String type;

    /**
     * 模板名称
     */
    private String name;

}
