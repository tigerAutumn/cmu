package cc.newex.dax.boss.common.util;

/**
 * @author allen
 * @date 2018/5/30
 * @des
 */
public class PageUtil {

    public static int totalSize(final Integer size, final Integer pageIndex, final Integer pageSize) {
        if (size <= 0) {
            return pageIndex * pageSize;
        }
        if (pageIndex * pageSize < 100 && size > 0) {
            return 100;
        }
        return (pageIndex * pageSize) + 100;

    }
}
