package cc.newex.dax.boss.web.controller.outer.v1.extra.cms;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.consts.UserAuthConsts;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.extra.cms.BannerNoticeExtraVO;
import cc.newex.dax.extra.client.ExtraCmsBannerNoticeAdminClient;
import cc.newex.dax.extra.dto.cms.BannerNoticeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author newex-jinlong
 * @date 2018-04-28
 */
@RestController
@RequestMapping(value = "/v1/boss/extra/cms/banner")
@Slf4j
public class BannerController {

    @Autowired
    private ExtraCmsBannerNoticeAdminClient extraCmsBannerNoticeAdminClient;

    @RequestMapping(value = "/add")
    @OpLog(name = "新增banner")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BANNER_ADD"})
    public ResponseResult add(@Valid final BannerNoticeExtraVO bn, final HttpServletRequest request) throws
            ParseException {

        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);
        final String beginDateTime = request.getParameter("startTime");
        final String endDateTime = request.getParameter("endTime");
        final Date day = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Date newStartTime = sdf.parse(beginDateTime);
        final Date newEndTime = sdf.parse(endDateTime);
        final Date createTime = sdf.parse(sdf.format(day));

        try {
            final BannerNoticeDTO bannerNotice = BannerNoticeDTO.builder()
                    .type(bn.getType())
                    .platform(bn.getPlatform())
                    .startTime(newStartTime)
                    .endTime(newEndTime)
                    .sort(bn.getSort())
                    .locale(bn.getLocale())
                    .title(bn.getTitle())
                    .text(bn.getText())
                    .imageUrl(bn.getImageUrl())
                    .originalImageUrl(bn.getOriginalImageUrl())
                    .link(bn.getLink())
                    .shareTitle(bn.getShareTitle())
                    .shareText(bn.getShareText())
                    .shareLink(bn.getShareLink())
                    .shareImageUrl(bn.getShareImageUrl())
                    .status(bn.getStatus())
                    .publishUser(user.getAccount())
                    .createdDate(createTime)
                    .brokerId(bn.getBrokerId())
                    .build();

            final ResponseResult result = this.extraCmsBannerNoticeAdminClient.saveBannerNotice(bannerNotice);
            return ResultUtil.getCheckedResponseResult(result);

        } catch (final Exception e) {
            log.error("add banner api error " + e);
        }
        return ResultUtils.success();

    }

    @RequestMapping(value = "/edit")
    @OpLog(name = "修改banner")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BANNER_EDIT"})
    public ResponseResult edit(@Valid final BannerNoticeExtraVO bn, final HttpServletRequest request,
                               @RequestParam(value = "id", required = false) final Long id) throws ParseException {
        final User user = (User) request.getSession().getAttribute(UserAuthConsts.CURRENT_USER);
        final String beginDateTime = request.getParameter("startTime");
        final String endDateTime = request.getParameter("endTime");
        final Date day = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Date newStartTime = sdf.parse(beginDateTime);
        final Date newEndTime = sdf.parse(endDateTime);
        final Date updateTime = sdf.parse(sdf.format(day));
        try {
            final BannerNoticeDTO bannerNotice = BannerNoticeDTO.builder()
                    .id(id)
                    .type(bn.getType())
                    .platform(bn.getPlatform())
                    .startTime(newStartTime)
                    .endTime(newEndTime)
                    .sort(bn.getSort())
                    .locale(bn.getLocale())
                    .title(bn.getTitle())
                    .text(bn.getText())
                    .imageUrl(bn.getImageUrl())
                    .originalImageUrl(bn.getOriginalImageUrl())
                    .link(bn.getLink())
                    .shareTitle(bn.getShareTitle())
                    .shareText(bn.getShareText())
                    .shareLink(bn.getShareLink())
                    .shareImageUrl(bn.getShareImageUrl())
                    .status(bn.getStatus())
                    .publishUser(user.getAccount())
                    .updateDate(updateTime)
                    .brokerId(bn.getBrokerId())
                    .build();

            final ResponseResult result = this.extraCmsBannerNoticeAdminClient.updateBannerNotice(bannerNotice);
            return ResultUtil.getCheckedResponseResult(result);


        } catch (final Exception e) {
            log.error("edit banner api error ", e);
        }
        return ResultUtils.success();

    }

    @GetMapping(value = "/list")
    @OpLog(name = "分页获取banner列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_BANNER_VIEW"})
    public ResponseResult list(@CurrentUser final User loginUser, @RequestParam(value = "bannerType", required = false) final Integer bannerType,
                               @RequestParam(value = "bannerPlatform", required = false) final Byte bannerPlatform,
                               @RequestParam(value = "bannerStatus", required = false) final Integer bannerStatus,
                               @RequestParam(value = "bannerLocale", required = false) final String bannerLocale,
                               final DataGridPager<BannerNoticeDTO> pager) {

        try {

            final BannerNoticeDTO bannerNotice = BannerNoticeDTO.builder()
                    .type(bannerType)
                    .platform(bannerPlatform)
                    .status(bannerStatus)
                    .locale(bannerLocale)
                    .brokerId(loginUser.getLoginBrokerId())
                    .build();
            pager.setQueryParameter(bannerNotice);
            final ResponseResult responseResult = this.extraCmsBannerNoticeAdminClient.listBannerNotice(pager);
            log.info("query banner list param is => {} and result => {}", pager, responseResult.getData());
            return ResultUtil.getCheckedResponseResult(responseResult);
        } catch (final Exception e) {
            log.error("get banner list api error ", e);
        }
        return ResultUtils.success();

    }
}
