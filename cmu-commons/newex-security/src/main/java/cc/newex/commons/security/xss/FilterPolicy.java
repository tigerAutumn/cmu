package cc.newex.commons.security.xss;

/**
 * Xss过滤策略接口
 *
 * @author newex-team
 * @date 2017/12/09
 */
public interface FilterPolicy {
    /**
     * 执行Xss过滤方法
     *
     * @param input 原字符串
     * @return Xss过滤字符串
     */
    String filter(String input);
}

