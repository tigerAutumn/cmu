package cc.newex.commons.openapi.specs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author newex-team
 * @date 2018-04-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiUserInfo {
    /**
     * 用户id(分布式唯一id)
     */
    private Long id;
    /**
     * 账号所属的主账号ID(如果为0表示主账号,否则为子账号)
     */
    private Long parentId;
    /**
     * 全局用户账号冻结状态 1已冻结;0未冻结 默认为0
     */
    private Integer frozen;
    /**
     * 用户状态0为开启，1为禁用，其他保留，默认为0
     */
    private Integer status;
    /**
     * 用户API key权限列表
     */
    private List<String> authorities;
    /**
     * 是否冻结现货业务1表示是,0表示否，默认为0
     */
    private Integer spotFrozenFlag;
    /**
     * 是否冻结C2C业务1表示是,0表示否，默认为0
     */
    private Integer c2cFrozenFlag;
    /**
     * 是否冻结合约业务1表示是,0表示否，默认为0
     */
    private Integer contractsFrozen;
    /**
     * 是否冻结资产业务1表示是,0表示否，默认为0
     */
    private Integer assetFrozen;
    /**
     * 券商 id
     */
    private Integer brokerId;
}
