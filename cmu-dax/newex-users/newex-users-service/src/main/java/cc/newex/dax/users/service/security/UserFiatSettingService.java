package cc.newex.dax.users.service.security;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.users.common.consts.UserFiatConsts;
import cc.newex.dax.users.criteria.UserFiatSettingExample;
import cc.newex.dax.users.domain.UserFiatSetting;
import cc.newex.dax.users.dto.security.UserFiatSettingDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 用户法币交易设置 服务接口
 *
 * @author newex-team
 * @date 2018-05-14
 */
public interface UserFiatSettingService
        extends CrudService<UserFiatSetting, UserFiatSettingExample, Long> {
    /**
     * @param userId
     * @description 通过用户id获取用户法币设置信息
     * @date 2018/5/14 下午2:25
     */
    Map list(long userId);

    /**
     * 通过用户id获取法币设置列表
     *
     * @param userId
     * @return
     */
    List<UserFiatSetting> getListByUserId(Long userId);

    /**
     * @param userId
     * @description 通过用户id和payType获取用户法币设置
     * @date 2018/5/14 上午11:40
     */
    UserFiatSetting getByUserId(Long userId, int payType);

    /**
     * @param userId
     * @description 通过用户id和status, payType获取用户法币设置
     * @date 2018/5/14 上午11:40
     */
    UserFiatSetting getByUserId(Long userId, Integer status, Integer payType);


    /**
     * 修改支付宝收款码
     *
     * @param form
     * @return
     */
    ResponseResult editAlipay(UserFiatSettingDTO form);

    /**
     * 添加支付宝，银行卡，微信的支付方式
     * @param payType
     * @param form
     * @return
     */
    ResponseResult savePayment(int payType,UserFiatSettingDTO form);

    /**
     * @param form
     * @description 修改微信收款码
     * @date 2018/5/19 下午1:13
     */
    ResponseResult editWechatPay(UserFiatSettingDTO form);

    /**
     * @param file
     * @param userId
     * @description 上传微信和支付宝付款码
     * @date 2018/5/14 下午5:48
     */
    ResponseResult uploadFile(MultipartFile file, Long userId) throws Exception;
}
