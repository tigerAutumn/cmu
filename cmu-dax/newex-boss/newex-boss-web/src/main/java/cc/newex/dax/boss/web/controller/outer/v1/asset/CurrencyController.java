package cc.newex.dax.boss.web.controller.outer.v1.asset;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.client.AdminServiceClient;
import cc.newex.dax.asset.dto.TransferRecordReqDto;
import cc.newex.dax.asset.dto.UserAddressDto;
import cc.newex.dax.boss.admin.criteria.UserExample;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.admin.service.UserService;
import cc.newex.dax.boss.common.enums.BizErrorCodeEnum;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.common.enums.CurrencyBizEnum;
import cc.newex.dax.boss.web.common.util.DateFormater;
import cc.newex.dax.boss.web.controller.common.BaseController;
import cc.newex.dax.boss.web.model.workorder.WorkUserRecordVO;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.dax.users.dto.security.UserFiatSettingDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author newex-jinlong
 * @date 2018-04-28
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/asset/currency")
public class CurrencyController extends BaseController<UserService, User, UserExample, Integer> {

    @Autowired
    private AdminServiceClient adminServiceClient;

    @Autowired
    private UsersClient usersClient;

    /**
     * 根据ID获取地址
     */
    @GetMapping("/userAddress")
    @OpLog(name = "分页获取用户冲提币地址")
    public ResponseResult userAddress(@RequestParam(value = "userId", required = false) final Long userId,
                                      @RequestParam(value = "symbol", required = false) final Integer symbol,
                                      @RequestParam(value = "type", required = false) final Integer type) {

        final UserAddressDto.UserAddressDtoBuilder builder = UserAddressDto.builder();

        builder.biz(1);

        if (userId != null) {
            builder.userId(userId);
        }

        if (symbol != null && symbol >= 0) {
            builder.currency(symbol);
        }

        if (type != null) {
            builder.type(type);
        }

        final UserAddressDto userAddressDto = builder.build();

        final ResponseResult result = this.adminServiceClient.queryUserAddress(userAddressDto);

        return ResultUtil.getDataGridResult(result);
    }

    /**
     * 分页获取用户工单冲提币记录
     */
    @GetMapping("/getUserWorkRecord")
    @OpLog(name = "分页获取用户工单冲提币记录")
    public ResponseResult getUserWorkRecord(@CurrentUser final User loginUser, final DataGridPager pager,
                                            @RequestParam(value = "userId", required = false) final Long userId,
                                            @RequestParam(value = "transferType", required = false) final String transferType) {

        final TransferRecordReqDto transferRecordReqDto = TransferRecordReqDto.builder()
                .userId(userId)
                .transferType(transferType)
                .pageNum(pager.getPage())
                .pageSize(pager.getRows())
                .brokerId(loginUser.getLoginBrokerId())
                .build();

        try {
            final ResponseResult result = this.adminServiceClient.getTransferRecord(transferRecordReqDto);
            if (result != null && result.getCode() == 0) {
                final Map<String, Object> modelMap = new HashMap<>(2);
                final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                final JSONObject jsonObject = JSONObject.parseObject(result.getData().toString());
                final List<WorkUserRecordVO> workUserRecordVOS = JSON.parseArray(jsonObject.get("rows").toString(), WorkUserRecordVO.class);
                final List<WorkUserRecordVO> newList = workUserRecordVOS.stream().map(l -> {
                    String StatusTxt = "未确认";
                    if (l.getStatus() == 3) {
                        StatusTxt = "已确认";
                    }
                    //查询用户详情
                    final ResponseResult<List<UserFiatSettingDTO>> userFiatSetting = this.usersClient.getUserFiatSettingList(userId);
                    final List<UserFiatSettingDTO> data = userFiatSetting.getData();
                    String UserName = "";
                    String bankCard = "";
                    if (data.size() > 0) {
                        UserName = data.get(0).getUserName();
                        bankCard = data.get(0).getBankCard();
                    }
                    return WorkUserRecordVO.builder()
                            .content("<div style='padding: 10px;border-bottom:0px solid #ccc;'><div style='padding-top: 10px;'><div style='float: left;'>ID:" + l.getUserId() + "</div><div style='float: right;color: #00bbee;'>" + StatusTxt + "</div><div class='clear'></div></div><div style='padding-top: 10px;'><div style='float: left;'>充值金额:¥" + l.getAmount() + "</div><div style='float: right;'>" + formatter.format(l.getCreateDate()) + "</div><div class='clear'></div></div><div style='padding-top: 10px;'><div style='float: left;'>用户名:</div><div style='float: right;'>" + UserName + "</div><div class='clear'></div></div><div style='padding-top: 10px;'><div style='float: left;'>银行卡号:</div><div style='float: right;'>" + bankCard + "</div><div class='clear'></div></div></div>")
                            .build();
                }).collect(Collectors.toList());

                modelMap.put("total", jsonObject.get("total").toString());
                modelMap.put("rows", newList);
                return ResultUtils.success(modelMap);
            } else {
                log.error("get  getUserWorkRecord list error msg: " + result.getMsg() + " data:" + result.getData() + "code:" + result.getCode());
                return ResultUtils.failure(result.getMsg());
            }
        } catch (final Exception e) {
            log.error("get  getUserWorkRecord api error ", e);
            return ResultUtils.failure(BizErrorCodeEnum.API_ERROR);
        }

    }

    /**
     * 根据ID获取冲提币记录
     */
    @GetMapping("/getUserRecord")
    @OpLog(name = "分页获取用户冲提币记录")
    public ResponseResult getUserRecord(final DataGridPager pager,
                                        @RequestParam(value = "userId", required = false) final Long userId,
                                        @RequestParam(value = "symbol", required = false) final String symbol,
                                        @RequestParam(value = "startTime", required = false) final String startTimeStr,
                                        @RequestParam(value = "endTime", required = false) final String endTimeStr,
                                        @RequestParam(value = "transferType", required = false) final String transferType) throws ParseException {

        final TransferRecordReqDto.TransferRecordReqDtoBuilder builder = TransferRecordReqDto.builder();

        if (userId != null) {
            builder.userId(userId);
        }

        if (StringUtils.isNotBlank(symbol)) {
            builder.currency(symbol);
        }

        Date startTime = null;
        Date endTime = null;

        if (StringUtils.isNotBlank(startTimeStr)) {
            startTime = DateFormater.parse(startTimeStr);
        }

        if (StringUtils.isNotBlank(endTimeStr)) {
            endTime = DateFormater.parse(endTimeStr);
        }

        builder.startTime(startTime).endTime(endTime);

        if (StringUtils.isNotBlank(transferType)) {
            builder.transferType(transferType);
        }

        final TransferRecordReqDto transferRecordReqDto = builder.build();

        final ResponseResult result = this.adminServiceClient.getTransferRecord(transferRecordReqDto);

        return ResultUtil.getDataGridResult(result);
    }

    @OpLog(name = "获取所有的币种信息")
    @RequestMapping(value = "/currencies", params = "findInstitution")
    public ResponseResult getAllCurrencies(final HttpServletRequest request, @CurrentUser final User loginUser) {
        final ResponseResult result = this.adminServiceClient.getAllCurrencies(CurrencyBizEnum.SPOT.getName(), loginUser.getLoginBrokerId());

        return ResultUtil.getCheckedResponseResult(result);
    }

}
