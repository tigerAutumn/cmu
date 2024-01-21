package cc.newex.dax.boss.web.controller.outer.v1.activity.hundred;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.activity.client.celebration.RankAdminClient;
import cc.newex.dax.activity.dto.ccex.celebration.RankConfigDTO;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.common.util.DateFormater;
import cc.newex.dax.boss.web.model.activity.hundred.RankConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author liutiejun
 * @date 2018-12-29
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/activity/rank/config")
public class RankConfigController {

    @Autowired
    private RankAdminClient rankAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增RankConfig")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RANK_CONFIG_ADD"})
    public ResponseResult add(@Valid final RankConfigVO rankConfigVO, final HttpServletRequest request) {
        final RankConfigDTO rankConfigDTO = RankConfigDTO.builder()
                .term(rankConfigVO.getTerm())
                .startDate(DateFormater.parse(rankConfigVO.getStartDate()))
                .endDate(DateFormater.parse(rankConfigVO.getEndDate()))
                .status(rankConfigVO.getStatus())
                .brokerId(rankConfigVO.getBrokerId())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.rankAdminClient.saveRankConfig(rankConfigDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改RankConfig")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RANK_CONFIG_EDIT"})
    public ResponseResult edit(@Valid final RankConfigVO rankConfigVO, final Long id, final HttpServletRequest request) {
        final RankConfigDTO rankConfigDTO = RankConfigDTO.builder()
                .id(id)
                .term(rankConfigVO.getTerm())
                .startDate(DateFormater.parse(rankConfigVO.getStartDate()))
                .endDate(DateFormater.parse(rankConfigVO.getEndDate()))
                .status(rankConfigVO.getStatus())
                .brokerId(rankConfigVO.getBrokerId())
                .updatedDate(new Date())
                .build();

        final ResponseResult result = this.rankAdminClient.updateRankConfig(rankConfigDTO);

        return ResultUtil.getCheckedResponseResult(result);
    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取RankConfig列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RANK_CONFIG_VIEW"})
    public ResponseResult list(@RequestParam(value = "brokerId", required = false) final Integer brokerId, final DataGridPager<RankConfigDTO> pager) {
        final RankConfigDTO.RankConfigDTOBuilder builder = RankConfigDTO.builder();

        if (brokerId != null) {
            builder.brokerId(brokerId);
        }

        final RankConfigDTO rankConfigDTO = builder.build();
        pager.setQueryParameter(rankConfigDTO);

        final ResponseResult responseResult = this.rankAdminClient.listRankConfig(pager);

        return ResultUtil.getDataGridResult(responseResult);
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除RankConfig")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_RANK_CONFIG_REMOVE"})
    public ResponseResult remove(@RequestParam("id") final Long id) {
        final ResponseResult result = this.rankAdminClient.removeRankConfig(id);

        return ResultUtil.getCheckedResponseResult(result);
    }

}
