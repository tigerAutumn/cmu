package cc.newex.dax.perpetual.scheduler.service;

import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.PositionClear;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ClearUserPositionService {

    void clearUserPosition(Contract contract, List<Contract> allContract) throws ExecutionException, InterruptedException;

    void buildUserBill(List<PositionClear> list);
}
