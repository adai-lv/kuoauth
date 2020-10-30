package com.kupug.kuoauth.utils;

import com.kupug.kuoauth.model.Constants;

import java.util.Objects;

/**
 * <p>
 * Base64 编码
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public class Base64Utils {

    /**
     * 标准编码表
     */
    private static final byte[] STANDARD_ENCODE_TABLE = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'
    };

    /**
     * URL安全的编码表，将 + 和 / 替换为 - 和 _
     */
    private static final byte[] URL_SAFE_ENCODE_TABLE = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '-', '_'
    };

    private Base64Utils() {
    }

    /**
     * base64编码，非URL安全的
     *
     * @param source 被编码的base64字符串
     * @return 编码后的字符串
     */
    public static String valueOf(CharSequence source) {
        return valueOf(source, false, false);
    }

    /**
     * base64编码，非URL安全的
     *
     * @param source      被编码的base64字符串
     * @param isMultiLine 在76个char之后是CRLF还是EOF
     * @return 编码后的字符串
     */
    public static String valueOf(CharSequence source, boolean isMultiLine) {
        return valueOf(source, isMultiLine, false);
    }

    /**
     * base64编码
     *
     * @param source      被编码的base64字符串
     * @param isMultiLine 在76个char之后是CRLF还是EOF
     * @param isUrlSafe   是否使用URL安全字符，一般为<code>false</code>
     * @return 编码后的字符串
     */
    public static String valueOf(CharSequence source, boolean isMultiLine, boolean isUrlSafe) {
        if (Objects.isNull(source)) {
            return null;
        }

        byte[] target = source.toString().getBytes(Constants.ENCODE_CHARSET);

        return valueOf(target, isMultiLine, isUrlSafe);
    }

    /**
     * base64编码，非URL安全的
     *
     * @param source 被编码的base64字符串
     * @return 编码后的字符串
     */
    public static String valueOf(byte[] source) {
        return valueOf(source, false, false);
    }

    /**
     * base64编码，非URL安全的
     *
     * @param source      被编码的base64字符串
     * @param isMultiLine 在76个char之后是CRLF还是EOF
     * @return 编码后的字符串
     */
    public static String valueOf(byte[] source, boolean isMultiLine) {
        return valueOf(source, isMultiLine, false);
    }

    /**
     * base64编码
     *
     * @param source      被编码的base64字符串
     * @param isMultiLine 在76个char之后是CRLF还是EOF
     * @param isUrlSafe   是否使用URL安全字符，一般为<code>false</code>
     * @return 编码后的字符串
     */
    public static String valueOf(byte[] source, boolean isMultiLine, boolean isUrlSafe) {

        if (Objects.isNull(source)) {
            return null;
        }

        byte[] target = encode(source, isMultiLine, isUrlSafe);

        return new String(target, Constants.ENCODE_CHARSET);
    }

    /**
     * 编码为Base64，非URL安全的
     *
     * @param source 被编码的数组
     * @return 编码后的bytes
     */
    public static byte[] encode(byte[] source) {
        return encode(source, false, false);
    }

    /**
     * 编码为Base64，非URL安全的
     *
     * @param source  被编码的数组
     * @param lineSep 在76个char之后是CRLF还是EOF
     * @return 编码后的bytes
     */
    public static byte[] encode(byte[] source, boolean lineSep) {
        return encode(source, lineSep, false);
    }

    /**
     * 编码为Base64<br>
     * 如果isMultiLine为<code>true</code>，则每76个字符一个换行符，否则在一行显示
     *
     * @param source      被编码的数组
     * @param isMultiLine 在76个char之后是CRLF还是EOF
     * @param isUrlSafe   是否使用URL安全字符，一般为<code>false</code>
     * @return 编码后的bytes
     */
    public static byte[] encode(byte[] source, boolean isMultiLine, boolean isUrlSafe) {
        if (null == source) {
            return null;
        }

        int len = source.length;
        if (len == 0) {
            return new byte[0];
        }

        int evenlen = (len / 3) * 3;
        int cnt = ((len - 1) / 3 + 1) << 2;
        int destlen = cnt + (isMultiLine ? (cnt - 1) / 76 << 1 : 0);
        byte[] dest = new byte[destlen];

        byte[] encodeTable = isUrlSafe ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;

        for (int s = 0, d = 0, cc = 0; s < evenlen; ) {
            int i = (source[s++] & 0xff) << 16 | (source[s++] & 0xff) << 8 | (source[s++] & 0xff);

            dest[d++] = encodeTable[(i >>> 18) & 0x3f];
            dest[d++] = encodeTable[(i >>> 12) & 0x3f];
            dest[d++] = encodeTable[(i >>> 6) & 0x3f];
            dest[d++] = encodeTable[i & 0x3f];

            if (isMultiLine && ++cc == 19 && d < destlen - 2) {
                dest[d++] = '\r';
                dest[d++] = '\n';
                cc = 0;
            }
        }

        int left = len - evenlen;// 剩余位数
        if (left > 0) {
            int i = ((source[evenlen] & 0xff) << 10) | (left == 2 ? ((source[len - 1] & 0xff) << 2) : 0);

            dest[destlen - 4] = encodeTable[i >> 12];
            dest[destlen - 3] = encodeTable[(i >>> 6) & 0x3f];

            if (isUrlSafe) {
                // 在URL Safe模式下，=为URL中的关键字符，不需要补充。空余的byte位要去掉。
                int urlSafeLen = destlen - 2;
                if (2 == left) {
                    dest[destlen - 2] = encodeTable[i & 0x3f];
                    urlSafeLen += 1;
                }
                byte[] urlSafeDest = new byte[urlSafeLen];
                System.arraycopy(dest, 0, urlSafeDest, 0, urlSafeLen);
                return urlSafeDest;
            } else {
                dest[destlen - 2] = (left == 2) ? encodeTable[i & 0x3f] : (byte) '=';
                dest[destlen - 1] = '=';
            }
        }
        return dest;
    }
}
