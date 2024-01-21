package cc.newex.dax.asset.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.asset.criteria.UserAssetConfExample;
import cc.newex.dax.asset.domain.UserAssetConf;
import cc.newex.dax.asset.dto.WithDrawInfoDto;
import cc.newex.wallet.currency.BizEnum;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-06-26
 */
public interface UserAssetConfService
        extends CrudService<UserAssetConf, UserAssetConfExample, Long> {

    UserAssetConf getByUserId(Long userId);

    WithDrawInfoDto getTransferMsg(BizEnum bizEnum, Long userId, String currency, Integer brokerId);
}
