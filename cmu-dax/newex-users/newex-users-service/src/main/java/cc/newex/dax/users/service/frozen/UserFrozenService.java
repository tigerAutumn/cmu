package cc.newex.dax.users.service.frozen;

/**
 * 用户交易业务冻结控制器
 *
 * @author newex-team
 * @date 2018-07-03
 */
public interface UserFrozenService {

    /**
     * 获取全站交易业务冻结状态,表示所有用户都不能进行所有或相关业务(spot,c2c,contracts等)交易
     *
     * @param name {@link cc.newex.commons.security.jwt.enums.GlobalFrozenEnum}
     * @return 0：解冻 1：冻结
     */
    Integer getGlobalFrozen(String name);

    /**
     * 设置全站交易业务冻结状态,表示所有用户都不能进行所有或相关业务(spot,c2c,contracts等)交易
     *
     * @param name   {@link cc.newex.commons.security.jwt.enums.GlobalFrozenEnum}
     * @param status 0：解冻 1：冻结
     */
    void setGlobalFrozen(String name, final Integer status);

    /**
     * 删除全站交易业务冻结状态,表示所有用户都不能进行所有或相关业务(spot,c2c,contracts等)交易
     *
     * @param name {@link cc.newex.commons.security.jwt.enums.GlobalFrozenEnum}
     */
    void deleteGlobalFrozen(String name);

    /**
     * 冻结指定用户所有交易业务,表示该用户都不能进行所有业务(spot,c2c,contracts等)交易
     *
     * @param userId 用户id
     * @param status 0：解冻 1：冻结
     * @param remark 说明或原因
     * @return 大于0表示成功, 其他失败
     */
    int frozen(final Long userId, final Integer status, final String remark);

    /**
     * 冻结指定用户现货交易业务,表示该用户都不能进行现货业务(spot)交易
     *
     * @param userId 用户id
     * @param status 0：解冻 1：冻结
     * @param remark 说明或原因
     * @return 大于0表示成功, 其他失败
     */
    int frozenSpot(final Long userId, final Integer status, final String remark);

    /**
     * 冻结指定用户法币交易业务,表示该用户都不能进行法币业务(C2C)交易
     *
     * @param userId 用户id
     * @param status 0：解冻 1：冻结
     * @param remark 说明或原因
     * @return 大于0表示成功, 其他失败
     */
    int frozenC2C(final Long userId, final Integer status, final String remark);

    /**
     * 冻结指定用户合约交易业务,表示该用户都不能进行合约业务(contracts)交易
     *
     * @param userId 用户id
     * @param status 0：解冻 1：冻结
     * @param remark 说明或原因
     * @return 大于0表示成功, 其他失败
     */
    int frozenContracts(final Long userId, final Integer status, final String remark);

    /**
     * 冻结指定用户资产业务,表示该用户都不能进行资产业务(asset)交易
     *
     * @param userId 用户id
     * @param status 0：解冻 1：冻结
     * @param remark 说明或原因
     * @return 大于0表示成功, 其他失败
     */
    int frozenAsset(final Long userId, final Integer status, final String remark);


    /**
     * 获取用户当前所有业务冻结状态
     *
     * @param userId 用户ID
     * @return 0：解冻 1：冻结
     */
    int getUserFrozenStatus(final Long userId);

    /**
     * 获取用户当前现货业务(spot)冻结状态
     *
     * @param userId 用户ID
     * @return 0：解冻 1：冻结
     */
    int getUserSpotFrozenStatus(final Long userId);

    /**
     * 获取用户当前法币业务(C2C)冻结状态
     *
     * @param userId 用户ID
     * @return 0：解冻 1：冻结
     */
    int getUserC2CFrozenStatus(final Long userId);

    /**
     * 获取用户当前合约业务(contracts)冻结状态
     *
     * @param userId 用户ID
     * @return 0：解冻 1：冻结
     */
    int getUserContractsFrozenStatus(final Long userId);

    /**
     * 获取用户当前资产业务(Asset)冻结状态
     *
     * @param userId 用户ID
     * @return 0：解冻 1：冻结
     */
    int getUserAssetFrozenStatus(final Long userId);
}
