package cc.newex.dax.asset.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author lilaizhen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseDateDto {
    private Long id;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date date;
}
