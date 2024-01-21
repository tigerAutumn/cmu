package cc.newex.dax.extra.service.admin.cgm;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.dax.extra.criteria.cgm.ProjectTokenInfoExample;
import cc.newex.dax.extra.domain.cgm.ProjectTokenInfo;
import cc.newex.dax.extra.dto.cgm.ProjectTokenInfoDTO;
import cc.newex.dax.extra.enums.cgm.DepositStatusEnum;
import cc.newex.dax.extra.enums.cgm.SweetsStatusEnum;

/**
 * 项目基础信息表 服务接口
 *
 * @author mbg.generated
 * @date 2018-08-08 16:21:20
 */
public interface ProjectTokenInfoAdminService extends CrudService<ProjectTokenInfo, ProjectTokenInfoExample, Long> {

    /**
     * 获取project token分页信息
     *
     * @param pager
     * @return
     */
    DataGridPagerResult<ProjectTokenInfoDTO> getProjectTokenPageInfo(DataGridPager<ProjectTokenInfoDTO> pager);

    /**
     * 通过初始审核
     *
     * @param projectTokenInfoDTO
     * @return
     */
    int pass(ProjectTokenInfoDTO projectTokenInfoDTO);

    /**
     * 排期：给初始审核状态的数据设置上线时间
     *
     * @param projectTokenInfoDTO
     * @return
     */
    int schedule(ProjectTokenInfoDTO projectTokenInfoDTO);

    /**
     * 确认完成上线
     *
     * @param projectTokenInfoDTO
     * @return
     */
    int online(ProjectTokenInfoDTO projectTokenInfoDTO);

    /**
     * 更新保证金支付状态
     *
     * @param tokenInfoId
     * @param depositStatus
     * @return
     */
    int updateDepositStatus(Long tokenInfoId, Byte depositStatus);

    /**
     * 更新保证金支付状态
     *
     * @param tokenInfoId
     * @param depositStatus
     * @return
     */
    int updateDepositStatus(Long tokenInfoId, DepositStatusEnum depositStatus);

    /**
     * 更新糖果支付状态
     *
     * @param tokenInfoId
     * @param sweetsStatus
     * @return
     */
    int updateSweetsStatus(Long tokenInfoId, Byte sweetsStatus);

    /**
     * 更新糖果支付状态
     *
     * @param tokenInfoId
     * @param sweetsStatus
     * @return
     */
    int updateSweetsStatus(Long tokenInfoId, SweetsStatusEnum sweetsStatus);

    /**
     * 根据币种全称查询
     *
     * @param token
     * @return
     */
    ProjectTokenInfo getByToken(String token);

    /**
     * 根据币种简称查询
     *
     * @param tokenSymbol
     * @return
     */
    ProjectTokenInfo getByTokenSymbol(String tokenSymbol);

}