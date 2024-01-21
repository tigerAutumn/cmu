package cc.newex.dax.boss.web.controller.outer.v1.admin;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.criteria.BrokerExample;
import cc.newex.dax.boss.admin.domain.Broker;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.admin.service.BrokerService;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.controller.common.BaseController;
import cc.newex.dax.users.client.UsersAdminClient;
import cc.newex.dax.users.dto.membership.UserBrokerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author better
 * @date create in 2018/9/11 下午2:59
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/admin/broker")
public class BrokerController
        extends BaseController<BrokerService, Broker, BrokerExample, Long> {

    @Autowired
    private UsersAdminClient usersAdminClient;

    /**
     * 获取broker list
     *
     * @return
     */
    @GetMapping(value = "/listByPage")
    @OpLog(name = "分页获取broker列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_BROKER_VIEW"})
    public ResponseResult listBroker(final DataGridPager pager,
                                     @RequestParam(value = "brokerName", required = false) final String brokerName) {
        final DataGridPager<UserBrokerDTO> queryParam = new DataGridPager<>();
        final UserBrokerDTO dto = UserBrokerDTO.builder()
                .brokerName(brokerName).build();
        queryParam.setQueryParameter(dto);
        final ResponseResult result = this.usersAdminClient.selectBrokerList(queryParam);
        return ResultUtil.getCheckedResponseResult(result);
    }

    /**
     * 新增broker
     *
     * @param broker broker信息
     * @return
     */
    @PostMapping(value = "/add")
    @OpLog(name = "新增broker")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_BROKER_ADD"})
    public ResponseResult createBroker(final UserBrokerDTO broker) {
        final ResponseResult result = this.usersAdminClient.create(broker);
        if (result.getCode() == 0 && (Boolean) result.getData()) {
            return ResultUtils.success();
        } else {
            return ResultUtils.failure("add broker record error");
        }
    }

    /**
     * 更新broker
     *
     * @param broker broker更新信息
     * @return
     */
    @PostMapping(value = "/edit")
    @OpLog(name = "修改broker")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_BROKER_EDIT"})
    public ResponseResult updateBroker(final UserBrokerDTO broker) {
        if (broker.getId() == null) {
            return ResultUtils.failure("id is null");
        }
        final ResponseResult result = this.usersAdminClient.updateBroker(broker.getId(), broker);
        return ResultUtil.getCheckedResponseResult(result);
    }

    /**
     * 删除broker
     *
     * @param id id
     * @return
     */
    @PostMapping(value = "/remove")
    @OpLog(name = "删除broker")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_BROKER_REMOVE"})
    public ResponseResult deleteBroker(final Long id) {
        return ResultUtils.success();
    }

    /**
     * 券商下拉列表数据
     */
    @GetMapping(value = "/list")
    public List getAllList(@CurrentUser final User user) {
        final ResponseResult<List<UserBrokerDTO>> result = this.usersAdminClient.brokerList();

        final List<UserBrokerDTO> list = result.getData();

        log.info("brokerId: {}, UserBrokerDTO: {}", user.getBrokerId(), list);

        if (user.getBrokerId() != 0) {
            return list.stream().filter(x -> user.getBrokerId().equals(x.getId())).collect(Collectors.toList());
        } else {
            return list;
        }
    }
}
