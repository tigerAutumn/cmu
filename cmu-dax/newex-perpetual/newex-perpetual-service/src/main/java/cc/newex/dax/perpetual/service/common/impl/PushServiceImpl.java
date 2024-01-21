package cc.newex.dax.perpetual.service.common.impl;

import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.push.PushInfo;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.dto.enums.PushTypeEnum;
import cc.newex.dax.perpetual.service.common.PushService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PushServiceImpl implements PushService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void pushData(final List<PushContext> contexts) {
        if (CollectionUtils.isEmpty(contexts)) {
            return;
        }
        for (final PushContext p : contexts) {
            this.pushData(p.getContract(), p.getPushTypeEnum(), p.getData(), p.isContractCode(), p.isBase(), p.isQoute());
        }
    }

    @Override
    public void pushData(final Contract contract, final PushTypeEnum pushTypeEnum, final String data) {
        this.pushData(contract, pushTypeEnum, data, false, true, true);
    }

    @Override
    public void pushData(final Contract contract, final PushTypeEnum pushTypeEnum, final String data,
                         final boolean contractCode, final boolean base, final boolean qoute) {
        /*PushServiceImpl.log.info("PushServiceImpl pushData, contract : {}, pushType : {}, content : {}",
                contract.getContractCode(), pushTypeEnum, data);*/
        try {
            final String compress = PushInfo.builder().biz(PerpetualConstants.PERPETUAL_BIZ).type(pushTypeEnum.name())
                    .zip(true).contractCode(contractCode ? contract.getContractCode() : null)
                    .base(base ? contract.getBase() : null)
                    .quote(qoute ? contract.getQuote() : null)
                    .data(data).build().compress();
            this.stringRedisTemplate.convertAndSend(PerpetualConstants.buildChannel(contract),
                    compress);
        } catch (final Exception e) {
            PushServiceImpl.log.error("PushServiceImpl pushData error: ", e);
        }
    }
}
