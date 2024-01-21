package cc.newex.commons.openapi.specs.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Open Api Http Headers Info
 *
 * @author newex-team
 * @date 2018-04-28
 */
@Getter
@Setter
public class OpenApiHeadersInfo {

    protected String accessKey;

    protected String accessSign;

    protected String accessTimestamp;

    protected String accessPassphrase;
}
