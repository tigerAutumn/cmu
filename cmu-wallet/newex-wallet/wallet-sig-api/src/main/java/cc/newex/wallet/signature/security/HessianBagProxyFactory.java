package cc.newex.wallet.signature.security;


import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.security.X509Encryption;

/**
 * @author newex-team
 * @create 2018-11-22 下午4:11
 **/
public class HessianBagProxyFactory extends HessianProxyFactory {

    private final X509Encryption encryption = new X509Encryption();


    //@Override
    //public AbstractHessianInput getHessian2Input(InputStream is) {
    //
    //    Hessian2Input hessian2Input = (Hessian2Input)super.getHessian2Input( is );
    //
    //    try {
    //        return encryption.unwrap( hessian2Input );
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    }
    //    throw new IllegalStateException("X509 unwarp failed");
    //}
    //
    //@Override
    //public AbstractHessianOutput getHessianOutput(OutputStream os) {
    //
    //    AbstractHessianOutput hessianOutput = super.getHessianOutput( os );
    //
    //    //hessian 1.0
    //    if(hessianOutput instanceof HessianOutput){
    //        return hessianOutput;
    //    }
    //
    //    try {
    //        return encryption.wrap( (Hessian2Output)hessianOutput );
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    }
    //    throw new IllegalStateException("X509 unwarp failed");
    //}
}
