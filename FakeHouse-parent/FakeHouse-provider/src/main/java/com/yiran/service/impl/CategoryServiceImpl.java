package com.yiran.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiran.dao.CategoryMapper;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.Category;
import com.yiran.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;


@Service(interfaceClass = CategoryService.class)
@Transactional
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 根据游戏id查询分类信息
     * @param gameId 游戏id
     * @return 分类列表
     */
    @Override
    public List<Category> findByGameId(String gameId) {
        return categoryMapper.findByGameId(gameId);
    }


    /**
     * 查询分类信息
     * @return 分类列表
     */
    @Override
    public List<Category> findAll() {

        return categoryMapper.findAll();
    }


    /**
     * 关联游戏-分类关系
     * @param map 数据
     */
    @Override
    public void connectWithGame(Map<String, Object> map) {

        categoryMapper.connectWithGame(map);


    }

    /**
     * 通过游戏id查询分类id
     * @param gameId 游戏id
     * @return
     */
    @Override
    public List<Integer> findIdByGameId(String gameId) {

        return categoryMapper.findIdByGameId(gameId);

    }

    /**
     * 查询分页数据
     * @param queryPageBean 查询条件封装
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        Page<Category> result = null;
        String queryString = queryPageBean.getQueryString();

        if (!StringUtils.isEmpty(queryString)){
            result = categoryMapper.findPageByName("%"+queryString+"%");
        }else{
            result = categoryMapper.findPage();
        }
        if (result != null){
            return new PageResult(result.getTotal(),result.getResult());
        }
        return null;
    }


    /**
     * 添加分类数据
     * @param category 分类数据
     */
    @Override
    public void add(Category category) {
        // id采用数据库自增的方式添加, 所以设置为null
        category.setId(null);
        categoryMapper.insert(category);
    }


    /**
     * 通过id进行查询
     * @param id 分类id
     * @return
     */
    @Override
    public Category findById(Integer id) {

        return categoryMapper.selectByPrimaryKey(id);

    }

    /**
     * 修改分类数据
     * @param category 分类信息
     */
    @Override
    public void update(Category category) {

        categoryMapper.updateByPrimaryKey(category);

    }

    /**
     * 通过id删除
     * @param id 分类id
     */
    @Override
    public void deleteById(Integer id) {

        // 删除中间表数据
        categoryMapper.disconnectWithGame(id);

        // 删除分类数据
        categoryMapper.deleteByPrimaryKey(id);

    }
}
