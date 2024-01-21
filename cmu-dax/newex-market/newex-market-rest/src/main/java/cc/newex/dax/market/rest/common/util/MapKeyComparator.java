package cc.newex.dax.market.rest.common.util;

import java.util.Comparator;

/**
 * 给map根据key排序
 *
 * @author
 * @date 2018/03/18
 **/
public class MapKeyComparator implements Comparator<String> {

    @Override
    public int compare(final String o1, final String o2) {
        return o1.compareTo(o2);
    }

}
