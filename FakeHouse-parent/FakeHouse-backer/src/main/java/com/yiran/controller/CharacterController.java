package com.yiran.controller;


import com.yiran.constant.MessageConstant;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.entity.Result;
import com.yiran.pojo.Character;
import com.yiran.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/character")
public class CharacterController {



    @Autowired
    private CharacterService characterService;



    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('CHARACTER_SELECT')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean, Integer column){

        try {
            if (QueryPageBean.checkQueryPageBean(queryPageBean)) {
                if (column == null){
                    column = 1;
                }
                PageResult result = characterService.findPage(queryPageBean, column);
                return new Result(true, MessageConstant.QUERY_CHARACTER_SUCCESS, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_CHARACTER_FAIL);
    }


    // 不允许进行特色的添加, 因为业务逻辑, 不允许其在此处添加
//    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('CHARACTER_ADD')")
    public Result findPage(@RequestBody Character character){

        try {
            if (checkCharacter(character)){
                // 不用担心会出现重复的情况, 就算重复了也没有关系
                // 所以添加是不需要校验
                characterService.add(character);
                return new Result(true, MessageConstant.ADD_CHARACTER_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ADD_CHARACTER_FAIL);
    }


    @PutMapping("/update")
    @PreAuthorize("hasAuthority('CHARACTER_UPDATE')")
    public Result update(@RequestBody Character character){

        try {
            if (checkCharacter(character) && !StringUtils.isEmpty(character.getId())){
                characterService.updateById(character);
                return new Result(true, MessageConstant.UPDATE_CHARACTER_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.UPDATE_CHARACTER_FAIL);
    }


    @PostMapping("/findById")
    @PreAuthorize("hasAuthority('CHARACTER_SELECT')")
    public Result findById(String id){

        try {
            if (!StringUtils.isEmpty(id)) {
                Character character = characterService.findById(id);
                return new Result(true, MessageConstant.QUERY_CHARACTER_SUCCESS, character);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_CHARACTER_FAIL);
    }


    @DeleteMapping("/deleteById")
    @PreAuthorize("hasAuthority('CHARACTER_DELETE')")
    public Result delete(String id){

        try {
            if (!StringUtils.isEmpty(id)) {
                characterService.deleteById(id);
                return new Result(true, MessageConstant.DELETE_CHARACTER_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.DELETE_CHARACTER_FAIL);
    }



    private static boolean checkCharacter(Character character) {


        if (character != null){
            String chara = character.getChara();
            Integer score = character.getScore();
            if (!StringUtils.isEmpty(chara) && score != null){
                return true;
            }
        }
        return false;

    }


}
