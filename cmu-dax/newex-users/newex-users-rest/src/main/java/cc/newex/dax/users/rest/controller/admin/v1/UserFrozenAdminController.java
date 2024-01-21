package cc.newex.dax.users.rest.controller.admin.v1;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.domain.GlobalFrozenConfig;
import cc.newex.dax.users.dto.admin.GlobalFrozenConfigDTO;
import cc.newex.dax.users.dto.common.BizTypeEnum;
import cc.newex.dax.users.service.frozen.GlobalFrozenConfigService;
import cc.newex.dax.users.service.frozen.UserFrozenService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author newex-team
 * @date 2018-07-04
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/users/frozen")
public class UserFrozenAdminController {
    @Autowired
    private UserFrozenService userFrozenService;
    @Autowired
    private GlobalFrozenConfigService globalFrozenConfigService;

    /**
     * 查询所有全局冻结设置项
     */
    @GetMapping("/global/list")
    public ResponseResult list(@RequestParam(value = "brokerId", required = false) final Integer brokerId) {
        final List<GlobalFrozenConfig> list = this.globalFrozenConfigService.listAll(brokerId);
        final List<GlobalFrozenConfigDTO> dtoList = Lists.newArrayListWithCapacity(CollectionUtils.size(list));
        if (CollectionUtils.isNotEmpty(list)) {
            for (final GlobalFrozenConfig e : list) {
                dtoList.add(GlobalFrozenConfigDTO.builder()
                        .id(e.getId())
                        .code(e.getCode())
                        .status(e.getStatus())
                        .memo(e.getMemo())
                        .createdDate(e.getCreatedDate())
                        .updatedDate(e.getUpdatedDate())
                        .brokerId(e.getBrokerId())
                        .cacheStatus(this.userFrozenService.getGlobalFrozen(e.getCode()))
                        .build()
                );
            }
        }
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", CollectionUtils.size(dtoList));
        modelMap.put("rows", dtoList);
        return ResultUtils.success(modelMap);
    }

    /**
     * 增加全站交易业务冻结状态,表示所有用户都不能进行所有或相关业务(spot,c2c,contracts等)交易
     */
    @PostMapping(value = "/global")
    public ResponseResult addGlobalFrozenStatus(@RequestBody final GlobalFrozenConfigDTO dto) {
        //先更新数据库
        final int result = this.globalFrozenConfigService.insert(ObjectCopyUtils.map(dto, GlobalFrozenConfig.class));
        if (result > 0) {
            this.userFrozenService.setGlobalFrozen(dto.getCode(), dto.getStatus());
            return ResultUtils.success();
        }
        return ResultUtils.failure(BizErrorCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 设置全站交易业务冻结状态,表示所有用户都不能进行所有或相关业务(spot,c2c,contracts等)交易
     */
    @PutMapping(value = "/global")
    public ResponseResult editGlobalFrozenStatus(@RequestBody final GlobalFrozenConfigDTO dto) {
        dto.setStatus(null);
        this.globalFrozenConfigService.editById(ObjectCopyUtils.map(dto, GlobalFrozenConfig.class));
        return ResultUtils.success();
    }

    /**
     * 设置全站交易业务冻结状态,表示所有用户都不能进行所有或相关业务(spot,c2c,contracts等)交易
     *
     * @param name   {@link cc.newex.commons.security.jwt.enums.GlobalFrozenEnum}
     * @param status 0：解冻 1：冻结
     * @return
     */
    @PutMapping(value = "/global/{name}")
    public ResponseResult editGlobalFrozenStatus(
            @PathVariable("name") final String name,
            @RequestParam("status") final Integer status) {
        //先更新数据库
        this.globalFrozenConfigService.edit(name, status);
        this.userFrozenService.setGlobalFrozen(name, status);
        return ResultUtils.success();
    }

    /**
     * 获取全站交易业务冻结状态,表示所有用户都不能进行所有或相关业务(spot,c2c,contracts等)交易
     *
     * @param name {@link cc.newex.commons.security.jwt.enums.GlobalFrozenEnum}
     */
    @GetMapping(value = "/global/{name}")
    public ResponseResult getGlobalFrozenStatus(@PathVariable("name") final String name) {
        return ResultUtils.success(this.userFrozenService.getGlobalFrozen(name));
    }

    /**
     * 删除全站交易业务冻结状态
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/global/{id}")
    public ResponseResult deleteGlobalFrozenStatus(@PathVariable("id") final Integer id) {
        final GlobalFrozenConfig config = this.globalFrozenConfigService.getById(id);
        this.userFrozenService.deleteGlobalFrozen(config.getCode());
        return ResultUtils.success(this.globalFrozenConfigService.removeById(id));
    }

    /**
     * 冻结用户所有交易业务(spot,c2c,contracts等)
     *
     * @param bizType {@link BizTypeEnum}
     * @param userId  用户id
     * @param status  冻结状态 0:解冻,1:冻结
     * @param remark  原因
     */
    @PutMapping(value = "/{bizType}/{userId}")
    public ResponseResult frozen(
            @PathVariable("bizType") final String bizType,
            @PathVariable("userId") final long userId,
            @RequestParam("status") final Integer status,
            @RequestParam("remark") final String remark) {
        if (userId <= 0) {
            return ResultUtils.failure(BizErrorCodeEnum.BOSS_USER_NOT_EXIST);
        }
        final BizTypeEnum type = BizTypeEnum.forName(bizType);
        if (type == BizTypeEnum.ALL) {
            return ResultUtils.success(this.userFrozenService.frozen(userId, status, remark));
        }
        if (type == BizTypeEnum.SPOT) {
            return ResultUtils.success(this.userFrozenService.frozenSpot(userId, status, remark));
        }
        if (type == BizTypeEnum.C2C) {
            return ResultUtils.success(this.userFrozenService.frozenC2C(userId, status, remark));
        }
        if (type == BizTypeEnum.CONTRACTS) {
            return ResultUtils.success(this.userFrozenService.frozenContracts(userId, status, remark));
        }
        if (type == BizTypeEnum.ASSET) {
            return ResultUtils.success(this.userFrozenService.frozenAsset(userId, status, remark));
        }
        return ResultUtils.failure("Not Found Biz Type");
    }

    /**
     * 冻结用户所有交易业务(spot,c2c,contracts等)
     *
     * @param bizType {@link BizTypeEnum}
     * @param userId  用户id
     */
    @GetMapping(value = "/{bizType}/{userId}")
    public ResponseResult getFrozenStatus(
            @PathVariable("bizType") final String bizType,
            @PathVariable("userId") final long userId) {
        if (userId <= 0) {
            return ResultUtils.failure(BizErrorCodeEnum.BOSS_USER_NOT_EXIST);
        }
        final BizTypeEnum type = BizTypeEnum.forName(bizType);
        if (type == BizTypeEnum.ALL) {
            return ResultUtils.success(this.userFrozenService.getUserFrozenStatus(userId));
        }
        if (type == BizTypeEnum.SPOT) {
            return ResultUtils.success(this.userFrozenService.getUserSpotFrozenStatus(userId));
        }
        if (type == BizTypeEnum.C2C) {
            return ResultUtils.success(this.userFrozenService.getUserC2CFrozenStatus(userId));
        }
        if (type == BizTypeEnum.CONTRACTS) {
            return ResultUtils.success(this.userFrozenService.getUserContractsFrozenStatus(userId));
        }
        if (type == BizTypeEnum.ASSET) {
            return ResultUtils.success(this.userFrozenService.getUserAssetFrozenStatus(userId));
        }
        return ResultUtils.failure("Not Found Biz Type");
    }
}
