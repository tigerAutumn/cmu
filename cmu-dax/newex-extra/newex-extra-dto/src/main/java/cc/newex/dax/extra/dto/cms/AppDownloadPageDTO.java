package cc.newex.dax.extra.dto.cms;

import lombok.*;

import java.util.Date;

/**
 * App下载页
 *
 * @author liutiejun
 * @date 2018-08-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppDownloadPageDTO {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * logo图片
     */
    private String logoImg;

    /**
     * 语言
     */
    private String locale;

    /**
     * 标识码
     */
    private String uid;

    /**
     * 下载页链接
     */
    private String link;

    /**
     * Android下载链接
     */
    private String androidUrl;

    /**
     * iOS下载链接
     */
    private String iosUrl;

    /**
     * 状态：0已保存 1已发布 2已下架 -1已删除
     */
    private Integer status;

    /**
     * 文章模板
     */
    private Integer templateId;

    /**
     * 第一部分：标题
     */
    private String firstTitle;

    /**
     * 第一部分：简介
     */
    private String firstIntro;

    /**
     * 第一部分：图片
     */
    private String firstImg;

    /**
     * 第二部分：标题
     */
    private String secondTitle;

    /**
     * 第二部分：简介
     */
    private String secondIntro;

    /**
     * 第二部分：图片
     */
    private String secondImg;

    /**
     * 第三部分：特点
     */
    private String thirdFeature1;

    /**
     * 第三部分：特点
     */
    private String thirdFeature2;

    /**
     * 第三部分：特点
     */
    private String thirdFeature3;

    /**
     * 第三部分：特点
     */
    private String thirdFeature4;

    /**
     * 第三部分：简介
     */
    private String thirdIntro1;

    /**
     * 第三部分：简介
     */
    private String thirdIntro2;

    /**
     * 第三部分：简介
     */
    private String thirdIntro3;

    /**
     * 第三部分：简介
     */
    private String thirdIntro4;

    /**
     * 第三部分：图片
     */
    private String thirdImg1;

    /**
     * 第三部分：图片
     */
    private String thirdImg2;

    /**
     * 第三部分：图片
     */
    private String thirdImg3;

    /**
     * 第三部分：图片
     */
    private String thirdImg4;

    /**
     * 发布用户id对应boss系统的管理员id
     */
    private Integer publisherId;

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
