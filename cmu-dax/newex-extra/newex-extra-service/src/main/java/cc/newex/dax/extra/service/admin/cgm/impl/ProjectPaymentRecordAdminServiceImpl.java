package cc.newex.dax.extra.service.admin.cgm.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.dax.extra.criteria.cgm.ProjectPaymentRecordExample;
import cc.newex.dax.extra.data.cgm.ProjectPaymentRecordRepository;
import cc.newex.dax.extra.domain.cgm.ProjectPaymentRecord;
import cc.newex.dax.extra.dto.cgm.ProjectPaymentRecordDTO;
import cc.newex.dax.extra.enums.cgm.CurrencyTypeEnum;
import cc.newex.dax.extra.enums.cgm.DepositStatusEnum;
import cc.newex.dax.extra.enums.cgm.SweetsStatusEnum;
import cc.newex.dax.extra.service.admin.cgm.ProjectPaymentRecordAdminService;
import cc.newex.dax.extra.service.admin.cgm.ProjectTokenInfoAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 项目支付记录，记录支付CT、糖果活动币的记录 服务实现
 *
 * @author mbg.generated
 * @date 2018-08-13 15:28:20
 */
@Slf4j
@Service
public class ProjectPaymentRecordAdminServiceImpl
        extends AbstractCrudService<ProjectPaymentRecordRepository, ProjectPaymentRecord, ProjectPaymentRecordExample, Long>
        implements ProjectPaymentRecordAdminService {

    @Autowired
    private ProjectPaymentRecordRepository projectPaymentRecordRepos;

    @Autowired
    private ProjectTokenInfoAdminService projectTokenInfoAdminService;

    @Override
    protected ProjectPaymentRecordExample getPageExample(final String fieldName, final String keyword) {
        final ProjectPaymentRecordExample example = new ProjectPaymentRecordExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(final ProjectPaymentRecord projectPaymentRecord) {
        if (Objects.isNull(projectPaymentRecord)) {
            return 0;
        }

        // 币种类型：1-CT保证金，2-糖果活动币
        final Byte currencyType = projectPaymentRecord.getCurrencyType();
        if (Objects.isNull(currencyType)) {
            return 0;
        }

        final int save = this.projectPaymentRecordRepos.insert(projectPaymentRecord);

        if (save > 0) {
            final Long tokenInfoId = projectPaymentRecord.getTokenInfoId();

            // 更新CT保证金、糖果的支付状态
            if (currencyType.equals(CurrencyTypeEnum.DEPOSIT.getCode().byteValue())) {
                this.projectTokenInfoAdminService.updateDepositStatus(tokenInfoId, DepositStatusEnum.PAID);
            } else if (currencyType.equals(CurrencyTypeEnum.SWEETS.getCode().byteValue())) {
                this.projectTokenInfoAdminService.updateSweetsStatus(tokenInfoId, SweetsStatusEnum.PAID);
            }
        }

        return save;
    }

    @Override
    public DataGridPagerResult<ProjectPaymentRecordDTO> getProjectPaymentRecordPageInfo(final DataGridPager<ProjectPaymentRecordDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final ProjectPaymentRecord projectPaymentRecord = mapper.map(pager.getQueryParameter(), ProjectPaymentRecord.class);
        final ProjectPaymentRecordExample example = this.buildProjectPaymentRecordExample(projectPaymentRecord);

        final List<ProjectPaymentRecord> projectPaymentRecords = this.getByPage(pageInfo, example);
        final List<ProjectPaymentRecordDTO> result = mapper.map(
                projectPaymentRecords, new TypeToken<List<ProjectPaymentRecordDTO>>() {
                }.getType()
        );
        return new DataGridPagerResult<>(pageInfo.getTotals(), result);
    }

    private ProjectPaymentRecordExample buildProjectPaymentRecordExample(final ProjectPaymentRecord projectPaymentRecord) {
        final ProjectPaymentRecordExample example = new ProjectPaymentRecordExample();
        final ProjectPaymentRecordExample.Criteria criteria = example.createCriteria();

        if (Objects.nonNull(projectPaymentRecord.getId())) {
            criteria.andIdEqualTo(projectPaymentRecord.getId());
        }
        if (Objects.nonNull(projectPaymentRecord.getUserId())) {
            criteria.andUserIdEqualTo(projectPaymentRecord.getUserId());
        }
        if (Objects.nonNull(projectPaymentRecord.getTokenInfoId())) {
            criteria.andTokenInfoIdEqualTo(projectPaymentRecord.getTokenInfoId());
        }
        if (Objects.nonNull(projectPaymentRecord.getCurrencyType())) {
            criteria.andCurrencyTypeEqualTo(projectPaymentRecord.getCurrencyType());
        }
        if (StringUtils.isNotBlank(projectPaymentRecord.getTradeNo())) {
            criteria.andTradeNoEqualTo(projectPaymentRecord.getTradeNo());
        }
        if (Objects.nonNull(projectPaymentRecord.getBrokerId())) {
            criteria.andBrokerIdEqualTo(projectPaymentRecord.getBrokerId());
        }
        return example;
    }

    @Override
    public List<ProjectPaymentRecord> getByTokenInfoId(final Long tokenInfoId, Byte currencyType) {
        if (Objects.isNull(tokenInfoId)) {
            return null;
        }

        if (Objects.isNull(currencyType)) {
            currencyType = CurrencyTypeEnum.DEPOSIT.getCode().byteValue();
        }

        final ProjectPaymentRecordExample example = new ProjectPaymentRecordExample();
        example.createCriteria()
                .andTokenInfoIdEqualTo(tokenInfoId)
                .andCurrencyTypeEqualTo(currencyType);

        final List<ProjectPaymentRecord> projectPaymentRecordList = this.projectPaymentRecordRepos.selectByExample(example);

        return projectPaymentRecordList;
    }

}