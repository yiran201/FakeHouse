package com.yiran.entity;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 封装查询条件
 */
public class QueryPageBean implements Serializable{
    private Integer currentPage;//页码
    private Integer pageSize;//每页记录数
    private String queryString;//查询条件

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


    public static boolean checkQueryPageBean(QueryPageBean queryPageBean) {
        // 没有进行分页数据的封装的话,默认不允许其操作
        if (queryPageBean != null)
        {
            Integer currentPage = queryPageBean.getCurrentPage();
            Integer pageSize = queryPageBean.getPageSize();

            String queryString = queryPageBean.getQueryString();
            queryPageBean.setQueryString(produceSrr(queryString));

            if (currentPage == null || currentPage <= 0)
            {
                queryPageBean.setCurrentPage(1);
            }
            if (pageSize == null || pageSize <= 0)
            {
                queryPageBean.setPageSize(10);
            }
            return true;
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

}
