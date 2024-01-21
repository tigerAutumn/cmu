package cc.newex.dax.users.rest.controller.admin.v1.subaccount;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.criteria.subaccount.TransferRecordExample;
import cc.newex.dax.users.domain.subaccount.TransferRecord;
import cc.newex.dax.users.dto.subaccount.TransferRecordDTO;
import cc.newex.dax.users.service.subaccount.TransferRecordService;
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
 * 母子账户划转记录表 Admin Controller
 *
 * @author newex-team
 * @date 2018-11-05 17:21:17
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/users/subaccount/transfer-record")
public class TransferRecordAdminController {

    @Autowired
    private TransferRecordService transferRecordService;

    /**
     * 保存TransferRecord
     *
     * @param transferRecordDTO
     * @return
     */
    @PostMapping(value = "/saveTransferRecord")
    public ResponseResult saveTransferRecord(@RequestBody final TransferRecordDTO transferRecordDTO) {
        final ModelMapper mapper = new ModelMapper();
        final TransferRecord transferRecord = mapper.map(transferRecordDTO, TransferRecord.class);

        final int save = this.transferRecordService.add(transferRecord);

        if (save > 0) {
            return ResultUtils.success(save);
        } else {
            return ResultUtils.failure(BizErrorCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 更新TransferRecord
     *
     * @param transferRecordDTO
     * @return
     */
    @PostMapping(value = "/updateTransferRecord")
    public ResponseResult updateTransferRecord(@RequestBody final TransferRecordDTO transferRecordDTO) {
        final ModelMapper mapper = new ModelMapper();
        final TransferRecord transferRecord = mapper.map(transferRecordDTO, TransferRecord.class);

        final int update = this.transferRecordService.editById(transferRecord);

        if (update > 0) {
            return ResultUtils.success(update);
        } else {
            return ResultUtils.failure(BizErrorCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * List TransferRecord
     *
     * @param pager
     * @return
     */
    @PostMapping(value = "/listTransferRecord")
    public ResponseResult listTransferRecord(@RequestBody final DataGridPager<TransferRecordDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();
        final ModelMapper mapper = new ModelMapper();

        TransferRecord transferRecord = null;

        final TransferRecordDTO queryParameter = pager.getQueryParameter();
        if (Objects.nonNull(queryParameter)) {
            transferRecord = mapper.map(queryParameter, TransferRecord.class);
        }

        final TransferRecordExample example = TransferRecordExample.toExample(transferRecord);
        final List<TransferRecord> list = this.transferRecordService.getByPage(pageInfo, example);
        final List<TransferRecordDTO> transferRecordDTOS = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(entity -> {
                final TransferRecordDTO transferRecordDTO = mapper.map(entity, TransferRecordDTO.class);

                transferRecordDTOS.add(transferRecordDTO);
            });
        }

        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), transferRecordDTOS));
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getTransferRecordById")
    public ResponseResult getTransferRecordById(@RequestParam("id") final Long id) {
        final TransferRecord transferRecord = this.transferRecordService.getById(id);

        final ModelMapper mapper = new ModelMapper();
        final TransferRecordDTO transferRecordDTO = mapper.map(transferRecord, TransferRecordDTO.class);

        return ResultUtils.success(transferRecordDTO);
    }

    /**
     * 删除TransferRecord
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/removeTransferRecord")
    public ResponseResult removeTransferRecord(@RequestParam("id") final Long id) {
        final int remove = this.transferRecordService.removeById(id);

        return ResultUtils.success(remove);
    }

}