package com.kupug.kuoauth.platform;

import com.kupug.kuoauth.KuOAuthUser;

/**
 * <p>
 * OAuth平台授权用户统一接口
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public interface IOAuthUser {

    /**
     * 转化统一的 OAuth User
     *
     * @return KuOAuthUser
     */
    KuOAuthUser valueOf();
}
