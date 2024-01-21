package cc.newex.dax.users.service.frozen;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.GlobalFrozenConfigExample;
import cc.newex.dax.users.domain.GlobalFrozenConfig;

import java.util.List;

/**
 * 系统业务全局冻结配置表 服务接口
 *
 * @author newex-team
 * @date 2018-07-08
 */
public interface GlobalFrozenConfigService
        extends CrudService<GlobalFrozenConfig, GlobalFrozenConfigExample, Integer> {

    int edit(String name, Integer status);

    /**
     * 查询全局冻结配置
     *
     * @param brokerId
     * @return
     */
    List<GlobalFrozenConfig> listAll(Integer brokerId);

    int insert(GlobalFrozenConfig globalFrozenConfig);
}
