package cc.newex.dax.boss.web.model.activity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigVO {

    private Long actId;
    private String actKey;
    private String actName;
    private String startDate;
    private String endDate;
    private Integer status;
    private String whileList;
    private Long actSort;
}
