package cc.newex.dax.users.rest.model;

import cc.newex.dax.users.domain.behavior.model.UserBehaviorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmAccountResVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户名(手机或邮箱)
     */
    private String loginName;
    /**
     * 手机号加星
     */
    private String shadeMobile;
    /**
     * 邮箱加星
     */
    private String shadeEmail;
    /**
     * 区号
     */
    private Integer areaCode;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 当前操作需要执行的行为
     */
    private UserBehaviorResult behaviors;
}
