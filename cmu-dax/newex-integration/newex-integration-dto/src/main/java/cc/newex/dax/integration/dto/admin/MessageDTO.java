package cc.newex.dax.integration.dto.admin;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageDTO {
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
    private Boolean sent;
    private Integer retryTimes;
    private Date nextRetryTime;
    private String locale;
    private Integer brokerId;
}
