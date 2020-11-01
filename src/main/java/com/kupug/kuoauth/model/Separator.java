package com.kupug.kuoauth.model;

/**
 * <p>
 * 分隔符选项
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public enum Separator {
    SPACE(" ", "空格"),
    COMMA(",", "逗号");

    private String value;
    private String desc;

    Separator(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
