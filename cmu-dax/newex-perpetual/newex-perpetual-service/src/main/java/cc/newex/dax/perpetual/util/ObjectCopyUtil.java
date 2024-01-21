package cc.newex.dax.perpetual.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.InheritingConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.modelmapper.convention.MatchingStrategies.STANDARD;

/**
 * @author harry
 * @Date: 2018/6/26 09:06
 * @Description:
 */
public class ObjectCopyUtil {
    private static final InheritingConfiguration configuration = new InheritingConfiguration();

    static {
        configuration.setSkipNullEnabled(true);
    }

    /**
     * 简单的复制出新对象ArrayList
     */
    public static <S, D> List<D> mapList(final Iterable<S> sourceList,
                                         final Class<D> destinationClass) {
        final ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(STANDARD);
        final List<D> destionationList = new ArrayList<>();
        for (final S source : sourceList) {
            if (source != null) {
                destionationList.add(mapper.map(source, destinationClass));
            }
        }
        return destionationList;
    }

    /**
     * 简单的复制出新类型对象.
     */
    public static <S, D> D map(final S source, final Class<D> destinationClass) {
        final ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(STANDARD);
        return mapper.map(source, destinationClass);
    }
}
