package cc.newex.dax.boss.web.controller.outer.v1.activity.lockasset;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.activity.client.lockasset.ActLockAssetClient;
import cc.newex.dax.activity.dto.ccex.lockasset.ActLockAssetDTO;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.activity.lockasset.ActLockAssetVO;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author newex-team
 * @date 2018/11/12 14:29
 */
@RestController
@RequestMapping(value = "/v1/boss/activity/lockasset/actlockasset")
@Slf4j
public class ActLockAssetController {

    @Autowired
    private ActLockAssetClient actLockAssetClient;

    @OpLog(name = "新增锁仓活动")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACT_LOCK_ASSET_ADD"})
    @PostMapping("/add")
    public ResponseResult add(final ActLockAssetVO lockAssetVO,final HttpServletRequest request) throws ParseException {
        final String startTime = request.getParameter("startTime");
        final String endTime = request.getParameter("endTime");
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lockAssetVO.setActStartTime(sdf.parse(startTime));
        lockAssetVO.setActEndTime(sdf.parse(endTime));
        final ModelMapper mapper = new ModelMapper();
        final ResponseResult result = this.actLockAssetClient.add(mapper.map(lockAssetVO,ActLockAssetDTO.class));
        return ResultUtil.getCheckedResponseResult(result);
    }


    @OpLog(name = "编辑锁仓活动")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACT_LOCK_ASSET_EDIT"})
    @PostMapping("/edit")
    public ResponseResult edit(final ActLockAssetVO lockAssetVO, final HttpServletRequest request) throws ParseException{
        if(lockAssetVO.getId()==null){
            return ResultUtils.failure("ID不能为空！");
        }
        final String startTime = request.getParameter("startTime");
        final String endTime = request.getParameter("endTime");
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lockAssetVO.setActStartTime(sdf.parse(startTime));
        lockAssetVO.setActEndTime(sdf.parse(endTime));
        final ModelMapper mapper = new ModelMapper();
        final ResponseResult result = this.actLockAssetClient.edit(mapper.map(lockAssetVO,ActLockAssetDTO.class));
        return ResultUtil.getCheckedResponseResult(result);
    }

    @OpLog(name = "获取锁仓活动列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACT_LOCK_ASSET_VIEW"})
    @RequestMapping("/list")
    public ResponseResult getList(final DataGridPager<ActLockAssetDTO> pager,
                                  @RequestParam(value = "actIdentify", required = false) final String actIdentify,
                                  @RequestParam(value = "actName", required = false) final String actName){
        if(StringUtils.isNotBlank(actIdentify)|| StringUtils.isNotBlank(actName)){
            final ActLockAssetDTO dto =  ActLockAssetDTO.builder().actName(actName).actIdentify(actIdentify).build();
            pager.setQueryParameter(dto);
        }

        final ResponseResult responseResult = this.actLockAssetClient.getByPager(pager);
        return ResultUtil.getDataGridResult(responseResult);

    }

    @OpLog(name = "删除锁仓活动")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACT_LOCK_ASSET_DELETE"})
    @PostMapping("/delete")
    public ResponseResult delete(@RequestParam(value = "id",required = true) Long id){
        final ResponseResult responseResult = this.actLockAssetClient.delete(id);
        return ResultUtil.getCheckedResponseResult(responseResult);
    }

}
