package cc.newex.commons.support.transaction;

/**
 * @author newex-team
 * @date 2017/12/28
 */
@FunctionalInterface
public interface Transaction<V> {

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    V exec();
}
