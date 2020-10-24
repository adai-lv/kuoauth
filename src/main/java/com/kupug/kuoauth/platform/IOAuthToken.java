package com.kupug.kuoauth.platform;

import com.kupug.kuoauth.KuOAuthToken;

/**
 * <p>
 * 平台 oauth token 统一接口
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public interface IOAuthToken {

    /**
     * 转化统一的 OAuth Token
     *
     * @return KuOAuthToken
     */
    KuOAuthToken valueOf();
}
