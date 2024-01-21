package cc.newex.dax.perpetual.rest.controller.outer.v1.ccex;

import cc.newex.commons.lang.util.DateUtil;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.common.converter.UserBillConverter;
import cc.newex.dax.perpetual.common.enums.BillsColumnEnum;
import cc.newex.dax.perpetual.common.enums.BizErrorCodeEnum;
import cc.newex.dax.perpetual.domain.UserBill;
import cc.newex.dax.perpetual.dto.BillResultDTO;
import cc.newex.dax.perpetual.dto.ParamPageDTO;
import cc.newex.dax.perpetual.dto.UserBillDTO;
import cc.newex.dax.perpetual.rest.controller.base.BaseController;
import cc.newex.dax.perpetual.service.UserBillService;
import cc.newex.dax.perpetual.util.CsvDownloadUtil;
import cc.newex.dax.perpetual.util.WebUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/perpetual/bills")
public class UserBillController extends BaseController {
    @Autowired
    private UserBillService userBillService;

    /**
     * 合约账单
     *
     * @param page
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param currencyCode
     * @param type
     * @param request
     * @return
     */
    @GetMapping("")
    public ResponseResult billList(@RequestParam("page") final Integer page,
                                   @RequestParam("pageSize") final Integer pageSize,
                                   @RequestParam(value = "startDate", required = false) final Long startDate,
                                   @RequestParam(value = "endDate", required = false) final Long endDate,
                                   @RequestParam(value = "currencyCode", required = false) final String currencyCode,
                                   @RequestParam(value = "type", required = false) final Integer type,
                                   final HttpServletRequest request) {

        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);
        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(BizErrorCodeEnum.NOT_LOGIN);
        }
        final Long userId = userDetails.getUserId();
        final Integer brokerId = this.getBrokerId();
        final ParamPageDTO paramPageDTO = ParamPageDTO.toParamPage(page, pageSize);


        final List<Integer> typeList = type != null && type != 0 ? Arrays.asList(type) : Lists.newArrayList();

        final List<UserBill> userBillList = this.userBillService.getBillList(paramPageDTO, userId,
                currencyCode, typeList, startDate, endDate, brokerId);

        final List<UserBillDTO> billInfoList = UserBillConverter.convertToBillInfo(userBillList, 8);

        final BillResultDTO billResultDTO = BillResultDTO.builder()
                .bills(billInfoList)
                .paginate(paramPageDTO)
                .build();

        return ResultUtils.success(billResultDTO);
    }

    /**
     * 下载账户账单流水接口
     *
     * @param startDate
     * @param endDate
     * @param currencyCode
     * @param type
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/download")
    public ResponseResult downloadBillCsv(@RequestParam(value = "startDate", required = false) final Long startDate,
                                          @RequestParam(value = "endDate", required = false) final Long endDate,
                                          @RequestParam("currencyCode") final String currencyCode,
                                          @RequestParam("type") final Integer type,
                                          final HttpServletRequest request, final HttpServletResponse response) {
        final JwtUserDetails userDetails = JwtTokenUtils.getCurrentLoginUser(request);
        if (userDetails.getStatus() != 0) {
            return ResultUtils.failure(BizErrorCodeEnum.NOT_LOGIN);
        }
        final Long userId = userDetails.getUserId();
        final Integer brokerId = this.getBrokerId();

        final ParamPageDTO paramPageDTO = ParamPageDTO.toParamPage(1, WebUtil.MAX_DOWNLOAD_SIZE);

        final List<Integer> typeList = type != null && type != 0 ? Arrays.asList(type) : Lists.newArrayList();

        final List<UserBill> billList = this.userBillService.getBillList(paramPageDTO, userId, currencyCode, typeList,
                startDate, endDate, brokerId);
        final List<UserBillDTO> billInfoList = UserBillConverter.convertToBillInfo(billList, 8);

        final List<String> columnTypes = BillsColumnEnum.getAll();
        final String content = this.userBillService.getBillCsv(billInfoList, columnTypes);

        final String fileName = DateUtil.getFormatDate(new Date()) + ".csv";

        return CsvDownloadUtil.download(fileName, content, response);
    }
}
