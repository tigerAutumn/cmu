package cc.newex.wallet.service.impl;

import cc.newex.commons.mybatis.sharding.service.AbstractCrudService;
import cc.newex.wallet.criteria.WithdrawRecordExample;
import cc.newex.wallet.dao.WithdrawRecordRepository;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.service.WithdrawRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018-04-02
 */
@Slf4j
@Service
public class WithdrawRecordServiceImpl
        extends AbstractCrudService<WithdrawRecordRepository, WithdrawRecord, WithdrawRecordExample, Integer>
        implements WithdrawRecordService {

    @Autowired
    private WithdrawRecordRepository withdrawRecordRepos;

    @Override
    protected WithdrawRecordExample getPageExample(final String fieldName, final String keyword) {
        final WithdrawRecordExample example = new WithdrawRecordExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}