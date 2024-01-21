package cc.newex.dax.market.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.market.common.enums.BizErrorCodeEnum;
import cc.newex.dax.market.criteria.MinePoolShareDataExample;
import cc.newex.dax.market.data.MinePoolShareDataRepository;
import cc.newex.dax.market.domain.MinePoolShareData;
import cc.newex.dax.market.service.MinePoolShareDataService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Service
public class MinePoolShareDataServiceImpl
    extends AbstractCrudService<MinePoolShareDataRepository, MinePoolShareData, MinePoolShareDataExample, Long>
    implements MinePoolShareDataService {

    @Autowired
    private MinePoolShareDataRepository minePoolShareDataRepos;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    protected MinePoolShareDataExample getPageExample(final String fieldName, final String keyword) {
        final MinePoolShareDataExample example = new MinePoolShareDataExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    @Transactional
    public ResponseResult updateMinePoolData(final List<MinePoolShareData> dataList, final String poolMode) {
        final MinePoolShareDataExample example = new MinePoolShareDataExample();
        example.createCriteria().andPoolModeEqualTo(poolMode);
        if (this.minePoolShareDataRepos.deleteByExample(example) <= 0) {
            MinePoolShareDataServiceImpl.log.error("updateMinePoolData delete by poolMode, no data deleted! poolMode: {} ", poolMode);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtils.failure(BizErrorCodeEnum.TICKET_DATA_NOT_FOUND);
        }

        // 设置数据的创建时间。
        final Date now = new Date();
        dataList.parallelStream().forEach((item) -> {
            // 算力占比默认值。
            if (item.getMarketShareOfPool() == null) {
                item.setMarketShareOfPool(0.00D);
            }

            // 块数量默认值。
            if (item.getBlockAmount() == null) {
                item.setBlockAmount(0);
            }

            // 空块占比默认值。
            if (item.getEmptyBlockProportion() == null) {
                item.setEmptyBlockProportion(0.0000D);
            }

            // 平均块大小默认值。
            if (item.getAvgBlockSize() == null) {
                item.setAvgBlockSize(0L);
            }

            // 平均块矿工费默认值。
            if (item.getAvgBlockMinerFee() == null) {
                item.setAvgBlockMinerFee(0.0000D);
            }

            // 矿工费与块奖励占比默认值。
            if (item.getMinerFeeAndBlockBonusProportion() == null) {
                item.setMinerFeeAndBlockBonusProportion(0.0000D);
            }

            // 排名默认值。
            if (item.getRank() == null) {
                item.setRank(0);
            }

            item.setCreatedDate(now);

        });

        if (this.minePoolShareDataRepos.batchInsert(dataList) != dataList.size()) {
            MinePoolShareDataServiceImpl.log.error("updateMinePoolData insert result not equal to data size! poolMode: {}, data: {}", poolMode,
                JSONObject.toJSONString(dataList));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtils.failure(BizErrorCodeEnum.SYSTEM_ERROR);
        }

        return ResultUtils.success();
    }

    @Override
    public void putOrePoolToRedis() {
        final List<MinePoolShareData> data = this.minePoolShareDataRepos.selectAll();
        this.stringRedisTemplate.opsForValue().set("newAllPoolData", JSONObject.toJSONString(data));
        MinePoolShareDataServiceImpl.log.info("msg:矿池存到redis");

    }
}