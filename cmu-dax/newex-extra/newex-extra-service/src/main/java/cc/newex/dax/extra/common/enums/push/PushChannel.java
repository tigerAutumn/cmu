package cc.newex.dax.extra.common.enums.push;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author better
 * @date create in 2018/11/9 5:38 PM
 */
@Getter
@AllArgsConstructor
public enum PushChannel {

    /**
     * 消息管理推送通道
     */
    MESSAGE_MANAGEMENT_PUSH_CHANNEL("MESSAGE_MANAGEMENT_PUSH_CHANNEL");

    /**
     * 通道名称
     */
    private String channelName;
}
