package cc.newex.dax.extra.service.cms.impl;

import cc.newex.dax.extra.data.cms.NewsCategoryRepository;
import cc.newex.dax.extra.service.cms.NewsCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 新闻类别 服务实现
 *
 * @author liutiejun
 * @date 2018-06-01
 */
@Slf4j
@Service
public class NewsCategoryServiceImpl implements NewsCategoryService {

    @Autowired
    private NewsCategoryRepository newsCategoryRepository;

}
