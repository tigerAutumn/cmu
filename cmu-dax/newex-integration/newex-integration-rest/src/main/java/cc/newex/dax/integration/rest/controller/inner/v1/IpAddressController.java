package cc.newex.dax.integration.rest.controller.inner.v1;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.common.enums.BizErrorCodeEnum;
import cc.newex.dax.integration.domain.ip.IpLocation;
import cc.newex.dax.integration.dto.ip.IpLocationDTO;
import cc.newex.dax.integration.service.ip.IpAddressService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * author: lingqing.wan
 * date: 2018-04-02 下午5:49
 */
@Slf4j
@RestController
@RequestMapping("/inner/v1/integration/ip")
public class IpAddressController {
    @Resource
    private IpAddressService ipAddressService;

    @GetMapping("/info")
    public ResponseResult<IpLocationDTO> getIpLocationInfo(@RequestParam final String ip) {
        try {
            final IpLocation ipLocation = this.ipAddressService.getIpLocation(ip);
            if (ipLocation == null) {
                return ResultUtils.failure(BizErrorCodeEnum.GET_IP_ADDRESS_FAILURE, new IpLocationDTO());
            }
            final ModelMapper modelMapper = new ModelMapper();
            return ResultUtils.success(modelMapper.map(ipLocation, IpLocationDTO.class));
        } catch (final Exception ex) {
            log.error("get ip address error:{}", ex.getMessage());
            return ResultUtils.failure(BizErrorCodeEnum.GET_IP_ADDRESS_FAILURE, new IpLocationDTO());
        }
    }
}
