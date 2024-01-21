package cc.newex.dax.integration.service.msg.provider;

import java.util.Map;

/**
 * 信息提供者抽象类
 *
 * @author newex-team
 * @date 2018-04-13
 */
public abstract class AbstractMessageProvider implements MessageProvider {

    private Double weight = 5.0;

    private Integer brokerId;

    @Override
    public Double setWeight(final double weight) {
        return this.weight = weight;
    }

    @Override
    public Double getWeight() {
        return this.weight;
    }

    @Override
    public Integer getBrokerId() {
        return this.brokerId;
    }

    @Override
    public void setBrokerId(final Integer brokerId) {
        this.brokerId = brokerId;
    }

    @Override
    public Map<String, Object> getOptions() {
        return null;
    }
}


