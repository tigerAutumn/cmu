package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.common.enums.BillTypeEnum;
import cc.newex.dax.perpetual.criteria.UserBillExample;
import cc.newex.dax.perpetual.data.UserBillRepository;
import cc.newex.dax.perpetual.domain.UserBill;
import cc.newex.dax.perpetual.dto.ParamPageDTO;
import cc.newex.dax.perpetual.dto.UserBillDTO;
import cc.newex.dax.perpetual.service.UserBillService;
import cc.newex.dax.perpetual.util.PageUtil;
import cc.newex.dax.perpetual.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 账单 服务实现
 *
 * @author newex-team
 * @date 2018-11-01 09:32:34
 */
@Slf4j
@Service
public class UserBillServiceImpl
        extends AbstractCrudService<UserBillRepository, UserBill, UserBillExample, Long>
        implements UserBillService {
    @Autowired
    private UserBillRepository userBillRepository;

    @Override
    protected UserBillExample getPageExample(final String fieldName, final String keyword) {
        final UserBillExample example = new UserBillExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    private UserBillExample buildUserBillExample(final Long userId, final Long startDate,
                                                 final Long endDate, final String currencyCode, final List<Integer> recordTypes,
                                                 final Integer brokerId) {
        final UserBillExample example = new UserBillExample();
        final UserBillExample.Criteria criteria = example.createCriteria();

        criteria.andUserIdEqualTo(userId);

        if (startDate != null) {
            criteria.andCreatedDateGreaterThanOrEqualTo(new Date(startDate));
        }

        if (endDate != null) {
            criteria.andCreatedDateLessThanOrEqualTo(new Date(endDate));
        }

        if (StringUtils.isNotEmpty(currencyCode)) {
            criteria.andCurrencyCodeEqualTo(currencyCode.toLowerCase());
        }

        if (CollectionUtils.isNotEmpty(recordTypes)) {
            criteria.andTypeIn(recordTypes);
        }


        if (brokerId != null) {
            criteria.andBrokerIdEqualTo(brokerId);
        }

        return example;
    }

    @Override
    public List<UserBill> getBillList(final ParamPageDTO page, final Long userId,
                                      final String currencyCode, final List<Integer> typeList, final Long startDate,
                                      final Long endDate, final Integer brokerId) {
        final PageInfo pageInfo = PageUtil.toPageInfo(page);
        final UserBillExample example =
                this.buildUserBillExample(userId, startDate, endDate, currencyCode, typeList, brokerId);

        pageInfo.setSortItem("id");
        pageInfo.setSortType(PageInfo.SORT_TYPE_DES);

        final List<UserBill> userBillList = this.getByPage(pageInfo, example);
        page.setTotal((int) pageInfo.getTotals());

        return userBillList;
    }

    @Override
    public List<UserBill> getBillList(final Long[] userIds, final Integer[] brokerIds, final String[] contractCodes,
                                      final Date startTime, final Date endTime, final Long id, final Integer size) {
        final UserBillExample example = new UserBillExample();
        final UserBillExample.Criteria criteria = example.createCriteria();
        if (userIds != null && userIds.length > 0) {
            criteria.andUserIdIn(Arrays.asList(userIds));
        }
        if (brokerIds != null && brokerIds.length > 0) {
            criteria.andBrokerIdIn(Arrays.asList(brokerIds));
        }
        if (contractCodes != null && contractCodes.length > 0) {
            criteria.andContractCodeIn(Arrays.asList(contractCodes));
        }
        if (startTime != null) {
            criteria.andCreatedDateGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andCreatedDateLessThan(endTime);
        }
        if (id != null) {
            criteria.andIdGreaterThan(id);
        }
        final PageInfo pageInfo = new PageInfo();
        pageInfo.setStartIndex(0);
        pageInfo.setPageSize(size);
        pageInfo.setSortItem("id");
        pageInfo.setSortType(PageInfo.SORT_TYPE_ASC);
        return this.getByPage(pageInfo, example);
    }

    @Override
    public String getBillCsv(final List<UserBillDTO> billList, final List<String> columnTypes) {
        final StringBuilder billBuffer = new StringBuilder();

        billBuffer.append(StringUtils.join(columnTypes, ","));

        billList.forEach(row -> billBuffer.append("\r")
                .append(new SimpleDateFormat(WebUtil.DATE_FORMAT).format(row.getCreatedDate())).append(",")
                .append(BillTypeEnum.getDesc(row.getType())).append(",")
                .append(row.getCurrencyCode()).append(",")
                .append(row.getDetailSide()).append(",")
                .append(new BigDecimal(row.getSize()).setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString()).append(",")
                .append(new BigDecimal(row.getBalance()).setScale(8, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString()).append(",")
        );

        return billBuffer.toString();
    }

    @Override
    public List<UserBill> getBillList(final Long maxId, final Date endTime, final Integer size) {
        final UserBillExample example = new UserBillExample();
        final UserBillExample.Criteria criteria = example.createCriteria();
        criteria.andIdGreaterThan(maxId);
        criteria.andCreatedDateLessThan(endTime);
        final PageInfo pageInfo = new PageInfo();
        pageInfo.setSortType(maxId == 0 ? PageInfo.SORT_TYPE_DES : PageInfo.SORT_TYPE_ASC);
        pageInfo.setSortItem("id");
        pageInfo.setStartIndex(0);
        pageInfo.setPageSize(maxId == 0 ? 1 : size);

        return this.getByPage(pageInfo, example);
    }
}
