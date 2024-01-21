package cc.newex.wallet.signature.security;

import com.caucho.hessian.io.SerializerFactory;
import com.caucho.hessian.security.X509Encryption;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.util.Base64Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author newex-team
 * @create 2018-11-22 下午2:16
 **/
@Slf4j
public class HessianBagServiceExporter extends HessianServiceExporter {

    private final X509Encryption x509Encryption = new X509Encryption();
    private final SerializerFactory serializerFactory = new SerializerFactory();
    private String auth;

    @Override
    public void afterPropertiesSet() {

        this.setSerializerFactory(this.serializerFactory);
        super.afterPropertiesSet();
    }

    @Override
    public void handleRequest(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        if (!this.checkSecurity(request)) {
            final PrintWriter writer = response.getWriter();
            writer.write("error");
            writer.flush();
            return;
        }

        super.handleRequest(request, response);
    }

    private boolean checkSecurity(final HttpServletRequest request) {

        final String from = request.getHeader("from");

        final String authorization = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(this.auth) && StringUtils.isNotBlank(authorization)) {
            final String authCode = StringUtils.substringAfter(authorization, " ");
            final String expectCode = Base64Utils.encodeToString(this.auth.getBytes());

            if (!authCode.equals(expectCode)) {
                return false;
            }
            HessianBagServiceExporter.log.info("Request from is:{}", from);
        }
        return true;
    }

    //@Override
    //protected void doInvoke(HessianSkeleton skeleton, InputStream inputStream, OutputStream outputStream)
    //    throws Throwable {
    //    ClassLoader originalClassLoader = overrideThreadContextClassLoader();
    //    try {
    //        InputStream isToUse = inputStream;
    //        OutputStream osToUse = outputStream;
    //
    //        if (!isToUse.markSupported()) {
    //            isToUse = new BufferedInputStream( isToUse );
    //            isToUse.mark( 1 );
    //        }
    //
    //        int code = isToUse.read();
    //        int major;
    //        int minor;
    //
    //        AbstractHessianInput in;
    //        AbstractHessianOutput out;
    //
    //        if (code == 'H') {
    //            // Hessian 2.0 stream
    //            major = isToUse.read();
    //            minor = isToUse.read();
    //            if (major != 0x02) {
    //                throw new IOException( "Version " + major + '.' + minor + " is not understood" );
    //            }
    //            in = x509Encryption.unwrap( new Hessian2Input( isToUse ) );
    //            out = x509Encryption.wrap( new Hessian2Output( osToUse ) );
    //            in.readCall();
    //        } else if (code == 'C') {
    //            // Hessian 2.0 call... for some reason not handled in HessianServlet!
    //            isToUse.reset();
    //            in = x509Encryption.unwrap( new Hessian2Input( isToUse ) );
    //            out = x509Encryption.wrap( new Hessian2Output( osToUse ) );
    //            in.readCall();
    //        } else if (code == 'c') {// Hessian 1.0 不做数据加密
    //            // Hessian 1.0 call
    //            major = isToUse.read();
    //            minor = isToUse.read();
    //            in = new HessianInput( isToUse );
    //            if (major >= 2) {
    //                out = new Hessian2Output( osToUse );
    //            } else {
    //                out = new HessianOutput( osToUse );
    //            }
    //        } else {
    //            throw new IOException(
    //                "Expected 'H'/'C' (Hessian 2.0) or 'c' (Hessian 1.0) in hessian input at " + code );
    //        }
    //
    //        if (this.serializerFactory != null) {
    //            in.setSerializerFactory( this.serializerFactory );
    //            out.setSerializerFactory( this.serializerFactory );
    //        }
    //
    //        try {
    //            skeleton.invoke( in, out );
    //        } finally {
    //            try {
    //                in.close();
    //                isToUse.close();
    //            } catch (IOException ex) {
    //                // ignore
    //            }
    //            try {
    //                out.close();
    //                osToUse.close();
    //            } catch (IOException ex) {
    //                // ignore
    //            }
    //        }
    //    } finally {
    //        resetThreadContextClassLoader( originalClassLoader );
    //    }
    //}
}
