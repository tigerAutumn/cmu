package cc.newex.maker.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cmx-sdk-team
 * @date 2019-04-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParamPair {

    private String name;

    private String value;

}
