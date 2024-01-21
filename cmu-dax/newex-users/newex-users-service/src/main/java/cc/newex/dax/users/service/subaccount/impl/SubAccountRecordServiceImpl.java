package cc.newex.dax.users.service.subaccount.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.subaccount.SubAccountRecordExample;
import cc.newex.dax.users.data.subaccount.SubAccountRecordRepository;
import cc.newex.dax.users.domain.subaccount.SubAccountRecord;
import cc.newex.dax.users.service.subaccount.SubAccountRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 子账户绑定、解锁的操作记录表 服务实现
 *
 * @author newex-team
 * @date 2018-11-06 17:06:44
 */
@Slf4j
@Service
public class SubAccountRecordServiceImpl extends AbstractCrudService<SubAccountRecordRepository, SubAccountRecord, SubAccountRecordExample, Long> implements SubAccountRecordService {
    @Autowired
    private SubAccountRecordRepository subAccountRecordRepository;

    @Override
    protected SubAccountRecordExample getPageExample(final String fieldName, final String keyword) {
        final SubAccountRecordExample example = new SubAccountRecordExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}