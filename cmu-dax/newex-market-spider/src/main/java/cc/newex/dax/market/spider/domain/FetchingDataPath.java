package cc.newex.dax.market.spider.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FetchingDataPath implements Serializable {
    private static final long serialVersionUID = 1457108064119031360L;
    /**
     * 抓取数据URL
     */
    private Long id;
    /**
     * 唯一标识
     */
    private Integer marketFrom;
    /**
     * 添加时间
     */
    private Date createTime;
    /**
     * 路径
     */
    private String path;
    /**
     * 币种名称
     */
    private Integer symbol;
    /**
     * 网站名称
     */
    private String webName;
    /**
     * 更新时间
     */
    private Date modifyTime;
    /**
     *
     */
    private String urlKey;
    /**
     *
     */
    private String ovm;
    /**
     * 1启用0禁用
     */
    private Integer status;

    private String aSymbol;

}