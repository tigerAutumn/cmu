package cc.newex.dax.perpetual.util;

import cc.newex.dax.perpetual.domain.Contract;

import java.util.Calendar;

/**
 * @author newex-team
 * @date 2018/12/20
 */
public class SettlementDateUtil {

    public static void initSettlementDateAndContract(final String type, final Contract contract) {
        //每周五下午三点结算
        final Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY, 15);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MILLISECOND, 0);
        instance.setFirstDayOfWeek(Calendar.MONDAY);
        if ("week".equals(type)) {
            instance.set(Calendar.DAY_OF_WEEK, instance.getFirstDayOfWeek() + 4);
            if (System.currentTimeMillis() < instance.getTime().getTime()) {
                instance.add(Calendar.DAY_OF_WEEK, 7);
            }
        } else if ("nextweek".equals(type)) {
            instance.set(Calendar.DAY_OF_WEEK, instance.getFirstDayOfWeek() + 4);
            if (System.currentTimeMillis() < instance.getTime().getTime()) {
                instance.add(Calendar.DAY_OF_WEEK, 7);
            } else {
                instance.add(Calendar.DAY_OF_WEEK, 7);
                instance.add(Calendar.DAY_OF_WEEK, 7);
            }
        } else if ("month".equals(type)) {
            int lastDay = instance.getActualMaximum(Calendar.DAY_OF_MONTH);
            instance.set(Calendar.DAY_OF_MONTH, lastDay);
            while (instance.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                instance.set(Calendar.DAY_OF_MONTH, --lastDay);
            }
            if (System.currentTimeMillis() >= instance.getTime().getTime()) {
                instance.add(Calendar.MONTH, 1);
                lastDay = instance.getActualMaximum(Calendar.DAY_OF_MONTH);
                instance.set(Calendar.DAY_OF_MONTH, lastDay);
                while (instance.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                    instance.set(Calendar.DAY_OF_MONTH, --lastDay);
                }
            }
        } else {
            final int quarter = (int) Math.ceil((instance.get(Calendar.MONTH) + 1) / 3);
            instance.set(Calendar.MONTH, quarter * 3 - 1);

            int lastDay = instance.getActualMaximum(Calendar.DAY_OF_MONTH);
            instance.set(Calendar.DAY_OF_MONTH, lastDay);
            while (instance.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                instance.set(Calendar.DAY_OF_MONTH, --lastDay);
            }
            if (System.currentTimeMillis() >= instance.getTime().getTime()) {
                instance.add(Calendar.MONTH, 3);
                lastDay = instance.getActualMaximum(Calendar.DAY_OF_MONTH);
                instance.set(Calendar.DAY_OF_MONTH, lastDay);
                while (instance.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                    instance.set(Calendar.DAY_OF_MONTH, --lastDay);
                }
            }
        }
        contract.setSettlementDate(instance.getTime());

        String contractCode = contract.getBase() + contract.getQuote();
        final int month = instance.get(Calendar.MONTH) + 1;
        if (month < 10) {
            contractCode = contractCode + "0";
        }
        contractCode = contractCode + month;
        final int day = instance.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            contractCode = contractCode + "0";
        }
        contractCode = contractCode + day;
//        if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
//            contractCode = contractCode + "(" + contract.getQuote() + ")";
//        } else {
//            contractCode = contractCode + "(" + contract.getBase() + ")";
//        }
        contract.setContractCode(contractCode.toLowerCase());
    }
}
