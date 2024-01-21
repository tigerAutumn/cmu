package cc.newex.dax.extra.service.cgm;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.cgm.ProjectTokenInfoExample;
import cc.newex.dax.extra.domain.cgm.ProjectTokenInfo;
import cc.newex.dax.extra.enums.cgm.ProjectStatusEnum;

import java.util.List;

/**
 * @author newex-team
 * @date 2018/8/13 下午3:06
 */
public interface ProjectTokenInfoService extends CrudService<ProjectTokenInfo, ProjectTokenInfoExample, Long> {

    /**
     * 获取用户的项目信息
     *
     * @return
     */
    List<ProjectTokenInfo> listProjectByUserId(Long userId, Integer brokerId);

    /**
     * 糖果支付状态变更
     *
     * @param tokenInfoId
     * @return
     */
    int updateSweetsStatus(Long tokenInfoId, Long userId);

    /**
     * 获取待上线的币
     *
     * @return
     * @param brokerId
     */
    List<ProjectTokenInfo> listProjectsByBrokerId(Integer brokerId);

    /**
     * 根据币种全称查询
     *
     * @param token
     * @return
     */
    ProjectTokenInfo getByToken(String token);

    /**
     * 根据币种全称查询
     *
     * @param token
     * @return
     */
    ProjectTokenInfo getByTokenAndNotStatus(String token, ProjectStatusEnum projectStatusEnum);

    /**
     * 根据币种简称查询
     *
     * @param tokenSymbol
     * @return
     */
    ProjectTokenInfo getByTokenSymbol(String tokenSymbol);

    /**
     * 根据币种简称查询
     *
     * @param tokenSymbol
     * @return
     */
    ProjectTokenInfo getByTokenSymbolAndNotStatus(String tokenSymbol, ProjectStatusEnum projectStatusEnum);

}
