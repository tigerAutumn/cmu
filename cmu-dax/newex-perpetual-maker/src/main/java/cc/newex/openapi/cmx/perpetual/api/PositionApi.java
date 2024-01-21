package cc.newex.openapi.cmx.perpetual.api;

import cc.newex.openapi.cmx.perpetual.domain.CurrentPosition;
import cc.newex.openapi.cmx.perpetual.domain.PositionPubConfig;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * @author coinmex-sdk-team
 * @date 2018/12/08
 */
public interface PositionApi {

    /**
     * 获取持仓列表
     *
     * @return
     */
    @GET("perpetual/position/{contractCode}/list")
    Call<List<CurrentPosition>> list(@Path("contractCode") String contractCode);

    /**
     * 仓位和限额设置
     */
    @GET("perpetual/position/{contractCode}/configs")
    Call<PositionPubConfig> getPositionConfig(@Path("contractCode") final String contractCode);

}
