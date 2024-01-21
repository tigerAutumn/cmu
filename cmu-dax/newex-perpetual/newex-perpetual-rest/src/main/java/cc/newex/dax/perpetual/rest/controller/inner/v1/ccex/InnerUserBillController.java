package cc.newex.dax.perpetual.rest.controller.inner.v1.ccex;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.common.converter.UserBillConverter;
import cc.newex.dax.perpetual.domain.UserBill;
import cc.newex.dax.perpetual.dto.InnerUserBillDTO;
import cc.newex.dax.perpetual.service.UserBillService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/inner/v1/perpetual/userBill")
public class InnerUserBillController {

    @Autowired
    private UserBillService userBillService;

    @GetMapping("/getUserBill")
    public ResponseResult<List<InnerUserBillDTO>> getUserbill(@RequestParam(value = "userId", required = false) final Long[] userIds,
                                                              @RequestParam(value = "brokerId", required = false) final Integer[] brokerIds,
                                                              @RequestParam(value = "contractCode", required = false) final String[] contractCodes,
                                                              @RequestParam(value = "startTime", required = false) final Long startTime,
                                                              @RequestParam(value = "endTime", required = false) final Long endTime,
                                                              @RequestParam(value = "id", required = false, defaultValue = "0") final Long id,
                                                              @RequestParam(value = "size", required = false, defaultValue = "1000") final Integer size) {

        final List<UserBill> list = this.userBillService.getBillList(userIds, brokerIds, contractCodes, new Date(startTime), new Date(endTime), id, size);
        if (CollectionUtils.isEmpty(list)) {
            return ResultUtils.success(new ArrayList<>());
        }
        final List<InnerUserBillDTO> dtos = UserBillConverter.convertToInnerBillInfo(list, 16);
        return ResultUtils.success(dtos);
    }
}
