package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.system.entity.CommodityType;
import org.jeecg.modules.system.entity.Equipment;
import org.jeecg.modules.system.mapper.CommodityTypeMapper;
import org.jeecg.modules.system.mapper.EquipmentDbMapper;
import org.jeecg.modules.system.service.EquipmentDbService;
import org.jeecg.modules.system.service.ICommodityTypeService;
import org.jeecg.modules.system.vo.EquipmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentDbServiceImpI extends ServiceImpl<EquipmentDbMapper,EquipmentVO> implements EquipmentDbService {
    @Autowired
    private EquipmentDbMapper equipmentDbMapper;
    @Override
    public IPage<EquipmentVO> EquipmentDbShow(Page page,String id) {
        return page.setRecords(equipmentDbMapper.EquipmentDbShow(id));
    }
}
