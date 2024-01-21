package cc.newex.dax.extra.service.admin.cms.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.cms.ImgInfoExample;
import cc.newex.dax.extra.data.cms.ImgInfoRepository;
import cc.newex.dax.extra.domain.cms.ImgInfo;
import cc.newex.dax.extra.service.admin.cms.ImgInfoAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * cms图片表 服务实现
 *
 * @author mbg.generated
 * @date 2018-08-08 19:47:55
 */
@Slf4j
@Service
public class ImgInfoAdminServiceImpl extends AbstractCrudService<ImgInfoRepository, ImgInfo, ImgInfoExample, Long> implements ImgInfoAdminService {

    @Autowired
    private ImgInfoRepository cmsImgInfoRepos;

    @Override
    protected ImgInfoExample getPageExample(final String fieldName, final String keyword) {
        final ImgInfoExample example = new ImgInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }

}