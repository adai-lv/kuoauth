package com.kupug.kuoauth.utils;

import com.kupug.kuoauth.KuOAuthException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * <p>
 * oauth 通用工具助手
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public final class OAuthUtils {

    private OAuthUtils() {
    }

    /**
     * 生成随机 oauth state
     *
     * @return 随机的state字符串
     */
    public static String randomState() {
        return UuidUtils.getUUID();
    }

    /**
     * 签名
     *
     * @param key       key
     * @param data      data
     * @param algorithm algorithm
     * @return byte[]
     */
    public static byte[] signature(byte[] key, byte[] data, String algorithm) {
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(key, algorithm));
            return mac.doFinal(data);
        } catch (NoSuchAlgorithmException ex) {
            throw new KuOAuthException("Unsupported algorithm: " + algorithm, ex);
        } catch (InvalidKeyException ex) {
            throw new KuOAuthException("Invalid key: " + Arrays.toString(key), ex);
        }
    }
}
