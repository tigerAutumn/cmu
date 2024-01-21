package cc.newex.dax.boss.web.model.activity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ActPropVO {

    private Long propId;
    private String actKey;
    private String activityPropKey;
    private String activityPropValue;
}
