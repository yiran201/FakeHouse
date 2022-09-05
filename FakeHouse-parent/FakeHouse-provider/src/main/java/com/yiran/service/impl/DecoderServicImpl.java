package com.yiran.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yiran.dao.DecoderMapper;
import com.yiran.pojo.Decoder;
import com.yiran.service.DecoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service(interfaceClass = DecoderService.class)
@Transactional
public class DecoderServicImpl implements DecoderService {


    @Autowired
    private DecoderMapper decoderMapper;

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
}
