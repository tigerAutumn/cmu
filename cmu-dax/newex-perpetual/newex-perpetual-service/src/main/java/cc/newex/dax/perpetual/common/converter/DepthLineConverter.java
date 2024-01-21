package cc.newex.dax.perpetual.common.converter;

import cc.newex.commons.lang.util.NumberUtil;
import cc.newex.dax.perpetual.domain.redis.DepthLine;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 深度数据转换类
 *
 * @author newex-team
 * @date 2018-11-28
 */
public class DepthLineConverter {

    public static List<String[]> toStringArray(final List<DepthLine> depthLineList, final boolean reverse, final int length) {
        if (CollectionUtils.isEmpty(depthLineList)) {
            return Lists.newArrayListWithCapacity(0);
        }

        if (reverse) {
            return depthLineList.stream()
                    .sorted(Comparator.comparing(DepthLine::getPrice).reversed())
                    .limit(length)
                    .map(d -> toStringArray(d))
                    .collect(Collectors.toList());
        } else {
            return depthLineList.stream()
                    .sorted(Comparator.comparing(DepthLine::getPrice))
                    .limit(length)
                    .map(d -> toStringArray(d))
                    .collect(toList());
        }

    }

    public static String[] toStringArray(final DepthLine depthLine) {
        final BigDecimal price = depthLine.getPrice();
        final BigDecimal totalSize = depthLine.getTotalAmount();
        final BigDecimal sumTotalAmount = depthLine.getSumTotalAmount();

        return new String[]{NumberUtil.regularBigDecimalFromBigDecimal(price),//价格
                NumberUtil.regularBigDecimalFromBigDecimal(totalSize),//总数量
                NumberUtil.regularBigDecimalFromBigDecimal(sumTotalAmount)};//该价格前的总数量
    }

    /**
     * 多返回一个userId
     *
     * @param depthLineList
     * @param reverse
     * @param length
     * @return
     */
    public static List<String[]> toStringArrayForUserId(final List<DepthLine> depthLineList, final boolean reverse, final int length) {
        if (CollectionUtils.isEmpty(depthLineList)) {
            return Lists.newArrayListWithCapacity(0);
        }

        if (reverse) {
            return depthLineList.stream()
                    .sorted(Comparator.comparing(DepthLine::getPrice).reversed())
                    .limit(length)
                    .map(d -> toStringArrayForUserId(d))
                    .collect(Collectors.toList());
        } else {
            return depthLineList.stream()
                    .sorted(Comparator.comparing(DepthLine::getPrice))
                    .limit(length)
                    .map(d -> toStringArrayForUserId(d))
                    .collect(toList());
        }

    }

    /**
     * 多返回一个userId
     *
     * @param depthLine
     * @return
     */
    public static String[] toStringArrayForUserId(final DepthLine depthLine) {
        final BigDecimal price = depthLine.getPrice();
        final BigDecimal totalSize = depthLine.getTotalAmount();
        final BigDecimal sumTotalAmount = depthLine.getSumTotalAmount();

        return new String[]{NumberUtil.regularBigDecimalFromBigDecimal(price),
                NumberUtil.regularBigDecimalFromBigDecimal(totalSize),
                NumberUtil.regularBigDecimalFromBigDecimal(sumTotalAmount),
                depthLine.getUserId()};
    }

}
