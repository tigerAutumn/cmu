package cc.newex.dax.users.service.kyc;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.users.criteria.UserKycInfoExample;
import cc.newex.dax.users.domain.UserKycInfo;
import cc.newex.dax.users.domain.UserKycLevel;
import cc.newex.dax.users.domain.faceid.FaceToken;
import cc.newex.dax.users.dto.kyc.FaceIdReqDTO;
import cc.newex.dax.users.dto.kyc.KycFirstForeignDTO;
import cc.newex.dax.users.dto.kyc.KycSecondForeignDTO;
import cc.newex.dax.users.dto.kyc.KycUploadReqDTO;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户kyc表 服务接口
 *
 * @author newex-team
 * @date 2018-05-03
 */
public interface UserKycInfoService extends CrudService<UserKycInfo, UserKycInfoExample, Long> {
    /**
     * kyc文件上传
     *
     * @param file
     * @param type
     * @param country
     * @param userId
     * @return
     * @throws Exception
     */
    ResponseResult uploadFile(final MultipartFile file, String type, Integer country, Long userId) throws Exception;

    /**
     * @param form
     * @description 保存kyc一级认证信息
     * @date 2018/5/4 下午6:48
     */
    ResponseResult saveKycFirstInfo(KycUploadReqDTO form);

    /**
     * @param form
     * @description 国外用户的kyc1认证保存
     * @date 2018/5/7 下午9:30
     */
    ResponseResult saveForeignKycFirstInfo(KycFirstForeignDTO form);

    /**
     * 根据用户id查询kyc信息
     *
     * @param userId
     * @return
     */
    UserKycInfo getByUserId(Long userId);

    /**
     * 根据用户id查询kyc认证等级信息
     *
     * @param userId
     * @param level
     * @return
     */
    UserKycLevel getUserKycLevelByUserId(Long userId, Integer level);

    /**
     * 通过用户id获取用户认证的最高等级
     *
     * @param userId
     * @return
     */
    UserKycLevel getMaxKycLevelByUserId(Long userId);

    /**
     * @param faceIdReqDTO
     * @description 保存用户二级认证等级
     * @date 2018/5/4 下午6:48
     */
    boolean saveSecondLevel(FaceIdReqDTO faceIdReqDTO) throws UnirestException;

    /**
     * 获取用户kyc2访问face++需要的token
     *
     * @param userId
     * @return
     */
    FaceToken getFaceToken(Long userId);

    /**
     * @param userId
     * @param data
     * @description 验证活体证件
     * @date 2018/5/17 下午9:25
     */
    ResponseResult verify(Long userId, String data) throws Exception;

    ResponseResult v2_verify(Long userId, String delta, String userAgent);

    /**
     * @param kycSecondForeignDTO
     * @description 保存用户二级认证等级
     * @date 2018/5/4 下午6:48
     */
    ResponseResult saveSecondForeignLevel(KycSecondForeignDTO kycSecondForeignDTO);

    /**
     * 通过用户id返回通过kyc2认证的用户id列表
     *
     * @param idList
     * @return
     */
    ResponseResult<List<Long>> queryKyc2PassIdList(final List<Long> idList);
}
