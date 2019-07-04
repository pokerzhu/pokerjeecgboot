package org.jeecg.modules.system.service.impl;
import org.jeecg.modules.system.entity.Filterelement;
import org.jeecg.modules.system.mapper.FilterelementMapper;
import org.jeecg.modules.system.service.IFilterelementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 滤芯表
 * @Author: jeecg-boot
 * @Date:   2019-07-03
 * @Version: V1.0
 */
@Service
public class FilterelementServiceImpl extends ServiceImpl<FilterelementMapper, Filterelement> implements IFilterelementService {

    @Autowired
    private  FilterelementMapper filterelementMapper;
    /**
     * 上传图片
     * @param url
     * @return
     */
    @Override
    public String insertImg(String name, String url, String mimeType, String Suffix, String Created, long Size) {
        return filterelementMapper.insertImg(name,url,mimeType,Suffix,Created,Size);
    }
}
