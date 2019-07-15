package org.jeecg.modules.system.service.impl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.system.entity.Filterelement;
import org.jeecg.modules.system.mapper.FilterelementMapper;
import org.jeecg.modules.system.service.IFilterelementService;
import org.jeecg.modules.system.vo.RelationshipVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

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

    @Override
    public IPage<Filterelement> selectbytypeId(String typeId, Page page) {
        return page.setRecords(filterelementMapper.selectbytypeId(typeId));
    }

    @Override
    public IPage<RelationshipVO> selectLxXQ(Page page,String equipmentId) {
        return page.setRecords(filterelementMapper.selectLxXQ(equipmentId));
    }
}
