package cc.newex.commons.openapi.specs.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Open Api Page Params  <br/>
 * Created by newex-team on 2018/2/26 18:46. <br/>
 * <p>
 * Common paging: LIMIT rows OFFSET offset: <br/>
 * <blockquote><pre>
 *  SELECT * FROM user_info WHERE 1=1 LIMIT 5, 10;
 *  </pre></blockquote>
 * <p>
 * Cursor paging: If the primary key id is the cursor id: <br/>
 * <blockquote><pre>
 *  before: SELECT * FROM user_info WHERE id < 7 ORDER BY id DESC LIMIT 3;
 *  after: SELECT * FROM user_info WHERE id > 9 LIMIT 3;
 * </pre></blockquote>
 * </p>
 */
@Setter
@Getter
public class OpenApiPageParams extends CursorPageParams {
    /**
     * db limit first arg
     */
    protected int begin;

    /**
     * db limit second arg
     */
    protected int end;

    /**
     * init db limit args
     */
    public final void initPageParams() {
        if (this.limit < ONE || this.limit > HUNDRED) {
            this.limit = HUNDRED;
        }
        if (this.before > 0) {
            this.begin = (this.before - 2) * this.limit;
        } else if (this.after > 0) {
            this.begin = this.after * this.limit;
        } else {
            this.before = 1;
            this.after = 0;
        }
        if (this.begin < 0) {
            this.begin = 0;
        }
        this.end = this.limit;
    }
}
