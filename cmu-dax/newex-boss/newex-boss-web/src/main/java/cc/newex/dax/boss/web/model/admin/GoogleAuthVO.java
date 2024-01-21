package cc.newex.dax.boss.web.model.admin;

import lombok.*;

/**
 * @author allen
 * @date 2018/6/4
 * @des
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GoogleAuthVO {

    /**
     * google key
     */
    private String key;


    /**
     * google code
     */
    private String qRCode;
}
