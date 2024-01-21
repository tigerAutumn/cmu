package cc.newex.dax.users.rest.controller.inner.v1;

import cc.newex.commons.fileupload.FileUploadService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.consts.UserFiatConsts;
import cc.newex.dax.users.domain.UserFiatSetting;
import cc.newex.dax.users.domain.UserSettings;
import cc.newex.dax.users.dto.security.UserPaymentTypeDTO;
import cc.newex.dax.users.service.membership.UserSettingsService;
import cc.newex.dax.users.service.security.UserFiatSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gi
 * @date 11/8/18
 */
@Slf4j
@RestController
@RequestMapping(value = "/inner/v1/users/c2c")
public class UserC2cController {

    @Autowired
    UserSettingsService userSettingsService;
    @Autowired
    UserFiatSettingService userFiatSettingService;

    @Resource(name = "ossFileUploadService")
    private FileUploadService fileUploadService;

    @GetMapping(value = "payment/{userId}")
    public ResponseResult getPayment(@PathVariable(value = "userId") long userId){
        List<UserFiatSetting> userFiatSettings = this.userFiatSettingService.getListByUserId(userId);
        UserPaymentTypeDTO dto = UserPaymentTypeDTO.builder()
                .userId(userId)
                .bankCardFlag(false).build();
        for (UserFiatSetting setting:userFiatSettings){
            if(setting.getPayType()==UserFiatConsts.USER_FIAT_PAYTYPE_1){
                dto.setBankCardFlag(true);
                dto.setBankCard(setting.getBankCard());
                dto.setBankAddress(setting.getBankAddress());
                dto.setBankName(setting.getBankName());
                dto.setBankNo(setting.getBankNo());
            }
            if(setting.getPayType()==UserFiatConsts.USER_FIAT_PAYTYPE_2){
                dto.setAlipayCard(setting.getAlipayCard());
                dto.setAlipayName(setting.getAlipayName());
                dto.setAlipayReceivingImg(this.fileUploadService.getSignedUrl(setting.getAlipayReceivingImg().replace("&amp;", "&")));
            }
            if(setting.getPayType()==UserFiatConsts.USER_FIAT_PAYTYPE_3){
                dto.setWechatCard(setting.getWechatCard());
                dto.setWechatPayName(setting.getWechatPayName());
                dto.setWechatReceivingImg(this.fileUploadService.getSignedUrl(setting.getWechatReceivingImg()).replace("&amp;", "&"));
            }
        }
        UserSettings settings = this.userSettingsService.getByUserId(userId);
        dto.setBankCardAuthFlag(settings.getBankPayAuthFlag()==1);
        dto.setAlipayAuthFlag(settings.getAlipayAuthFlag()==1);
        dto.setWechatAuthFlag(settings.getWechatPayAuthFlag()==1);
        return ResultUtils.success(dto);
    }
}
