package cc.newex.dax.extra.service.currency;

import cc.newex.dax.extra.domain.currency.CurrencyPermission;

import java.util.List;

/**
 * @author liutiejun
 * @date 2018-08-21
 */
public interface CurrencyPermissionService {

    /**
     * 查看某一个用户的权限
     *
     * @param userId
     * @param code
     * @return
     */
    List<CurrencyPermission> getByUserId(Integer userId, String code);

}
