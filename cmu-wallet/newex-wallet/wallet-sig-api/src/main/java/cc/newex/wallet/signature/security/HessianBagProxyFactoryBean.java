package cc.newex.wallet.signature.security;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 * @author newex-team
 * @create 2018-11-22 上午11:28
 **/

@Setter(AccessLevel.PUBLIC)
@Slf4j
public class HessianBagProxyFactoryBean extends HessianProxyFactoryBean {

    private int readTimeOut = 60 * 1000;
    private int connectionTimeOut = 30000;

    @Override
    public void prepare() throws RemoteLookupFailureException {

        final HessianBagProxyFactory proxyFactory = new HessianBagProxyFactory();

        proxyFactory.setHessian2Request(true);
        proxyFactory.setReadTimeout(this.readTimeOut);
        proxyFactory.setConnectTimeout(this.connectionTimeOut);
        proxyFactory.setUser("liuhailin");
        proxyFactory.setPassword("liuhailin");

        final HessianBagURLConnectionFactory urlConnectionFactory = new HessianBagURLConnectionFactory();

        urlConnectionFactory.setHessianProxyFactory(proxyFactory);

        proxyFactory.setConnectionFactory(urlConnectionFactory);

        this.setProxyFactory(proxyFactory);

        super.prepare();
    }
}
