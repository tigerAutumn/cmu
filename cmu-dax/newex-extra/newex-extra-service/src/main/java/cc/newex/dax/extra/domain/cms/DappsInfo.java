package cc.newex.dax.extra.domain.cms;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * dapps应用程序表
 *
 * @author mbg.generated
 * @date 2018-09-12 17:14:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DappsInfo {
    /**
     */
    private Long id;

    /**
     * 语言
     */
    private String locale;

    /**
     * dapps名称
     */
    private String title;

    /**
     * 封面地址
     */
    private String imageUrl;

    /**
     * app链接
     */
    private String appLink;

    /**
     * pc链接
     */
    private String pcLink;

    /**
     * 交易币对
     */
    private String product;

    /**
     * 法币币种
     */
    private String currency;

    /**
     * 状态：0未上线 1已上线 2预发
     */
    private Integer status;

    /**
     * 展示顺序
     */
    private Integer sort;

    /**
     * 解释文案
     */
    private String text;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;

    /**
     * 券商ID
     */
    private Integer brokerId;
}