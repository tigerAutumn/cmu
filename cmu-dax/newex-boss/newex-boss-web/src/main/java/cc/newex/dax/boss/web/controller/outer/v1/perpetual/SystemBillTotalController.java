package cc.newex.dax.boss.web.controller.outer.v1.perpetual;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.perpetual.client.SystemBillTotalAdminClient;
import cc.newex.dax.perpetual.dto.SystemBillTotalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * The type System bill total controller.
 *
 * @author better
 * @date create in 2018/11/29 7:04 PM
 */
@RestController
@RequestMapping(value = "/v1/boss/perpetual/systemBillTotal")
public class SystemBillTotalController {

    private final SystemBillTotalAdminClient client;

    /**
     * Instantiates a new System bill total controller.
     *
     * @param client the client
     */
    @Autowired
    public SystemBillTotalController(final SystemBillTotalAdminClient client) {
        this.client = client;
    }

    /**
     * List system bill total response result.
     *
     * @param currencyCode the currency code
     * @param start        the start
     * @param end          the end
     * @return the response result
     */
    @GetMapping(value = "/list")
    @OpLog(name = "查看合约账单")
    @Secured(value = {"ROLE_SUPER_ADMIN", "ROLE_PERPETUAL_SYSTEM_BILL_VIEW"})
    public ResponseResult<List<SystemBillTotalDTO>> listSystemBillTotal(final String currencyCode, final Date start, final Date end) {

        return this.client.querySystemBillTotal(currencyCode, Objects.isNull(start) ? null : start.getTime(), Objects.isNull(end) ? null : end.getTime());
    }
}
