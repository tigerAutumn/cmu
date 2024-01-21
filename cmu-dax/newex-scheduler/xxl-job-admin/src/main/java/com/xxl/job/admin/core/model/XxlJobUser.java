package com.xxl.job.admin.core.model;


import java.util.Date;

import lombok.Data;

@Data
public class XxlJobUser {

    private int id;
    private int type;
    private String userName;
    private String password;
    private Date addTime;

}
