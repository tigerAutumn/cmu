package cc.newex.dax.asset.rest.controller.inner.v1;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.enums.AddressType;
import cc.newex.dax.asset.domain.DepositAddress;
import cc.newex.dax.asset.domain.WithdrawAddress;
import cc.newex.dax.asset.dto.UserAddressDto;
import cc.newex.dax.asset.service.DepositAddressService;
import cc.newex.dax.asset.service.WithdrawAddressService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author newex-team
 * @data 2018/6/5
 */
@Slf4j
@RestController
@RequestMapping("/inner/v1/asset")
public class InnerUserAddressController {

    @Autowired
    private WithdrawAddressService withdrawAddressService;
    @Autowired
    private DepositAddressService depositAddressService;


    @PostMapping("/user-address")
    ResponseResult<?> getUserAddress(@RequestBody @Valid final UserAddressDto userAddressDto) {
        log.info("userAddressDto begin:{}", JSONObject.toJSON(userAddressDto));
        try {
            final List<UserAddressDto> userAddressList;

            if (userAddressDto.getType() == AddressType.WITHDRAW.getCode()) {
                final WithdrawAddress withdrawAddress = this.withdrawAddressService.convertFromUserAddressDto(userAddressDto);
                userAddressList = this.withdrawAddressService.getByDomain(withdrawAddress);
                InnerUserAddressController.log.info("userAddressDto end");
            } else {
                final DepositAddress depositAddress = this.depositAddressService.convertFromUserAddressDto(userAddressDto);
                userAddressList = this.depositAddressService.getByDomain(depositAddress);
                log.info("userAddressDto end");
            }

            return ResultUtils.success(userAddressList);

        } catch (final Throwable e) {
            log.info("userAddressDto error", e);

            return ResultUtils.failure("userAddressDto error");
        }
    }

}
