package cc.newex.dax.users.service.admin.impl;

import cc.newex.commons.fileupload.FileUploadService;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.consts.UserKycConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.enums.FileTypeEnum;
import cc.newex.dax.users.common.util.DateUtils;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.criteria.UserFileInfoExample;
import cc.newex.dax.users.criteria.UserInfoExample;
import cc.newex.dax.users.criteria.UserInviteRecordExample;
import cc.newex.dax.users.criteria.UserKycEventExample;
import cc.newex.dax.users.criteria.UserKycInfoExample;
import cc.newex.dax.users.criteria.UserKycLevelExample;
import cc.newex.dax.users.data.UserFiatSettingRepository;
import cc.newex.dax.users.data.UserFileInfoRepository;
import cc.newex.dax.users.data.UserInfoRepository;
import cc.newex.dax.users.data.UserInviteRecordRepository;
import cc.newex.dax.users.data.UserKycInfoRepository;
import cc.newex.dax.users.domain.UserFiatSetting;
import cc.newex.dax.users.domain.UserFileInfo;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.UserKycEvent;
import cc.newex.dax.users.domain.UserKycLevel;
import cc.newex.dax.users.domain.UserLoginInfo;
import cc.newex.dax.users.dto.common.PageResultDTO;
import cc.newex.dax.users.dto.common.ParamPageDTO;
import cc.newex.dax.users.dto.common.UserAgeEnum;
import cc.newex.dax.users.dto.kyc.AdminUserKycAuditDTO;
import cc.newex.dax.users.dto.kyc.UserInfoStatDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminKycFirstDetailDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminKycSecondDetailDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminListReqDTO;
import cc.newex.dax.users.dto.kyc.UserKycAdminListResDTO;
import cc.newex.dax.users.service.admin.UserKycAdminService;
import cc.newex.dax.users.service.kyc.UserKycEventService;
import cc.newex.dax.users.service.kyc.UserKycImgService;
import cc.newex.dax.users.service.kyc.UserKycLevelService;
import cc.newex.dax.users.service.kyc.UserKycTokenService;
import cc.newex.dax.users.service.membership.UserInfoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserKycAdminServiceImpl implements UserKycAdminService {

    @Autowired
    private UserKycInfoRepository userKycInfoRepository;

    @Autowired
    private UserKycEventService userKycEventService;

    @Autowired
    private UserKycLevelService userKycLevelService;

    @Autowired
    private UserKycImgService userKycImgService;

    @Autowired
    private UserKycTokenService userKycTokenService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserFiatSettingRepository userFiatSettingRepository;

    @Autowired
    private UserInviteRecordRepository userInviteRecordRepository;

    @Autowired
    private UserFileInfoRepository userFileInfoRepository;

    @Override
    public PageResultDTO list(final UserKycAdminListReqDTO userKycAdminListReqDTO) {
        final ParamPageDTO paramPageDTO = userKycAdminListReqDTO.getParamPageDTO();
        final PageInfo pageInfo = new PageInfo();
        pageInfo.setStartIndex((paramPageDTO.getPageSize() * (paramPageDTO.getPage() - 1)));
        pageInfo.setPageSize(paramPageDTO.getPageSize());
        pageInfo.setSortItem("updated_date");
        pageInfo.setSortType(PageInfo.SORT_TYPE_DES);

        if (StringUtils.isNotEmpty(userKycAdminListReqDTO.getLoginName())) {
            final UserLoginInfo userLoginInfo = this.userInfoService.getUserLoginInfo(userKycAdminListReqDTO.getLoginName());

            if (userLoginInfo == null) {
                log.error("loginName not exist: {}", userKycAdminListReqDTO.getLoginName());
                return null;
            }

            userKycAdminListReqDTO.setUserId(userLoginInfo.getId());
        }

        final PageResultDTO resultDTO = PageResultDTO.builder().page(paramPageDTO.getPage() - 1).pageSize(paramPageDTO.getPageSize()).build();
        final UserKycAdminListResDTO dto = this.userKycInfoRepository.countKycAdminListByPager(pageInfo, userKycAdminListReqDTO);
        final List<UserKycAdminListResDTO> resultList = this.userKycInfoRepository.selectKycAdminListByPager(pageInfo, userKycAdminListReqDTO);
        //根据用户的年龄范围查询
        resultList.forEach(v -> {
            if (StringUtils.isNotEmpty(v.getCardNumber()) && v.getCardNumber().length() == 18) {
                final String birthday = v.getCardNumber();
                v.setCardNumber(birthday.substring(6, 14));
            }
        });
        final List<UserKycAdminListResDTO> result;
        final Long total;
        if (Objects.nonNull(userKycAdminListReqDTO.getAgeCode())) {
            result = this.compareAge(resultList, userKycAdminListReqDTO.getAgeCode());
            total = new Integer(result.size()).longValue();
        } else {
            result = resultList;
            total = dto.getTotal();
        }
        resultDTO.setTotal(total);
        resultDTO.setRows(result);
        return resultDTO;
    }

    @Override
    public UserKycAdminKycFirstDetailDTO selectKycAdminFirstDetail(final Long userId, final Integer level) {
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        UserKycAdminKycFirstDetailDTO detailDTO = this.userKycInfoRepository.selectChinaFirstDetail(userId, level);
        if (!ObjectUtils.isEmpty(detailDTO) && detailDTO.getCountry() == 156) {
            detailDTO.setFrontImg(this.fileUploadService.getSignedUrl(detailDTO.getFrontImg()));
            detailDTO.setBackImg(this.fileUploadService.getSignedUrl(detailDTO.getBackImg()));
        } else {
            detailDTO = this.userKycInfoRepository.selectForeignFirstDetail(userId, level);
        }
        final UserKycEventExample eventExample = new UserKycEventExample();
        eventExample.createCriteria().andUserIdEqualTo(userId).andLevelEqualTo(level);
        final List<UserKycEvent> list = this.userKycEventService.getByExample(eventExample);
        final List<UserKycAdminKycFirstDetailDTO.UserKycEventVO> userKycEventList = Lists.newArrayList();

        if (CollectionUtils.isNotEmpty(list)) {
            final UserKycAdminKycFirstDetailDTO finalDetailDTO = detailDTO;
            list.stream().forEach(event -> {
                final UserKycAdminKycFirstDetailDTO.UserKycEventVO userKycEventVO = ObjectCopyUtils.map(event, UserKycAdminKycFirstDetailDTO.UserKycEventVO.class);
                if (!StringUtils.contains(event.getRemarks(), "{")) {
                    userKycEventList.add(userKycEventVO);
                }
            });
            finalDetailDTO.setUserKycEventList(userKycEventList);
        }
        detailDTO.setUserId(userId);
        detailDTO.setMobile(userInfo.getMobile());
        detailDTO.setEmail(userInfo.getEmail());
        return detailDTO;
    }

    @Override
    public UserKycAdminKycSecondDetailDTO selectKycAdminSecondDetail(final Long userId, final Integer level) {
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            log.info("UserInfo not exist, userId: {}", userId);
            return null;
        }

        final UserKycAdminKycSecondDetailDTO detailDTO = this.userKycInfoRepository.selectKycAdminSecondDetail(userId, level);
        if (detailDTO == null) {
            log.info("UserKycAdminKycSecondDetailDTO not exist, userId: {}, level: {}", userId, level);
            return null;
        }

        final UserKycLevelExample example = new UserKycLevelExample();
        example.createCriteria().andUserIdEqualTo(userId).andLevelEqualTo(UserKycConsts.USER_KYC_LEVEL_1);
        final UserKycLevel userKycLevel = this.userKycLevelService.getOneByExample(example);

        if (!ObjectUtils.isEmpty(userKycLevel)) {
            detailDTO.setFirstStatus(userKycLevel.getStatus());
        }

        if (!ObjectUtils.isEmpty(detailDTO) && detailDTO.getCountry() == 156) {
            final UserKycLevelExample example2 = new UserKycLevelExample();
            example2.createCriteria().andUserIdEqualTo(userId).andLevelEqualTo(UserKycConsts.USER_KYC_LEVEL_2);
            final UserKycLevel userKycLevel2 = this.userKycLevelService.getOneByExample(example2);
            if (!ObjectUtils.isEmpty(userKycLevel2)) {
                if (userKycLevel2.getRemarks().contains("{")) {
                    final JSONObject object = JSON.parseObject(userKycLevel2.getRemarks());
                    final String requestId = object.getString("request_id");
                    detailDTO.setRequestId(requestId);
                }
            }

            detailDTO.setFrontImg(this.fileUploadService.getSignedUrl(detailDTO.getFrontImg()));
            detailDTO.setBackImg(this.fileUploadService.getSignedUrl(detailDTO.getBackImg()));

            //先用客服上传的图片，没有的话再用自己的图片
            final UserFileInfoExample userFileInfoExample = new UserFileInfoExample();
            userFileInfoExample.createCriteria().andUserIdEqualTo(userId).andFileTypeEqualTo(FileTypeEnum.KYC2.getType());
            final UserFileInfo po = this.userFileInfoRepository.selectOneByExample(userFileInfoExample);
            if (po != null && StringUtils.isNotEmpty(po.getFilePath())) {
                detailDTO.setHandsImg(this.fileUploadService.getSignedUrl(po.getFilePath()));
            } else if (StringUtils.isNotEmpty(detailDTO.getHandsImg())) {
                detailDTO.setHandsImg(this.fileUploadService.getSignedUrl(detailDTO.getHandsImg()));
            }

            if (StringUtils.isNotEmpty(detailDTO.getAddressImg())) {
                detailDTO.setAddressImg(this.fileUploadService.getSignedUrl(detailDTO.getAddressImg()));
            }
        } else {
            detailDTO.setHandsImg(this.fileUploadService.getSignedUrl(detailDTO.getHandsImg()));
            detailDTO.setAddressImg(this.fileUploadService.getSignedUrl(detailDTO.getAddressImg()));
            detailDTO.setCardImg(this.fileUploadService.getSignedUrl(detailDTO.getCardImg()));
        }
        final UserKycEventExample eventExample = new UserKycEventExample();
        eventExample.createCriteria().andUserIdEqualTo(userId).andLevelEqualTo(level);
        final List<UserKycEvent> list = this.userKycEventService.getByExample(eventExample);
        if (CollectionUtils.isNotEmpty(list)) {
            final UserKycAdminKycSecondDetailDTO finalDetailDTO = detailDTO;
            final List userKycEventList = new ArrayList<>();
            for (final UserKycEvent event : list) {
                final UserKycAdminKycSecondDetailDTO.UserKycEventVO userKycEventVO = ObjectCopyUtils.map(event, UserKycAdminKycSecondDetailDTO.UserKycEventVO.class);
                if (!StringUtils.contains(event.getRemarks(), "{")) {
                    userKycEventList.add(userKycEventVO);
                }
            }
            finalDetailDTO.setUserKycEventList(userKycEventList);
        }
        detailDTO.setUserId(userId);
        detailDTO.setMobile(userInfo.getMobile());
        detailDTO.setEmail(userInfo.getEmail());
        return detailDTO;
    }

    @Override
    public void deleteKycInfo(final Long userId) {
        this.deleteByUserId(userId);
        this.userKycLevelService.deleteByUserId(userId);
        this.userKycEventService.deleteByUserId(userId);
        this.userKycImgService.deleteByUserId(userId);
        this.userKycTokenService.deleteByUserId(userId);
    }

    @Override
    public void deleteByUserId(final Long userId) {
        if (userId == null) {
            return;
        }

        final UserKycInfoExample example = new UserKycInfoExample();
        example.createCriteria().andUserIdEqualTo(userId);

        this.userKycInfoRepository.deleteByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult auditKyc(final AdminUserKycAuditDTO audit) {
        final UserKycLevel userKycLevel = this.userKycLevelService.getById(audit.getId());
        if (ObjectUtils.isEmpty(userKycLevel)) {
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC_INFO_NOT_FOUND);
        }
        if (audit.getStatus() == UserKycConsts.USER_KYC_STATUS_2 && StringUtils.isAnyEmpty(audit.getRemarks(), audit.getRejectReason())) {
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_REJECT_NO_REASON);
        }
        final UserKycEvent userKycEvent = UserKycEvent.builder().userId(audit.getUserId()).dealUserId(audit.getDealUserId())
                .dealUserName(audit.getDealUserName()).remarks(audit.getRemarks())
                .rejectReason(audit.getRejectReason()).level(userKycLevel.getLevel())
                .kycLevelId(userKycLevel.getId()).status(audit.getStatus()).build();

        userKycLevel.setRemarks(audit.getRemarks());
        userKycLevel.setRejectReason(audit.getRejectReason());
        userKycLevel.setDealUserId(audit.getDealUserId());
        userKycLevel.setStatus(audit.getStatus());
        userKycLevel.setUpdatedDate(new Date());

        log.info("audit ={}", JSON.toJSONString(audit));

        this.userKycLevelService.editById(userKycLevel);
        this.userKycEventService.add(userKycEvent);
        return ResultUtils.success();
    }

    @Override
    public UserInfoStatDTO getUserInfoStat(final Long startTime, final Long endTime) {
        final UserKycLevelExample example = new UserKycLevelExample();
        example.createCriteria().andCreatedDateBetween(DateUtils.getDate(startTime), DateUtils.getDate(endTime))
                .andLevelEqualTo(UserKycConsts.USER_KYC_LEVEL_1).andStatusEqualTo(UserKycConsts.USER_KYC_STATUS_1);
        final int level1Count = this.userKycLevelService.countByExample(example);
        final UserKycLevelExample kyc2Example = new UserKycLevelExample();
        kyc2Example.createCriteria().andCreatedDateBetween(DateUtils.getDate(startTime), DateUtils.getDate(endTime))
                .andLevelEqualTo(UserKycConsts.USER_KYC_LEVEL_2).andStatusEqualTo(UserKycConsts.USER_KYC_STATUS_1);
        final int level2Count = this.userKycLevelService.countByExample(kyc2Example);
        final UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andCreatedDateBetween(DateUtils.getDate(startTime), DateUtils.getDate(endTime));
        final int userCount = this.userInfoRepository.countByExample(userInfoExample);

        final UserInviteRecordExample userInviteRecordExample = new UserInviteRecordExample();
        userInviteRecordExample.createCriteria().andCreatedDateBetween(DateUtils.getDate(startTime), DateUtils.getDate(endTime));
        final Integer inviteKyc2Count = this.userInviteRecordRepository.inviteKyc2Count(DateUtils.getDate(startTime), DateUtils.getDate(endTime));
        final UserInfoStatDTO stat = UserInfoStatDTO.builder().kyc1Count(level1Count).kyc2Count(level2Count)
                .userCount(userCount).inviteKyc2Count(inviteKyc2Count).build();
        return stat;
    }

    @Override
    public boolean enableFiatStatus(final long userId, final int status) {
        final UserFiatSetting userFiatSetting = UserFiatSetting.builder()
                .userId(userId)
                .status(status)
                .build();
        return this.userFiatSettingRepository.updateById(userFiatSetting) > 0;
    }

    private List<UserKycAdminListResDTO> compareAge(final List<UserKycAdminListResDTO> resultList, final Integer ageCode) {
        final List<UserKycAdminListResDTO> result = new ArrayList<>();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final Calendar moreDate = Calendar.getInstance();
        moreDate.add(Calendar.YEAR, -60);
        final Calendar lessDate = Calendar.getInstance();
        lessDate.add(Calendar.YEAR, -18);
        resultList.forEach(v -> {
            final Calendar birthday = Calendar.getInstance();
            try {
                birthday.setTime(sdf.parse(v.getCardNumber()));
                if (ageCode.equals(UserAgeEnum.LESS.getCode())) {
                    if (birthday.after(lessDate)) {
                        result.add(v);
                    }
                } else if (ageCode.equals(UserAgeEnum.BETWEEN.getCode())) {
                    if (birthday.before(lessDate) && birthday.after(moreDate)) {
                        result.add(v);
                    }
                } else if (ageCode.equals(UserAgeEnum.MORE.getCode())) {
                    if (birthday.before(moreDate)) {
                        result.add(v);
                    }
                }

            } catch (final ParseException e) {
                e.printStackTrace();
            }
        });
        return result;
    }
}
