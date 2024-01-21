package cc.newex.dax.boss.web.controller.outer.v1.activity.lockasset;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.activity.client.lockasset.ActLockAssetConfigClient;
import cc.newex.dax.activity.dto.ccex.lockasset.ActLockAssetConfigDTO;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.common.enums.LockAssetTypeEnum;
import cc.newex.dax.boss.web.model.activity.lockasset.ActLockAssetConfigVO;
import cc.newex.dax.boss.web.model.activity.lockasset.RewardVO;
import cc.newex.wallet.currency.CurrencyEnum;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author newex-team
 * @date 2018/11/12 14:29
 */
@RestController
@RequestMapping(value = "/v1/boss/activity/lockasset/actlockassetconfig")
@Slf4j
public class ActLockAssetConfigController {

    @Autowired
    private ActLockAssetConfigClient actLockAssetConfigClient;

    @OpLog(name = "新增锁仓活动")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACT_LOCK_ASSET_ADD"})
    @PostMapping("/add")
    public ResponseResult add(final ActLockAssetConfigVO configVO, final HttpServletRequest request) throws ParseException {
        final String startTime = request.getParameter("startTime");
        final String endTime = request.getParameter("endTime");
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        configVO.setActStartTime(sdf.parse(startTime));
        configVO.setActEndTime(sdf.parse(endTime));
        configVO.setCurrencySymbol(CurrencyEnum.parseValue(configVO.getCurrencyId()).getName());
        configVO.setUnit((byte)2);//默认设置周期单位为天
        final String checkMsg = this.checkReward(configVO);
        if(checkMsg != null){
            return ResultUtils.failure(checkMsg);
        }
        final ModelMapper mapper = new ModelMapper();
        final ResponseResult result = this.actLockAssetConfigClient.add(mapper.map(configVO, ActLockAssetConfigDTO.class));
        return ResultUtil.getCheckedResponseResult(result);
    }

    private String checkReward(final ActLockAssetConfigVO configVO){

        if(LockAssetTypeEnum.UNLOCK_BY_STAGES.getType()==configVO.getType().intValue()){
            if(configVO.getUnlockRateRemainder()==null || configVO.getPeriod()==null){
                return "分期解锁比例或分期解锁周期长度未填写！";
            }
        }
        if(LockAssetTypeEnum.LUCKWIN.getType()==configVO.getType().intValue()){
            return null;
        }
        final List<RewardVO> list = JSON.parseArray(configVO.getReward(),RewardVO.class);
        if(list == null || list.size()<=0 ){
            return "未添加奖励配置！";
        }
        if(list.size()>4){
            return "奖励配置不能超过四项！";
        }
        for(RewardVO vo : list){
            if(StringUtils.isBlank(vo.getPeriodName())){
                return "周期名称有空值！";
            }
            if(StringUtils.isBlank(vo.getRewardName())){
                return "收益说明有空值！";
            }
            if( vo.getPeriodValue() == null){
                return "周期时长有空值！";
            }
            if(vo.getRewardValue()==null || vo.getRewardValue().compareTo(new BigDecimal(0))<0 || vo.getRewardValue().compareTo(new BigDecimal(100))>=0){

                return "奖励系数不能为空，且在0--100之间！";
            }
        }
        return null;

    }

    @OpLog(name = "编辑锁仓活动")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACT_LOCK_ASSET_EDIT"})
    @PostMapping("/edit")
    public ResponseResult edit(final ActLockAssetConfigVO configVO, final HttpServletRequest request) throws ParseException{
        if(configVO.getId()==null){
            return ResultUtils.failure("ID不能为空！");
        }
        final String startTime = request.getParameter("startTime");
        final String endTime = request.getParameter("endTime");
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        configVO.setActStartTime(sdf.parse(startTime));
        configVO.setActEndTime(sdf.parse(endTime));
        configVO.setCurrencySymbol(CurrencyEnum.parseValue(configVO.getCurrencyId()).getName());
        final String checkMsg = this.checkReward(configVO);
        if(checkMsg != null){
            return ResultUtils.failure(checkMsg);
        }
        final ModelMapper mapper = new ModelMapper();
        final ResponseResult result = this.actLockAssetConfigClient.edit(mapper.map(configVO,ActLockAssetConfigDTO.class));
        return ResultUtil.getCheckedResponseResult(result);
    }

    @OpLog(name = "获取锁仓活动列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACT_LOCK_ASSET_VIEW"})
    @RequestMapping("/list")
    public ResponseResult getList(final DataGridPager<ActLockAssetConfigDTO> pager,
                                  @RequestParam(value = "actIdentify", required = false) final String actIdentify,
                                  @RequestParam(value = "actName", required = false) final String actName){
        if(StringUtils.isNotBlank(actName) || StringUtils.isNotBlank(actIdentify)){
            final ActLockAssetConfigDTO dto =  ActLockAssetConfigDTO.builder().actName(actName).actIdentify(actIdentify) .build();
            pager.setQueryParameter(dto);
        }

        final ResponseResult responseResult = this.actLockAssetConfigClient.getByPager(pager);
        return ResultUtil.getDataGridResult(responseResult);

    }

    @OpLog(name = "删除锁仓活动")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACT_LOCK_ASSET_DELETE"})
    @PostMapping("/delete")
    public ResponseResult delete(@RequestParam(value = "id",required = true) Long id,@RequestParam(value = "actIdentify",required = true) String actIdentify){
        final ResponseResult responseResult = this.actLockAssetConfigClient.delete(actIdentify,id);
        return ResultUtil.getCheckedResponseResult(responseResult);
    }

}
