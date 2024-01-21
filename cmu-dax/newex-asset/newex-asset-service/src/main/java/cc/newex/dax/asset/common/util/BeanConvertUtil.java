package cc.newex.dax.asset.common.util;

import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.dax.asset.common.consts.MessageCons;
import cc.newex.dax.asset.common.enums.TransferStatus;
import cc.newex.dax.asset.domain.AssetCurrency;
import cc.newex.dax.asset.domain.TransferRecord;
import cc.newex.dax.asset.dto.*;
import cc.newex.wallet.currency.CurrencyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.text.MessageFormat;

/**
 * @author newex-team
 * @data 2018/7/31
 */
@Slf4j
public class BeanConvertUtil {
    public static void fillAddressDtoInfo(DepositAddressDto addressDto, AssetCurrency currency) {
        if (ObjectUtils.isEmpty(addressDto) || ObjectUtils.isEmpty(currency)) {
            log.error("BeanConvertUtil-fillAddressDtoInfo: DepositAddressDto or AssetCurrency is null");
            return;
        }
        addressDto.setDepositConfirm(currency.getDepositConfirm());
        addressDto.setMinDepositAmount(currency.getMinDepositAmount());
        addressDto.setRechargeable(currency.getRechargeable());
        if (ObjectUtils.isEmpty(currency.getNeedTag())) {
            addressDto.setNeedTag(false);
        } else {
            addressDto.setNeedTag(currency.getNeedTag() > 0);
        }
        addressDto.setTagField(LocaleUtils.getMessage(currency.getTagField()));
        addressDto.setWithdrawConfirm(currency.getWithdrawConfirm());
        if (ObjectUtils.isEmpty(currency.getNeedAlert()) || currency.getNeedAlert() == 0) {
            addressDto.setNeedAlert(0);
        } else {
            addressDto.setNeedAlert(1);
            addressDto.setAlert(LocaleUtils.getMessage(MessageFormat.format(MessageCons.AlertMessageKey, currency.getSymbol().toLowerCase())));
        }
    }

    public static void fillWithdrawInfo(WithDrawInfoDto withDrawInfoDto, AssetCurrency currency) {
        if (ObjectUtils.isEmpty(withDrawInfoDto) || ObjectUtils.isEmpty(currency)) {
            log.error("BeanConvertUtil-fillWithdrawInfo: WithDrawInfoDto or AssetCurrency is null");
            return;
        }

        withDrawInfoDto.setMiniAmount(currency.getMinWithdrawAmount());
        withDrawInfoDto.setFee(currency.getWithdrawFee());
        withDrawInfoDto.setNeedAlert(0);
        withDrawInfoDto.setWithdrawable(currency.getWithdrawable());

        if (ObjectUtils.isEmpty(currency.getNeedTag())) {
            withDrawInfoDto.setNeedTag(0);
        } else {
            withDrawInfoDto.setNeedTag(currency.getNeedTag());
        }
        withDrawInfoDto.setTagField(LocaleUtils.getMessage(currency.getTagField()));
        if (ObjectUtils.isEmpty(currency.getNeedAlert()) || currency.getNeedAlert() == 0) {
            withDrawInfoDto.setNeedAlert(0);
        } else {
            withDrawInfoDto.setNeedAlert(1);
            withDrawInfoDto.setAlertInfo(LocaleUtils.getMessage(MessageFormat.format(MessageCons.AlertMessageKey, currency.getSymbol().toLowerCase())));
        }
    }

    public static void convert(DepositRecordDto depositRecordDto, TransferRecord record) {
        if (ObjectUtils.isEmpty(depositRecordDto) || ObjectUtils.isEmpty(record)) {
            log.error("BeanConvertUtil-convert: DepositRecordDto or TransferRecord is null");
            return;
        }
        depositRecordDto.setAddress(record.getTo());
        depositRecordDto.setAmount(record.getAmount().toPlainString());
        depositRecordDto.setBiz(record.getBiz());
        depositRecordDto.setConfirmation(record.getConfirmation());
        depositRecordDto.setCurrency(record.getCurrency());
        depositRecordDto.setTxId(record.getFrom());
        depositRecordDto.setUserId(record.getUserId());
        depositRecordDto.setUpdateDate(record.getUpdateDate());
        CurrencyEnum coin = CurrencyEnum.parseValue(record.getCurrency());
        if (depositRecordDto.getConfirmation() >= coin.getConfirmNum()) {
            depositRecordDto.setStatus(TransferStatus.CONFIRMED.getCode());
        } else {
            depositRecordDto.setStatus(TransferStatus.WAITING.getCode());
        }
    }

    public static void convert(WithdrawRecordDto withdrawRecordDto, TransferRecord record) {
        if (ObjectUtils.isEmpty(withdrawRecordDto) || ObjectUtils.isEmpty(record)) {
            log.error("BeanConvertUtil-convert: WithdrawRecordDto or TransferRecord is null");
            return;
        }
        withdrawRecordDto.setAddress(record.getTo());
        withdrawRecordDto.setAmount(record.getAmount().toPlainString());
        withdrawRecordDto.setBiz(record.getBiz());
        withdrawRecordDto.setCurrency(record.getCurrency());
        withdrawRecordDto.setTxId(record.getFrom());
        withdrawRecordDto.setUserId(record.getUserId());
        withdrawRecordDto.setUpdateDate(record.getUpdateDate());
        withdrawRecordDto.setFee(record.getFee().toPlainString());
        withdrawRecordDto.setTradeNo(record.getTraderNo());

    }

    public static void convert(TransferRecordResDto transferRecordResDto, TransferRecord record) {
        if (ObjectUtils.isEmpty(transferRecordResDto) || ObjectUtils.isEmpty(record)) {
            log.error("BeanConvertUtil-convert: TransferRecordResDto or TransferRecord is null");
            return;
        }
        BeanUtils.copyProperties(record, transferRecordResDto);
        transferRecordResDto.setAddress(record.getTo());
        transferRecordResDto.setFee(record.getFee().toPlainString());
        transferRecordResDto.setAmount(record.getAmount().toPlainString());
    }
}
