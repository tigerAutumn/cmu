package cc.newex.dax.perpetual.rest.controller.outer.v1.ccex;

import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.perpetual.common.enums.BizErrorCodeEnum;
import cc.newex.dax.perpetual.common.enums.PositionTypeEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.domain.bean.CurrentPosition;
import cc.newex.dax.perpetual.domain.bean.PositionPubConfig;
import cc.newex.dax.perpetual.dto.UserLeverDTO;
import cc.newex.dax.perpetual.rest.controller.base.BaseController;
import cc.newex.dax.perpetual.service.ContractService;
import cc.newex.dax.perpetual.service.UserPositionService;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/v1/perpetual/position")
public class PositionController extends BaseController {
  @Resource
  private UserPositionService userPositionService;
  @Resource
  private ContractService contractService;

  /**
   * 设置杠杆
   *
   * @param contractCode 合约
   * @param userLeverDTO 全仓:0,逐仓:1
   * @return
   */
  @PostMapping("/{contractCode}/lever")
  public ResponseResult<Map<String, BigDecimal>> updateLever(
      @PathVariable("contractCode") final String contractCode,
      @RequestBody final UserLeverDTO userLeverDTO) {

    final JwtUserDetails userJwt = this.getUserDetails();
    final Integer brokerId = this.getBrokerId();
    this.checkContract(contractCode, true);

    if (userLeverDTO.getType() == PositionTypeEnum.PART_IN.getType() && userLeverDTO.getLever().compareTo(BigDecimal.ONE) < 0) {
      log.error("update part_in lever error,userId={}, lever={}", userJwt.getUserId(), userLeverDTO.getLever());
      return ResultUtils.failure(BizErrorCodeEnum.ILLEGAL_PARAM);
    }
    final UserPosition userPosition =
        this.userPositionService.updateLeverGear(true, userJwt.getUserId(), brokerId, contractCode,
            userLeverDTO.getType(), userLeverDTO.getLever(), null);

    final Map<String, BigDecimal> result = Maps.newConcurrentMap();
    result.put("lever", userPosition.getLever());
    result.put("gear", userPosition.getGear());

    return ResultUtils.success(result);
  }

  /**
   * 设置风险限额
   *
   * @param contractCode
   * @param userLeverDTO
   * @return
   */
  @PostMapping("/{contractCode}/gear")
  public ResponseResult<Map<String, BigDecimal>> updateGear(
      @PathVariable("contractCode") final String contractCode,
      @RequestBody final UserLeverDTO userLeverDTO) {

    final JwtUserDetails userJwt = this.getUserDetails();
    final Integer brokerId = this.getBrokerId();
    this.checkContract(contractCode, userLeverDTO.getGear(), true);

    final Long userId = userJwt.getUserId();
    final UserPosition userPosition = this.userPositionService.updateLeverGear(false, userId,
        brokerId, contractCode, null, null, userLeverDTO.getGear());
    final Map<String, BigDecimal> result = Maps.newConcurrentMap();
    result.put("lever", userPosition.getLever());
    result.put("gear", userPosition.getGear());
    return ResultUtils.success(result);
  }

  /**
   * 获取用户仓位,持有仓位和已平仓位
   */
  @GetMapping("/{contractCode}/positions")
  public ResponseResult<List<CurrentPosition>> positions(
      @PathVariable("contractCode") final String contractCode) {
    final JwtUserDetails userJwt = this.getUserDetails();
    final Integer brokerId = this.getBrokerId();
    final List<CurrentPosition> currentPositionList =
        this.userPositionService.getUserPositionByType(userJwt.getUserId(), brokerId, contractCode);
    return ResultUtils.success(currentPositionList);
  }

  /**
   * 获取用户所有仓位
   */
  @GetMapping("list-all")
  public ResponseResult<List<CurrentPosition>> listAll() {
    final JwtUserDetails userJwt = this.getUserDetails();
    final Integer brokerId = this.getBrokerId();
    final List<CurrentPosition> currentPositionList =
        this.userPositionService.getUserPositionByType(userJwt.getUserId(), brokerId, null);
    return ResultUtils.success(currentPositionList);
  }

  /**
   * 计算逐仓保证金最小值
   *
   * @param contractCode 合约code
   */
  @GetMapping("/{contractCode}/lowest-margin")
  public ResponseResult<JSONObject> lowestOpenMargin(
      @PathVariable("contractCode") final String contractCode) {
    final JwtUserDetails userJwt = this.getUserDetails();
    final Integer brokerId = this.getBrokerId();
    final BigDecimal lowestOpenMargin = this.userPositionService.lowestOpenMargin(userJwt.getUserId(), brokerId, contractCode);
    final JSONObject result = new JSONObject();
    final Contract contract = this.contractService.getContract(contractCode);
    result.put("lowestOpenMargin",
            lowestOpenMargin.setScale(contract.getMinTradeDigit(), RoundingMode.CEILING).stripTrailingZeros().toPlainString());
    return ResultUtils.success(result);
  }

  /**
   * 修改保证金
   *
   * @param contractCode 合约code
   * @param margin 修改的保证金,整数增加,负数减少
   */
  @PostMapping("/{contractCode}/change-margin")
  public ResponseResult updateOpenMargin(@PathVariable("contractCode") final String contractCode,
      @RequestParam(value = "margin") final String margin) {
    final JwtUserDetails userJwt = this.getUserDetails();
    final Integer brokerId = this.getBrokerId();
    final int result = this.userPositionService.updateOpenMargin(userJwt.getUserId(), brokerId,
        contractCode, margin);
    if (result == 0) {
      log.error("修改保证金失败,userId={}", userJwt.getUserId());
      return ResultUtils.failure(BizErrorCodeEnum.UPDATE_POSITION_MARGIN_ERROR);
    }
    return ResultUtils.success();
  }

  /**
   * 仓位和限额设置
   */
  @GetMapping("/{contractCode}/configs")
  public ResponseResult<PositionPubConfig> getPositionConfig(@PathVariable("contractCode") final String contractCode) {
    final JwtUserDetails userJwt = this.getUserDetails();
    final Integer brokerId = this.getBrokerId();
    final PositionPubConfig config = this.userPositionService.getPositionConfig(userJwt.getUserId(), brokerId, contractCode);
    return ResultUtils.success(config);
  }
}
