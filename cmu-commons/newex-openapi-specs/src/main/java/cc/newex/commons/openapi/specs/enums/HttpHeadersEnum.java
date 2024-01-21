package cc.newex.commons.openapi.specs.enums;

import lombok.Getter;

/**
 * All REST requests must contain the following headers. <br/>
 * The Key and Secret will be randomly generated and provided by . <br/>
 * The Passphrase will be provided by you to further secure your API access. <br/>
 * stores the salted hash of your passphrase for verification
 * but cannot recover the passphrase if you forget it.
 * didn't design the passphrase, and didn't use it. <br/>
 * Created by newex-team on 2018/2/5 20:45. <br/>
 */
@Getter
public enum HttpHeadersEnum {

    ACCESS_KEY("ACCESS-KEY", "The api key as a string"),
    ACCESS_SIGN("ACCESS-SIGN", "The base64-encoded signature"),
    ACCESS_TIMESTAMP("ACCESS-TIMESTAMP", "A timestamp for your request"),
    ACCESS_PASSPHRASE("ACCESS-PASSPHRASE", "The passphrase you specified when creating the API key."),;

    private final String name;
    private final String comment;

    HttpHeadersEnum(final String name, final String comment) {
        this.name = name;
        this.comment = comment;
    }
}
