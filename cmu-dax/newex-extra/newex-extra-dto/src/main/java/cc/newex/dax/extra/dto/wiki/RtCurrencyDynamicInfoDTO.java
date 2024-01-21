package cc.newex.dax.extra.dto.wiki;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 币种动态信息DTO
 *
 * @author better
 * @date create in 2018/11/27 6:29 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RtCurrencyDynamicInfoDTO implements RtCurrencyInfoDTO {

    /**
     * 动态集合
     */
    private List<DynamicInfoDTO> dynamicInfos;

    /**
     * 币种动态信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DynamicInfoDTO {

        /**
         * 动态标题
         */
        private String title;

        /**
         * 动态链接
         */
        private String link;

        /**
         * 动态细节
         */
        private String detail;

        /**
         * 图片
         */
        private String imgUrl;

        /**
         * 时间
         */
        private Date time;
    }
}
