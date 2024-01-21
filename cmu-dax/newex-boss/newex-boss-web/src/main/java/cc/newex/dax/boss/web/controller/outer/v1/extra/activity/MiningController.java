package cc.newex.dax.boss.web.controller.outer.v1.extra.activity;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.activity.client.ActivityConfigClient;
import cc.newex.dax.activity.client.ActivityPropClient;
import cc.newex.dax.activity.client.mining.ActMiningClient;
import cc.newex.dax.activity.client.mining.ActMiningSubClient;
import cc.newex.dax.activity.dto.ccex.ActivityConfigDTO;
import cc.newex.dax.activity.dto.ccex.ActivityPropDTO;
import cc.newex.dax.activity.dto.ccex.mining.ActMiningDTO;
import cc.newex.dax.activity.dto.ccex.mining.ActMiningSubDTO;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.activity.ActPropVO;
import cc.newex.dax.boss.web.model.activity.ConfigVO;
import cc.newex.dax.boss.web.model.activity.MiningSubVO;
import cc.newex.dax.boss.web.model.activity.MiningVO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/boss/activity/mining")
public class MiningController {

    @Resource
    ActivityConfigClient activityConfigClient;

    @Resource
    ActivityPropClient activityPropClient;

    @Resource
    ActMiningClient actMiningClient;

    @Resource
    ActMiningSubClient actMiningSubClient;

