package com.yiran.pojo;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Game implements Serializable {

    // 主键
    private String id;
    // 游戏原名
    private String name;
    // 游戏中文名
    private String chsName;

    // 盗版游戏上传时间
    // 排序
    private Date uploadTime;
    // 游戏容量  排序
    private String capacity;

    // 不需要使用该字段  用于排序即可
    private Long size;

    // 原始Url
    private String originUrl;

    // 下载Url
    private String downloadUrl;

    // 链接是否有效
    private Boolean active;

    // 插入时间, 在后端系统有效, 在前端系统无效, 不区分算了, 前后端都有, 但是前段不展示即可
    // 排序
    private Date insertTime;

    // 游戏详情信息
    private String detailId;
    private DetailGame detail;

    // 破解者
    private Integer decoderId;
    private Decoder decoder;

    // 游戏分类列表
    private List<Category> categoryList;

    // 游戏特色信息, 很有可能为空
    private List<Character> characterList;

    // 下载数, 通过recode表可以进行查看
    private Integer downloadCount;

    // 查看数, 通过recode表可以查看
    private Integer watchCount;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChsName() {
        return chsName;
    }

    public void setChsName(String chsName) {
        this.chsName = chsName;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public DetailGame getDetail() {
        return detail;
    }

    public void setDetail(DetailGame detail) {
        this.detail = detail;
    }

    public Integer getDecoderId() {
        return decoderId;
    }

    public void setDecoderId(Integer decoderId) {
        this.decoderId = decoderId;
    }

    public Decoder getDecoder() {
        return decoder;
    }

    public void setDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<Character> characterList) {
        this.characterList = characterList;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Integer getWatchCount() {
        return watchCount;
    }

    public void setWatchCount(Integer watchCount) {
        this.watchCount = watchCount;
    }
}