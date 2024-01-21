package cc.newex.dax.users.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户文件表
 *
 * @author newex-team
 * @date 2018-08-15 18:27:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFileInfo {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 文件类型：比如图片，视频等
     */
    private String fileType;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件相对路径
     */
    private String filePath;

    /**
     * 备注
     */
    private String note;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;
}