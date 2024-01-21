package cc.newex.dax.extra.service.cms.impl;

import cc.newex.dax.extra.data.cms.NewsRepository;
import cc.newex.dax.extra.service.cms.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 新闻文章 服务实现
 *
 * @author liutiejun
 * @date 2018-06-01
 */
@Slf4j
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

}
