package com.xxl.job.admin.dao;

import java.util.List;

import com.xxl.job.admin.core.model.XxlJobRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * XxlJobRelationDao
 * @author huixin
 */
@Mapper
public interface XxlJobRelationDao {

	int save(XxlJobRelation info);

	XxlJobRelation loadById(@Param("id") int id);

	int update(XxlJobRelation item);

	int delete(@Param("userId") int userId, @Param("relationId") int relationId, @Param("type") int type);

	int deleteById(@Param("id") int id);


	List<XxlJobRelation> findAll();

	List<XxlJobRelation> queryXxlJobRelationByUseridAndRelationId(@Param("userId") int userId,
                                                                  @Param("relationId") int relationId);

	List<XxlJobRelation> queryXxlJobRelationByUserid(@Param("userId") int userId);


}
