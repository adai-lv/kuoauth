package com.kupug.kuoauth.enums;

import com.kupug.kuoauth.KuOAuthPlatform;
import com.kupug.kuoauth.platform.aliyun.AliyunPlatform;
import com.kupug.kuoauth.platform.baidu.BaiduPlatform;
import com.kupug.kuoauth.platform.dingtalk.DingtalkPlatform;
import com.kupug.kuoauth.platform.facebook.FacebookPlatform;
import com.kupug.kuoauth.platform.gitee.GiteePlatform;
import com.kupug.kuoauth.platform.github.GithubPlatform;
import com.kupug.kuoauth.platform.google.GooglePlatform;
import com.kupug.kuoauth.platform.qq.QqPlatform;
import com.kupug.kuoauth.platform.wechat.WechatMpPlatform;
import com.kupug.kuoauth.platform.wechat.WechatOpPlatform;
import com.kupug.kuoauth.platform.weibo.WeiboPlatform;

/**
 * <p>
 * OAuth Platform 平台
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
public enum Platform {

    GOOGLE(GooglePlatform.class, "google平台"),
    FACEBOOK(FacebookPlatform.class, "facebook平台"),
    BAIDU(BaiduPlatform.class, "百度平台"),

    WECHATMP(WechatMpPlatform.class, "微信公众号平台"),
    WECHAT(WechatOpPlatform.class,"微信开放平台"),
    QQ(QqPlatform.class, "QQ平台"),

    ALIYUN(AliyunPlatform.class, "阿里云平台"),
    DINGTALK(DingtalkPlatform.class, "钉钉平台"),

    WEIBO(WeiboPlatform.class, "新浪微博平台"),
    GITHUB(GithubPlatform.class, "github平台"),
    GITEE(GiteePlatform.class, "gitee平台");

    private Class<? extends KuOAuthPlatform> clazz;
    private String desc;

    Platform(Class<? extends KuOAuthPlatform> clazz, String desc) {
        this.clazz = clazz;
        this.desc = desc;
    }

    public Class<? extends KuOAuthPlatform> getClazz() {
        return clazz;
    }

    public String getDesc() {
        return desc;
    }
}
