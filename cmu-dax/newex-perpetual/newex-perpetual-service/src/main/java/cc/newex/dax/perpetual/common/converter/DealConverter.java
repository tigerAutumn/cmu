package cc.newex.dax.perpetual.common.converter;

import cc.newex.commons.lang.util.NumberUtil;
import cc.newex.dax.perpetual.domain.Deal;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 最新成交价转换类
 *
 * @author newex-team
 * @date 2018-11-26
 */
public class DealConverter {

    public static List<Object[]> toObjectArray(final List<Deal> dealList) {
        if (CollectionUtils.isEmpty(dealList)) {
            return Lists.newArrayListWithCapacity(0);
        }

        return dealList.stream()
                .map(deal -> toObjectArray(deal))
                .collect(Collectors.toList());
    }

    public static Object[] toObjectArray(final Deal deal) {
        return new Object[]{
                NumberUtil.regularBigDecimalFromBigDecimal(deal.getPrice()),    //价格
                NumberUtil.regularBigDecimalFromBigDecimal(deal.getAmount()),   //数量
                deal.getSide(),                                                 //方向
                deal.getCreatedDate().getTime(),                                //时间
                deal.getId()                                                    //成交id
        };
    }

}
