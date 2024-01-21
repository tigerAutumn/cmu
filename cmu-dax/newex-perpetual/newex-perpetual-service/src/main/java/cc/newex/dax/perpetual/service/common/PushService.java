package cc.newex.dax.perpetual.service.common;

import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.dto.enums.PushTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public interface PushService {

    void pushData(List<PushContext> contexts);
    /**
     * 推送
     *
     * @param contract
     * @param pushTypeEnum
     * @param data
     */
    void pushData(Contract contract, PushTypeEnum pushTypeEnum, String data);

    void pushData(Contract contract, PushTypeEnum pushTypeEnum, String data, boolean contractCode, boolean base, boolean qoute);

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class PushContext {
        private Contract contract;
        private PushTypeEnum pushTypeEnum;
        private String data;
        private boolean contractCode;
        private boolean base;
        private boolean qoute;
    }
}
