package cc.newex.commons.openapi.specs.enums;

import lombok.Getter;

/**
 * Http Status Codes.  <br/>
 * Created by newex-team on 2018/2/5 20:38
 */
@Getter
public enum HttpStatusEnum {

    BAD_REQUEST(400, "Bad Request", "Invalid request format"),
    UNAUTHORIZED(401, "Unauthorized", "Invalid authorization"),
    FORBIDDEN(403, "Forbidden", "You do not have access to the requested resource"),
    NOT_FOUND(404, "Not Found", "Request resource path error"),
    TOO_MANY_REQUESTS(429, "Too Many Requests", "Too many requests,has been limited"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", "We had a problem with our server"),;

    private final int status;
    private final String message;
    private final String comment;

    HttpStatusEnum(final int status, final String message, final String comment) {
        this.status = status;
        this.message = message;
        this.comment = comment;
    }
}
