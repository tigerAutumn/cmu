package cc.newex.dax.users.service.verification;

import cc.newex.dax.users.common.enums.security.VerifyCodeTypeEnum;

import java.util.Optional;

/**
 * @author newex-team
 */
public interface VerifyCodeService {

    void put(VerifyCodeTypeEnum type, String id, String code);

    boolean check(VerifyCodeTypeEnum type, String id, String code);

    Optional<String> get(VerifyCodeTypeEnum type, String id);

    void remove(VerifyCodeTypeEnum type, String id);
}
