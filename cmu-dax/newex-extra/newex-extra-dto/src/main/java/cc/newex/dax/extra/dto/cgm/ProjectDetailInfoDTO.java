package cc.newex.dax.extra.dto.cgm;

import lombok.*;

import java.io.Serializable;

/**
 * 上币申请详情
 *
 * @author liutiejun
 * @date 2018-08-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectDetailInfoDTO implements Serializable {


    private static final long serialVersionUID = -1532732975788001695L;
    /**
     * 项目ID
     */
    private Long tokenInfoId;
    /**
     * token
     */
    private ProjectTokenInfoDTO projectTokenInfoDTO;

    /**
     * token info
     */
    private ProjectApplyInfoDTO projectApplyInfoDTO;

}
