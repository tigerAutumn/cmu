package cc.newex.dax.perpetual.util;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.dax.perpetual.dto.ParamPageDTO;
import org.apache.commons.lang3.StringUtils;

/**
 * @author allen
 * @date 2018/4/6
 * @des 计算分页总数
 */
public class PageUtil {

    /**
     * 计算分页总条数
     *
     * @param size
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static int totalSize(final Integer size, final Integer pageIndex, final Integer pageSize) {
        if (size <= 0) {
            return pageIndex * pageSize;
        }
        if (pageIndex * pageSize < 100 && size > 0) {
            return 100;
        }
        return (pageIndex * pageSize) + 100;

    }

    public static PageInfo toPageInfo(final Integer page, final Integer pageSize) {
        return toPageInfo(page, pageSize, null, null);
    }

    public static PageInfo toPageInfo(final Integer page, final Integer pageSize, final String sortItem, final String sortType) {
        final PageInfo pageInfo = new PageInfo();

        pageInfo.setStartIndex(pageSize * (page - 1));
        pageInfo.setPageSize(pageSize);

        if (StringUtils.isNotBlank(sortItem)) {
            pageInfo.setSortItem(sortItem);
        }

        if (StringUtils.isNotBlank(sortType)) {
            pageInfo.setSortType(sortType);
        }

        return pageInfo;
    }

    public static PageInfo toPageInfo(final ParamPageDTO paramPageDTO) {
        return toPageInfo(paramPageDTO.getPage(), paramPageDTO.getPageSize());
    }

}
