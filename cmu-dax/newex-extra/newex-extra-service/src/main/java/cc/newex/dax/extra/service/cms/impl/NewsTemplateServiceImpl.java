package cc.newex.dax.extra.service.cms.impl;

import cc.newex.dax.extra.data.cms.NewsTemplateRepository;
import cc.newex.dax.extra.service.cms.NewsTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 新闻模板 服务实现
 *
 * @author liutiejun
 * @date 2018-06-01
 */
@Slf4j
@Service
public class NewsTemplateServiceImpl implements NewsTemplateService {

    @Autowired
    private NewsTemplateRepository newsTemplateRepository;

}
