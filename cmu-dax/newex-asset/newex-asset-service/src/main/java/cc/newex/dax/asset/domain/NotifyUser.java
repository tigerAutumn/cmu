package cc.newex.dax.asset.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 充值、提现通知
 *
 * @author newex-team
 * @date 2018-08-01 11:17:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotifyUser {
    /**
     */
    private Integer id;

    /**
     */
    private Long userId;

    /**
     */
    private String auth;

    /**
     */
    private String urlPath;

    /**
     */
    private String remark;

    /**
     */
    private Integer status;

    /**
     */
    private Date createDate;

    /**
     */
    private Date updateDate;
}