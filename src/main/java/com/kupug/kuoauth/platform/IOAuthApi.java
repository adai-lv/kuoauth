package com.kupug.kuoauth.platform;

import com.kupug.kuoauth.KuOAuthException;

/**
 * <p>
 * OAuth平台的 URI API 统一配置
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public interface IOAuthApi {

    /**
     * 获取授权url
     *
     * @return url
     */
    String authorize();

    /**
     * 获取用户信息的url
     *
     * @return url
     */
    String userInfo();

    /**
     * 获取accessToken的url（部分平台不支持）
     *
     * @return url
     */
    default String accessToken() {
        throw new KuOAuthException("Get accessToken is not supported");
    }

    /**
     * 获取openid的url（部分平台不支持）
     * QQ 平台支持获取 openid
     *
     * @return url
     * @throws KuOAuthException OAuth 平台不支持
     */
    default String openId() {
        throw new KuOAuthException("Get openId is not supported");
    }

    /**
     * 获取取消授权的url（部分平台不支持）
     *
     * @return url
     * @throws KuOAuthException OAuth 平台不支持
     */
    default String revoke() {
        throw new KuOAuthException("Revocation of authorization is not supported");
    }

    /**
     * 获取刷新授权的url（部分平台不支持）
     *
     * @return url
     * @throws KuOAuthException OAuth 平台不支持
     */
    default String refresh() {
        throw new KuOAuthException("Refresh authorization is not supported");
    }
}
