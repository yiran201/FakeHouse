package com.yiran.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yiran.constant.MessageConstant;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.entity.Result;
import com.yiran.pojo.Decoder;
import com.yiran.service.DecoderService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.DELETE;

@RestController
@RequestMapping("/decoder")
public class DecoderController {


    @Reference
    private DecoderService decoderService;


    /**
     * 分页查询decoder数据
     * @param queryPageBean 分页和查询条件
     * @return
     */
    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('DECODER_SELECT')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){

        try {
            if (QueryPageBean.checkQueryPageBean(queryPageBean)){
                PageResult result = decoderService.findPage(queryPageBean);
                if (result != null){
                    return new Result(true, MessageConstant.QUERY_DECODER_SUCCESS, result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_DECODER_FAIL);
    }


    /**
     * 添加decoder数据
     * @param decoder  decoder数据
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('DECODER_ADD')")
    public Result add(@RequestBody Decoder decoder) {

        try {
            if (checkDecoder(decoder)){
                boolean flag = decoderService.addCheck(decoder);
                if (flag){
                    return new Result(true, MessageConstant.ADD_DECODER_SUCCESS);
                }else{
                    return new Result(false, MessageConstant.DECODER_EXISTS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(false, MessageConstant.ADD_DECODER_FAIL);
    }


    /**
     * 通过id查询decoder
     * @param id decoder的id
     * @return
     */
    @GetMapping("/findById")
    @PreAuthorize("hasAuthority('DECODER_SELECT')")
    public Result findById(Integer id){
        try {
            if (id != null){
                Decoder decoder = decoderService.findById(id);
                if (decoder != null){
                    return new Result(true, MessageConstant.QUERY_DECODER_SUCCESS, decoder);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_DECODER_FAIL);
    }


    /**
     * 通过id删除decoder
     * @param id decoder的id
     * @return
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('DECODER_DELETE')")
    public Result delete(Integer id){

        try {
            if (id != null){
                boolean flag = decoderService.deleteById(id);
                if (flag){
                    return new Result(true, MessageConstant.DELETE_DECODER_SUCCESS);
                }else{
                    return new Result(false, MessageConstant.DECODER_CONNECTED);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.DELETE_DECODER_FAIL);
    }

    /**
     * 修改decoder数据, 通过id
     * @param decoder decoder信息
     * @return
     */
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('DECODER_UPDATE')")
    public Result update(@RequestBody Decoder decoder){

        try {
            if (checkDecoder(decoder) && decoder.getId() != null){
                decoderService.updateById(decoder);
                return new Result(true, MessageConstant.UPDATE_DECODER_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.UPDATE_DECODER_FAIL);
    }


    private boolean checkDecoder(Decoder decoder) {

        if (decoder != null){
            String name = decoder.getName();
            if (!StringUtils.isEmpty(name)){
                return true;
            }
        }
        return false;
    }

}
