package org.jeecg.modules.system.service.impl;

import org.jeecg.modules.system.entity.FilterelementReplace;
import org.jeecg.modules.system.mapper.FilterelementReplaceMapper;
import org.jeecg.modules.system.service.IFilterelementReplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 滤芯安装记录表
 * @Author: jeecg-boot
 * @Date:   2019-07-12
 * @Version: V1.0
 */
@Service
public class FilterelementReplaceServiceImpl extends ServiceImpl<FilterelementReplaceMapper, FilterelementReplace> implements IFilterelementReplaceService {

    @Autowired
    private  FilterelementReplaceMapper filterelementReplaceMapper;

    @Override
    public boolean insertByfilterelementid(List<FilterelementReplace> list) {
        return filterelementReplaceMapper.insertByfilterelementid(list);
    }

    @Override
    public boolean DelEquipmentId(String equipmentId) {
        return filterelementReplaceMapper.DelEquipmentId(equipmentId);
    }

}
