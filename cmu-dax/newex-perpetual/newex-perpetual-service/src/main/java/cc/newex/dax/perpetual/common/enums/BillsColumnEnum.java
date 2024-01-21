package cc.newex.dax.perpetual.common.enums;

import cc.newex.commons.support.i18n.LocaleUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author newex-team
 * @date 2018/11/01
 */
public enum BillsColumnEnum {
    TIME(1),
    CONTRACT(2),
    TYPE(3),
    COST(4),
    BALANCE(5);

    public final static String BILL_TYPE_PREFIX = "bill.column.code.";
    private final Integer code;

    BillsColumnEnum(final Integer code) {
        this.code = code;
    }

    public static JSONArray toJSONArray() {
        final JSONArray array = new JSONArray();
        for (final BillsColumnEnum billsColumnEnum : BillsColumnEnum.values()) {
            final JSONObject billTypeItem = new JSONObject();
            billTypeItem.put("type", billsColumnEnum);
            billTypeItem.put("code", billsColumnEnum.code);
            billTypeItem.put("desc", billsColumnEnum.getDesc());
            array.add(billTypeItem);
        }
        return array;
    }

    public static List<String> getAll() {
        final List<String> list = new ArrayList<>();
        for (final BillsColumnEnum billTypeEnum : BillsColumnEnum.values()) {
            list.add(billTypeEnum.getDesc());
        }
        return list;
    }

    public static String getDesc(final Integer code) {
        return LocaleUtils.getMessage(BillsColumnEnum.BILL_TYPE_PREFIX + code);
    }

    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return LocaleUtils.getMessage(BillsColumnEnum.BILL_TYPE_PREFIX + this.code);
    }

    public JSONObject toJSON() {
        final JSONObject json = new JSONObject();
        json.put("type", this.name());
        json.put("code", this.code);
        final String desc = LocaleUtils.getMessage(BillsColumnEnum.BILL_TYPE_PREFIX + this.code);
        json.put("desc", desc);
        return json;
    }
}
