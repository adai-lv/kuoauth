package com.kupug.kuoauth.enums;

import java.util.Arrays;
import java.util.Objects;

/**
 * <p>
 * OAuth 平台用户性别选项
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public enum Gender {

    MALE("1", "男"),
    FEMALE("0", "女"),
    UNKNOWN("-1", "保密");

    private String code;
    private String desc;

    Gender(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 获取用户的实际性别
     *
     * @param originalGender 用户第三方标注的原始性别
     * @return 用户性别
     */
    public static Gender getRealGender(String originalGender) {

        if (Objects.nonNull(originalGender)) {
            String[] males = {"1", "m", "male", "男", "男性", "男生"};
            if (Arrays.asList(males).contains(originalGender.toLowerCase())) {
                return MALE;
            }

            String[] females = {"0", "f", "female", "女", "女性", "女生"};
            if (Arrays.asList(females).contains(originalGender.toLowerCase())) {
                return FEMALE;
            }
        }

        return UNKNOWN;
    }
}
