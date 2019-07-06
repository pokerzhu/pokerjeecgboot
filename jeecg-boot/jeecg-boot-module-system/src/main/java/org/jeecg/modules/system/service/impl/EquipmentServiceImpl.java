package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.system.mapper.EquipmentMapper;
import org.jeecg.modules.system.service.IEquipmentService;
import org.jeecg.modules.system.vo.EquipmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 设备表
 * @Author: jeecg-boot
 * @Date:   2019-07-05
 * @Version: V1.0
 */
@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper,EquipmentVO> implements IEquipmentService {

    @Autowired
    private EquipmentMapper equipmentMapper;
    @Override
    public IPage<EquipmentVO> Equfindselect(Page page) {
        return page.setRecords(equipmentMapper.Equfindselect());
    }
}
