package cc.newex.dax.boss.admin.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 后台系统用户登录验证码表
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CaptchaCode {
    /**
     *
     */
    private Long id;
    /**
     * 验证方式 0google验证 1手机验证 2邮箱验证 3图片验证
     */
    private Integer type;
    /**
     * 验证key（谷歌密钥、手机号，邮箱地址，图片关联的用户）
     */
    private String key;
    /**
     * 验证码
     */
    private String code;
    /**
     * 具体验证方式的业务验证类型（代码中定义：如注册、登录、修改密码登）
     */
    private Integer codeType;
    /**
     * 0:未使用 1:已使用
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createdDate;

    public static CaptchaCode getInstance() {
        return CaptchaCode.builder()
                .type(0)
                .key("")
                .code("")
                .codeType(0)
                .status(0)
                .createdDate(new Date())
                .build();
    }
}