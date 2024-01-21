package cc.newex.dax.integration.service.msg.provider.email;

import cc.newex.dax.integration.service.msg.provider.MessageProvider;

/**
 * 阿里云企业邮箱
 *
 * @author newex-team
 * @date 2018-05-18
 */
public class AliyunEmailProvider
        extends AbstractEmailProvider implements MessageProvider {
    public AliyunEmailProvider() {
    }

    @Override
    public String getName() {
        return "aliyun-email";
    }
}
