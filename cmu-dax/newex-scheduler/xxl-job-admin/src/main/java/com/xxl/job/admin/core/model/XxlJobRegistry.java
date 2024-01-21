package com.xxl.job.admin.core.model;

import java.util.Date;

import lombok.Data;

/**
 * Created by xuxueli on 16/9/30.
 */
@Data
public class XxlJobRegistry {

    private int id;
    private String registryGroup;
    private String registryKey;
    private String registryValue;
    private Date updateTime;


}
