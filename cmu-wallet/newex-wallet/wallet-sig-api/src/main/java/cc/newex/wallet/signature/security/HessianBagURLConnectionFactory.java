package cc.newex.wallet.signature.security;


import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianURLConnectionFactory;

import java.io.IOException;
import java.net.URL;

/**
 * @author newex-team
 * @create 2018-11-22 下午2:09
 **/
public class HessianBagURLConnectionFactory extends HessianURLConnectionFactory {


    @Override
    public HessianConnection open(final URL url) throws IOException {

        final HessianConnection connection = super.open(url);

        connection.addHeader("from", "100.0.0.1");

        return connection;
    }
}
