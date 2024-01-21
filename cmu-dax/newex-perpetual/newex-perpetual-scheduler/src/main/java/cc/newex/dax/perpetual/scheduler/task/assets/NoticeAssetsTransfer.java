package cc.newex.dax.perpetual.scheduler.task.assets;

import cc.newex.commons.dictionary.enums.TransferType;
import cc.newex.dax.perpetual.domain.AssetsTransfer;
import cc.newex.dax.perpetual.service.AssetsTransferService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@JobHandler(value = "NoticeAssetsTransfer")
public class NoticeAssetsTransfer extends IJobHandler {

    @Autowired
    private AssetsTransferService assetsTransferService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        final List<AssetsTransfer> transferList = this.assetsTransferService.listNotYetAssetsTransferByType(TransferType.TRANSFER_OUT,
                TransferType.WITHDRAW, TransferType.LOCKED_POSITION);
        if (CollectionUtils.isEmpty(transferList)) {
            return ReturnT.SUCCESS;
        }
        for (final AssetsTransfer a : transferList) {
            this.assetsTransferService.noticeAssetsTransfer(a.getId());
        }
        return ReturnT.SUCCESS;
    }
}
