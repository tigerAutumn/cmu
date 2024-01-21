package cc.newex.dax.extra.service.vlink.impl;

import cc.newex.commons.dictionary.consts.BrokerIdConsts;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.extra.criteria.vlink.ContractInfoExample;
import cc.newex.dax.extra.data.vlink.ContractInfoRepository;
import cc.newex.dax.extra.domain.vlink.ContractInfo;
import cc.newex.dax.extra.enums.vlink.ContractStatusEnum;
import cc.newex.dax.extra.service.vlink.ContractInfoService;
import cc.newex.dax.spot.client.inner.SpotCurrencyClient;
import cc.newex.dax.spot.client.inner.SpotTransferServiceClient;
import cc.newex.dax.spot.dto.ccex.ActRecordDTO;
import cc.newex.dax.spot.dto.result.model.Currency;
import cc.newex.dax.users.client.UsersAdminClient;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * vlink合约购买记录 服务实现
 *
 * @author mbg.generated
 * @date 2018-10-27 22:42:17
 */
@Slf4j
@Service
public class ContractInfoServiceImpl extends AbstractCrudService<ContractInfoRepository, ContractInfo, ContractInfoExample, Long> implements ContractInfoService {
    @Autowired
    private ContractInfoRepository vlinkContractInfoRepos;

    @Autowired
    private UsersAdminClient usersAdminClient;

    @Autowired
    private SpotCurrencyClient spotCurrencyClient;

    @Autowired
    private SpotTransferServiceClient spotTransferServiceClient;


    @Override
    protected ContractInfoExample getPageExample(final String fieldName, final String keyword) {
        final ContractInfoExample example = new ContractInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public String getEmail(final Long userId) {
        final ResponseResult<UserInfoResDTO> userResult = this.usersAdminClient.queryUserInfo(userId, BrokerIdConsts.COIN_MEX);
        if (userResult.getCode() != 0 || userResult.getData() == null) {
            return null;
        }
        String email = userResult.getData().getEmail();
        String regString = "^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regString);
        Matcher m = p.matcher(email);
        if (m.find()) {
            return email;
        }
        return null;
    }

    @Override
    public Currency getCurrency(final String currencyCode) {
        final ResponseResult<List<Currency>> result = this.spotCurrencyClient.listCurrency();
        if (result.getCode() != 0 && result.getData() == null) {
            return null;
        }
        for (final Currency v : result.getData()) {
            if (v.getSymbol().toUpperCase().equals(currencyCode.toUpperCase())) {
                return v;
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean transfer(List<ActRecordDTO> actRecordDTOList, ContractInfo info) {
        ResponseResult result = spotTransferServiceClient.transfer(actRecordDTOList);
        if (result.getCode() == 0 && result.getData() != null) {
            info.setStatus(ContractStatusEnum.TRANSFER_SUCCESS.getCode());
            this.add(info);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean transferBack(List<ActRecordDTO> actRecordDTOList) {
        ResponseResult result = spotTransferServiceClient.transfer(actRecordDTOList);
        if (result.getCode() == 0 && result.getData() != null) {
            return true;
        }
        return false;
    }
}