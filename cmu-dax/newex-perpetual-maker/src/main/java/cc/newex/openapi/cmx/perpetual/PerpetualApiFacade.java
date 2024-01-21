package cc.newex.openapi.cmx.perpetual;

import cc.newex.openapi.cmx.client.ApiClient;
import cc.newex.openapi.cmx.perpetual.service.AccountService;
import cc.newex.openapi.cmx.perpetual.service.OrderService;
import cc.newex.openapi.cmx.perpetual.service.PositionService;
import cc.newex.openapi.cmx.perpetual.service.impl.AccountServiceImpl;
import cc.newex.openapi.cmx.perpetual.service.impl.OrderServiceImpl;
import cc.newex.openapi.cmx.perpetual.service.impl.PositionServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @author newex-team
 * @date 2018-12-08
 */
@Slf4j
public class PerpetualApiFacade {
    private final ApiClient apiClient;

    public PerpetualApiFacade(final ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * 永续 REST API Endpoint
     *
     * @return CCEXEndpoint
     */
    public CCEXEndpoint ccex() {
        return new CCEXEndpoint(this.apiClient);
    }

    public static class CCEXEndpoint {
        private final ApiClient apiClient;

        CCEXEndpoint(final ApiClient apiClient) {
            this.apiClient = apiClient;
        }

        /**
         * 永续订单服务
         */
        public OrderService order() {
            return new OrderServiceImpl(this.apiClient);
        }

        /**
         * 永续持仓服务
         */
        public PositionService position() {
            return new PositionServiceImpl(this.apiClient);
        }

        public AccountService account() {
            return new AccountServiceImpl(this.apiClient);
        }
    }

}
