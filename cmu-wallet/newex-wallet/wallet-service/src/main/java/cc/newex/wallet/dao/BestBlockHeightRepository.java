package cc.newex.wallet.dao;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.wallet.criteria.BestBlockHeightExample;
import cc.newex.wallet.pojo.BestBlockHeight;
import org.springframework.stereotype.Repository;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018-05-02
 */
@Repository
public interface BestBlockHeightRepository
        extends CrudRepository<BestBlockHeight, BestBlockHeightExample, Integer> {
}