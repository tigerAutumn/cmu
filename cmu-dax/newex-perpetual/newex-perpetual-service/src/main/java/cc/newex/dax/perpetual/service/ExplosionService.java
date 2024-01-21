package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.ExplosionExample;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Explosion;

import java.util.List;

/**
 * 爆仓 服务接口
 *
 * @author newex-team
 * @date 2018-11-02 11:19:54
 */
public interface ExplosionService extends CrudService<Explosion, ExplosionExample, Long> {

    /**
     * 分页获取待爆仓数据
     * @param id id 游标
     * @param size 获取数据数量
     * @param contractCode 合约
     * @return
     */
    List<Explosion> listExplosion(long id, int size, String contractCode);

    /**
     * 爆仓处理任务
     * @param contract
     * @param allContract
     * @param explosion
     */
    void explosion(Contract contract, List<Contract> allContract, List<Explosion> explosion);

    /**
     * 批量插入，过滤重复数据
     *
     * @param list
     * @return
     */
    int batchInsertOnDuplicateKeyDoNothing(List<Explosion> list);

    /**
     * 根据 主键 批量删除
     *
     * @param ids
     */
    void removeInById(List<Long> ids);
}