package cc.newex.dax.users.dto.kyc;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FaceIdReqDTO {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 实名验证返回值，具体见“”FaceID Lite 返回值说明
     */
    private String data;
    /**
     * 数据段的签名，签名方法为 sign = sha1(API_SECRET + json_data)，其中sha1返回的字符均为小写。
     */
    private String sign;
    /**
     * 业务流串号，可以用于反查比对结果；
     */
    private String bizId;


}
