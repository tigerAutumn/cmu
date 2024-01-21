package cc.newex.dax.extra.dto.currency;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * 全球数字货币基本信息表
 *
 * @author mbg.generated
 * @date 2018-08-20 17:31:59
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyBaseInfoDTO {
    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 币种唯一代码（币种简称）
     */
    @NotBlank
    private String code;

    /**
     * 币种名称（币种全称）
     */
    @NotBlank
    private String name;

    /**
     * 币种符号
     */
    private String symbol;

    /**
     * 币种图标
     */
    private String imgUrl;

    /**
     * 币种发行日期
     */
    private String issueDate;

    /**
     * 币种发行价格
     */
    private String issuePrice;

    /**
     * 币种发行总量
     */
    private Long maxSupply;

    /**
     * 币种当前流通数量
     */
    private Long circulatingSupply;

    /**
     * 币种官网地址
     */
    private String officalWebsite;

    /**
     * 币种区块浏览器地址，多个之间用英文逗号隔开
     */
    private String explorer;

    /**
     * 源代码
     */
    private String sourceCodeUrl;

    /**
     * 状态，1-待审核，2-已发布，3-已下架
     */
    private Integer status;

    /**
     * 发布用户id
     */
    private Integer publisherId;

    /**
     * 排序，可用于置顶，越小排序越靠前
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;
}