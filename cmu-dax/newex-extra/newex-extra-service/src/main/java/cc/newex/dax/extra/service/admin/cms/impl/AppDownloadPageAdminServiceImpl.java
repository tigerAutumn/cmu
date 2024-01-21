package cc.newex.dax.extra.service.admin.cms.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.dax.extra.criteria.cms.AppDownloadPageExample;
import cc.newex.dax.extra.data.cms.AppDownloadPageRepository;
import cc.newex.dax.extra.domain.cms.AppDownloadPage;
import cc.newex.dax.extra.dto.cms.AppDownloadPageDTO;
import cc.newex.dax.extra.service.admin.cms.AppDownloadPageAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author liutiejun
 * @date 2018-08-08
 */
@Slf4j
@Service
public class AppDownloadPageAdminServiceImpl
        extends AbstractCrudService<AppDownloadPageRepository, AppDownloadPage, AppDownloadPageExample, Long>
        implements AppDownloadPageAdminService {

    @Autowired
    private AppDownloadPageRepository appDownloadPageRepository;

    @Override
    protected AppDownloadPageExample getPageExample(final String fieldName, final String keyword) {
        final AppDownloadPageExample example = new AppDownloadPageExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }

    @Override
    public DataGridPagerResult<AppDownloadPageDTO> getAppDownloadPagerInfo(DataGridPager<AppDownloadPageDTO> pager) {

        final PageInfo pageInfo = pager.toPageInfo();
        final ModelMapper mapper = new ModelMapper();
        final AppDownloadPage appDownloadPage = mapper.map(pager.getQueryParameter(), AppDownloadPage.class);

        final AppDownloadPageExample example = new AppDownloadPageExample();
        final AppDownloadPageExample.Criteria criteria = example.createCriteria();

        if (Objects.nonNull(appDownloadPage.getBrokerId())) {
            criteria.andBrokerIdEqualTo(appDownloadPage.getBrokerId());
        }

        final List<AppDownloadPage> list = getByPage(pageInfo, example);
        final List<AppDownloadPageDTO> result = mapper.map(
                list, new TypeToken<List<AppDownloadPageDTO>>() {
                }.getType()
        );
        return new DataGridPagerResult<>(pageInfo.getTotals(), result);
    }

    @Override
    public List<AppDownloadPage> getByUid(String locale, final String uid) {
        if (StringUtils.isBlank(uid)) {
            return null;
        }

        if (StringUtils.isBlank(locale)) {
            locale = "en-us";
        }

        final AppDownloadPageExample example = new AppDownloadPageExample();
        final AppDownloadPageExample.Criteria criteria = example.createCriteria();

        criteria.andLocaleEqualTo(locale);
        criteria.andUidEqualTo(uid);

        return this.appDownloadPageRepository.selectByExample(example);
    }

}
