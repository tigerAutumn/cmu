package cc.newex.dax.users.service.subaccount;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.dax.users.dto.subaccount.SubAccountInfoDTO;

import java.util.List;

/**
 * 子账户绑定、解锁的操作记录表 服务接口
 *
 * @author newex-team
 * @date 2018-11-05 17:21:06
 */
public interface SubAccountInfoService {

    List<SubAccountInfoDTO> getByParentId(PageInfo pageInfo, Long parentId, Integer brokerId);

}