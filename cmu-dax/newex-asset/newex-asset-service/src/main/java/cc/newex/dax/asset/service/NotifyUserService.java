package cc.newex.dax.asset.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.asset.criteria.NotifyUserExample;
import cc.newex.dax.asset.domain.NotifyUser;
import cc.newex.dax.asset.domain.TransferRecord;

/**
 * 充值、提现通知 服务接口
 *
 * @author newex-team
 * @date 2018-08-01 11:17:17
 */
public interface NotifyUserService extends CrudService<NotifyUser, NotifyUserExample, Integer> {
    NotifyUser getByUserId(Long userId);

    void notifyDeposit(TransferRecord record);

    void notifyWithdraw(TransferRecord record);
}