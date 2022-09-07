package com.yiran.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yiran.dao.DecoderMapper;
import com.yiran.entity.PageResult;
import com.yiran.entity.QueryPageBean;
import com.yiran.pojo.Decoder;
import com.yiran.service.DecoderService;
import com.yiran.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;


@Service(interfaceClass = DecoderService.class)
@Transactional
public class DecoderServicImpl implements DecoderService {


    @Autowired
    private DecoderMapper decoderMapper;

    @Autowired
    private GameService gameService;

    /**
     * 插入数据到decoder表
     * @param decoder decoder信息
     * @return 返回decoder的id
     */
    @Override
    public Integer add(Decoder decoder) {

        if (decoder != null && !StringUtils.isEmpty(decoder.getName())){
            decoder.setId(null);
            // 插入逻辑
            // 先判断 是否存在对应数据
            Integer id = decoderMapper.findDecoderIdByName(decoder.getName());
            if (id != null){
                return id;
            }
            // 插入数据返回主键
            decoderMapper.insertAndGetKey(decoder);

            return decoder.getId();
        }
        return null;
    }


    /**
     * 通过id查询decoder
     * @param decoderId decoder的id
     * @return
     */
    @Override
    public Decoder findById(Integer decoderId) {

        return decoderMapper.selectByPrimaryKey(decoderId);
    }


    /**
     * 分页查询decoder
     * @param queryPageBean 分页和查询条件
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        String queryString = queryPageBean.getQueryString();
        Page<Decoder> result = null;
        if (!StringUtils.isEmpty(queryString)){
            // 查询条件不为空
            result = decoderMapper.findPageByName("%"+queryString+"%");
        }else{
            result = decoderMapper.findPage();
        }

        if (result != null){
            return new PageResult(result.getTotal(), result.getResult());
        }

        return null;
    }



    /**
     * 添加decoder, 并进行校验
     * @param decoder decoder数据
     * @return
     */
    @Override
    public boolean addCheck(Decoder decoder) {

        // 查询是否存在数据
        Integer id = decoderMapper.findDecoderIdByName(decoder.getName());
        // 不知道会不会出现查询到空值, 返回0的情况
        if (id != null){
            return false;
        }else{
            decoderMapper.insert(decoder);
        }
        return true;
    }



    /**
     * 通过id删除decoder
     * @param id decoder的id
     * @return
     */
    @Override
    public boolean deleteById(Integer id) {

        // 如果decoder被关联, 则decoder不能被删除
        // 通过这种方式进行控制其实不太好, 查询效率会有点低, 导致删除逻辑需要耗时比较久
        // 换成count函数会比较好
        Integer count = gameService.findCountByDecoderId(id);
        if (count != null && count > 0){
            return false;
        }else{
            decoderMapper.deleteByPrimaryKey(id);
        }
        return true;
    }



    /**
     * 通过id更新decoder
     * @param decoder 数据和decoder的id
     */
    @Override
    public void updateById(Decoder decoder) {
        decoderMapper.updateByPrimaryKey(decoder);
    }
}
