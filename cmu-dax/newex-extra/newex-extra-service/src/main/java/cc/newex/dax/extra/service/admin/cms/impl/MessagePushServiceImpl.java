package cc.newex.dax.extra.service.admin.cms.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.cms.MessagePushExample;
import cc.newex.dax.extra.data.cms.MessagePushRepository;
import cc.newex.dax.extra.domain.cms.MessagePush;
import cc.newex.dax.extra.dto.cms.MessagePushDTO;
import cc.newex.dax.extra.service.admin.cms.MessagePushService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Message push service.
 *
 * @author huxingkong
 * @date 2018 /10/19 4:54 PM
 */
@Slf4j
@Service
public class MessagePushServiceImpl
        extends AbstractCrudService<MessagePushRepository, MessagePush, MessagePushExample, Integer>
        implements MessagePushService {

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    protected MessagePushExample getPageExample(String fieldName, String keyword) {
        MessagePushExample messagePushExample = new MessagePushExample();
        messagePushExample.createCriteria().andFieldLike(fieldName, keyword);
        return messagePushExample;
    }

    /**
     * 获取未推送切推送时间在当前时间之前的消息
     *
     * @param now 当前时间
     * @return list
     */
    @Override
    public List<MessagePush> listNotPushMessageAndPushTimeBeforeNow(LocalDateTime now) {

        MessagePushExample example = new MessagePushExample();
        MessagePushExample.Criteria criteria = example.createCriteria();

        criteria.andStatusEqualTo(WAIT_PUSH_STATUS);
        criteria.andPushTimeLessThanOrEqualTo(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));

        return this.getByExample(example);
    }

    @Override
    public List<MessagePushDTO> listByConditionAndPage(PageInfo pageInfo, MessagePushDTO queryParameter) {

        MessagePushExample example = new MessagePushExample();
        MessagePushExample.Criteria criteria = example.createCriteria();

        if (Objects.nonNull(queryParameter)) {
            if (StringUtils.isNotEmpty(queryParameter.getLocale())) {
                criteria.andLocaleEqualTo(queryParameter.getLocale());
            }
            if (StringUtils.isNotEmpty(queryParameter.getType())) {
                criteria.andTypeEqualTo(queryParameter.getType());
            }
            if (StringUtils.isNotEmpty(queryParameter.getPlatform())) {
                criteria.andPlatformEqualTo(queryParameter.getPlatform());
            }
        }

        List<MessagePush> queryData = Optional.ofNullable(this.getByPage(pageInfo, example)).orElse(Collections.emptyList());
        return queryData.stream().map(item -> modelMapper.map(item, MessagePushDTO.class)).collect(Collectors.toList());
    }
}
