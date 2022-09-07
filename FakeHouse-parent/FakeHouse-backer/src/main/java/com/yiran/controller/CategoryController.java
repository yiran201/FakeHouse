package com.yiran.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yiran.constant.MessageConstant;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.entity.Result;
import com.yiran.pojo.Category;
import com.yiran.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    // 权限列表
    // GAME_CATEGORY_SELECT 通过游戏id查询分类信息


    @Reference
    private CategoryService categoryService;


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('CATEGORY_ADD')")
    public Result add(@RequestBody Category category){

        // 校验分析信息
        try {
            if (checkCategory(category)){
                boolean flag = categoryService.add(category);
                if (flag){
                    return new Result(true, MessageConstant.ADD_CATEGORY_SUCCESS);
                }else{
                    return new Result(false, MessageConstant.CATEGORY_NAME_EXISTS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ADD_CATEGORY_FAIL);
    }



    @GetMapping("/findByGameId")
    @PreAuthorize("hasAuthority('CATEGORY_SELECT')")
    public Result findByGameId(String gameId){

        try {
            if (gameId != null){
                List<Category> categoryList = categoryService.findByGameId(gameId);

                if (categoryList != null && categoryList.size() > 0){
                    return new Result(true, MessageConstant.QUERY_CATEGORY_BY_GAMEID_SUCCESS, categoryList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_CATEGORY_BY_GAMEID_FAIL);
    }

    /**
     * 查询分页数据
     * @param queryPageBean 查询条件封装
     * @return
     */
    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('CATEGORY_SELECT')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean, Integer column){

//        System.out.println(column);
        try {
            if (column == null){
                column = 1;
            }
            if (QueryPageBean.checkQueryPageBean(queryPageBean)){
                PageResult result = categoryService.findPage(queryPageBean, column);
                return new Result(true, MessageConstant.QUERY_CATEGORY_SUCCESS, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_CATEGORY_FAIL);
    }


    /**
     * 通过分类id查询分类数据
     * @param id 分类id
     * @return
     */
    @GetMapping("/findById")
    @PreAuthorize("hasAuthority('CATEGORY_SELECT')")
    public Result findById(Integer id){

        if (id != null){
            Category category = categoryService.findById(id);
            if (category != null){
                return new Result(true, MessageConstant.QUERY_CATEGORY_SUCCESS, category);
            }
        }
        return new Result(false, MessageConstant.QUERY_CATEGORY_FAIL);
    }


    // 出现名字处重复的会导致修改失败和添加失败
    /**
     * 修改分类数据
     * @param category 分类数据
     * @return
     */
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('CATEGORY_UPDATE')")
    public Result update(@RequestBody Category category){

        try {
            if (checkCategory(category) && category.getId() != null){
                boolean flag = categoryService.update(category);
                if (flag) {
                    return new Result(true, MessageConstant.UPDATE_CATEGORY_SUCCESS);
                }else{
                    return new Result(false, MessageConstant.CATEGORY_NAME_EXISTS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.UPDATE_CATEGORY_FAIL);
    }


    /**
     * 通过id删除
     * @param id 分类id
     * @return
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('CATEGORY_DELETE')")
    public Result delete(Integer id){

        try {
            if (id != null){
                categoryService.deleteById(id);
                return new Result(true, MessageConstant.DELETE_CATEGORY_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.DELETE_CATEGORY_FAIL);
    }

    private static boolean checkCategory(Category category) {

        if (category != null){
            String name = category.getName();
            if (!StringUtils.isEmpty(name)){
                return true;
            }
        }
        return false;
    }

}
