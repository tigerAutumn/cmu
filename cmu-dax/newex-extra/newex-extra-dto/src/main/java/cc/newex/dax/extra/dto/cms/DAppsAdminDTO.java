package cc.newex.dax.extra.dto.cms;

import lombok.*;

/**
 * Dapps
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DAppsAdminDTO {
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
     * 发币币种
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
     * brokerId
     */
    private Integer brokerId;
}