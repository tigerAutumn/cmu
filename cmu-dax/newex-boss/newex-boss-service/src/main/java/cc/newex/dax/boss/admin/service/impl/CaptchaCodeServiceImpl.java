package cc.newex.dax.boss.admin.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.boss.admin.criteria.CaptchaCodeExample;
import cc.newex.dax.boss.admin.data.CaptchaCodeRepository;
import cc.newex.dax.boss.admin.domain.CaptchaCode;
import cc.newex.dax.boss.admin.service.CaptchaCodeService;
import cc.newex.dax.boss.common.consts.CaptchaConsts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 后台系统用户登录验证码表 服务实现
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Service
public class CaptchaCodeServiceImpl
        extends AbstractCrudService<CaptchaCodeRepository, CaptchaCode, CaptchaCodeExample, Long>
        implements CaptchaCodeService {

    @Autowired
    private CaptchaCodeRepository captchaCodeRepos;

    @Override
    protected CaptchaCodeExample getPageExample(final String fieldName, final String keyword) {
        final CaptchaCodeExample example = new CaptchaCodeExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public boolean addGoogleCaptcha(final String key, final String code) {
        final CaptchaCode build = CaptchaCode.builder()
                .type(CaptchaConsts.CHECK_WAY_GOOGLE)
                .key(key)
                .code(code)
                .codeType(CaptchaConsts.CHECK_CODE_TYPE_DEFAULT)
                .status(CaptchaConsts.CHECK_CODE_STATUS_INVALID)
                .createdDate(new Date()).build();
        return this.captchaCodeRepos.insert(build) > 0;
    }

    @Override
    public CaptchaCode getGoogleCaptcha(final String key) {
        final CaptchaCodeExample example = new CaptchaCodeExample();
        CaptchaCodeExample.Criteria criteria = example.createCriteria()
                .andTypeEqualTo(0)
                .andCreatedDateEqualTo(DateUtils.addMinutes(new Date(), -10));
        if (StringUtils.isNotBlank(key)) {
            criteria.andKeyEqualTo(key);
        }
        return this.captchaCodeRepos.selectOneByExample(example);
    }
}