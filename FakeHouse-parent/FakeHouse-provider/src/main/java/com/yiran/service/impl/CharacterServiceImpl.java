package com.yiran.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiran.dao.CharacterMapper;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.Character;
import com.yiran.pojo.DetailGame;
import com.yiran.service.CharacterService;
import com.yiran.service.DetailGameService;
import com.yiran.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = CharacterService.class)
@Transactional
public class CharacterServiceImpl implements CharacterService {


    @Autowired
    private CharacterMapper characterMapper;

    @Autowired
    private DetailGameService detailGameService;


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
    public boolean add(Character character, String detailId) {

        // 添加关联关系
        // 查询detailId的对象, 判断是否为空
        DetailGame detailGame = detailGameService.findById(detailId);
        if (detailGame == null){
            return false;
        }
        // 添加游戏详情数据
        String characterId = Long.toString(idWorker.nextId());
        character.setId(characterId);
        characterMapper.insert(character);

        // 添加关联关系
        Map<String, String> map = new HashMap<>(2);
        map.put("detailId", detailId);
        map.put("characterId", characterId);
        characterMapper.connectWithDetailGame(map);

        return true;
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



    /**
     * 通过detailId进行查询
     * @param detailId 游戏详情id
     * @return
     */
    @Override
    public List<Character> findByDetailId(String detailId) {

        return characterMapper.findByDetailGameId(detailId);

    }
}
