package cc.newex.dax.users.dto.kyc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 邀请活动和kyc等级完成统计
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoStatDTO implements Serializable {
    /**
     * 24h的新增用户
     */
    private int userCount;
    /**
     * 24h完成KYC1的用户
     */
    private int kyc1Count;
    /**
     * 24h完成KYC2的用户
     */
    private int kyc2Count;
    /**
     * 24h被邀请认证且完成KYC2的用户数
     */
    private int inviteKyc2Count;

}
