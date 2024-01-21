package cc.newex.dax.asset.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.criteria.LockedPositionExample;
import cc.newex.dax.asset.criteria.UnlockedPositionExample;
import cc.newex.dax.asset.dao.UnlockedPositionRepository;
import cc.newex.dax.asset.domain.LockedPosition;
import cc.newex.dax.asset.domain.UnlockedPosition;
import cc.newex.dax.asset.service.LockedPositionService;
import cc.newex.dax.asset.service.UnlockedPositionService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 解锁计划表 服务实现
 *
 * @author newex-team
 * @date 2018-07-05
 */
@Slf4j
@Service
public class UnlockedPositionServiceImpl extends AbstractCrudService<UnlockedPositionRepository, UnlockedPosition, UnlockedPositionExample, Long> implements UnlockedPositionService {

    @Autowired
    private LockedPositionService lockedPositionService;

    @Override
    protected UnlockedPositionExample getPageExample(String fieldName, String keyword) {
        UnlockedPositionExample example = new UnlockedPositionExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public boolean unlockedPositionJob(Date time) {
        // 查询锁仓计划
        LockedPositionExample lockedExample = new LockedPositionExample();
        lockedExample.createCriteria()
                .andStatusEqualTo(1)
                .andNextReleaseDateEqualTo(time)
                .andLockAmountGreaterThan(BigDecimal.ZERO);
        List<LockedPosition> lockedPositionList = lockedPositionService.getByExample(lockedExample);
        log.info("query waiting unlock time={} list={}", time, lockedPositionList);
        // 为用户解锁
        for (LockedPosition lockedPosition : lockedPositionList) {
            try {
                BigDecimal releaseAmount = getReleaseAmount(lockedPosition);
                if (releaseAmount.compareTo(BigDecimal.ZERO) > 0) {
                    int result = unlockedAuto(lockedPosition, releaseAmount);
                    Assert.isTrue(result == 1, "unlockedPositionJob error lockedPositionId" + lockedPosition.getId());
                }
            } catch (Throwable e) {
                log.error("用户锁仓自动解锁异常，unlockedPosition={}", lockedPosition, e);
            }
        }
        return true;
    }

    BigDecimal getReleaseAmount(LockedPosition lockedPosition) {
        // 没有解锁计划则解锁所有锁定资产
        if (StringUtils.isEmpty(lockedPosition.getReleaseContent())) {
            return lockedPosition.getLockAmount();
        }

        // 则根据解锁计划金额解锁，若是最后一次解锁（需解锁金额 > lockAmount）则解锁所有锁定资产
        JSONObject content = JSONObject.parseObject(lockedPosition.getReleaseContent());
        BigDecimal proportion = new BigDecimal(content.get("proportion").toString());

        BigDecimal releaseAmount = lockedPosition.getLockAmount();
        BigDecimal proportionAmount = proportion.multiply(lockedPosition.getAmount()).setScale(8, BigDecimal.ROUND_DOWN);
        releaseAmount = proportionAmount.compareTo(releaseAmount) > 0 ? releaseAmount : proportionAmount;

        return releaseAmount;
    }

    private int unlockedAuto(LockedPosition lockedPosition, BigDecimal unlockAmount) {
        int result = 0;
        try {
            // 解锁
            ResponseResult responseResult = lockedPositionService.unlockUserPosition(unlockAmount, lockedPosition, "system", null);
            if (responseResult.getCode() == 0) {
                result = 1;
            }

            // 更新解锁时间及状态
            JSONObject content = JSONObject.parseObject(lockedPosition.getReleaseContent());
            int frequency = Integer.parseInt(content.get("frequency").toString());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(lockedPosition.getNextReleaseDate());
            calendar.add(frequency, 1);

            LockedPosition lockedPosition4Update = new LockedPosition();
            lockedPosition4Update.setId(lockedPosition.getId());
            lockedPosition4Update.setNextReleaseDate(calendar.getTime());
            lockedPositionService.editById(lockedPosition4Update);

            return result;
        } catch (Throwable e) {
            log.error("auto unlock position error，lockedPosition={}, unlockAmount={}", lockedPosition, unlockAmount, e);
            return result;
        }
    }

}