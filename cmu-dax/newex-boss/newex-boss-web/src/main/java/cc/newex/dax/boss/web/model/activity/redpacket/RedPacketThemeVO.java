package cc.newex.dax.boss.web.model.activity.redpacket;

import lombok.Data;

/**
 * @author better
 * @date create in 2019-01-08 14:49
 */
@Data
public class RedPacketThemeVO {

    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 主题名称
     */
    private String themeName;

    /**
     * 主题图片
     */
    private String imageUrl;

    /**
     * 祝福语
     */
    private String greeting;

    /**
     * 状态，1-已保存，2-已发布，3-已下架
     */
    private Integer status;

    /**
     * 券商Id
     */
    private Integer brokerId;
}
