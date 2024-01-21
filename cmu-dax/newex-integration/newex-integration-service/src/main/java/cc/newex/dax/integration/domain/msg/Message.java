package cc.newex.dax.integration.domain.msg;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Message {
    private Long id;
    private Date createdDate;
    private Date modifyDate;
    private String countryCode;
    private String phoneNumber;
    private String emailAddress;
    private String fromAlias;
    private String templateCode;
    private String templateParams;
    private String subject;
    private String content;
    private boolean isSent;
    private int retryTimes;
    private Date nextRetryTime;
    private String locale;
    private String channelTplCode;
    private String type;
    /**
     * brokerId
     */
    private Integer brokerId;
}
