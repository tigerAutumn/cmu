package com.xxl.job.admin.service.impl;

import com.xxl.job.admin.dao.XxlJobLogBakDao;
import com.xxl.job.admin.dao.XxlJobLogDao;
import com.xxl.job.admin.service.XxlJobLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author minhan
 * @Date: 2018/8/8 09:56
 * @Description:
 */
@Service
public class XxlJogLogServiceImpl implements XxlJobLogService {

    @Resource
    private XxlJobLogDao xxlJobLogDao;
    @Resource
    private XxlJobLogBakDao xxlJobLogBakDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean replaceLog() {

        Integer maxId = xxlJobLogDao.getMaxId();
        if (maxId == null) {
            return false;
        }
        xxlJobLogBakDao.replaceData(maxId);
        xxlJobLogDao.deleteById(maxId);
        return false;
    }
}
