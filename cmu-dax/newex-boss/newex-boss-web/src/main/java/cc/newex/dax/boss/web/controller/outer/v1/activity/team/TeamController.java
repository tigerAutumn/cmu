package cc.newex.dax.boss.web.controller.outer.v1.activity.team;

import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.activity.client.team.ContractTeamClient;
import cc.newex.dax.activity.dto.ccex.team.*;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.common.util.ResultUtil;
import cc.newex.dax.boss.web.model.activity.team.*;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 合约战队控制器
 *
 * @author better
 * @date create in 2018_12_18 14:31
 */
@RestController
@RequestMapping(value = "/v1/boss/activity/team")
@Slf4j
public class TeamController {


    private final ContractTeamClient contractTeamClient;
    private final ModelMapper modelMapper;
    private final Random random = new Random();

    /**
     * Instantiates a new Team controller.
     *
     * @param contractTeamClient the contract team client
     */
    @Autowired
    public TeamController(final ContractTeamClient contractTeamClient) {
        this.contractTeamClient = contractTeamClient;
        this.modelMapper = new ModelMapper();
    }

    /**
     * List contract team response result.
     *
     * @param dataGridPager the data grid pager
     * @param id            the id
     * @param teamName      the team name
     * @param teamNumber    the team number
     * @param balance       the balance
     * @return the response result
     */
    @GetMapping(value = "/contract_team/list")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_TEAM_VIEW"})
    @OpLog(name = "查询合约战队信息")
    public ResponseResult listContractTeam(final DataGridPager<ContractTeamDTO> dataGridPager, final Long id, final String teamName, final Integer teamNumber,
                                           final BigDecimal balance, final String teamNameEnUs) {

        final ContractTeamDTO queryParam = ContractTeamDTO.builder().id(id).teamName(teamName).teamNameEnUs(teamNameEnUs).teamNumber(teamNumber).balance(balance).build();
        dataGridPager.setQueryParameter(queryParam);
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.listContractTeam(dataGridPager));
    }

    /**
     * /**
     * List team id response result.
     *
     * @return the response result
     */
    @GetMapping(value = "/contract_team/ids")
    public List<ContractTeamDTO> listTeamId() {

        final JSONArray json = (JSONArray) this.contractTeamClient.listAllContractTeam().getData();
        return json.toJavaList(ContractTeamDTO.class);
    }

    /**
     * Save contract team response result.
     *
     * @param contractTeamVO the contract team vo
     * @return the response result
     */
    @PostMapping(value = "/contract_team/add")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_TEAM_ADD"})
    @OpLog(name = "添加合约战队信息")
    public ResponseResult saveContractTeam(final ContractTeamVO contractTeamVO) {

        final ContractTeamDTO contractTeam = this.modelMapper.map(contractTeamVO, ContractTeamDTO.class);
        contractTeam.setUid(this.generateRandomCode(4));
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.saveContractTeam(contractTeam));
    }

    /**
     * Edit contract team response result.
     *
     * @param contractTeamVO the contract team vo
     * @return the response result
     */
    @PostMapping(value = "/contract_team/edit")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_TEAM_EDIT"})
    @OpLog(name = "修改合约战队信息")
    public ResponseResult editContractTeam(final ContractTeamVO contractTeamVO) {

        final ContractTeamDTO contractTeam = this.modelMapper.map(contractTeamVO, ContractTeamDTO.class);
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.updateContractTeam(contractTeam));
    }

    /**
     * Remove contract team response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/contract_team/remove")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_TEAM_REMOVE"})
    @OpLog(name = "删除合约战队信息")
    public ResponseResult removeContractTeam(final Long id) {
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.removeContractTeam(id));
    }

    // ________________________________________________________________________________________________

    /**
     * List contract team response result.
     *
     * @param dataGridPager the data grid pager
     * @param userId        the user id
     * @param currencyCode  the currency code
     * @return the response result
     */
    @GetMapping(value = "/contract_balance/list")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_BALANCE_VIEW"})
    @OpLog(name = "查询合约模拟金信息")
    public ResponseResult listContractBalance(final DataGridPager<ContractBalanceDTO> dataGridPager,
                                              final Long userId, final String currencyCode) {

        final ContractBalanceDTO queryParam = ContractBalanceDTO.builder().userId(userId).currencyCode(currencyCode).build();
        dataGridPager.setQueryParameter(queryParam);
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.listContractBalance(dataGridPager));
    }

    /**
     * Save contract balance response result.
     *
     * @param user              the user
     * @param contractBalanceVO the contract balance vo
     * @return the response result
     */
    @PostMapping(value = "/contract_balance/add")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_BALANCE_ADD"})
    @OpLog(name = "添加合约模拟金信息")
    public ResponseResult saveContractBalance(@CurrentUser final User user, final ContractBalanceVO contractBalanceVO) {

        final ContractBalanceDTO contractBalance = this.modelMapper.map(contractBalanceVO, ContractBalanceDTO.class);
        contractBalance.setManagerId(user.getId().longValue());
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.saveContractBalance(contractBalance));
    }

    /**
     * Edit contract balance response result.
     *
     * @param contractBalanceVO the contract balance vo
     * @return the response result
     */
    @PostMapping(value = "/contract_balance/edit")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_BALANCE_EDIT"})
    @OpLog(name = "修改合约模拟金信息")
    public ResponseResult editContractBalance(final ContractBalanceVO contractBalanceVO) {

        final ContractBalanceDTO contractBalance = this.modelMapper.map(contractBalanceVO, ContractBalanceDTO.class);
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.updateContractBalance(contractBalance));
    }

    /**
     * Remove contract balance response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/contract_balance/remove")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_BALANCE_REMOVE"})
    @OpLog(name = "删除合约模拟金信息")
    public ResponseResult removeContractBalance(final Long id) {
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.removeContractBalance(id));
    }

    // ________________________________________________________________________________________________

    /**
     * List contract team response result.
     *
     * @param dataGridPager the data grid pager
     * @param teamId        the team id
     * @param currencyCode  the currency code
     * @return the response result
     */
    @GetMapping(value = "/contract_member/list")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_MEMBER_VIEW"})
    @OpLog(name = "查询团队成员信息")
    public ResponseResult listContractMember(final DataGridPager<ContractMemberDTO> dataGridPager,
                                             final Long teamId, final String currencyCode) {
        final ContractMemberDTO queryParam = ContractMemberDTO.builder().teamId(teamId).currencyCode(currencyCode).build();
        dataGridPager.setQueryParameter(queryParam);
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.listContractMember(dataGridPager));
    }

    /**
     * Save contract member response result.
     *
     * @param contractMemberVO the contract member vo
     * @return the response result
     */
    @PostMapping(value = "/contract_member/add")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_MEMBER_ADD"})
    @OpLog(name = "添加团队成员信息")
    public ResponseResult saveContractMember(final ContractMemberVO contractMemberVO) {

        final ContractMemberDTO contractMember = this.modelMapper.map(contractMemberVO, ContractMemberDTO.class);
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.saveContractMember(contractMember));
    }

    /**
     * Edit contract member response result.
     *
     * @param contractMemberVO the contract member vo
     * @return the response result
     */
    @PostMapping(value = "/contract_member/edit")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_MEMBER_EDIT"})
    @OpLog(name = "修改团队成员信息")
    public ResponseResult editContractMember(final ContractMemberVO contractMemberVO) {

        final ContractMemberDTO contractMember = this.modelMapper.map(contractMemberVO, ContractMemberDTO.class);
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.updateContractMember(contractMember));
    }

    /**
     * Remove contract balance response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/contract_member/remove")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_MEMBER_REMOVE"})
    @OpLog(name = "删除团队成员信息")
    public ResponseResult removeContractMember(final Long id) {
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.removeContractMember(id));
    }

    // ________________________________________________________________________________________________

    /**
     * List contract simulation game response result.
     *
     * @param dataGridPager the data grid pager
     * @param startDate     the start date
     * @param endDate       the end date
     * @return the response result
     * @throws ParseException the parse exception
     */
    @GetMapping(value = "/contract_simulation_game/list")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_SIMULATION_GAME_VIEW"})
    @OpLog(name = "查询合约模拟赛信息")
    public ResponseResult listContractSimulationGame(final DataGridPager<ContractSimulationGameDTO> dataGridPager,
                                                     final String startDate, final String endDate) throws ParseException {
        Date start = null, end = null;
        if (StringUtils.isNotEmpty(startDate)) {
            start = DateUtils.parseDate(startDate, "yyyy-MM-dd HH:mm:ss");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            end = DateUtils.parseDate(endDate, "yyyy-MM-dd HH:mm:ss");
        }
        final ContractSimulationGameDTO queryParam = ContractSimulationGameDTO.builder().startDate(start).endDate(end).build();
        dataGridPager.setQueryParameter(queryParam);
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.listContractSimulationGame(dataGridPager));
    }

    /**
     * Save contract simulation game response result.
     *
     * @param contractSimulationGameVO the contract simulation game vo
     * @return the response result
     */
    @PostMapping(value = "/contract_simulation_game/add")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_SIMULATION_GAME_ADD"})
    @OpLog(name = "添加合约模拟赛信息")
    public ResponseResult saveContractSimulationGame(final ContractSimulationGameVO contractSimulationGameVO) {

        final ContractSimulationGameDTO contractSimulationGame = this.modelMapper.map(contractSimulationGameVO, ContractSimulationGameDTO.class);
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.saveContractSimulationGame(contractSimulationGame));
    }

    /**
     * Edit contract simulation game response result.
     *
     * @param contractSimulationGameVO the contract simulation game vo
     * @return the response result
     */
    @PostMapping(value = "/contract_simulation_game/edit")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_SIMULATION_GAME_EDIT"})
    @OpLog(name = "修改合约模拟赛信息")
    public ResponseResult editContractSimulationGame(final ContractSimulationGameVO contractSimulationGameVO) {

        final ContractSimulationGameDTO contractSimulationGame = this.modelMapper.map(contractSimulationGameVO, ContractSimulationGameDTO.class);
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.updateContractSimulationGame(contractSimulationGame));
    }

    /**
     * Remove contract simulation game response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/contract_simulation_game/remove")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_SIMULATION_GAME_REMOVE"})
    @OpLog(name = "删除合约模拟赛信息")
    public ResponseResult removeContractSimulationGame(final Long id) {
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.removeContractSimulationGame(id));
    }


    // ________________________________________________________________________________________________

    /**
     * List contract description config response result.
     *
     * @param dataGridPager the data grid pager
     * @param startInterval the start interval
     * @param endInterval   the end interval
     * @return the response result
     */
    @GetMapping(value = "/contract_description_config/list")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_DESCRIPTION_CONFIG_VIEW"})
    @OpLog(name = "查询合约盈亏描述配置信息")
    public ResponseResult listContractDescriptionConfig(final DataGridPager<ContractDescriptionConfigDTO> dataGridPager,
                                                        final BigDecimal startInterval, final BigDecimal endInterval) {

        final ContractDescriptionConfigDTO queryParam = ContractDescriptionConfigDTO.builder().startInterval(startInterval).endInterval(endInterval).build();
        dataGridPager.setQueryParameter(queryParam);
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.listByCondition(dataGridPager));
    }

    /**
     * Save contract description config response result.
     *
     * @param descriptionConfigVO the description config vo
     * @return the response result
     */
    @PostMapping(value = "/contract_description_config/add")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_DESCRIPTION_CONFIG_ADD"})
    @OpLog(name = "添加合约盈亏描述配置信息")
    public ResponseResult saveContractDescriptionConfig(final ContractDescriptionConfigVO descriptionConfigVO) {

        final ContractDescriptionConfigDTO contractDescriptionConfig = this.modelMapper.map(descriptionConfigVO, ContractDescriptionConfigDTO.class);
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.save(contractDescriptionConfig));
    }

    /**
     * Edit contract description config response result.
     *
     * @param descriptionConfigVO the description config vo
     * @return the response result
     */
    @PostMapping(value = "/contract_description_config/edit")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_DESCRIPTION_CONFIG_EDIT"})
    @OpLog(name = "修改合约盈亏描述配置信息")
    public ResponseResult editContractDescriptionConfig(final ContractDescriptionConfigVO descriptionConfigVO) {

        final ContractDescriptionConfigDTO contractDescriptionConfig = this.modelMapper.map(descriptionConfigVO, ContractDescriptionConfigDTO.class);
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.edit(descriptionConfigVO.getId(), contractDescriptionConfig));
    }

    /**
     * Remove contract description config response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping(value = "/contract_description_config/remove")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ACTIVITY_CONTRACT_DESCRIPTION_CONFIG_REMOVE"})
    @OpLog(name = "删除合约盈亏描述配置信息")
    public ResponseResult removeContractDescriptionConfig(final Long id) {
        return ResultUtil.getCheckedResponseResult(this.contractTeamClient.delete(id));
    }


    private String generateRandomCode(final int length) {
        final String[] codeStr = new String[]{
                "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
        };

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(codeStr[this.random.nextInt(codeStr.length)]);
        }
        return sb.toString();
    }
}