    @GetMapping(value = "/list")
    @OpLog(name = "活动列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_MINING_VIEW"})
    public ResponseResult list() {
        final ResponseResult<List<ActMiningDTO>> source = actMiningClient.getMining();
        if (source.getCode() == 0 && source.getData() != null) {
            final List<MiningVO> result = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            source.getData().forEach(s -> {
                final MiningVO vo = MiningVO.builder()
                        .actId(s.getId())
                        .activityIdd(s.getActivityId())
                        .language(s.getLanguage())
                        .title(s.getTitle())
                        .titleCN(s.getTitleCH())
                        .titleEN(s.getTitleEH())
                        .startDate(sdf.format(s.getStartDate()))
                        .endDate(sdf.format(s.getEndDate()))
                        .finishFlag(s.getFinishFlag())
                        .currencyCode(s.getCurrencyCode().toUpperCase())
                        .productIds(s.getProductIds())
                        .reward(s.getReward())
                        .dailyLimit(s.getDailyLimit())
                        .totalLimit(s.getTotalLimit())
                        .personDailyLimit(s.getPersonDailyLimit())
                        .feeHoldReturn(s.getFeeHoldReturn())
                        .feeProjectReturn(s.getFeeProjectReturn())
                        .personHoldLimit(s.getPersonHoldLimit())
                        .projectAccount(s.getProjectAccount())
                        .projectReturnFlag(s.getProjectReturnFlag())
                        .personHolds(s.getPersonHolds())
                        .totalMinings(s.getTotalMinings())
                        .effectiveHoldMinings(s.getEffectiveHoldMinings())
                        .platformMinings(s.getPlatformMinings())
                        .personMining(s.getPersonMining())
                        .miningReward(s.getMiningReward())
                        .feeReward(s.getFeeReward())
                        .explainLink(s.getExplainLink())
                        .totalReward(s.getTotalReward())
                        .online(s.getOnline()).build();
                result.add(vo);
            });
            return ResultUtils.success(result);
        }
        return ResultUtils.failure(source.getMsg());
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "修改挖矿活动")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_MINING_EDIT"})
    public ResponseResult edit(@Valid final MiningVO po) {
        log.info(po.toString());
        ActMiningDTO actMiningDTO = new ActMiningDTO();
        try {
            actMiningDTO = ActMiningDTO.builder()
                    .id(po.getActId())
                    .titleCH(po.getTitleCN())
                    .titleEH(po.getTitleEN())
                    .activityId(po.getActivityIdd())
                    .totalLimit(po.getTotalLimit())
                    .currencyCode(po.getCurrencyCode().toLowerCase())
                    .productIds(po.getProductIds())
                    .effectiveHoldMinings(po.getEffectiveHoldMinings())
                    .feeHoldReturn(po.getFeeHoldReturn())
                    .reward(po.getReward())
                    .personDailyLimit(po.getPersonDailyLimit())
                    .projectAccount(po.getProjectAccount())
                    .dailyLimit(po.getDailyLimit())
                    .totalMinings(po.getTotalMinings())
                    .personHolds(po.getPersonHolds())
                    .projectReturnFlag(po.getProjectReturnFlag())
                    .feeProjectReturn(po.getFeeProjectReturn())
                    .personHoldLimit(po.getPersonHoldLimit())
                    .platformMinings(po.getPlatformMinings())
                    .explainLink(po.getExplainLink())
                    .language(po.getLanguage())
                    .online(po.getOnline())
                    .feeReward(po.getFeeReward())
                    .startDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(po.getStartDate()))
                    .endDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(po.getEndDate())).build();
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        log.info(actMiningDTO.toString());
        final ResponseResult<Boolean> result = actMiningClient.updateMining(actMiningDTO);
        if (result.getCode() == 0 && result.getData()) {
            return ResultUtils.success();
        }
        return ResultUtils.failure(result.getMsg());
    }

    @PostMapping(value = "/delete")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_MINING_DELETE"})
    public ResponseResult delete(@RequestParam(value = "id") Long id) {
        ResponseResult<Boolean> result = actMiningClient.deleteMining(id);
        if (result.getCode() == 0 && result.getData()) {
            return ResultUtils.success();
        }
        return ResultUtils.failure(result.getMsg());
    }
    @GetMapping(value = "/listSub")
    @OpLog(name = "活动列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_MINING_SUB_VIEW"})
    public ResponseResult listSub(){
        final ResponseResult<List<ActMiningSubDTO>> result = actMiningSubClient.getMiningSub();
        return ResultUtil.getCheckedResponseResult(result);
    }


    @PostMapping(value = "/editSub")
    @OpLog(name = "sub")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_MINING_SUB_EDIT"})
    public ResponseResult editSub(@Valid final MiningSubVO po) {
        final ActMiningSubDTO actMiningSubDTO = ActMiningSubDTO.builder()
                .productIds(po.getProductIds().toString())
                .id(po.getMiningId()).build();
        final ResponseResult<Boolean> source = actMiningSubClient.updateMiningSub(actMiningSubDTO);
        if (source.getCode() == 0 && source.getData()) {
            return ResultUtils.success();
        }
        return ResultUtils.failure(source.getMsg());
    }

    @GetMapping(value = "/config")
    @OpLog(name = "获取活动配置")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONFIG_VIEW"})
    public ResponseResult activity(){
        final ResponseResult<List<ActivityConfigDTO>> source = activityConfigClient.getConfig();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (source.getCode() == 0) {
            final List<ConfigVO> result = new ArrayList<>();
            source.getData().forEach(s -> {
                final ConfigVO vo = new ConfigVO();
                vo.setActId(s.getId());
                vo.setActKey(s.getActKey());
                vo.setActName(s.getActName());
                vo.setActSort(s.getSort());
                vo.setWhileList(s.getWhileList());
                vo.setStatus(s.getStatus());
                vo.setStartDate(sdf.format(s.getStartDate()));
                vo.setEndDate(sdf.format(s.getEndDate()));
                result.add(vo);
            });
            return ResultUtils.success(result);
        }
        return ResultUtils.success(source.getMsg());
    }

    @PostMapping(value = "/editConfig")
    @OpLog(name = "修改状态")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONFIG_EDIT"})
    public ResponseResult status(@Valid final ConfigVO vo) {
        ActivityConfigDTO configDTO = null;
        try {
            configDTO = ActivityConfigDTO.builder()
                    .id(vo.getActId())
                    .actKey(vo.getActKey())
                    .startDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(vo.getStartDate()))
                    .endDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(vo.getEndDate()))
                    .actName(vo.getActName())
                    .status(vo.getStatus())
                    .sort(vo.getActSort())
                    .whileList(vo.getWhileList()).build();
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        final ResponseResult<Boolean> result = activityConfigClient.updateConfig(configDTO);
        if (result.getCode() == 0 && result.getData()) {
            return ResultUtils.success();
        }

        return ResultUtils.failure(result.getMsg());

    }

    @GetMapping(value = "/getProp")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_MANAGE_VIEW"})
    public ResponseResult pro(){
        final ResponseResult<List<ActivityPropDTO>> source = activityPropClient.getActivityProp();
        if (source.getCode() == 0) {
            final List<ActPropVO> result = new ArrayList<>();
            source.getData().forEach(s -> {
                final ActPropVO vo = new ActPropVO();
                vo.setActivityPropKey(s.getActivityPropKey());
                vo.setPropId(s.getId());
                vo.setActivityPropValue(s.getActivityPropValue());
                vo.setActKey(s.getActKey());
                result.add(vo);
            });
            return ResultUtils.success(result);
        }
        return ResultUtils.failure(source.getMsg());
    }

    @PostMapping(value = "/editProp")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_MANAGE_EDIT"})
    public ResponseResult editProp(@Valid final ActPropVO po) {
        final ActivityPropDTO activityPropDTO = ActivityPropDTO.builder()
                .id(po.getPropId())
                .activityPropKey(po.getActivityPropKey())
                .activityPropValue(po.getActivityPropValue())
                .actKey(po.getActKey()).build();
        final ResponseResult<Boolean> result = activityPropClient.updateActivityProp(activityPropDTO);
        if (result.getCode() == 0 && result.getData()) {
            return ResultUtils.success();
        }
        return ResultUtils.failure(result.getMsg());
    }

    @GetMapping(value = "/getActivity")
    public JSONArray getActivity() {
        final ResponseResult<List<ActivityConfigDTO>> source = activityConfigClient.getConfig();
        JSONArray jsonArray = new JSONArray();
        source.getData().forEach(v -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("activityId", v.getId());
            jsonObject.put("name", v.getActName());
            jsonArray.add(jsonObject);
        });
        return jsonArray;
    }
}
