package cc.newex.dax.extra.service.cache;

import java.util.List;

/**
 * 应用所有操作存调用都集中封装到该类
 *
 * @author newex-team
 * @date 2018-06-18
 */
public interface AppCacheService {

    <T> List<T> getList(final String key, Class<T> clazz);

    <T> void setList(final String key, final List<T> list);

    void deleteKey(final String key);
}
