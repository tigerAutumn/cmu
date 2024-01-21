package cc.newex.dax.extra.scheduler.service.impl;

import cc.newex.dax.extra.domain.wiki.RtCurrencyDetail;
import cc.newex.dax.extra.domain.wiki.RtCurrencyRank;
import cc.newex.dax.extra.domain.wiki.RtCurrencyScore;
import cc.newex.dax.extra.domain.wiki.RtCurrencyTeam;
import cc.newex.dax.extra.scheduler.service.RatingTokenXxlService;
import cc.newex.dax.extra.service.wiki.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Rating token xxl service.
 *
 * @author better
 * @date create in 2018-12-03 11:04
 */
@Service
@Slf4j
public class RatingTokenXxlServiceImpl implements RatingTokenXxlService {

    private final RatingTokenService ratingTokenService;
    private final RtCurrencyRankService rtCurrencyRankService;
    private final RtCurrencyDetailService rtCurrencyDetailService;
    private final RtCurrencyTeamService rtCurrencyTeamService;
    private final RtCurrencyScoreService rtCurrencyScoreService;

    /**
     * Instantiates a new Rating token xxl service.
     *
     * @param ratingTokenService      the rating token service
     * @param rtCurrencyRankService   the rt currency rank service
     * @param rtCurrencyDetailService the rt currency detail service
     * @param rtCurrencyTeamService   the rt currency team service
     * @param rtCurrencyScoreService
     */
    @Autowired
    public RatingTokenXxlServiceImpl(final RatingTokenService ratingTokenService, final RtCurrencyRankService rtCurrencyRankService,
                                     final RtCurrencyDetailService rtCurrencyDetailService, final RtCurrencyTeamService rtCurrencyTeamService, final RtCurrencyScoreService rtCurrencyScoreService) {
        this.ratingTokenService = ratingTokenService;
        this.rtCurrencyRankService = rtCurrencyRankService;
        this.rtCurrencyDetailService = rtCurrencyDetailService;
        this.rtCurrencyTeamService = rtCurrencyTeamService;
        this.rtCurrencyScoreService = rtCurrencyScoreService;
    }

    @Override
    public void getRatingTokenRankListInfo() {
        final String locale = "en_us";
        final JSONArray json = this.ratingTokenService.getRankList(locale);
        final List<RtCurrencyRank> result = json.toJavaList(RtCurrencyRank.class);

        final Map<String, Long> idAndCidMap = this.rtCurrencyRankService.getAll().stream().collect(Collectors.toMap(RtCurrencyRank::getCid, RtCurrencyRank::getId));
        if (MapUtils.isEmpty(idAndCidMap)) {
            result.forEach(this.rtCurrencyRankService::add);
            return;
        }
        result.forEach(item -> {
            if (idAndCidMap.containsKey(item.getCid())) {
                item.setId(idAndCidMap.get(item.getCid()));
            }
        });
        this.rtCurrencyRankService.batchAddOnDuplicateKey(result);
    }

    @Override
    public void getRatingTokenBaseInfo() {
        final List<RtCurrencyRank> queryRankData = this.rtCurrencyRankService.getAll();
        final List<RtCurrencyDetail> result = queryRankData.parallelStream()
                .map(rtCurrencyRank -> {
                    JSONObject json;
                    try {
                        json = this.ratingTokenService.getBaseInfo(rtCurrencyRank.getCid());
                    } catch (Exception e) {
                        return null;
                    }
                    return json.toJavaObject(RtCurrencyDetail.class);
                })
                .collect(Collectors.toList());

        final Map<String, Long> idAndCidMap = this.rtCurrencyDetailService.getAll().stream().collect(Collectors.toMap(RtCurrencyDetail::getCid, RtCurrencyDetail::getId));
        if (MapUtils.isEmpty(idAndCidMap)) {
            result.forEach(this.rtCurrencyDetailService::add);
            return;
        }
        result.forEach(item -> {
            if (idAndCidMap.containsKey(item.getCid())) {
                item.setId(idAndCidMap.get(item.getCid()));
            }
        });
        this.rtCurrencyDetailService.batchAddOnDuplicateKey(result);
    }

    @Override
    public void getRatingTokenTeamInfo() {
        final List<RtCurrencyRank> queryRankData = this.rtCurrencyRankService.getAll();
        final List<RtCurrencyTeam> result = queryRankData.parallelStream()
                .flatMap(rtCurrencyRank -> {
                    JSONArray json;
                    try {
                        json = this.ratingTokenService.getTeamList(rtCurrencyRank.getCid());
                    } catch (Exception e) {
                        return Stream.empty();
                    }
                    return json.toJavaList(RtCurrencyTeam.class).stream().peek(item -> item.setCid(rtCurrencyRank.getCid()));
                })
                .collect(Collectors.toList());

        final List<RtCurrencyTeam> queryData = this.rtCurrencyTeamService.getAll();
        if (!CollectionUtils.isEmpty(queryData)) {
            this.rtCurrencyTeamService.removeIn(queryData);
        }
        this.rtCurrencyTeamService.batchAddOnDuplicateKey(result);
    }

    @Override
    public void getRatingTokenScoreInfo() {
        final List<RtCurrencyRank> queryRankData = this.rtCurrencyRankService.getAll();
        final List<RtCurrencyScore> result = queryRankData.parallelStream()
                .map(rtCurrencyRank -> {
                    JSONObject json = this.ratingTokenService.getScoreInfo(rtCurrencyRank.getCid());
                    RtCurrencyScore item = json.toJavaObject(RtCurrencyScore.class);
                    item.setSymbol(rtCurrencyRank.getSymbol());
                    item.setCid(rtCurrencyRank.getCid());
                    return item;
                })
                .collect(Collectors.toList());

        final Map<String, Long> idAndCidMap = this.rtCurrencyScoreService.getAll().stream().collect(Collectors.toMap(RtCurrencyScore::getCid, RtCurrencyScore::getId));
        if (MapUtils.isEmpty(idAndCidMap)) {
            result.forEach(this.rtCurrencyScoreService::add);
            return;
        }
        result.forEach(item -> {
            if (idAndCidMap.containsKey(item.getCid())) {
                item.setId(idAndCidMap.get(item.getCid()));
            }
        });
        this.rtCurrencyScoreService.batchAddOnDuplicateKey(result);
    }
}
