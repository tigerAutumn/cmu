package cc.newex.dax.users.common.util;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author minhan
 * @Date: 2018/6/26 09:06
 * @Description:
 */
public class ObjectCopyUtils {
    final static ModelMapper mapper = new ModelMapper();

    /**
     * 简单的复制出新对象ArrayList
     */
    public static <S, D> List<D> mapList(final Iterable<S> sourceList, final Class<D> destinationClass) {
        final List<D> destionationList = new ArrayList<>();
        for (final S source : sourceList) {
            if (source != null) {
                destionationList.add(ObjectCopyUtils.mapper.map(source, destinationClass));
            }
        }
        return destionationList;
    }

    /**
     * 简单的复制出新类型对象.
     */
    public static <S, D> D map(final S source, final Class<D> destinationClass) {
        return ObjectCopyUtils.mapper.map(source, destinationClass);
    }
}
