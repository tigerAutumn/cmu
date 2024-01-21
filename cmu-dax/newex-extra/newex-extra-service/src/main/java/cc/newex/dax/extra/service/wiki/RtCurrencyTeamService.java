package cc.newex.dax.extra.service.wiki;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.wiki.RtCurrencyTeamExample;
import cc.newex.dax.extra.domain.wiki.RtCurrencyTeam;
import cc.newex.dax.extra.dto.wiki.RtCurrencyProjectInfoDTO;

import java.util.List;

/**
 * rt代币团队信息 服务接口
 *
 * @author mbg.generated
 * @date 2018 -11-28 14:55:25
 */
public interface RtCurrencyTeamService extends CrudService<RtCurrencyTeam, RtCurrencyTeamExample, Long> {

    /**
     * 获取Rt币种的团队信息
     *
     * @param cid 代币ID
     * @return list
     */
    List<RtCurrencyProjectInfoDTO.TeamDTO> listRtTeamByCid(String cid);
}