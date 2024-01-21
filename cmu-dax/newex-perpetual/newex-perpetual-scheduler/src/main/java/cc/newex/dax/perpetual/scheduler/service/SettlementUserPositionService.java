package cc.newex.dax.perpetual.scheduler.service;


import cc.newex.dax.perpetual.domain.Contract;

import java.util.List;

public interface SettlementUserPositionService {

    void settlement(Contract contract, List<Contract> contracts);
}
