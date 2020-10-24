package com.kupug.kuoauth.platform.qq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kupug.kuoauth.KuOAuthUser;
import com.kupug.kuoauth.platform.IOAuthUser;
import com.kupug.kuoauth.enums.Platform;
import com.kupug.kuoauth.enums.Gender;
import com.kupug.kuoauth.utils.JsonUtils;
import com.kupug.kuoauth.utils.StringUtils;

import java.util.Objects;

/**
 * <p>
 * QQ oauth user
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.0
 */
final class OAuthUser implements IOAuthUser {

    private Integer ret;
    private String msg;
    private Integer isLost;
    private String nickname;
    private String gender;
    private Integer genderType;
    private String province;
    private String city;
    private String year;
    private String constellation;
    private String figureurl;

    @JsonProperty(value = "figureurl_1")
    private String figureurl1;

    @JsonProperty(value = "figureurl_2")
    private String figureurl2;
    private String figureurlQq;

    @JsonProperty(value = "figureurl_qq_1")
    private String figureurlQq1;

    @JsonProperty(value = "figureurl_qq_2")
    private String figureurlQq2;
    private String figureurlType;
    private String isYellowVip;
    private String vip;
    private String yellowVipLevel;
    private String level;
    private String isYellowYearVip;

    public Integer getRet() {
        return ret;
    }

    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getIsLost() {
        return isLost;
    }

    public void setIsLost(Integer isLost) {
        this.isLost = isLost;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getGenderType() {
        return genderType;
    }

    public void setGenderType(Integer genderType) {
        this.genderType = genderType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }

    public String getFigureurl1() {
        return figureurl1;
    }

    public void setFigureurl1(String figureurl1) {
        this.figureurl1 = figureurl1;
    }

    public String getFigureurl2() {
        return figureurl2;
    }

    public void setFigureurl2(String figureurl2) {
        this.figureurl2 = figureurl2;
    }

    public String getFigureurlQq() {
        return figureurlQq;
    }

    public void setFigureurlQq(String figureurlQq) {
        this.figureurlQq = figureurlQq;
    }

    public String getFigureurlQq1() {
        return figureurlQq1;
    }

    public void setFigureurlQq1(String figureurlQq1) {
        this.figureurlQq1 = figureurlQq1;
    }

    public String getFigureurlQq2() {
        return figureurlQq2;
    }

    public void setFigureurlQq2(String figureurlQq2) {
        this.figureurlQq2 = figureurlQq2;
    }

    public String getFigureurlType() {
        return figureurlType;
    }

    public void setFigureurlType(String figureurlType) {
        this.figureurlType = figureurlType;
    }

    public String getIsYellowVip() {
        return isYellowVip;
    }

    public void setIsYellowVip(String isYellowVip) {
        this.isYellowVip = isYellowVip;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getYellowVipLevel() {
        return yellowVipLevel;
    }

    public void setYellowVipLevel(String yellowVipLevel) {
        this.yellowVipLevel = yellowVipLevel;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIsYellowYearVip() {
        return isYellowYearVip;
    }

    public void setIsYellowYearVip(String isYellowYearVip) {
        this.isYellowYearVip = isYellowYearVip;
    }

    @Override
    public String toString() {
        return "OAuthUser{" +
                "isLost=" + isLost +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", genderType=" + genderType +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", year='" + year + '\'' +
                ", constellation='" + constellation + '\'' +
                ", figureurl='" + figureurl + '\'' +
                ", figureurl1='" + figureurl1 + '\'' +
                ", figureurl2='" + figureurl2 + '\'' +
                ", figureurlQq='" + figureurlQq + '\'' +
                ", figureurlQq1='" + figureurlQq1 + '\'' +
                ", figureurlQq2='" + figureurlQq2 + '\'' +
                ", figureurlType='" + figureurlType + '\'' +
                ", isYellowVip='" + isYellowVip + '\'' +
                ", vip='" + vip + '\'' +
                ", yellowVipLevel='" + yellowVipLevel + '\'' +
                ", level='" + level + '\'' +
                ", isYellowYearVip='" + isYellowYearVip + '\'' +
                '}';
    }

    /**
     * 转化统一的 OAuth User
     *
     * @return KuOAuthUser
     */
    @Override
    public KuOAuthUser valueOf() {

        String avatarUrl = StringUtils.isEmpty(this.getFigureurl2())
                ? this.getFigureurl1()
                : this.getFigureurl2();

        String location = String.format("%s-%s", this.getProvince(), this.getCity());
        Gender gender = Objects.isNull(this.getGenderType())
                ? Gender.UNKNOWN
                : Gender.getRealGender(this.genderType.toString());

        return KuOAuthUser.builder()
                .username(this.getNickname())
                .nickname(this.getNickname())
                .avatar(avatarUrl)
                .location(location)
                .gender(gender)
                .platform(Platform.QQ.name())
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }
}
