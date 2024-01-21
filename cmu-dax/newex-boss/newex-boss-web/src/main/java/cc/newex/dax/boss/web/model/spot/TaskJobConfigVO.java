package cc.newex.dax.boss.web.model.spot;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskJobConfigVO {
    private Long id;
    private String startTime;
    private String endTime;
    private String taskJobName;
    private Integer[] currency;
    private Integer taskType;
    private Integer calender;
    private Integer period;
    private Integer status;
    private Integer brokerId;
}
