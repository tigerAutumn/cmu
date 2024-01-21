package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author liutiejun
 * @date 2018-11-27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeReqVO {

    /**
     * 相关行为业务
     */
    @NotNull
    private Integer behavior;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 区域码
     */
    private Integer areaCode;

    /**
     * 币种
     */
    private String coin;

    /**
     * 链接地址
     */
    private String toAddress;

    /**
     * 提现数量
     */
    private String amount;

}
