package cc.newex.commons.openapi.specs.model;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;

/**
 * Cursor pagination   <br/>
 * Created by newex-team on 2018/2/26 19:55. <br/>
 */
@Setter
@Getter
public class CursorPageParams {
    /**
     * cursor pagination response headers
     */
    public static final String BEFORE = "BEFORE";
    public static final String AFTER = "AFTER";
    public static final String LIMIT = "LIMIT";
    /**
     * default cursor id
     */
    public static final int ONE = 1;
    /**
     * max limit: 100
     */
    public static final int HUNDRED = 100;
    /**
     * Request page before (newer) this pagination id.
     * eg: before=2, page number = 1.
     */
    protected int before;
    /**
     * Request page after (older) this pagination id.
     * eg: after=2, page number = 3.
     */
    protected int after;
    /**
     * Number of results per request. Maximum 100. (default 100)
     */
    protected int limit;

    /**
     * set cursor pagination response headers
     */
    public final void setCursorPageResponseHeaders(final HttpServletResponse response) {
        response.setHeader(CursorPageParams.BEFORE, this.toString(this.before));
        response.setHeader(CursorPageParams.AFTER, this.toString(this.after));
        response.setHeader(CursorPageParams.LIMIT, this.toString(this.limit));
    }

    private String toString(final int data) {
        return String.valueOf(data);
    }
}
