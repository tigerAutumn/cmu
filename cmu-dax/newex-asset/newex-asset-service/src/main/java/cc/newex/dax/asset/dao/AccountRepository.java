package cc.newex.dax.asset.dao;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.asset.criteria.AccountExample;
import cc.newex.dax.asset.domain.Account;
import cc.newex.dax.asset.dto.BizCurrencyBalance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户账户表 数据访问类
 *
 * @author newex-team
 * @date 2018-09-17 18:12:21
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, AccountExample, Long> {
    Account selectAndLockOneByExample(@Param("example") AccountExample example);

    int insertOnDuplicateKey(@Param("record") Account account);

    List<BizCurrencyBalance> getLockPositionSum();
}