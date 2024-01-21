package cc.newex.openapi.cmx.perpetual.service;

import cc.newex.openapi.cmx.perpetual.domain.CurrentPosition;
import cc.newex.openapi.cmx.perpetual.domain.PositionPubConfig;

import java.io.IOException;
import java.util.List;

/**
 * @author coinmex-sdk-team
 * @date 2018/12/10
 */
public interface PositionService {
    /**
     * 获取用户仓位列表
     *
     * @return
     * @throws IOException
     */
    List<CurrentPosition> listAll(String contractCode) throws IOException;

    PositionPubConfig getPositionConfig(String contractCode) throws IOException;

}
