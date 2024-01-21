package cc.newex.openapi.cmx.perpetual.service.impl;

import cc.newex.openapi.cmx.client.ApiClient;
import cc.newex.openapi.cmx.perpetual.api.PositionApi;
import cc.newex.openapi.cmx.perpetual.domain.CurrentPosition;
import cc.newex.openapi.cmx.perpetual.domain.PositionPubConfig;
import cc.newex.openapi.cmx.perpetual.service.PositionService;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;

/**
 * @author coinmex-sdk-team
 * @date 2018/12/10
 */
public class PositionServiceImpl implements PositionService {

    private final PositionApi positionApi;

    public PositionServiceImpl(final ApiClient client) {
        this.positionApi = client.create(PositionApi.class);
    }

    @Override
    public List<CurrentPosition> listAll(final String contractCode) throws IOException {
        final Call<List<CurrentPosition>> call = this.positionApi.list(contractCode);
        return call.execute().body();
    }

    @Override
    public PositionPubConfig getPositionConfig(final String contractCode) throws IOException {
        final Call<PositionPubConfig> call = this.positionApi.getPositionConfig(contractCode);
        return call.execute().body();
    }

}
