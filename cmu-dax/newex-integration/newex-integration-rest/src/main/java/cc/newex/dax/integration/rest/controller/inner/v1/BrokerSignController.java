package cc.newex.dax.integration.rest.controller.inner.v1;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.domain.conf.BrokerSignConfig;
import cc.newex.dax.integration.dto.message.BrokerSignConfigDTO;
import cc.newex.dax.integration.service.conf.BrokerSignConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/inner/v1/integration/broker")
public class BrokerSignController {

    @Autowired
    private BrokerSignConfigService brokerSignConfigService;

    @PostMapping("/batchupdate")
    public ResponseResult batchUpdate(@RequestBody final List<BrokerSignConfigDTO> list) {

        if (CollectionUtils.isEmpty(list)) {
            return ResultUtils.success();
        }

        final Date nowTime = new Date();
        final List<BrokerSignConfig> record = list.stream()
                .map(l -> BrokerSignConfig.builder()
                        .brokerId(l.getBrokerId())
                        .createTime(nowTime)
                        .modifyTime(nowTime)
                        .sign(l.getSign()).build())
                .collect(Collectors.toList());

        this.brokerSignConfigService.batchAddOnDuplicateKey(record);
        return ResultUtils.success();
    }
}
