package com.kupug.kuoauth.platform;

import com.kupug.kuoauth.model.KuOAuthConfig;
import com.kupug.kuoauth.KuOAuthException;
import com.kupug.kuoauth.KuOAuthPlatform;
import com.kupug.kuoauth.model.Platform;

import java.lang.reflect.Constructor;

/**
 * <p>
 * platform factory
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public class PlatformFactory {

    public static KuOAuthPlatform newInstance(Platform platform, KuOAuthConfig config) {

        KuOAuthPlatform oAuthPlatform;
        Class<? extends KuOAuthPlatform> clazz = platform.getClazz();
        try {
            Constructor<? extends KuOAuthPlatform> constructor = clazz.getDeclaredConstructor(KuOAuthConfig.class);
            oAuthPlatform = constructor.newInstance(config);

        } catch (Exception e) {
            throw new KuOAuthException("不支持此授权平台，请确认", e);
        }

        return oAuthPlatform;
    }
}
