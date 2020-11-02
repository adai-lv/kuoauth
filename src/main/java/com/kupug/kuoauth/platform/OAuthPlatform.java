package com.kupug.kuoauth.platform;

import com.kupug.kuoauth.KuOAuthException;
import com.kupug.kuoauth.KuOAuthPlatform;
import com.kupug.kuoauth.model.HttpConfig;
import com.kupug.kuoauth.model.KuOAuthCallback;
import com.kupug.kuoauth.model.KuOAuthConfig;
import com.kupug.kuoauth.model.KuOAuthLogin;
import com.kupug.kuoauth.model.KuOAuthToken;
import com.kupug.kuoauth.model.KuOAuthUser;
import com.kupug.kuoauth.model.Separator;
import com.kupug.kuoauth.utils.CollectionUtils;
import com.kupug.kuoauth.utils.HttpClient;
import com.kupug.kuoauth.utils.OAuthUtils;
import com.kupug.kuoauth.utils.StringUtils;
import com.kupug.kuoauth.utils.UrlUtils;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 授权平台基本逻辑抽象
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public abstract class OAuthPlatform implements KuOAuthPlatform {

    protected final KuOAuthConfig config;
    protected IOAuthApi oAuthApi;
    protected IOAuthScope[] oAuthScopes;

    /**
     * 获取access token
     *
     * @param authCallback 授权成功后的回调参数
     * @return OAuth token
     */
    protected abstract KuOAuthToken getAccessToken(KuOAuthCallback authCallback);

    /**
     * 使用token换取用户信息
     *
     * @param authToken 通过{@link OAuthPlatform#getAccessToken(KuOAuthCallback)}获取到的{@code authToken}
     * @return 用户信息
     */
    protected abstract KuOAuthUser getUserInfo(KuOAuthToken authToken);

    public OAuthPlatform(KuOAuthConfig config) {
        this.config = config;

        checkConfig(config);
    }

    @Override
    public KuOAuthLogin login(KuOAuthCallback authCallback) {

        checkAuthCode(authCallback);

        if (!config.isIgnoreCheckState()) {
            checkAuthState(authCallback.getState());
        }

        KuOAuthToken authToken = this.getAccessToken(authCallback);
        KuOAuthUser authUser = this.getUserInfo(authToken);

        return KuOAuthLogin.builder().oAuthToken(authToken).oAuthUser(authUser).build();
    }

    /**
     * 获取state，如果为空，取当前服务器生成的UUID
     *
     * @param state 原始的state
     * @return 返回不为null的state
     */
    protected String getRealState(String state) {
        if (StringUtils.isEmpty(state)) {
            return OAuthUtils.randomState();
        }

        return state;
    }

    /**
     * 获取 oauth scope 参数值，以指定的分隔符分离多个 scope，默认为空格
     * 如果 OAuth Platform 要求的分隔符不一样，可以在 OAuth Platform 实现类中
     * Override {@code BaseOAuthPlatform#getOAuthScopes()}
     *
     * @return String
     */
    protected String getOAuthScopes() {
        return separateOAuthScopes(Separator.SPACE.getValue());
    }

    /**
     * 获取以 {@code separator}分割过后的 OAuth Scope 信息
     *
     * @param separator 多个 {@code scope} 间的分隔符
     * @return String
     */
    protected String separateOAuthScopes(String separator) {

        List<String> scopes = config.getScopes();
        if (CollectionUtils.isEmpty(scopes)) {
            scopes = getDefaultScopes(oAuthScopes);
        }

        if (CollectionUtils.isNotEmpty(scopes)) {
            if (StringUtils.isEmpty(separator)) {
                separator = Separator.SPACE.getValue();
            }

            return String.join(separator, scopes);
        }

        return "";
    }

    /**
     * 针对部分平台，对 redirect uri 有特定要求。
     * 一般来说redirect uri都是http://，而对于facebook平台，redirect uri 必须是https的链接
     * 可以在 OAuth Platform 实现类中 Override {@code BaseOAuthPlatform#checkRedirectUri()}
     *
     * @param redirectUri 应用回调地址
     */
    protected void checkRedirectUri(String redirectUri) {

        if (!UrlUtils.isHttpProtocol(redirectUri) &&
                !UrlUtils.isHttpsProtocol(redirectUri)) {
            throw new KuOAuthException("redirectUri must be HTTP and HTTPS protocols");
        }
    }

    /**
     * 校验回调传回的{@code code}，为空或者不存在
     * 对于不同平台使用不同参数接受code的情况统一做处理
     * 可以在 OAuth Platform 实现类中 Override {@code BaseOAuthPlatform#checkAuthCode()}
     *
     * @param authCallback 从第三方授权回调回来时传入的参数集合
     */
    protected void checkAuthCode(KuOAuthCallback authCallback) {
        if (StringUtils.isEmpty(authCallback.getCode())) {
            throw new KuOAuthException("Illegal OAuth code");
        }
    }

    /**
     * 包装 http proxy
     *
     * @param builder http client builder
     * @return HttpClient.Builder
     */
    protected HttpClient.Builder wrapHttpProxy(HttpClient.Builder builder) {

        HttpConfig httpConfig = config.getHttpConfig();
        if (Objects.nonNull(httpConfig)) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(httpConfig.getProxyHost(), httpConfig.getProxyPort());
            return builder.connectTimeout(httpConfig.getConnectTimeout())
                    .readTimeout(httpConfig.getReadTimeout())
                    .writeTimeout(httpConfig.getWriteTimeout())
                    .proxy(new Proxy(Proxy.Type.HTTP, inetSocketAddress));
        }

        return builder;
    }

    /**
     * 校验回调传回的{@code state}，为空或者不存在
     *
     * @param state 回调传回的{@code state}
     */
    private void checkAuthState(String state) {
        if (StringUtils.isEmpty(state)) {
            throw new KuOAuthException("Illegal OAuth state");
        }
    }

    /**
     * 检查配置合法性。
     *
     * @param config OAuth config
     */
    private void checkConfig(KuOAuthConfig config) {

        String clientId = config.getClientId();
        String clientSecret = config.getClientSecret();
        String redirectUri = config.getRedirectUri();

        if (StringUtils.isEmpty(clientId)) {
            throw new KuOAuthException("clientId not empty");
        }

        if (StringUtils.isEmpty(clientSecret)) {
            throw new KuOAuthException("clientSecret not empty");
        }

        if (StringUtils.isEmpty(redirectUri)) {
            throw new KuOAuthException("redirectUri not empty");
        }

        checkRedirectUri(redirectUri);
    }

    /**
     * 获取默认配置的 oauth scope
     *
     * @param oAuthScopes oauth scope
     * @return 默认配置 scopes
     */
    private List<String> getDefaultScopes(IOAuthScope[] oAuthScopes) {

        List<String> scopes = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(oAuthScopes)) {
            scopes = Arrays.stream(oAuthScopes)
                    .filter((IOAuthScope::isDefault))
                    .map(IOAuthScope::getScope)
                    .collect(Collectors.toList());
        }

        return scopes;
    }
}
