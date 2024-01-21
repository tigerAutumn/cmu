package cc.newex.wallet.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.wallet.criteria.BestBlockHeightExample;
import cc.newex.wallet.pojo.BestBlockHeight;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-05-02
 */
public interface BestBlockHeightService
        extends CrudService<BestBlockHeight, BestBlockHeightExample, Integer> {
}
