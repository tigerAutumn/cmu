package cc.newex.dax.asset.rest.controller.outer.v1.common;

import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.asset.common.enums.BizErrorCodeEnum;
import cc.newex.dax.asset.common.enums.LockPositionStatus;
import cc.newex.dax.asset.criteria.LockedPositionExample;
import cc.newex.dax.asset.domain.LockedPosition;
import cc.newex.dax.asset.domain.LockedPositionDto;
import cc.newex.dax.asset.domain.PageEntity;
import cc.newex.dax.asset.service.LockedPositionService;
import cc.newex.dax.asset.util.DomainUtil;
import cc.newex.dax.users.client.UsersClient;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.exception.UnsupportedCurrency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 前端的个人锁仓管理页面接口
 *
 * @author lilaizhen
 */
@Slf4j
@RestController
@RequestMapping("/v1/asset/{biz}/position")
public class LockPositionController {

    @Autowired
    private LockedPositionService lockedPositionService;
    @Autowired
    private UsersClient usersClient;

    /**
     * 前段获取positions列表
     * 返回的数据的状态属性：1.正在锁仓 2.解锁完成
     *
     * @param pageNum  页数
     * @param pageSize 每页数量
     * @param request
     * @return
     */
    @GetMapping("/positions")
    public ResponseResult getPositions(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") final int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") final int pageSize,
            final HttpServletRequest request) {

        final ResponseResult<PageEntity> result;
        try {
            Long userId = this.getUserId(request);
            if (ObjectUtils.isEmpty(userId)) {
                return ResultUtils.failure(BizErrorCodeEnum.UNAUTHORIZED);
            }

            String domain = DomainUtil.getDomain(request);
            ResponseResult<Integer> brokerIdResult = this.usersClient.getBrokerId(domain);
            if (brokerIdResult.getCode() != 0) {
                return ResultUtils.failure(BizErrorCodeEnum.UNKNOWN_DOMAIN);
            }

            final LockedPositionExample example = new LockedPositionExample();
            List<Integer> status = new ArrayList<>();
            status.add(LockPositionStatus.PREPARED.getCode());
            status.add(LockPositionStatus.SENDING.getCode());
            example.createCriteria()
                    .andUserIdEqualTo(userId)
                    .andStatusIn(status)
                    .andBrokerIdEqualTo(brokerIdResult.getData());
            final PageEntity pageEntity = PageEntity.getPage(this.lockedPositionService, pageNum, pageSize, example);

            final List<LockedPosition> lockedPositions = pageEntity.getData();
            final List<LockedPositionDto> newData = lockedPositions.parallelStream().map((lockedPosition) -> {
                LockedPositionDto dto = new LockedPositionDto();
                BeanUtils.copyProperties(lockedPosition, dto);
                dto.setUnlockAmount(lockedPosition.getAmount().subtract(lockedPosition.getLockAmount()));
                dto.setCurrencyName(CurrencyEnum.parseValue(dto.getCurrency()).getName().toUpperCase());
                dto.setReleaseRule(lockedPosition.getRemark());
                return dto;
            }).collect(Collectors.toList());
            pageEntity.setData(newData);
            result = ResultUtils.success(pageEntity);
        } catch (final UnsupportedCurrency e) {
            log.error("getDepositRecord error", e);
            return ResultUtils.failure("getDepositRecord error");
        } catch (final Throwable e) {
            log.error("getDepositRecord error", e);
            return ResultUtils.failure(e.getMessage());
        }

        return result;
    }

    private Long getUserId(final HttpServletRequest request) {
        final JwtUserDetails userJwt = JwtTokenUtils.getCurrentLoginUser(request);
        if (userJwt.getStatus() != 0) {
            return null;
        }
        return userJwt.getUserId();
    }

}
