package cc.newex.dax.market.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.market.criteria.MinePoolShareDataExample;
import cc.newex.dax.market.domain.MinePoolShareData;

import java.util.List;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */
public interface MinePoolShareDataService
        extends CrudService<MinePoolShareData, MinePoolShareDataExample, Long> {

    /**
     * 批量更新指定poolMode的所有数据。
     *
     * @param poolMode
     * @return
     */
    ResponseResult updateMinePoolData(List<MinePoolShareData> dataList, String poolMode);

    void putOrePoolToRedis();

}
