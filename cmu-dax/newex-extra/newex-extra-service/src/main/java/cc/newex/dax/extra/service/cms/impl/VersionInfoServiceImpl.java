package cc.newex.dax.extra.service.cms.impl;

import cc.newex.dax.extra.data.cms.VersionInfoRepository;
import cc.newex.dax.extra.service.cms.VersionInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 版本信息 服务实现
 *
 * @author liutiejun
 * @date 2018-07-03
 */
@Slf4j
@Service
public class VersionInfoServiceImpl implements VersionInfoService {

    @Autowired
    private VersionInfoRepository versionInfoRepository;

}
