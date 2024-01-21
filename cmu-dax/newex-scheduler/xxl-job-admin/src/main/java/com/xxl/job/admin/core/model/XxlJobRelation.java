package com.xxl.job.admin.core.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
public class XxlJobRelation {

    private int id;
    private int userId;
    private int relationId;
    private int type;
    private Date addTime;

    /**
     *
     */
    private String userName;
    private String title;
    private String addressList;
}
