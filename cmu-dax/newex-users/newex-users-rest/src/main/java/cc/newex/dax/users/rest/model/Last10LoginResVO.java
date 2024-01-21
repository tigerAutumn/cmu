package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Last10LoginResVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dateTime;
    private String type;
    private String ipAddress;
    private String region;

}
