package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.SystemBillExample;
import cc.newex.dax.perpetual.data.SystemBillRepository;
import cc.newex.dax.perpetual.domain.SystemBill;
import cc.newex.dax.perpetual.service.SystemBillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 对账流水表 服务实现
 *
 * @author newex-team
 * @date 2018-11-01 09:32:43
 */
@Slf4j
@Service
public class SystemBillServiceImpl extends AbstractCrudService<SystemBillRepository, SystemBill, SystemBillExample, Long> implements SystemBillService {
    @Autowired
    private SystemBillRepository systemBillRepository;

    @Override
    protected SystemBillExample getPageExample(final String fieldName, final String keyword) {
        final SystemBillExample example = new SystemBillExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    /**
     * 获取最大的账号ID
     *
     * @return the max id
     */
    @Override
    public Long getMaxId() {
        SystemBillExample example = new SystemBillExample();
        example.setOrderByClause("id desc");
        return Optional.ofNullable(this.getOneByExample(example)).map(SystemBill::getId).orElse(null);
    }
}