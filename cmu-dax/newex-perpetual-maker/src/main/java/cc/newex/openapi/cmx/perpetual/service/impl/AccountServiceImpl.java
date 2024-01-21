package cc.newex.openapi.cmx.perpetual.service.impl;

import cc.newex.openapi.cmx.client.ApiClient;
import cc.newex.openapi.cmx.perpetual.api.AccountApi;
import cc.newex.openapi.cmx.perpetual.service.AccountService;
import retrofit2.Call;

import java.io.IOException;
import java.util.Map;

/**
 * @author liutiejun
 * @date 2019-04-29
 */
public class AccountServiceImpl implements AccountService {

    private AccountApi accountApi;

    public AccountServiceImpl(final ApiClient client) {
        this.accountApi = client.create(AccountApi.class);
    }

    @Override
    public Map assets(final String contractCode) throws IOException {
        final Call<Map> call = this.accountApi.assets(contractCode);
        return call.execute().body();
    }
}
