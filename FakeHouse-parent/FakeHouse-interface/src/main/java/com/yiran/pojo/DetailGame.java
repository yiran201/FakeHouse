package com.yiran.pojo;

import java.io.Serializable;
import java.util.List;

public class DetailGame implements Serializable {

    private String id;

    private String introduction;

    // 游戏发行时间
    private String presentTime;

    // 开发商
    private String development;

    // 发行商
    private String deliver;

    // 平台
    private String platform;

    // 游戏版本
    private String version;

    // 文字语言
    private String backgroundLanguage;

    // 语音语言
    private String voiceLanguage;

    // 游戏特色信息, 很有可能为空
    private List<Character> characterList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPresentTime() {
        return presentTime;
    }

    public void setPresentTime(String presentTime) {
        this.presentTime = presentTime;
    }

    public String getDevelopment() {
        return development;
    }

    public void setDevelopment(String development) {
        this.development = development;
    }

    public String getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBackgroundLanguage() {
        return backgroundLanguage;
    }

    public void setBackgroundLanguage(String backgroundLanguage) {
        this.backgroundLanguage = backgroundLanguage;
    }

    public String getVoiceLanguage() {
        return voiceLanguage;
    }

    public void setVoiceLanguage(String voiceLanguage) {
        this.voiceLanguage = voiceLanguage;
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<Character> characterList) {
        this.characterList = characterList;
    }
}