package cc.newex.dax.extra.common.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

/**
 * @author gi
 * @date 10/25/18
 */

@Slf4j
@Data
public class VlinkResponseUtils {

    public static boolean checkResponse(Response response) {
        if (response.isSuccessful()) {
            log.error("vlink http server error : code={},body={}", response.code(), response.body());
            return false;
        }
        return true;
    }
}
