package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileKycLevelVO {
    /**
     * 等级
     */
    private Integer level;
    /**
     * 状态
     */
    private String status;
    /**
     * 国家
     */
    private Integer country;
    /**
     * 失败原因
     */
    private String rejectReason;
}
