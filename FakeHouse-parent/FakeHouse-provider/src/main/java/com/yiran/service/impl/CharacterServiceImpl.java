package com.yiran.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiran.dao.CharacterMapper;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.Character;
import com.yiran.service.CharacterService;
import com.yiran.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;


@Service(interfaceClass = CharacterService.class)
@Transactional
public class CharacterServiceImpl implements CharacterService {


    @Autowired
    private CharacterMapper characterMapper;


    @Autowired
    private IdWorker idWorker;

    /**
     * 通过id删除
     * @param charaIds id集合
     */
    @Override
    public void deleteByList(List<String> charaIds) {

        characterMapper.deleteByList(charaIds);

    }


    @Override
    public PageResult findPage(QueryPageBean queryPageBean, Integer column) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        String queryString = queryPageBean.getQueryString();

        Page<Character> result = null;
        if (!StringUtils.isEmpty(queryString)){
            // 查询条件不为空
            switch (column) {
                case 1:
                    result =characterMapper.findPageByGameId(queryString);
                    break;
                case 2:
                    result = characterMapper.findPageByDetailGameId(queryString);
                    break;
                default:
                    result =characterMapper.findPageByGameId(queryString);
            }
        }else{
            result =characterMapper.findPage();
        }

        if (result != null){
            return new PageResult(result.getTotal(), result.getResult());
        }

        return null;
    }

    @Override
    public void add(Character character) {

        character.setId(Long.toString(idWorker.nextId()));
        characterMapper.insert(character);

    }

    @Override
    public void updateById(Character character) {

        // 本来应该是要判断id是否存在再进行修改的, 但是对于此处简单的逻辑, 就进行处理
        // 如果id不存在, 会报错; 之后被捕捉后, 及时修改失败
        characterMapper.updateByPrimaryKey(character);
    }

    @Override
    public Character findById(String id) {

        return characterMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteById(String id) {

        // 先要删除关联关系
        characterMapper.disconnectWithDetailGame(id);

        characterMapper.deleteByPrimaryKey(id);
    }


    /**
     * 通过detailGame的id删除其关联的所有 character数据
     * @param detailId  detailGame的id
     */
    @Override
    public void deleteByDetailGameId(String detailId) {

        characterMapper.deleteByDetailGameId(detailId);
    }
}
