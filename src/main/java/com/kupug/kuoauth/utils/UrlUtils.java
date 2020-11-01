package com.kupug.kuoauth.utils;

import com.kupug.kuoauth.model.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * URL 工具助手
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public final class UrlUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(UrlUtils.class);

    private UrlUtils() {
    }

    /**
     * 是否为http协议
     *
     * @param url 待验证的url
     * @return true: http协议, false: 非http协议
     */
    public static boolean isHttpProtocol(String url) {
        return StringUtils.isNotEmpty(url) && url.startsWith("http://");
    }

    /**
     * 是否为https协议
     *
     * @param url 待验证的url
     * @return true: https协议, false: 非https协议
     */
    public static boolean isHttpsProtocol(String url) {
        return StringUtils.isNotEmpty(url) && url.startsWith("https://");
    }

    /**
     * 是否为本地主机（域名）
     *
     * @param url 待验证的url
     * @return true: 本地主机（域名）, false: 非本地主机（域名）
     */
    public static boolean isLocalHost(String url) {
        return StringUtils.isEmpty(url) || url.contains("127.0.0.1") || url.contains("localhost");
    }

    /**
     * 获取IP
     *
     * @return ip
     */
    public static String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOGGER.error("获取 ip 信息错误", e);
            return "";
        }
    }

    /**
     * 编码URL
     *
     * @param url URL
     * @return 加密后的 URL
     */
    public static String urlEncode(String url) {

        if (StringUtils.isEmpty(url)) {
            return "";
        }

        try {

            String encoded = URLEncoder.encode(url, Constants.ENCODE_CHARSET_STRING);
            return encoded
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("/", "%2F")
                    .replace("~", "%7E");

        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Unsupported encoding", e);
            return url;
        }
    }

    /**
     * 解码URL<br>
     * 将%开头的16进制表示的内容解码。
     *
     * @param url URL
     * @return 解码后的URL
     */
    public static String urlDecode(String url) {

        if (StringUtils.isEmpty(url)) {
            return url;
        }

        try {
            return URLDecoder.decode(url, Constants.ENCODE_CHARSET_STRING);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Unsupported decoding", e);
            return url;
        }
    }

    /**
     * map转字符串，转换后的字符串格式为 {@code xxx=xxx&xxx=xxx}
     *
     * @param params 待转换的map
     * @param encode 是否转码
     * @return str
     */
    public static String valueOf(Map<String, String> params, boolean encode) {

        if (CollectionUtils.isEmpty(params)) {
            return "";
        }

        List<String> paramList = new ArrayList<>();
        CollectionUtils.forEach(params, (k, v) -> {
            if (StringUtils.isEmpty(v)) {
                paramList.add(k + "=");
            } else {
                paramList.add(k + "=" + (encode ? urlEncode(v) : v));
            }
        });

        return String.join("&", paramList);
    }

    /**
     * map转字符串，转换后的字符串格式为 {@code xxx=xxx&xxx=xxx}
     * 对转换的字符串不加密
     *
     * @param params 待转换的map
     * @return str
     */
    public static String valueOf(Map<String, String> params) {
        return valueOf(params, false);
    }

    /**
     * 字符串转map，字符串格式为 {@code xxx=xxx&xxx=xxx}
     *
     * @param str    待转换的字符串
     * @param decode 是否解码
     * @return map
     */
    public static Map<String, String> valueOf(String str, boolean decode) {

        Map<String, String> params = new HashMap<>();
        if (StringUtils.isEmpty(str)) {
            return params;
        }

        String[] kvArray = str.split("&");
        for (String kv : kvArray) {
            String[] param = kv.split("=");

            String value;
            if (param.length == 2) {
                value = decode ? urlDecode(param[1]) : param[1];
            } else {
                // 对于无参数值的字符串，将值设为""
                value = "";
            }

            params.put(param[0], value);
        }

        return params;
    }

    /**
     * 字符串转map，字符串格式为 {@code xxx=xxx&xxx=xxx}
     * 对params 参数值，不做解密处理
     *
     * @param str 待转换的字符串
     * @return map
     */
    public static Map<String, String> valueOf(String str) {
        return valueOf(str, false);
    }

    /**
     * 拼接 URL
     *
     * @param url    给定的字符串
     * @param params 需要追加的内容
     * @return 追加后的字符串
     */
    public static String concat(String url, String params) {

        final String questionMark = "?";
        final String andMark = "&";

        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(params)) {
            return url;
        }

        if (url.contains(questionMark)) {
            if (!url.endsWith(questionMark)) {
                url = url.concat(andMark);
            }
        } else {
            url = url.concat(questionMark);
        }

        return url.concat(params);
    }
}
