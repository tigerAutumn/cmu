package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SwitchSettingVO implements Serializable {
    /**
     * 法币设置类型 1:支付宝 2:微信 3:银行卡
     */
    @NotNull
    private Integer type;
}
