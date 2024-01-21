package cc.newex.dax.extra.domain.mobile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * ios请求参数表
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IosReqParam {
    /**
     * 主键
     */
    private Long id;
    /**
     * 类型
     */
    private Integer type;
    /**
     * userid
     */
    private Long userId;
    /**
     * 参数内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;
}