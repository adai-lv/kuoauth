package com.kupug.kuoauth.utils;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * <p>
 * 集合工具助手
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public final class CollectionUtils {

    private CollectionUtils() {
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return null == collection || collection.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }

    public static <T, V> boolean isEmpty(Map<T, V> map) {
        return null == map || map.size() == 0;
    }

    public static <T, V> boolean isNotEmpty(Map<T, V> map) {
        return !isEmpty(map);
    }

    /**
     *  数组是否为空
     *
     * @param arrays Array
     * @return true 为空，否则非空
     */
    public static <T> boolean isEmpty(T[] arrays) {
        return null == arrays || arrays.length == 0;
    }

    /**
     *  数组是否为空
     *
     * @param arrays Array
     * @return true 为非空，否则为空
     */
    public static <T> boolean isNotEmpty(T[] arrays) {
        return !isEmpty(arrays);
    }

    /**
     * Map 遍历
     *
     * @param map    待遍历的 map
     * @param action 操作
     * @param <K>    map键泛型
     * @param <V>    map值泛型
     */
    public static <K, V> void forEach(Map<K, V> map, BiConsumer<? super K, ? super V> action) {
        if (isEmpty(map) || action == null) {
            return;
        }

        for (Map.Entry<K, V> entry : map.entrySet()) {
            action.accept(entry.getKey(), entry.getValue());
        }
    }
}
