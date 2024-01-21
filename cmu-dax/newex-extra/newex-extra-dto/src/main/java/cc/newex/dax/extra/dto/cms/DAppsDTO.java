package cc.newex.dax.extra.dto.cms;

import lombok.*;

/**
 * 新闻文章表
 *
 * @author liutiejun
 * @date 2018-06-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DAppsDTO {
    private Long id;

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
     * 展示顺序
     */
    private Integer sort;

    /**
     * 解释文案
     */
    private String text;

}