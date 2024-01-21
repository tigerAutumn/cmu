package cc.newex.dax.extra.rest.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author: lingqing.wan
 * date: 2018-04-13 下午4:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostVo {
    private long id;
    private String title;
    private String imageUrl;
    private String redirectUrl;
}
