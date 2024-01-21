package cc.newex.dax.boss.web.model.portfolio;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MixIndexVO {
    private Long id;
    private Integer symbol;
    private String symbolName;
    private String initialDate;
    private Integer status;
    private List<Integer> currency;
    private String configs;
}
