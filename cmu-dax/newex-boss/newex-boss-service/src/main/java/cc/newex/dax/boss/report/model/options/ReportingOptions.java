package cc.newex.dax.boss.report.model.options;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 报表配置选项(report表的options字段)持久化类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportingOptions implements Serializable {
    /**
     * 报表默认展示多少天的数据
     */
    private Integer dataRange;
    /**
     * 布局形式.1横向;2纵向
     */
    private Integer layout;
    /**
     * 统计列布局形式.1横向;2纵向
     */
    private Integer statColumnLayout;
}
