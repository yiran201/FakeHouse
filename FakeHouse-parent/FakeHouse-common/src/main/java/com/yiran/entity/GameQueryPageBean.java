package com.yiran.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 封装查询条件
 */
public class GameQueryPageBean implements Serializable{

    private Integer currentPage;//页码
    private Integer pageSize;//每页记录数
    private String queryString;//查询条件


    // 用于指明queryString对应要查询的字段
    // 默认通过名字进行搜索
    private Integer queryType;
    public static String[] queryTypeList = {"name", "category", "decoder"};

    // 用于指定排序字段
    // int 中的值对应orderTypeList中的值
    // 默认为 通过破解游戏的上传时间进行排序
    private Integer orderType;
    // 游戏大小  对于insertTime, 是给管理员查看使用的, 对于会员没有价值
    public static String[] orderTypeList = {"uploadTime", "size",  "insertTime", "downloadCount", "watchCount"};

    // 排序顺序
    // 默认设置为0, 降序排序
    private Integer order;
    // 升序和降序
    public static String[] orderList = {"desc", "asc"};


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


    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public static boolean checkGameQueryPageBean(GameQueryPageBean queryPageBean) {
        // 没有进行分页数据的封装的话,默认不允许其操作
        if (queryPageBean != null)
        {
            Integer currentPage = queryPageBean.getCurrentPage();
            Integer pageSize = queryPageBean.getPageSize();
            Integer queryType = queryPageBean.getQueryType();
            Integer orderType = queryPageBean.getOrderType();
            Integer order = queryPageBean.getOrder();

            if (currentPage == null || currentPage <= 0) {
                queryPageBean.setCurrentPage(1);
            }
            if (pageSize == null || pageSize <= 0) {
                queryPageBean.setPageSize(10);
            }
            if (queryType == null) {
                queryPageBean.setQueryType(0);
            }
            if (orderType == null){
                queryPageBean.setOrderType(0);
            }
            if (order == null){
                queryPageBean.setOrder(0);
            }
            return true;
        }

        return false;
    }
}