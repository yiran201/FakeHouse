package com.yiran.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Member implements Serializable {

    private String id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Boolean isCustomer;

    private Integer downloadCount;

    private Boolean isVip;

    // 观看记录
    private List<Recode> watchRecodes;

    // 下载记录
    private List<Recode> downloadRecodes;

    private Date registerTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Boolean getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(Boolean isCustomer) {
        this.isCustomer = isCustomer;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Boolean getIsVip() {
        return isVip;
    }

    public void setIsVip(Boolean isVip) {
        this.isVip = isVip;
    }

    public Boolean getCustomer() {
        return isCustomer;
    }

    public void setCustomer(Boolean customer) {
        isCustomer = customer;
    }

    public Boolean getVip() {
        return isVip;
    }

    public void setVip(Boolean vip) {
        isVip = vip;
    }

    public List<Recode> getWatchRecodes() {
        return watchRecodes;
    }

    public void setWatchRecodes(List<Recode> watchRecodes) {
        this.watchRecodes = watchRecodes;
    }

    public List<Recode> getDownloadRecodes() {
        return downloadRecodes;
    }

    public void setDownloadRecodes(List<Recode> downloadRecodes) {
        this.downloadRecodes = downloadRecodes;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }
}