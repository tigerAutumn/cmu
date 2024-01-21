package cc.newex.commons.openapi.specs.auth;

/**
 * Open Api Auth Manager. Please use after implementation. <br/>
 *
 * @author newex-team
 * @date 2018-04-28
 */
public interface OpenApiAuthManager {

    /**
     * OpenApi Auth: per biz platform customizes Auth Impl. <br/>
     * user-defined .<br/>
     * The message is returned by exception.
     *
     * @param authToken {@link OpenApiAuthToken}
     * @return true validate pass
     */
    boolean authenticate(OpenApiAuthToken authToken);
}
