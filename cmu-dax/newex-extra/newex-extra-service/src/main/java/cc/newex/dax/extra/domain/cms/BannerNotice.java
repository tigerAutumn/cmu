package cc.newex.dax.extra.domain.cms;

import lombok.*;

import java.util.Date;

/**
 * 首页banner广告表
 *
 * @author mbg.generated
 * @date 2018-09-12 16:53:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BannerNotice {
    /**
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * banner在当前locale语言下唯一编号
     */
    private String uid;

    /**
     * 类型：1banner 2公告 3轮播图
     */
    private Integer type;

    /**
     */
    private String locale;

    /**
     * 文本
     */
    private String text;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 原始图片
     */
    private String originalImageUrl;

    /**
     * 链接地址
     */
    private String link;

    /**
     * 展示平台 0默认 1PC 2IOS 3android
     */
    private Byte platform;

    /**
     * 发布时间
     */
    private Date startTime;

    /**
     * 下架时间
     */
    private Date endTime;

    /**
     * 状态：0已保存 1已发布 2已下架 -1已删除
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 发布人
     */
    private String publishUser;

    /**
     * 随机数：时间戳+随机数(4位数字)
     */
    private String rndNum;

    /**
     * 分享标题
     */
    private String shareTitle;

    /**
     */
    private String shareText;

    /**
     */
    private String shareImageUrl;

    /**
     */
    private String shareLink;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     */
    private Date updateDate;

    /**
     * 券商ID
     */
    private Integer brokerId;
}