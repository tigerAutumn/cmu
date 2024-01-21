package cc.newex.openapi.cmx.perpetual.service;

import java.io.IOException;
import java.util.Map;

/**
 * @author liutiejun
 * @date 2019-04-29
 */
public interface AccountService {

    Map assets(String contractCode) throws IOException;

}
