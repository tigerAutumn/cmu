package cc.newex.dax.market.spider.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 汇率
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Data
public class Rate implements Serializable {
    private static final long serialVersionUID = 523566013985704362L;
    private String rateName;
    private String rateParities;
    private String createTime;
}
