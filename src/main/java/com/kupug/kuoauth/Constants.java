package com.kupug.kuoauth;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 常量池配置
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public interface Constants {

    /**
     * 编码格式
     */
    Charset ENCODE_CHARSET = StandardCharsets.UTF_8;
    String ENCODE_CHARSET_STRING = ENCODE_CHARSET.displayName();

    /**
     * 加密算法
     */
    String HMAC_SHA_256 = "HmacSHA256";
}
