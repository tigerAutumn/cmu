package cc.newex.dax.users.service.membership.impl;

import cc.newex.commons.dictionary.consts.BrokerIdConsts;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.client.BrokerSignConfigClient;
import cc.newex.dax.integration.dto.message.BrokerSignConfigDTO;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.criteria.UserBrokerExample;
import cc.newex.dax.users.data.UserBrokerRepository;
import cc.newex.dax.users.domain.UserBroker;
import cc.newex.dax.users.dto.membership.UserBrokerDTO;
import cc.newex.dax.users.service.membership.UserBrokerService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 券商表 服务实现
 *
 * @author newex-team
 * @date 2018-09-11 10:57:27
 */
@Slf4j
@Service
public class UserBrokerServiceImpl
        extends AbstractCrudService<UserBrokerRepository, UserBroker, UserBrokerExample, Integer>
        implements UserBrokerService {

    @Autowired
    private UserBrokerRepository userBrokerRepository;

    @Autowired
    private BrokerSignConfigClient brokerSignConfigClient;

    private final LoadingCache<String, Integer> localeCache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<String, Integer>() {
                        @Override
                        public Integer load(final String key) {
                            final Integer value = UserBrokerServiceImpl.this.getBrokerId(key);

                            return value;
                        }
                    }
            );

    @Override
    protected UserBrokerExample getPageExample(final String fieldName, final String keyword) {
        final UserBrokerExample example = new UserBrokerExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public Integer getBrokerId(final String host) {
        final UserBroker userBroker = this.getUserBrokerByHost(host);

        log.info("getBrokerId, host: {}, UserBroker: {}", host, userBroker);

        if (userBroker == null) {
            return BrokerIdConsts.COIN_MEX;
        }

        return userBroker.getId();
    }

    @Override
    public Integer getBrokerIdFromCache(final String host) {
        if (StringUtils.isBlank(host)) {
            return null;
        }

        return this.localeCache.getUnchecked(host);
    }

    @Override
    public Integer save(final UserBroker userBroker) {
        final String[] hosts = userBroker.getBrokerHosts().split(",");
        for (final String host : hosts) {
            final UserBroker broker = this.getUserBrokerByHost(host);
            if (!ObjectUtils.isEmpty(broker)) {
                return 0;
            }
        }

        final int result = this.userBrokerRepository.insert(userBroker);
        this.batchUpdate();
        return result;
    }

    @Override
    public UserBroker getUserBrokerByHost(final String host) {
        if (StringUtils.isBlank(host)) {
            return null;
        }

        final List<UserBroker> userBrokerList = this.getAll();
        if (CollectionUtils.isEmpty(userBrokerList)) {
            return null;
        }

        final UserBroker result = userBrokerList.stream()
                .filter(userBroker -> this.filter(host, userBroker))
                .findFirst()
                .orElse(null);

        return result;
    }

    /**
     * 根据host查询对应的brokerId
     *
     * @param host
     * @param userBroker
     * @return
     */
    private boolean filter(final String host, final UserBroker userBroker) {
        final String brokerHosts = userBroker.getBrokerHosts();
        if (StringUtils.isBlank(brokerHosts)) {
            return false;
        }

        final String[] allHost = brokerHosts.split("\\s*,\\s*");

        for (final String oneHost : allHost) {
            if (StringUtils.equalsIgnoreCase(oneHost.trim(), host.trim())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<UserBrokerDTO> selectBrokerList() {
        final UserBrokerExample example = new UserBrokerExample();
        example.createCriteria().andStatusEqualTo(0);
        final List<UserBroker> list = this.userBrokerRepository.selectByExample(example);
        final List<UserBrokerDTO> dtoList = ObjectCopyUtils.mapList(list, UserBrokerDTO.class);
        return dtoList;
    }

    @Override
    public List<UserBrokerDTO> listByPage(final PageInfo pageInfo, final UserBrokerDTO dto) {
        final UserBrokerExample example = new UserBrokerExample();
        final UserBrokerExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(dto.getId())) {
            criteria.andIdEqualTo(dto.getId());
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(dto.getBrokerHosts())) {
            criteria.andBrokerHostsLike("%" + dto.getBrokerHosts() + "%");
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(dto.getBrokerName())) {
            criteria.andBrokerNameLike("%" + dto.getBrokerName() + "%");
        }
        if (Objects.nonNull(dto.getStatus())) {
            criteria.andStatusEqualTo(dto.getStatus());
        }
        return ObjectCopyUtils.mapList(this.getByPage(pageInfo, example), UserBrokerDTO.class);
    }

    @Override
    public boolean updateBroker(final UserBrokerDTO dto) {
        final boolean resutlt = this.userBrokerRepository.updateById(ObjectCopyUtils.map(dto, UserBroker.class)) > 1 ? true : false;
        this.batchUpdate();
        return resutlt;
    }

    /**
     * 更新integration的缓存
     *
     * @param
     * @date 2018/9/14 下午2:52
     */
    private void batchUpdate() {
        log.info("broker-batchUpdate");
        final List<BrokerSignConfigDTO> configList = Lists.newArrayList();
        final List<UserBrokerDTO> brokerDTOList = this.selectBrokerList();
        brokerDTOList.stream().forEach(x -> {
            configList.add(BrokerSignConfigDTO.builder().brokerId(x.getId()).sign(x.getSign()).build());
        });
        this.brokerSignConfigClient.batchUpdate(configList);
    }

}