package cc.newex.dax.extra.service.admin.currency.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.currency.CurrencyPermissionExample;
import cc.newex.dax.extra.data.currency.CurrencyPermissionRepository;
import cc.newex.dax.extra.domain.currency.CurrencyPermission;
import cc.newex.dax.extra.service.admin.currency.CurrencyPermissionAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 币种wiki用户权限管理表 服务实现
 *
 * @author mbg.generated
 * @date 2018-08-20 17:32:13
 */
@Slf4j
@Service
public class CurrencyPermissionAdminServiceImpl
        extends AbstractCrudService<CurrencyPermissionRepository, CurrencyPermission, CurrencyPermissionExample, Long>
        implements CurrencyPermissionAdminService {

    @Autowired
    private CurrencyPermissionRepository currencyPermissionRepos;

    @Override
    protected CurrencyPermissionExample getPageExample(final String fieldName, final String keyword) {
        final CurrencyPermissionExample example = new CurrencyPermissionExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

}