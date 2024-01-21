package cc.newex.dax.boss.report.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 报表数据源配置信息表
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DataSource {
    /**
     * 数据源ID
     */
    private Integer id;
    /**
     * 数据源唯一ID,由调接口方传入
     */
    private String uid;
    /**
     * 数据源名称
     */
    private String name;
    /**
     * 数据源驱动类
     */
    private String driverClass;
    /**
     * 数据源连接字符串(JDBC)
     */
    private String jdbcUrl;
    /**
     * 数据源登录用户名
     */
    private String user;
    /**
     * 数据源登录密码
     */
    private String password;
    /**
     * 获取报表引擎查询器类名
     */
    private String queryerClass;
    /**
     * 报表引擎查询器使用的数据源连接池类名
     */
    private String poolClass;
    /**
     * 数据源配置选项(JSON格式）
     */
    private String options;
    /**
     * 说明备注
     */
    private String comment;
    /**
     * 记录创建时间
     */
    private Date createdDate;
    /**
     * 记录修改时间
     */
    private Date updatedDate;

    public static DataSource getInstance() {
        return DataSource.builder()
                .uid("")
                .name("")
                .driverClass("")
                .jdbcUrl("")
                .user("")
                .password("")
                .queryerClass("")
                .poolClass("")
                .options("")
                .comment("")
                .updatedDate(new Date())
                .build();
    }
}