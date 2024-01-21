package cc.newex.openapi.cmx.perpetual.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.Map;

/**
 * @author liutiejun
 * @date 2019-04-29
 */
public interface AccountApi {

    /**
     * 我的资产
     */
    @GET("perpetual/account/assets/{contractCode}")
    Call<Map> assets(@Path("contractCode") String contractCode);

}
