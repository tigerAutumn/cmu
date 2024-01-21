package cc.newex.dax.extra.service.currency.impl;

import cc.newex.dax.extra.criteria.currency.CurrencyPermissionExample;
import cc.newex.dax.extra.data.currency.CurrencyPermissionRepository;
import cc.newex.dax.extra.domain.currency.CurrencyPermission;
import cc.newex.dax.extra.enums.currency.PermissionStatusEnum;
import cc.newex.dax.extra.service.currency.CurrencyPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liutiejun
 * @date 2018-08-21
 */
@Slf4j
@Service
public class CurrencyPermissionServiceImpl implements CurrencyPermissionService {

    @Autowired
    private CurrencyPermissionRepository currencyPermissionRepository;

    @Override
    public List<CurrencyPermission> getByUserId(final Integer userId, final String code) {
        if (userId == null || userId <= 0) {
            return null;
        }

        final CurrencyPermissionExample example = new CurrencyPermissionExample();
        final CurrencyPermissionExample.Criteria criteria = example.createCriteria();

        criteria.andUserIdEqualTo(userId);

        if (StringUtils.isNotBlank(code)) {
            criteria.andCodeEqualTo(code);
        }

        criteria.andStatusEqualTo(PermissionStatusEnum.ENABLE.getCode());

        return this.currencyPermissionRepository.selectByExample(example);
    }
}
