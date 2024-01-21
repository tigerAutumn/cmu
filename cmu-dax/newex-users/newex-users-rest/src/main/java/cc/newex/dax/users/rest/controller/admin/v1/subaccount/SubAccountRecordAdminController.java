package cc.newex.dax.users.rest.controller.admin.v1.subaccount;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.criteria.subaccount.SubAccountRecordExample;
import cc.newex.dax.users.domain.subaccount.SubAccountRecord;
import cc.newex.dax.users.dto.subaccount.SubAccountRecordDTO;
import cc.newex.dax.users.service.subaccount.SubAccountRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 子账户绑定、解锁的操作记录表 Admin Controller
 *
 * @author newex-team
 * @date 2018-11-06 17:06:44
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/users/subaccount/record")
public class SubAccountRecordAdminController {

    @Autowired
    private SubAccountRecordService subAccountRecordService;

    /**
     * 保存SubAccountRecord
     *
     * @param subAccountRecordDTO
     * @return
     */
    @PostMapping(value = "/saveSubAccountRecord")
    public ResponseResult saveSubAccountRecord(@RequestBody final SubAccountRecordDTO subAccountRecordDTO) {
        final ModelMapper mapper = new ModelMapper();
        final SubAccountRecord subAccountRecord = mapper.map(subAccountRecordDTO, SubAccountRecord.class);

        final int save = this.subAccountRecordService.add(subAccountRecord);

        if (save > 0) {
            return ResultUtils.success(save);
        } else {
            return ResultUtils.failure(BizErrorCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 更新SubAccountRecord
     *
     * @param subAccountRecordDTO
     * @return
     */
    @PostMapping(value = "/updateSubAccountRecord")
    public ResponseResult updateSubAccountRecord(@RequestBody final SubAccountRecordDTO subAccountRecordDTO) {
        final ModelMapper mapper = new ModelMapper();
        final SubAccountRecord subAccountRecord = mapper.map(subAccountRecordDTO, SubAccountRecord.class);

        final int update = this.subAccountRecordService.editById(subAccountRecord);

        if (update > 0) {
            return ResultUtils.success(update);
        } else {
            return ResultUtils.failure(BizErrorCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * List SubAccountRecord
     *
     * @param pager
     * @return
     */
    @PostMapping(value = "/listSubAccountRecord")
    public ResponseResult listSubAccountRecord(@RequestBody final DataGridPager<SubAccountRecordDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();
        final ModelMapper mapper = new ModelMapper();

        SubAccountRecord subAccountRecord = null;

        final SubAccountRecordDTO queryParameter = pager.getQueryParameter();
        if (Objects.nonNull(queryParameter)) {
            subAccountRecord = mapper.map(queryParameter, SubAccountRecord.class);
        }

        final SubAccountRecordExample example = SubAccountRecordExample.toExample(subAccountRecord);
        final List<SubAccountRecord> list = this.subAccountRecordService.getByPage(pageInfo, example);
        final List<SubAccountRecordDTO> subAccountRecordDTOS = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(entity -> {
                final SubAccountRecordDTO subAccountRecordDTO = mapper.map(entity, SubAccountRecordDTO.class);

                subAccountRecordDTOS.add(subAccountRecordDTO);
            });
        }

        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), subAccountRecordDTOS));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getSubAccountRecordById")
    public ResponseResult getSubAccountRecordById(@RequestParam("id") final Long id) {
        final SubAccountRecord subAccountRecord = this.subAccountRecordService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final SubAccountRecordDTO subAccountRecordDTO = mapper.map(subAccountRecord, SubAccountRecordDTO.class);

        return ResultUtils.success(subAccountRecordDTO);
    }

    /**
     * 删除SubAccountRecord
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeSubAccountRecord")
    public ResponseResult removeSubAccountRecord(@RequestParam("id") final Long id) {
        final int remove = this.subAccountRecordService.removeById(id);

        return ResultUtils.success(remove);
    }

}