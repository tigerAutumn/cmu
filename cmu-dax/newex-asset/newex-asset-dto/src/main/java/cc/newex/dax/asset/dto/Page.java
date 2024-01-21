package cc.newex.dax.asset.dto;

import lombok.Data;

import java.util.List;

/**
 * UserCurrencyBalance, UserCurrencyBalanceExample, Long
 *
 * @author newex-team
 * @data 05/04/2018
 */
@Data
public class Page<T> {
    int pageTotal;
    int pageNum;
    int pageSize;
    List<T> data;
}
