package cc.newex.commons.mybatis.data;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @param <T> Po
 * @param <U> Example
 * @param <K> Key字段数据类型(Integer,Long,String等)
 * @author newex-team
 * @date 2017/12/09
 */
public interface DeleteRepository<T, U, K> {
    /**
     * 根据主键删除记录
     *
     * @param id id主键值
     * @return 影响的记录数
     */
    int deleteById(@Param("id") K id);

    /**
     * 根据条件删除记录
     *
     * @param example 查询Example条件参数
     * @return 影响的记录数
     */
    int deleteByExample(@Param("example") U example);

    /**
     * @param records
     * @return
     */
    int deleteIn(@Param("records") List<T> records);
}

