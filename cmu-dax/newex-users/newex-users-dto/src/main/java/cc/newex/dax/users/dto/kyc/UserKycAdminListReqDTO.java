package cc.newex.dax.users.dto.kyc;

import cc.newex.dax.users.dto.common.ParamPageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserKycAdminListReqDTO implements Serializable {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 登录用户名称
     */
    private String loginName;
    /**
     * 用户账号
     */
    private String cardNumber;
    /**
     * 0:全部,1:国内 2:国际
     */
    private Integer country = 0;
    /**
     * 认证状态
     */
    private String status;
    /**
     * kyc级别
     */
    private String level;
    /**
     * 受理客服
     */
    private String dealUserId;
    /**
     * 创建时间开始
     */
    private String createdDateStart;
    /**
     * 创建时间结束
     */
    private String createdDateEnd;
    /**
     * 受理时间开始
     */
    private String updatedDateStart;
    /**
     * 受理时间结束
     */
    private String updatedDateEnd;
    /**
     * 券商ID
     */
    private Integer brokerId;

    private Integer ageCode;

    /**
     * 分页请求
     */
    @Builder.Default
    private ParamPageDTO paramPageDTO = new ParamPageDTO();

}
