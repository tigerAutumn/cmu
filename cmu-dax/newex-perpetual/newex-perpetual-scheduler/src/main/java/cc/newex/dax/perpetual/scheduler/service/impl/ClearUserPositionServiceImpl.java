package cc.newex.dax.perpetual.scheduler.service.impl;

import cc.newex.dax.perpetual.common.enums.BillTypeEnum;
import cc.newex.dax.perpetual.common.enums.PositionClearEnum;
import cc.newex.dax.perpetual.common.enums.PositionSideEnum;
import cc.newex.dax.perpetual.domain.*;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;
import cc.newex.dax.perpetual.scheduler.service.ClearUserPositionService;
import cc.newex.dax.perpetual.service.*;
import cc.newex.dax.perpetual.service.common.MarketService;
import cc.newex.dax.perpetual.util.ClearPositionUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClearUserPositionServiceImpl implements ClearUserPositionService {

    private static final int MAX_SIZE = 500;
    private final ExecutorService executorService = new ThreadPoolExecutor(128,
            128, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    @Autowired
    private UserPositionService userPositionService;
    @Autowired
    private MarketService marketService;
    @Autowired
    private CurrencyPairBrokerRelationService currencyPairBrokerRelationService;
    @Autowired
    private CurrencyPairService currencyPairService;
    @Autowired
    private AssetsFeeRateService assetsFeeRateService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private UserBillService userBillService;
    @Autowired
    private SystemBillService systemBillService;
    @Autowired
    private PositionClearService positionClearService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearUserPosition(final Contract contract, final List<Contract> allContract) throws ExecutionException, InterruptedException {

        final CurrencyPair currencyPair = this.currencyPairService.getByPairCode(contract.getPairCode());

        if (currencyPair == null) {
            ClearUserPositionServiceImpl.log.error("not found currencyPair by contractCode : {}", contract.getContractCode());
            return;
        }

        final List<CurrencyPairBrokerRelation> relation = this.currencyPairBrokerRelationService.getByPairCode(currencyPair.getPairCode());

        if (CollectionUtils.isEmpty(relation)) {
            ClearUserPositionServiceImpl.log.error("not found currency pair broker relation, currencyPairId : {}, pairCode : {}",
                    currencyPair.getId(), currencyPair.getPairCode());
            return;
        }

        long ts = System.currentTimeMillis();
        final List<ClearSubWorker> subWorkers = new ArrayList<>();
        final List<ClearAddWorker> addWorkers = new ArrayList<>();
        final Map<String, MarkIndexPriceDTO> priceMap = JSON.parseArray(contract.getMarketPrice(), MarkIndexPriceDTO.class)
                .stream().collect(Collectors.toMap(MarkIndexPriceDTO::getContractCode, Function.identity(), (x, y) -> x));
        for (final CurrencyPairBrokerRelation r : relation) {

            final Map<Long, List<UserPosition>> positionMap = this.userPositionService.positionMap(r.getBrokerId(), allContract, null);
            final MarkIndexPriceDTO price = priceMap.get(contract.getContractCode());
            if (price == null) {
                ClearUserPositionServiceImpl.log.error("not found market price, contractCode : {}", contract.getContractCode());
                continue;
            }
            if (price.getFeeRate().compareTo(BigDecimal.ZERO) == 0) {
                ClearUserPositionServiceImpl.log.info("fee rate is 0, contractCode : {}", contract.getContractCode());
                continue;
            }
            if (price.getMarkPrice().compareTo(BigDecimal.ZERO) <= 0) {
                ClearUserPositionServiceImpl.log.info("market price less then 0, contractCode : {}, market price : {}", contract.getContractCode(), price.getMarkPrice());
                continue;
            }
            final boolean longToShort = price.getFeeRate().compareTo(BigDecimal.ZERO) > 0;
            List<Long> subUserIds = new LinkedList<>();
            List<Long> addUserIds = new LinkedList<>();
            final Map<Long, BigDecimal> expectSizeMap = new HashMap<>();

            for (final Map.Entry<Long, List<UserPosition>> entry : positionMap.entrySet()) {
                final UserPosition userPosition = this.takeCurrentPosition(entry.getValue(), contract);
                if (userPosition == null || userPosition.getAmount().compareTo(BigDecimal.ONE) < 0) {
                    continue;
                }
                if (userPosition.getSide().equalsIgnoreCase(PositionSideEnum.LONG.getSide())) {
                    if (longToShort) {
                        subUserIds.add(entry.getKey());
                    } else {
                        addUserIds.add(entry.getKey());
                        // 用户持仓期望得到的资金费用
                        final BigDecimal totalSize = ClearPositionUtils.takeTotalSize(contract, userPosition, price);
                        expectSizeMap.put(entry.getKey(), totalSize);
                    }
                } else {
                    if (!longToShort) {
                        subUserIds.add(entry.getKey());
                    } else {
                        addUserIds.add(entry.getKey());
                        // 用户持仓期望得到的资金费用
                        final BigDecimal totalSize = ClearPositionUtils.takeTotalSize(contract, userPosition, price);
                        expectSizeMap.put(entry.getKey(), totalSize);
                    }
                }

                if (subUserIds.size() > ClearUserPositionServiceImpl.MAX_SIZE) {
                    final ClearSubWorker clearSubWorker = this.applicationContext.getBean(ClearSubWorker.class);
                    clearSubWorker.init(r.getBrokerId(), subUserIds, priceMap, contract, allContract, price.getFeeRate());
                    subWorkers.add(clearSubWorker);
                    subUserIds = new LinkedList<>();
                }

                if (addUserIds.size() > ClearUserPositionServiceImpl.MAX_SIZE) {
                    final ClearAddWorker clearAddWorker = this.applicationContext.getBean(ClearAddWorker.class);
                    clearAddWorker.init(r.getBrokerId(), addUserIds, priceMap, contract, allContract, price.getFeeRate());
                    clearAddWorker.setExpectSizeMap(expectSizeMap);
                    addWorkers.add(clearAddWorker);
                    addUserIds = new LinkedList<>();
                }
            }
            if (subUserIds.size() > 0) {
                final ClearSubWorker clearSubWorker = this.applicationContext.getBean(ClearSubWorker.class);
                clearSubWorker.init(r.getBrokerId(), subUserIds, priceMap, contract, allContract, price.getFeeRate());
                subWorkers.add(clearSubWorker);
            }

            if (addUserIds.size() > 0) {
                final ClearAddWorker clearAddWorker = this.applicationContext.getBean(ClearAddWorker.class);
                clearAddWorker.init(r.getBrokerId(), addUserIds, priceMap, contract, allContract, price.getFeeRate());
                clearAddWorker.setExpectSizeMap(expectSizeMap);
                addWorkers.add(clearAddWorker);
            }
            final BigDecimal sum = expectSizeMap.entrySet().stream().map(entry -> entry.getValue()).reduce(BigDecimal.ZERO, BigDecimal::add);
            ClearUserPositionServiceImpl.log.info("contractCode: {}, sum : {}", contract.getContractCode(), sum);
        }

        ClearUserPositionServiceImpl.log.info("clear position, proper data spent : {}", (System.currentTimeMillis() - ts));
        ts = System.currentTimeMillis();
        final List<Future<BigDecimal>> subFutures = new LinkedList<>();
        for (final ClearSubWorker w : subWorkers) {
            final Future<BigDecimal> future = this.executorService.submit(w);
            subFutures.add(future);
        }

        BigDecimal subTotal = BigDecimal.ZERO;
        for (final Future<BigDecimal> f : subFutures) {
            subTotal = subTotal.add(f.get());
        }
        ClearUserPositionServiceImpl.log.info("clear position, sub worker spent : {}", (System.currentTimeMillis() - ts));
        ts = System.currentTimeMillis();
        BigDecimal tempTotal = subTotal;
        final List<Future<BigDecimal>> addFutures = new LinkedList<>();
        for (final ClearAddWorker w : addWorkers) {
            if (tempTotal.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
            final BigDecimal expectSize = w.getExpectSize();
            if (tempTotal.compareTo(expectSize) <= 0) {
                w.setSize(tempTotal);
                tempTotal = BigDecimal.ZERO;
            } else {
                w.setSize(expectSize);
                tempTotal = tempTotal.subtract(expectSize);
            }
            final Future<BigDecimal> future = this.executorService.submit(w);
            addFutures.add(future);
        }
        BigDecimal addTotal = BigDecimal.ZERO;
        for (final Future<BigDecimal> f : addFutures) {
            addTotal = addTotal.add(f.get());
        }
        ClearUserPositionServiceImpl.log.info("clear position, add worker spent : {}", (System.currentTimeMillis() - ts));
        if (subTotal.compareTo(addTotal) != 0) {
            ClearUserPositionServiceImpl.log.error("clear error, contractCode : {}, subTotal : {}, addTotal : {}", contract.getContractCode(), subTotal, addTotal);
            return;
        }
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(contract.getLiquidationDate());
        calendar.add(Calendar.HOUR_OF_DAY, currencyPair.getLiquidationHour() * -1);
        this.assetsFeeRateService.scheduleFeeRate(contract, calendar.getTime(), contract.getLiquidationDate());
        this.contractService.createNextContract(contract, currencyPair);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void buildUserBill(final List<PositionClear> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        final Date date = new Date();
        final List<UserBill> userBills = list.stream().map(x -> UserBill.builder()
                .modifyDate(date)
                .createdDate(date)
                .referId(x.getId())
                .makerTaker(0)
                .detailSide(x.getSide())
                .tradeNo("")
                .feeCurrencyCode("")
                .fee(BigDecimal.ZERO)
                .contractCode(x.getContractCode())
                .userId(x.getUserId())
                .uId("")
                .profit(x.getSumSize())
                .size(BigDecimal.ZERO)
                .amount(BigDecimal.ZERO)
                .price(BigDecimal.ZERO)
                .type(BillTypeEnum.SETTLEMENT.getBillType())
                .beforeBalance(x.getBeforeBalance())
                .afterBalance(x.getAfterBalance())
                .brokerId(x.getBrokerId())
                .currencyCode(x.getBase())
                .beforePosition(x.getAmount())
                .afterPosition(x.getAmount())
                .build()).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(userBills)) {
            this.userBillService.batchAdd(userBills);
        }

        final List<SystemBill> systemBills = list.stream().map(x -> SystemBill.builder()
                .brokerId(x.getBrokerId())
                .userId(x.getUserId())
                .feeCurrencyCode("")
                .fee(BigDecimal.ZERO)
                .uId("")
                .profit(x.getSumSize())
                .modifyDate(date)
                .currencyCode(x.getBase())
                .createdDate(date)
                .build())
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(systemBills)) {
            this.systemBillService.batchAdd(systemBills);
        }
        final List<Long> ids = list.stream().map(PositionClear::getId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(ids)) {
            this.positionClearService.batchUpdateStatus(ids, PositionClearEnum.DEAL_BILL);
        }
    }

    private UserPosition takeCurrentPosition(final List<UserPosition> userPositions, final Contract contract) {
        if (CollectionUtils.isEmpty(userPositions)) {
            return null;
        }
        return userPositions.stream().filter(u -> u.getContractCode().equalsIgnoreCase(contract.getContractCode())).findFirst().orElse(null);
    }
}
