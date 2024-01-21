package cc.newex.dax.extra.domain.wiki;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * rt代币团队信息
 *
 * @author mbg.generated
 * @date 2018-12-04 11:15:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RtCurrencyTeam {
    /**
     * 主键
     */
    private Long id;

    /**
     * 代币标识
     */
    private String cid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 个人图片
     */
    private String photoUrl;

    /**
     * 职位
     */
    private String bio;

    /**
     * 职位得分
     */
    private Integer bioScore;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date modifyDate;
}