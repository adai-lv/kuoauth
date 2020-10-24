package com.kupug.kuoauth;

/**
 * <p>
 * 平台授权公共接口，所有平台的都需要实现该接口
 * </p>
 *
 * @author MaoHai.Lv
 * @since 1.0
 */
public interface KuOAuthPlatform {

    /**
     * 返回带{@code state}参数的授权url，授权回调时会带上这个{@code state}
     *
     * @param state state 验证授权流程的参数，可以防止csrf
     * @return 返回授权地址
     */
    String authorize(String state);

    /**
     * 第三方登录
     *
     * @param authCallback 用于接收回调参数的实体
     * @return 返回登录成功后的用户信息
     */
    KuOAuthLogin login(KuOAuthCallback authCallback);

    /**
     * 撤销授权
     *
     * @param authToken 登录成功后返回的Token信息
     * @return AuthResponse
     */
    default boolean revoke(KuOAuthToken authToken) {
        throw new KuOAuthException("revoke is not implemented");
    }

    /**
     * 刷新access token （续期）
     *
     * @param authToken 登录成功后返回的Token信息
     * @return AuthResponse
     */
    default KuOAuthToken refresh(KuOAuthToken authToken) {
        throw new KuOAuthException("refresh is not implemented");
    }
}
