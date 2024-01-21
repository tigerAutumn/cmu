package cc.newex.dax.market.job.service;


import cc.newex.dax.market.common.enums.PublishTypeEnum;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public interface PublishService {

    /**
     * redis push
     * chanel redis channel
     *
     * @param typeEnum
     * @param symbolIndex
     * @param binary
     * @param data
     * @param contract
     * @param period
     */
    void publish(String channel, PublishTypeEnum typeEnum, final String symbolIndex, int binary, Object data, String contract,
                 String period);

    /**
     * redis key
     *
     * @param key
     * @param data
     */
    void setInfo(String key, String data);
}
