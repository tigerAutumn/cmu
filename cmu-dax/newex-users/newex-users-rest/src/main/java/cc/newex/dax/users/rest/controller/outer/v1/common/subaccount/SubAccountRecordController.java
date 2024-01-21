package cc.newex.dax.users.rest.controller.outer.v1.common.subaccount;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.users.dto.subaccount.TransferRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author liutiejun
 * @date 2018-11-06
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/users/subaccount/record")
public class SubAccountRecordController {

    /**
     * 母子账户资金划转
     *
     * @param transferRequestDTO
     * @param request
     * @return
     */
    @PostMapping(value = "/transfer")
    public ResponseResult transfer(@RequestBody final TransferRequestDTO transferRequestDTO, final HttpServletRequest request) {
        return null;
    }

    /**
     * 查看母子账户资金划转记录
     *
     * @param pageIndex
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping(value = "/list")
    public ResponseResult list(@RequestParam(value = "pageIndex", required = true) @NotNull @Min(1) final Integer pageIndex,
                               @RequestParam(value = "pageSize", required = true) @NotNull @Min(1) final Integer pageSize,
                               @RequestParam(value = "userId", required = false) final Long userId,
                               @RequestParam(value = "optType", required = false) final Integer optType,
                               @RequestParam(value = "startTime", required = false) final Long startTime,
                               @RequestParam(value = "endTime", required = false) final Long endTime,
                               final HttpServletRequest request) {
        return null;
    }

}
