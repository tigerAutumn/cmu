package cc.newex.dax.users.service.admin;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.users.criteria.UserFileInfoExample;
import cc.newex.dax.users.domain.UserFileInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户文件表 服务接口
 *
 * @author newex-team
 * @date 2018-08-15 18:27:33
 */
public interface UserFileInfoService extends CrudService<UserFileInfo, UserFileInfoExample, Long> {

    ResponseResult<String> uploadFile(final MultipartFile file, final Long userId);

    String getFullPath(final String fileName);

}