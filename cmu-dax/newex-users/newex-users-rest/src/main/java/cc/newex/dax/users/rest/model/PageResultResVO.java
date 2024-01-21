package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResultResVO<T> {
    private List<T> list;
//    private PageInfo pageInfo;
}
