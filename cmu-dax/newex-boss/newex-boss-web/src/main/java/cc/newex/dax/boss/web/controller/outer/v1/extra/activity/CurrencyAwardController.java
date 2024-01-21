package cc.newex.dax.boss.web.controller.outer.v1.extra.activity;


import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.activity.client.award.CurrencyAwardClient;
import cc.newex.dax.activity.dto.ccex.award.CurrencyAwardDTO;
import cc.newex.dax.activity.dto.ccex.award.CurrencyAwardRecordDTO;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.activity.CurrencyAwardRecordVO;
import cc.newex.dax.boss.web.model.activity.CurrencyAwardVO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author huxingkong
 * @date 2018-09-30
 */
@RestController
@RequestMapping(value = "/v1/boss/extra/activity/currencyAward")
@Slf4j
public class CurrencyAwardController {

    @Autowired
    private CurrencyAwardClient currencyAwardClient;

    private static final int pointsDataLimit = 500;


    @RequestMapping(value = "/add")
    @OpLog(name = "新增发币活动")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CURRENCY_AWARD_ADD"})
    public ResponseResult add(final CurrencyAwardVO currencyAwardVO, final HttpServletRequest request) {

        //发放人数
        final Long number = 0L;
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);
        final List<CurrencyAwardDTO.LocaleContent> localeContent = Lists.newArrayList();
        localeContent.add(CurrencyAwardDTO.LocaleContent.builder().locale("zh-cn").title(currencyAwardVO.getCnTitle()).build());
        localeContent.add(CurrencyAwardDTO.LocaleContent.builder().locale("en-us").title(currencyAwardVO.getEnTitle()).build());
        final CurrencyAwardDTO currencyAwardDTO = CurrencyAwardDTO.builder()
                .code(currencyAwardVO.getCode())
                .localeContent(localeContent)
                .smsCode(currencyAwardVO.getSmsCode())
                .mailCode(currencyAwardVO.getMailCode())
                .number(number)
                .adminUser(user.getAccount())
                .build();
        return this.currencyAwardClient.add(currencyAwardDTO);
    }

    @RequestMapping(value = "/getByPager")
    @OpLog(name = "分页获活发币活动")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CURRENCY_AWARD_VIEW"})
    public ResponseResult getByPager(final DataGridPager<CurrencyAwardDTO> pager,
                                     @RequestParam(value = "actId", required = false) final Integer actId,
                                     @RequestParam(value = "code", required = false) final String code) {
        final CurrencyAwardDTO currencyAwardDTO = CurrencyAwardDTO.builder()
                .id(actId)
                .build();
        if (StringUtils.isNotBlank(code)) {
            currencyAwardDTO.setCode(code);
        }
        pager.setQueryParameter(currencyAwardDTO);
        final ResponseResult responseResult = this.currencyAwardClient.getByPager(pager);
        return ResultUtil.getCheckedResponseResult(responseResult);
    }

    @RequestMapping(value = "/update")
    @OpLog(name = "更新发币活动")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CURRENCY_AWARD_EDIT"})
    public ResponseResult update(@Valid final CurrencyAwardVO currencyAwardVO,
                                 @RequestParam(value = "id", required = true) final Integer id,
                                 final HttpServletRequest request) {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);
        final List<CurrencyAwardDTO.LocaleContent> localeContent = Lists.newArrayList();
        localeContent.add(CurrencyAwardDTO.LocaleContent.builder().locale("zh-cn").title(currencyAwardVO.getCnTitle()).build());
        localeContent.add(CurrencyAwardDTO.LocaleContent.builder().locale("en-us").title(currencyAwardVO.getEnTitle()).build());
        final CurrencyAwardDTO currencyAwardDTO = CurrencyAwardDTO.builder()
                .id(id)
                .code(currencyAwardVO.getCode())
                .localeContent(localeContent)
                .smsCode(currencyAwardVO.getSmsCode())
                .mailCode(currencyAwardVO.getMailCode())
                .adminUser(user.getAccount())
                .number(currencyAwardVO.getNumber())
                .build();

        return this.currencyAwardClient.update(currencyAwardDTO);
    }

    @RequestMapping(value = "/delete")
    @OpLog(name = "删除发币活动")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CURRENCY_AWARD_DELETE"})
    public ResponseResult delete(final Long id) {

        return this.currencyAwardClient.delete(id);
    }

    @RequestMapping(value = "/all")
    @OpLog(name = "发币活动列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CURRENCY_AWARD_ALL"})
    public ResponseResult all() {
        log.info("get all menu");
        return this.currencyAwardClient.all();
    }

    @RequestMapping(value = "/record/add")
    @OpLog(name = "添加发奖")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CURRENCY_AWARD_RECORD_ADD"})
    public ResponseResult addRecord(final CurrencyAwardRecordVO currencyAwardRecordVO) {
        final List<CurrencyAwardRecordDTO> currencyAwardRecordDTOS = Lists.newArrayList();

        //1,用户id 2,币种 3,数量
        final String[] areas = currencyAwardRecordVO.getArea().split("\\r\\n");
        for (final String area : areas) {
            final String[] singleArea = area.split(",");
            final CurrencyAwardRecordDTO currencyAwardRecordDTO = CurrencyAwardRecordDTO.builder()
                    .actId(Integer.parseInt(currencyAwardRecordVO.getCode()))
                    .userId(Long.parseLong(singleArea[0]))
                    .currencyId(Integer.parseInt(singleArea[1]))
                    .amount(new BigDecimal(singleArea[2]))
                    .build();
            currencyAwardRecordDTOS.add(currencyAwardRecordDTO);
        }

        //分批发送
        final List<CurrencyAwardRecordDTO> updateList = Lists.newArrayList();
        for (int i = 0; i < currencyAwardRecordDTOS.size(); i++) {
            final CurrencyAwardRecordDTO currencyAwardRecordDTO = new CurrencyAwardRecordDTO();
            currencyAwardRecordDTO.setActId(currencyAwardRecordDTOS.get(i).getActId());
            currencyAwardRecordDTO.setUserId(currencyAwardRecordDTOS.get(i).getUserId());
            currencyAwardRecordDTO.setCurrencyId(currencyAwardRecordDTOS.get(i).getCurrencyId());
            currencyAwardRecordDTO.setAmount(currencyAwardRecordDTOS.get(i).getAmount());
            updateList.add(currencyAwardRecordDTO);
            if (pointsDataLimit == updateList.size()) {
                this.currencyAwardClient.addRecord(updateList);
                updateList.clear();
            } else if (i == currencyAwardRecordDTOS.size() - 1) {
                break;
            }
        }
        return this.currencyAwardClient.addRecord(updateList);
    }

    @RequestMapping(value = "/record/getByPager")
    @OpLog(name = "分页获活发币活动记录")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CURRENCY_AWARD_RECORD_VIEW"})
    public ResponseResult getRecordByPager(final DataGridPager<CurrencyAwardRecordDTO> pager,
                                           @RequestParam(value = "actId", required = false) final Integer actId,
                                           @RequestParam(value = "userId", required = false) final Long userId,
                                           @RequestParam(value = "currencyId", required = false) final Integer currencyId) {
        final CurrencyAwardRecordDTO currencyAwardRecordDTO = CurrencyAwardRecordDTO.builder()
                .actId(actId)
                .userId(userId)
                .currencyId(currencyId)
                .build();
        pager.setQueryParameter(currencyAwardRecordDTO);
        final ResponseResult responseResult = this.currencyAwardClient.getRecordByPager(pager);
        return ResultUtil.getCheckedResponseResult(responseResult);
    }
}
