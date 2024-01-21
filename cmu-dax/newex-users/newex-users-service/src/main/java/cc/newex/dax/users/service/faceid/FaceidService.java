package cc.newex.dax.users.service.faceid;

import cc.newex.dax.users.domain.UserKycInfo;
import cc.newex.dax.users.domain.faceid.FaceToken;

/**
 * @author liutiejun
 * @date 2018-12-05
 */
public interface FaceidService {

    /**
     * 获得一个用于实名验证的 token（token唯一且只能使用一次）
     *
     * @param userKycInfo
     * @return
     */
    FaceToken getToken(final UserKycInfo userKycInfo);

}
