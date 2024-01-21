package cc.newex.dax.boss.admin.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.boss.admin.criteria.IpWhiteExample;
import cc.newex.dax.boss.admin.domain.IpWhite;

/**
 * 后台系统ip白名单表 服务接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface IpWhiteService
        extends CrudService<IpWhite, IpWhiteExample, Integer> {
    /**
     * ip是否在白名单中
     *
     * @param ip 地址
     * @return true|false
     */
    boolean isIpInWhite(final String ip);

    /**
     * 根据ip地址获取ip白名单信息
     * @param ip 地址
     * @return {@link IpWhite}
     */
    IpWhite getByIpAddress(final String ip);
}
