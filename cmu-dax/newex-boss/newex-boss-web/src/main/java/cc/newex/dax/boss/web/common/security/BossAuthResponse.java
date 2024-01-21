package cc.newex.dax.boss.web.common.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author newex-team
 * @date 2018/03/18
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BossAuthResponse implements Serializable {
    private static final long serialVersionUID = 1250166508152483573L;
    private String token;
}
