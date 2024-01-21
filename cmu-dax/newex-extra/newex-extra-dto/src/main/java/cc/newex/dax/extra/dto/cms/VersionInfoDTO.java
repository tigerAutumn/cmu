package cc.newex.dax.extra.dto.cms;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 版本信息，以及确定是否需要强制升级
 *
 * @author liutiejun
 * @date 2018-07-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VersionInfoDTO {

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 客户端对应的平台：1-iOS，2-Android
     */
    private Integer platform;

    /**
     * 当前最新版本号
     */
    private String newVersion;

    /**
     * 是否强制升级：0不提醒 1仅提醒升级 2 强制升级
     */
    private Integer forceUpdate;

    /**
     * 更新内容
     */
    private String content;

    /**
     * 下载链接
     */
    private String downloadUrl;

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

    /**
     * 本地化语言代号(zh-cn/en-us等）
     */
    private String locale;

    public static VersionInfoDTO getInstance() {
        return VersionInfoDTO.builder()
                .id(0)
                .platform(0)
                .newVersion("")
                .forceUpdate(0)
                .content("")
                .downloadUrl("")
                .createdDate(new Date())
                .updatedDate(new Date())
                .locale("")
                .build();
    }

}
