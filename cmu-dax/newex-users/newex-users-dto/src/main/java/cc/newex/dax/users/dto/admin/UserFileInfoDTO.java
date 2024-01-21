package cc.newex.dax.users.dto.admin;

import lombok.*;

import java.util.Date;

/**
 * @author newex-team
 * @date 2018/8/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFileInfoDTO {
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
