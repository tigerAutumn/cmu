package cc.newex.dax.users.rest.controller.outer.v1.common.support;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.client.IpAddressClient;
import cc.newex.dax.integration.dto.ip.IpLocationDTO;
import cc.newex.dax.users.common.consts.NoticeSendLogConsts;
import cc.newex.dax.users.common.consts.UserConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.exception.UsersBizException;
import cc.newex.dax.users.common.util.ImageUtils;
import cc.newex.dax.users.common.util.MobileUtils;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.common.util.VerificationCodeUtils;
import cc.newex.dax.users.domain.DictCountry;
import cc.newex.dax.users.domain.UserLoginInfo;
import cc.newex.dax.users.limited.BehaviorTheme;
import cc.newex.dax.users.rest.common.limit.annotation.RetryLimit;
import cc.newex.dax.users.rest.common.limit.enums.RetryLimitTypeEnum;
import cc.newex.dax.users.rest.common.util.QrCodeCreateUtils;
import cc.newex.dax.users.rest.controller.base.BaseController;
import cc.newex.dax.users.rest.model.CheckUsernameReqVO;
import cc.newex.dax.users.rest.model.DictCountryResVO;
import cc.newex.dax.users.rest.model.DictLimitCountryVO;
import cc.newex.dax.users.rest.model.VerifyCodeReqVO;
import cc.newex.dax.users.service.cache.AppCacheService;
import cc.newex.dax.users.service.membership.DictCountryService;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.security.UserNoticeEventService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * note:还需要实现限流控制
 *
 * @author newex-team
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/v1/users/support/public")
public class PublicController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private DictCountryService dictCountryService;

    @Autowired
    private UserNoticeEventService userNoticeRecordService;

    @Autowired
    private AppCacheService appCacheService;

    @Autowired
    private IpAddressClient ipAddressClient;

    @PostMapping("/merge-image")
    public void mergeImage(@RequestParam("file") final MultipartFile file, @RequestParam("content") final String content, final HttpServletResponse response) {
        try {
            // 二维码
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            QrCodeCreateUtils.createQrCode(out, content, 100, 100, "JPEG");
            final InputStream swapStream = QrCodeCreateUtils.parse(out);

            // 合并文件
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            Thumbnails.of(file.getInputStream())
                    .size(375, 667)
                    .watermark((final int enclosingWidth, final int enclosingHeight, final int width, final int height, final int insetLeft, final int insetRight, final int insetTop, final int insetBottom) -> {
                        final int x = insetLeft + 15;
                        final int y = insetTop + 550;
                        return new Point(x, y);
                    }, ImageIO.read(swapStream), 1.0f)
                    .outputQuality(1.0f)
                    .toOutputStream(os);

            response.setContentType("image/jpeg");
            response.setHeader("Content-disposition", "attachment;filename=merge.jpg");
            final OutputStream stream = response.getOutputStream();
            stream.write(os.toByteArray());
            stream.flush();
            stream.close();
        } catch (final IOException | WriterException e) {
            log.error("mergeImage error: ", e);
            throw new UsersBizException(e.getMessage());
        }
    }

    @PostMapping(value = "/verification-code/image")
    public ResponseResult getImageVerificationCode() {
        final Map<String, Object> params = Maps.newHashMap();
        final Object[] imageCode = VerificationCodeUtils.getImageCode(4);
        final String code = (String) imageCode[1];
        final String base64Img = ImageUtils.getBase64Image((BufferedImage) imageCode[0]);

        final String serialNO = UUID.randomUUID().toString();
        params.put("base64Img", base64Img);
        params.put("serialNO", serialNO);
        this.appCacheService.setImageVerificationCode(serialNO, code);
        return ResultUtils.success(params);
    }

    /**
     * 无登录, 获取手机验证码
     *
     * @param form
     * @param request
     */
    @RetryLimit(type = RetryLimitTypeEnum.VERIFY_CODE)
    @PostMapping(value = "/verification-code/mobile")
    public ResponseResult getSmsVerificationCode(@RequestBody @Valid final VerifyCodeReqVO form, final HttpServletRequest request) {
        log.info("sms verification code, ip: {}, form: {}", IpUtil.getRealIPAddress(request), JSON.toJSONString(form));

        final BehaviorTheme behaviorTheme = BehaviorTheme.getBehavior(form.getBehavior());
        boolean result = false;

        //防止攻击者恶意群发短信
        form.setMobile(StringUtils.left(form.getMobile(), 16));

        try {
            //判断二次登录验证
            if (form.getBehavior() == BehaviorTheme.USERS2_LOGIN_STEP2AUTH_2.getBehavior() ||
                    form.getBehavior() == BehaviorTheme.USERS2_LOGIN_RESET_PASSWORD_4.getBehavior()) {

                final JwtUserDetails jwtUserDetails = JwtTokenUtils.getCurrentLoginUserFromToken(request);

                final Long userId = jwtUserDetails.getUserId();

                if (Objects.nonNull(userId)) {
                    final UserLoginInfo userLoginInfo = this.userInfoService.getUserLoginInfoById(userId);

                    result = this.userNoticeRecordService.sendSMS(LocaleUtils.getLocale(request),
                            behaviorTheme,
                            NoticeSendLogConsts.BUSINESS_CODE,
                            userLoginInfo.getMobile(),
                            userLoginInfo.getAreaCode(),
                            behaviorTheme.getMobileCode(),
                            userLoginInfo.getId(),
                            Maps.newHashMap(), this.getBrokerId(request)
                    );
                }
            } else {
                result = this.userNoticeRecordService.sendSMS(LocaleUtils.getLocale(request),
                        behaviorTheme,
                        NoticeSendLogConsts.BUSINESS_CODE,
                        form.getMobile(),
                        form.getAreaCode(),
                        behaviorTheme.getMobileCode(),
                        -1L,
                        Maps.newHashMap(), this.getBrokerId(request)
                );
            }
        } catch (final Exception e) {
            log.error("getSmsVerificationCode error: " + JSON.toJSONString(form), e);

            return ResultUtils.failure(BizErrorCodeEnum.SMS_CODE_SEND_FAIL);
        }

        return result ? ResultUtils.success() : ResultUtils.failure(BizErrorCodeEnum.SMS_CODE_SEND_FAIL);
    }

    /**
     * 无登录, 获取邮箱验证码
     *
     * @param form
     * @param request
     */
    @RetryLimit(type = RetryLimitTypeEnum.VERIFY_CODE)
    @PostMapping(value = "/verification-code/email")
    public ResponseResult getEmailVerificationCode(@RequestBody @Valid final VerifyCodeReqVO form, final HttpServletRequest request) {
        log.info("email verification code, ip: {}, form: {}", IpUtil.getRealIPAddress(request), JSON.toJSONString(form));

        final BehaviorTheme behaviorTheme = BehaviorTheme.getBehavior(form.getBehavior());

        boolean result = false;

        try {
            // 判断二次登录验证
            if (form.getBehavior() == BehaviorTheme.USERS2_LOGIN_STEP2AUTH_2.getBehavior() || form.getBehavior() ==
                    BehaviorTheme.USERS2_LOGIN_RESET_PASSWORD_4.getBehavior()) {

                final JwtUserDetails jwtUserDetails = JwtTokenUtils.getCurrentLoginUserFromToken(request);

                final Long userId = jwtUserDetails.getUserId();

                if (Objects.nonNull(userId)) {
                    final UserLoginInfo userLoginInfo = this.userInfoService.getUserLoginInfoById(userId);
                    result = this.userNoticeRecordService.sendEmail(LocaleUtils.getLocale(request),
                            behaviorTheme,
                            NoticeSendLogConsts.BUSINESS_CODE,
                            userLoginInfo.getEmail(),
                            behaviorTheme.getEmailCode(),
                            userLoginInfo.getId(),
                            Maps.newHashMap(), this.getBrokerId(request));
                }
            } else {
                result = this.userNoticeRecordService.sendEmail(LocaleUtils.getLocale(request),
                        behaviorTheme,
                        NoticeSendLogConsts.BUSINESS_CODE,
                        form.getEmail(),
                        behaviorTheme.getEmailCode(),
                        -1L,
                        Maps.newHashMap(), this.getBrokerId(request));
            }
        } catch (final Exception e) {
            log.error("getEmailVerificationCode error: " + JSON.toJSONString(form), e);

            return ResultUtils.failure(BizErrorCodeEnum.EMAIL_CODE_SEND_FAIL);
        }

        return result ? ResultUtils.success() : ResultUtils.failure(BizErrorCodeEnum.EMAIL_CODE_SEND_FAIL);
    }

    /**
     * @param form
     * @description 验证登录名是否已经注册
     * @date 2018/5/29 上午10:28
     */
    // @IpRequestRateLimit(value = "6/2")
    @PostMapping(value = "/username/validation")
    public ResponseResult checkUsername(@RequestBody @Valid final CheckUsernameReqVO form) {
        log.info("checkUsername parmas: {}", form);
        // 手机
        if (StringUtils.equalsIgnoreCase(form.getType(), UserConsts.USER_REGISTER_TYPE_MOBILE)) {
            final boolean isPhone = MobileUtils.checkPhoneNumber(form.getUsername(), form.getAreaCode());
            if (!isPhone) {
                log.error("mobile number format error! mobile={}", form.getUsername());
                return ResultUtils.failure(BizErrorCodeEnum.PHONE_FORMAT_ERROR);
            }
        }
        // 邮箱
        else if (!StringUtil.isEmail(form.getUsername())) {
            log.error("email address format error! email={}", form.getUsername());
            return ResultUtils.failure(BizErrorCodeEnum.EMAIL_FORMAT_ERROR);
        }

        // 判断用户名是否已经被注册
        final String username = StringUtils.defaultIfBlank(StringUtils.trim(form.getUsername()), StringUtils.EMPTY);
        final boolean isExist = this.userInfoService.checkLoginName(username);
        if (isExist) {
            log.error("username is exists! username={}", form.getUsername());
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_USER_EXIST);
        }

        return ResultUtils.success();
    }

    /**
     * @param request
     * @description 获取国家列表
     * @date 2018/5/29 上午10:25
     */
    @GetMapping("/countries")
    public ResponseResult getCountries(final HttpServletRequest request) {
        final String lang = LocaleUtils.getLocale(request);

        final List<DictCountry> list = this.countries(request);

        final List<DictCountryResVO> countryResForms = Lists.newArrayListWithCapacity(CollectionUtils.size(list));
        if (StringUtils.equalsIgnoreCase("en-us", lang)) {
            list.forEach(item -> {
                final DictCountryResVO vo = ObjectCopyUtils.map(item, DictCountryResVO.class);
                vo.setName(item.getAbbr());
                countryResForms.add(vo);
            });
        } else {
            list.forEach(item -> {
                countryResForms.add(ObjectCopyUtils.map(item, DictCountryResVO.class));
            });
        }

        return ResultUtils.success(countryResForms);
    }

    /**
     * 获取受限制的国家列表（check ip 在受限制的国家中）
     * <p>
     * TODO 查询country很慢，目前没有配置country，先返回空数组，后续需要再真实调用
     * <p>
     * TODO 查询country很慢，目前没有配置country，先返回空数组，后续需要再真实调用
     *
     * @param request
     * @return
     */
    @GetMapping("/limit/countries")
    public ResponseResult getLimitCountries(final HttpServletRequest request) {
        final String locale = LocaleUtils.getLocale(request);

        final List<DictCountry> dictCountryList = this.dictCountryService.limitCountries(locale);

        if (CollectionUtils.isEmpty(dictCountryList)) {
            log.info("受限国家列表为空");

            return ResultUtils.success(Lists.newArrayListWithCapacity(0));
        }

        final IpLocationDTO ipLocationDTO = this.getDictCountryByIp(request);

        final List<DictLimitCountryVO> dictLimitCountryVOList = dictCountryList.stream()
                .map(dictCountry -> {
                    DictLimitCountryVO dictLimitCountryVO = convert(dictCountry);

                    if (ipLocationDTO != null && StringUtils.equalsIgnoreCase(dictCountry.getLetterCode2(), ipLocationDTO.getCountryId())) {
                        dictLimitCountryVO.setVisit(true);
                    } else {
                        dictLimitCountryVO.setVisit(false);
                    }

                    return dictLimitCountryVO;
                })
                .collect(Collectors.toList());

        return ResultUtils.success(dictLimitCountryVOList);
    }

    private static DictLimitCountryVO convert(final DictCountry dictCountry) {
        return DictLimitCountryVO.builder().name(dictCountry.getName()).build();
    }

    /**
     * 根据ip获取受限制的国家
     *
     * @param request
     * @return
     */
    @GetMapping("/limit/country")
    public ResponseResult getLimitCountry(final HttpServletRequest request) {
        final String locale = LocaleUtils.getLocale(request);

        final List<DictCountry> dictCountryList = this.dictCountryService.limitCountries(locale);

        final DictCountry visitCountry = this.getDictCountryByIp(dictCountryList, request);

        if (visitCountry == null) {
            return ResultUtils.success();
        }

        final DictLimitCountryVO dictLimitCountryVO = convert(visitCountry);
        dictLimitCountryVO.setVisit(true);

        return ResultUtils.success(dictLimitCountryVO);
    }

    /**
     * 判断当前访问的ip是否在受限的国家内
     *
     * @param dictCountryList
     * @param request
     * @return
     */
    private DictCountry getDictCountryByIp(final List<DictCountry> dictCountryList, final HttpServletRequest request) {
        if (CollectionUtils.isEmpty(dictCountryList)) {
            log.info("受限国家列表为空");

            return null;
        }

        final IpLocationDTO ipLocationDTO = this.getDictCountryByIp(request);
        if (ipLocationDTO == null) {
            return null;
        }

        final DictCountry dictCountry = dictCountryList.stream()
                .filter(x -> StringUtils.equalsIgnoreCase(x.getLetterCode2(), ipLocationDTO.getCountryId()))
                .findFirst()
                .orElse(null);

        return dictCountry;
    }

    private IpLocationDTO getDictCountryByIp(final HttpServletRequest request) {
        final String ip = IpUtil.getRealIPAddress(request);

        final ResponseResult<IpLocationDTO> responseResult = this.ipAddressClient.getIpLocationInfo(ip);

        log.info("check ip, ip: {}, response: {}", ip, JSON.toJSONString(responseResult));

        if (responseResult == null || responseResult.getCode() != 0) {
            return null;
        }

        final IpLocationDTO ipLocationDTO = responseResult.getData();

        return ipLocationDTO;
    }

    private List<DictCountry> countries(final HttpServletRequest request) {
        final String lang = LocaleUtils.getLocale(request);
        List<DictCountry> list = this.dictCountryService.getAllByLocale(lang.toLowerCase());
        if (CollectionUtils.isEmpty(list)) {
            //如果不支持当前国家语言则默认为英文
            list = this.dictCountryService.getAllByLocale(Locale.US.toLanguageTag().toLowerCase());
        }
        return list;
    }

    @GetMapping("/broker")
    public ResponseResult<Integer> getBrokerIdByHost(final HttpServletRequest request) {
        final Integer brokerId = this.getBrokerId(request);
        return ResultUtils.success(brokerId);

    }
}
