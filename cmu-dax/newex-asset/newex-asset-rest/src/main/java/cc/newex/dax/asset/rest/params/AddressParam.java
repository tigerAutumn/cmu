package cc.newex.dax.asset.rest.params;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressParam implements Serializable {
    @NotBlank
    String address;

    String remark;
}
