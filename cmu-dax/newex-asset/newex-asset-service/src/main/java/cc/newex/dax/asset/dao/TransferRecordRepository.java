package cc.newex.dax.asset.dao;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.asset.criteria.TransferRecordExample;
import cc.newex.dax.asset.domain.TransferRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018-04-06
 */
@Repository
public interface TransferRecordRepository
        extends CrudRepository<TransferRecord, TransferRecordExample, Long> {
    TransferRecord selectAndLockOneByExample(@Param("example") TransferRecordExample example);

    List<Map<String, Object>> getUnconfirmedRecordSum(@Param("transferType") Integer transferType);
}