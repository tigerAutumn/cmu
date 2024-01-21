package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.UserBillExample;
import cc.newex.dax.perpetual.domain.UserBill;
import cc.newex.dax.perpetual.dto.ParamPageDTO;
import cc.newex.dax.perpetual.dto.UserBillDTO;

import java.util.Date;
import java.util.List;

/**
 * 账单 服务接口
 *
 * @author newex-team
 * @date 2018-11-01 09:32:34
 */
public interface UserBillService extends CrudService<UserBill, UserBillExample, Long> {
    /**
     * 用户账单查询
     *
     * @param page
     * @param userId
     * @param currencyCode
     * @param typeList
     * @param startDate
     * @param endDate
     * @param brokerId
     * @return
     */
    List<UserBill> getBillList(final ParamPageDTO page, Long userId, String currencyCode, final List<Integer> typeList, Long startDate,
                               Long endDate, Integer brokerId);

    List<UserBill> getBillList(Long[] userIds, Integer[] brokerIds, String[] contractCodes,
                               Date startTime, Date endTime, Long id, Integer size);

    /**
     * 生成csv的账单字符串
     *
     * @param billList    账单列表
     * @param columnTypes 列标题
     * @return
     */
    String getBillCsv(List<UserBillDTO> billList, List<String> columnTypes);

    /**
     * @return
     */
    List<UserBill> getBillList(Long maxId, Date endTime, Integer size);
}
