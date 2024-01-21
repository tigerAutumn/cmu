package cc.newex.dax.integration.service.msg.provider;

import cc.newex.dax.integration.domain.msg.Message;

import java.util.Map;

/**
 * 信息提供者接口
 *
 * @author newex-team
 * @date 2018-04-13
 */
public interface MessageProvider {

    /**
     * 获取当前提供者的名称
     *
     * @return provider name
     */
    String getName();

    /**
     * 设置在同类所有提供者的权重
     *
     * @param weight 权重值
     */
    Double setWeight(double weight);

    /**
     * 获取在同类所有提供者的权重
     */
    Double getWeight();

    Integer getBrokerId();

    void setBrokerId(Integer brokerId);

    /**
     * 设置provider配置选项
     *
     * @param options 配置选项集合
     */
    void setOptions(Map<String, Object> options);

    Map<String, Object> getOptions();

    /**
     * 发送消息
     *
     * @param message
     * @return true|false
     */
    boolean send(Message message);
}
