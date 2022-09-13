package com.yiran.entity;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装查询条件
 */
public class GameQueryPageBean implements Serializable{

    private Integer currentPage;//页码
    private Integer pageSize;//每页记录数
    private String queryString;//查询条件


    // 分类名称, 需要给定准确的分类名称
    // 允许为空, 此时不通过分类名称进行筛选
    private String categoryName;

    // 查询字段
    private String queryColumn;

    // 排序字段
    private String orderColumn;

    // 排序方式 升序还是降序
    private String orderType;

    // 是否有效
    private Boolean active;

    // queryColumn的允许取值
    public static final String[] queryColumnList = {"name", "td.name", "development", "deliver",
            "platform", "backgroundLanguage", "voiceLanguage"};

    // orderColumn的允许取值
    public static final String[] orderColumnList = {"insertTime", "uploadTime", "size"};

    // orderType的允许取值
    public static final String[] orderTypeList = {"desc", "asc"};


    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getQueryColumn() {
        return queryColumn;
    }

    public void setQueryColumn(String queryColumn) {
        this.queryColumn = queryColumn;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }


    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public static boolean checkGameQueryPageBean(GameQueryPageBean queryPageBean) {
        // 没有进行分页数据的封装的话,默认不允许其操作
        if (queryPageBean != null)
        {
            Integer currentPage = queryPageBean.getCurrentPage();
            Integer pageSize = queryPageBean.getPageSize();
            String queryColumn = queryPageBean.getQueryColumn();
            String orderColumn = queryPageBean.getOrderColumn();
            String orderType = queryPageBean.getOrderType();

            // 对查询字符串进行处理, 防止出错
            String queryString = queryPageBean.getQueryString();
            String categoryName = queryPageBean.getCategoryName();
            queryPageBean.setQueryString(produceSrr(queryString));
            queryPageBean.setCategoryName(produceSrr(categoryName));

            // 默认不查询无效数据
            Boolean active = queryPageBean.getActive();
            if (active == null){
                queryPageBean.setActive(false);
            }


            if (currentPage == null || currentPage <= 0) {
                queryPageBean.setCurrentPage(1);
            }
            if (pageSize == null || pageSize <= 0) {
                queryPageBean.setPageSize(10);
            }
            if (StringUtils.isEmpty(queryColumn) || !strInList(queryColumn, queryColumnList)){
                // 默认通过名字进行查询
                queryColumn = queryColumnList[0];
                queryPageBean.setQueryColumn(queryColumn);
            }
            if (StringUtils.isEmpty(orderColumn) || !strInList(orderColumn, orderColumnList)){
                // 默认通过插入时间进行排序
                orderColumn = orderColumnList[0];
                queryPageBean.setOrderColumn(orderColumn);
            }
            if (StringUtils.isEmpty(orderType) || !strInList(orderType, orderTypeList)){
                // 默认降序排序
                orderType = orderTypeList[0];
                queryPageBean.setOrderType(orderType);
            }
            return true;
        }

        return false;
    }

    /**
     * 判断str是否存在于对应的取值范围中
     * @param str 目标字符串
     * @param list 取值集合
     * @return true 在; false 不在
     */
    private static boolean strInList(String str, String [] list){
        for (String s : list) {
            if (s.equals(str)){
                return true;
            }
        }
        return false;
    }

    /**
     * 由于不允许str中包含 ? 和  %, 所以需要对其进行处理
     * @param str 需要处理的字符串
     * @return
     */
    private static String produceSrr(String str){
        if(!StringUtils.isEmpty(str)){
            str.replaceAll("%", "");
            str.replaceAll("[?]", "");
            if (StringUtils.isEmpty(str)){
                return null;
            }
            return str;
        }
        return null;
    }



    @Override
    public String toString() {
        return "GameQueryPageBean{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", queryString='" + queryString + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", queryColumn='" + queryColumn + '\'' +
                ", orderColumn='" + orderColumn + '\'' +
                ", orderType='" + orderType + '\'' +
                '}';
    }
}