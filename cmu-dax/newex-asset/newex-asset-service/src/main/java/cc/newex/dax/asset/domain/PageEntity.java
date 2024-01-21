package cc.newex.dax.asset.domain;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import lombok.Data;

import java.util.List;

/**
 * UserCurrencyBalance, UserCurrencyBalanceExample, Long
 *
 * @author newex-team
 * @data 05/04/2018
 */
@Data
public class PageEntity {
    int pageTotal;
    int pageNum;
    int pageSize;
    List data;

    public static <B, E, L> PageEntity getPage(CrudService<B, E, L> service, int pageNum, int pageSize, E example) {

        return PageEntity.getPage(service, pageNum, pageSize, "id", example);
    }

    public static <B, E, L> PageEntity getPage(CrudService<B, E, L> service, int pageNum, int pageSize, String sortItem, E example) {
        int startIndex = (pageNum - 1) * pageSize;
        startIndex = startIndex >= 0 ? startIndex : 0;
        pageSize = pageSize >= 0 ? pageSize : 10;
        PageInfo pageInfo = new PageInfo();
        pageInfo.setSortItem(sortItem);
        pageInfo.setPageSize(pageSize);
        pageInfo.setStartIndex(startIndex);
        pageInfo.setSortType(PageInfo.SORT_TYPE_DES);

        List<B> recordList = service.getByPage(pageInfo, example);

        long total = pageInfo.getTotals();
        Long pageTotal = (total + pageSize - 1) / pageSize;
        PageEntity pageEntity = new PageEntity();
        pageEntity.setData(recordList);
        pageEntity.setPageNum(pageNum);
        pageEntity.setPageSize(pageSize);
        pageEntity.setPageTotal(pageTotal.intValue());
        return pageEntity;
    }
}
