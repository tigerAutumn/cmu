package cc.newex.dax.integration.service.msg.provider.email;

import cc.newex.dax.integration.service.msg.provider.MessageProvider;

/**
 * 美橙互联企业邮箱
 * <a href="https://www.cndns.com/cn/edm/"></a>
 *
 * @author newex-team
 * @date 2018-05-18
 */
public class CndnsEmailProvider
        extends AbstractEmailProvider implements MessageProvider {

    public CndnsEmailProvider() {
    }

    @Override
    public String getName() {
        return "cndns-email";
    }
}
