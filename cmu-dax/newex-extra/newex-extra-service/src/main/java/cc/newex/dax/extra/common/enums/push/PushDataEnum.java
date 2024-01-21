package cc.newex.dax.extra.common.enums.push;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 推送数据的枚举，统一管理
 *
 * @author better
 * @date create in 2018/11/9 5:43 PM
 */
@Getter
@AllArgsConstructor
public enum PushDataEnum {

    /**
     * extra消息管理
     */
    EXTRA_MESSAGE_MANAGEMENT("extra", "manualPushMessage", "", "", false) {
        @Override
        public PushData getPushData(Object data) {
            return new PushData(this.getBiz(), this.getType(), this.getBase(), this.getQuote(), this.getZip(), data);
        }
    };

    /**
     * 表示平台，例如：extra，spot，c2c
     */
    private String biz;

    /**
     * 表示业务类型，例如：spot有币种，币对业务
     */
    private String type;

    /**
     * 币对的交易货币
     */
    private String base;

    /**
     * 币对的计价货币
     */
    private String quote;

    /**
     * 是否需要压缩
     */
    private Boolean zip;

    /**
     * 获取data
     *
     * @return
     */
    public abstract PushData getPushData(Object data);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PushData {

        /**
         * 表示平台，例如：extra，spot，c2c
         */
        private String biz;

        /**
         * 表示业务类型，例如：spot有币种，币对业务
         */
        private String type;

        /**
         * 币对的交易货币
         */
        private String base;

        /**
         * 币对的计价货币
         */
        private String quote;

        /**
         * 是否需要压缩
         */
        private Boolean zip;

        /**
         * 推送的数据
         */
        private Object data;
    }
}
