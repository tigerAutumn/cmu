package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobTrigger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * job info
 * @author xuxueli 2016-1-12 18:03:45
 */
@Mapper
public interface XxlJobTriggerDao {

	List<XxlJobTrigger> findAll();

	XxlJobTrigger find(@Param("jobId") int jobId);

}
