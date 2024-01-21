package cc.newex.dax.perpetual.common.converter;

import cc.newex.dax.perpetual.domain.UserBill;
import cc.newex.dax.perpetual.dto.InnerUserBillDTO;
import cc.newex.dax.perpetual.dto.UserBillDTO;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @date 2018/12/01
 */
public class UserBillConverter {

    public static List<UserBillDTO> convertToBillInfo(final List<UserBill> userBillList,
                                                      final int quoteDigit) {
        if (CollectionUtils.isEmpty(userBillList)) {
            return Lists.newArrayListWithCapacity(0);
        }

        return userBillList.stream().map(userBill -> convertToBillInfo(userBill, quoteDigit))
                .collect(Collectors.toList());
    }

    public static UserBillDTO convertToBillInfo(final UserBill userBill, final int quoteDigit) {

        return UserBillDTO.builder().createdDate(userBill.getCreatedDate().getTime())
                .currencyCode(userBill.getCurrencyCode()).detailSide(userBill.getDetailSide())
                .type(userBill.getType())
                .amount(UserBillConverter.setDigit(userBill.getAmount(), quoteDigit))
                .size(UserBillConverter.setDigit(userBill.getSize(), quoteDigit))
                .profit(UserBillConverter.setDigit(userBill.getProfit(), quoteDigit))
                .balance(UserBillConverter.setDigit(userBill.getAfterBalance(), quoteDigit))
                .feeCurrencyCode(userBill.getFeeCurrencyCode())
                .fee(UserBillConverter.setDigit(userBill.getFee(), quoteDigit)).build();
    }

    public static List<InnerUserBillDTO> convertToInnerBillInfo(final List<UserBill> userBillList,
                                                      final int quoteDigit) {
        if (CollectionUtils.isEmpty(userBillList)) {
            return Lists.newArrayListWithCapacity(0);
        }

        return userBillList.stream().map(userBill -> convertToInnerBillInfo(userBill, quoteDigit))
                .collect(Collectors.toList());
    }

    public static InnerUserBillDTO convertToInnerBillInfo(final UserBill userBill, final int quoteDigit) {

        return InnerUserBillDTO.builder().createdDate(userBill.getCreatedDate().getTime())
                .currencyCode(userBill.getCurrencyCode()).detailSide(userBill.getDetailSide())
                .type(userBill.getType())
                .amount(UserBillConverter.setDigit(userBill.getAmount(), quoteDigit))
                .size(UserBillConverter.setDigit(userBill.getSize(), quoteDigit))
                .profit(UserBillConverter.setDigit(userBill.getProfit(), quoteDigit))
                .balance(UserBillConverter.setDigit(userBill.getAfterBalance(), quoteDigit))
                .feeCurrencyCode(userBill.getFeeCurrencyCode())
                .fee(UserBillConverter.setDigit(userBill.getFee(), quoteDigit))
                .id(userBill.getId())
                .userId(userBill.getUserId()).build();
    }

    private static String setDigit(final BigDecimal num, final int digit) {
        return Optional.ofNullable(num).orElse(BigDecimal.ZERO).setScale(digit, RoundingMode.DOWN)
                .stripTrailingZeros().toPlainString();

    }

}
