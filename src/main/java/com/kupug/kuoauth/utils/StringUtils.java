package com.kupug.kuoauth.utils;

/**
 * <p>
 * 字符串工具助手
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public final class StringUtils {

    private StringUtils() {}

    public static boolean isEmpty(String str) {
        return null == str || str.trim().isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
