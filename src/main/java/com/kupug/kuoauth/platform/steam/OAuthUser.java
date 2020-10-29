package com.kupug.kuoauth.platform.steam;

import com.fasterxml.jackson.databind.JsonNode;
import com.kupug.kuoauth.KuOAuthUser;
import com.kupug.kuoauth.enums.Gender;
import com.kupug.kuoauth.enums.Platform;
import com.kupug.kuoauth.platform.IOAuthUser;
import com.kupug.kuoauth.utils.JsonUtils;

/**
 * <p>
 * steam oauth user
 * </p>
 *
 * @author MaoHai.LV
 * @since 1.2
 */
final class OAuthUser implements IOAuthUser {

    private String steamId;
    private String personaName;
    private String profileUrl;
    private String avatar;
    private String avatarMedium;
    private String avatarFull;
    private String avatarHash;
    private String primaryClanid;
    private Long timeCreated;
    private Integer personaState;
    private Integer personaStateFlags;
    private Integer communityVisibilityState;

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public String getPersonaName() {
        return personaName;
    }

    public void setPersonaName(String personaName) {
        this.personaName = personaName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarMedium() {
        return avatarMedium;
    }

    public void setAvatarMedium(String avatarMedium) {
        this.avatarMedium = avatarMedium;
    }

    public String getAvatarFull() {
        return avatarFull;
    }

    public void setAvatarFull(String avatarFull) {
        this.avatarFull = avatarFull;
    }

    public String getAvatarHash() {
        return avatarHash;
    }

    public void setAvatarHash(String avatarHash) {
        this.avatarHash = avatarHash;
    }

    public String getPrimaryClanid() {
        return primaryClanid;
    }

    public void setPrimaryClanid(String primaryClanid) {
        this.primaryClanid = primaryClanid;
    }

    public Long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Integer getPersonaState() {
        return personaState;
    }

    public void setPersonaState(Integer personaState) {
        this.personaState = personaState;
    }

    public Integer getPersonaStateFlags() {
        return personaStateFlags;
    }

    public void setPersonaStateFlags(Integer personaStateFlags) {
        this.personaStateFlags = personaStateFlags;
    }

    public Integer getCommunityVisibilityState() {
        return communityVisibilityState;
    }

    public void setCommunityVisibilityState(Integer communityVisibilityState) {
        this.communityVisibilityState = communityVisibilityState;
    }

    @Override
    public String toString() {
        return "OAuthUser{" +
                "steamId='" + steamId + '\'' +
                ", personaName='" + personaName + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", avatar='" + avatar + '\'' +
                ", avatarMedium='" + avatarMedium + '\'' +
                ", avatarFull='" + avatarFull + '\'' +
                ", avatarHash='" + avatarHash + '\'' +
                ", primaryClanid='" + primaryClanid + '\'' +
                ", timeCreated=" + timeCreated +
                ", personaState=" + personaState +
                ", personaStateFlags=" + personaStateFlags +
                ", communityVisibilityState=" + communityVisibilityState +
                '}';
    }

    /**
     * 转化统一的 OAuth User
     *
     * @return KuOAuthUser
     */
    @Override
    public KuOAuthUser valueOf() {
        return KuOAuthUser.builder()
                .openId(this.getSteamId())
                .username(this.getPersonaName())
                .nickname(this.getPersonaName())
                .avatar(this.getAvatarFull())
                .gender(Gender.UNKNOWN)
                .platform(Platform.STEAM.name())
                .rawInfo(JsonUtils.toJSONString(this))
                .build();
    }

    public static KuOAuthUser valueOf(JsonNode players) {

        JsonNode player = players.get(0);

        OAuthUser oAuthUser = new OAuthUser();
        oAuthUser.setSteamId(player.get("steamid").asText());
        oAuthUser.setPersonaName(player.get("personaname").asText());
        oAuthUser.setProfileUrl(player.get("profileurl").asText());
        oAuthUser.setAvatar(player.get("avatar").asText());
        oAuthUser.setAvatarMedium(player.get("avatarmedium").asText());
        oAuthUser.setAvatarFull(player.get("avatarfull").asText());
        oAuthUser.setAvatarHash(player.get("avatarhash").asText());
        oAuthUser.setPrimaryClanid(player.get("primaryclanid").asText());
        oAuthUser.setTimeCreated(player.get("timecreated").asLong());
        oAuthUser.setPersonaState(player.get("personastate").asInt());
        oAuthUser.setPersonaStateFlags(player.get("personastateflags").asInt());
        oAuthUser.setCommunityVisibilityState(player.get("communityvisibilitystate").asInt());

        return oAuthUser.valueOf();
    }
}
