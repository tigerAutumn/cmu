package cc.newex.dax.extra.service.admin.cgm;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.dax.extra.criteria.cgm.ProjectPaymentRecordExample;
import cc.newex.dax.extra.domain.cgm.ProjectPaymentRecord;
import cc.newex.dax.extra.dto.cgm.ProjectPaymentRecordDTO;

import java.util.List;

/**
 * 项目支付记录，记录支付CT、糖果活动币的记录 服务接口
 *
 * @author mbg.generated
 * @date 2018-08-13 15:28:20
 */
public interface ProjectPaymentRecordAdminService extends CrudService<ProjectPaymentRecord, ProjectPaymentRecordExample, Long> {

    /**
     * 获取Project PaymentRecord分页信息
     *
     * @param pager
     * @return
     */
    DataGridPagerResult<ProjectPaymentRecordDTO> getProjectPaymentRecordPageInfo(DataGridPager<ProjectPaymentRecordDTO> pager);

    List<ProjectPaymentRecord> getByTokenInfoId(Long tokenInfoId, Byte currencyType);

}