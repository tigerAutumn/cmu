package cc.newex.dax.boss.web.common.kyc;

import cc.newex.dax.boss.web.model.users.kyc.UserKycReasonVO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author liutiejun
 * @date 2018-07-27
 */
public class KycReasonUtils {

    private static final Map<String, List<UserKycReasonVO>> localeMap = new HashMap<>();

    static {
        init();
    }

    private static void init() {
        int start = 0;

        start = initZhcnReason(start, localeMap);

        initEnusReason(start, localeMap);
    }

    private static int initZhcnReason(final int start, final Map<String, List<UserKycReasonVO>> localeMap) {
        final String[] allReason = new String[]{
                "无",
                "审核通过",
                "高级信息认证与基本信息认证不一致",
                "身份证不在有效期内",
                "证件不真实有PS痕迹",
                "身份证正面照片信息不清楚",
                "身份证背面照片信息不清楚",
                "证件不符合认证要求，请选择正确的国籍进行认证",
                "地址证明不符合要求",
                "手持证件不清晰",
                "手持证件无公司名称和日期",
                "年龄不在18-60岁之间",
                "实名认证名字有误，请重新认证",
                "该地区暂不支持服务"
        };

        final String locale = Locale.CHINA.toLanguageTag().toLowerCase();

        initReason(start, allReason, locale, localeMap);

        return allReason.length;
    }

    private static int initEnusReason(final int start, final Map<String, List<UserKycReasonVO>> localeMap) {
        final String[] allReason = new String[]{
                "Nothing.",
                "Approve.",
                "Advanced information verification is inconsistent with basic information verification.",
                "Document card is not valid.",
                "The document is not true and there are PS marks.",
                "The information on the front of the document is not clear.",
                "The information on the back of the document is not clear.",
                "The certificate does not meet the verification requirements, please select the correct nationality for verification.",
                "The proof of address does not meet the verification requirements.",
                "The hand-held document is not clear.",
                "The hand-held document without company name and date.",
                "Age range not between 18-60 years old.",
                "Wrong name, please verify again.",
                "Services are not supported in the region."
        };

        final String locale = Locale.US.toLanguageTag().toLowerCase();

        initReason(start, allReason, locale, localeMap);

        return allReason.length;
    }

    private static void initReason(final int start, final String[] allReason, final String locale, final Map<String, List<UserKycReasonVO>> localeMap) {
        final List<UserKycReasonVO> userKycReasonVOList = new ArrayList<>();

        for (int i = 0; i < allReason.length; i++) {
            final UserKycReasonVO reasonVO = UserKycReasonVO.builder()
                    .id(start + i)
                    .locale(locale)
                    .value(allReason[i])
                    .build();

            userKycReasonVOList.add(reasonVO);
        }

        localeMap.put(locale, userKycReasonVOList);
    }

    /**
     * 获取某一种语言对应的kyc拒绝理由
     *
     * @param locale
     * @return
     */
    public static List<UserKycReasonVO> getKycReason(String locale) {
        if (StringUtils.isBlank(locale)) {
            locale = Locale.US.toLanguageTag().toLowerCase();
        }

        return localeMap.get(locale);
    }

}
