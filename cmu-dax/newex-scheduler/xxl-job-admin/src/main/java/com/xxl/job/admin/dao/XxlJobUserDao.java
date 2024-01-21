package com.xxl.job.admin.dao;

import java.util.List;

import com.xxl.job.admin.core.model.XxlJobUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * user info
 * @author huixin
 */
@Mapper
public interface XxlJobUserDao {

	XxlJobUser login(@Param("userName") String userName, @Param("password") String password);

	int save(XxlJobUser info);

	XxlJobUser loadById(@Param("id") int id);

	int update(XxlJobUser item);

	int delete(@Param("id") int id);

	List<XxlJobUser> findAll();

	XxlJobUser getUserByUserName(@Param("userName") String userName);

}
