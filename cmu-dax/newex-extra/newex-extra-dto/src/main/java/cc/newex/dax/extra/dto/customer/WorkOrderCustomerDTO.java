package cc.newex.dax.extra.dto.customer;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkOrderCustomerDTO {
    /**
     * 工单处理人Id
     */
    private Integer adminUserId;
    /**
     * 工单账号
     */
    private String adminAccount;
    /**
     * 处理人姓名
     */
    private String adminName;

}
