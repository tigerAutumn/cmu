package cc.newex.dax.boss.web.model.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFeeVO {

    private Integer id;
    private Integer productId;
    private String productDesc;
    private Long userId;
    private Integer side;
    private Double rate;
    private String createdDate;
    private String expireDate;
}
