package com.kupug.kuoauth.platform;

/**
 * <p>
 * OAuth平台  Scope 配置项的统一接口
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public interface IOAuthScope {

    /**
     * 获取字符串 {@code scope}，对应为各平台实际使用的 {@code scope}
     *
     * @return String
     */
    String getScope();

    /**
     * 判断当前 {@code scope} 是否为各平台默认启用的
     *
     * @return boolean
     */
    boolean isDefault();
}
