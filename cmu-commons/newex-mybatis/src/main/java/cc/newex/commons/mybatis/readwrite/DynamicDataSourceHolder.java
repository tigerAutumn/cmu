package cc.newex.commons.mybatis.readwrite;

/**
 * @author newex-team
 * @date 2017/12/09
 */
public final class DynamicDataSourceHolder {
    private static final ThreadLocal<DataSourceFrom> DATASOURCE_THREAD_LOCAL = new ThreadLocal<>();

    private DynamicDataSourceHolder() {
    }

    public static void putDataSource(final DataSourceFrom dataSource) {
        DATASOURCE_THREAD_LOCAL.set(dataSource);
    }

    public static DataSourceFrom getDataSource() {
        return DATASOURCE_THREAD_LOCAL.get();
    }

    public static void clearDataSource() {
        DATASOURCE_THREAD_LOCAL.remove();
    }

}
