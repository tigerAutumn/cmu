package cc.newex.dax.users.service.membership;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserApiSecretExample;
import cc.newex.dax.users.domain.UserApiSecret;
import cc.newex.dax.users.dto.apisecret.UserApiSecretResDTO;

import java.util.List;

/**
 * apikey表 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */
public interface UserApiSecretService
        extends CrudService<UserApiSecret, UserApiSecretExample, Long> {

    List<UserApiSecret> getApiSecrets(long userId, Integer brokerId);

    UserApiSecret getApiSecret(String apiKey);

    UserApiSecret getApiSecretById(Long id);

    UserApiSecretResDTO getApiSecretByCache(String apiKey);

    long count(long userId);

    boolean existLabel(String label, long userId, long id);

    boolean existLabel(String label, long userId);

    int save(UserApiSecret apiSecret);

    UserApiSecret getApiSecret(long userId, long id);

    UserApiSecret getApiSecret(long userId, final String apiKey);

    int update(UserApiSecret apiSecret);

    int delete(final long id, final long userId, final String apiKey);
}
