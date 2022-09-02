package com.yiran.pojo;

import java.io.Serializable;

public class Character implements Serializable {

    private String id;

    private String chara;

    private Integer score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getChara() {
        return chara;
    }

    public void setChara(String chara) {
        this.chara = chara == null ? null : chara.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}