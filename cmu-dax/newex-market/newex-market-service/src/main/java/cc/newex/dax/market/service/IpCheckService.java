package cc.newex.dax.market.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author allen
 * @date 2018/3/29
 * @des
 */
public interface IpCheckService {

    /**
     * 采集白名单校验
     *
     * @param request
     * @return
     */
    Boolean IPWhiteCheck(HttpServletRequest request);

}
