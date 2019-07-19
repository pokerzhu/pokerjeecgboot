package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.system.entity.Equipment;
import org.jeecg.modules.system.mapper.EquipmentMapper;
import org.jeecg.modules.system.service.IEquipmentService;
import org.jeecg.modules.system.vo.EquipmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 设备表
 * @Author: jeecg-boot
 * @Date:   2019-07-05
 * @Version: V1.0
 */
@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment> implements IEquipmentService {

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Override
    public IPage<EquipmentVO> Equfindselect(Page page) {
        return page.setRecords(equipmentMapper.Equfindselect());
    }

    @Transactional
    @Override
    public boolean updateequipment(Equipment equipment) {
        return equipmentMapper.updateequipment(equipment);
    }
    @Override
    public boolean UpdEquipment(Equipment equipment) {
        boolean bool=equipmentMapper.UpdEquipment(equipment);
        return bool;
    }

    @Override
    public boolean UpdEquipmentClient(Equipment equipment) {
        boolean bool=equipmentMapper.UpdEquipmentClient(equipment);
        return bool;
    }

    @Override
    public IPage<EquipmentVO> SelectByEquId(Page page,String equipmentId) {
        List<EquipmentVO> equipmentVOS = equipmentMapper.SelectByEquId(equipmentId);

        return page.setRecords(equipmentVOS);
    }

    @Override
    public IPage<EquipmentVO> likeEquipment(Page page, String equipmentId, String ids, Integer enabled) {
        return page.setRecords(equipmentMapper.likeEquipment(page,equipmentId,ids,enabled));
    }

    @Override
    public Integer EquipmentById(String clientId) {

        return equipmentMapper.EquipmentById(clientId);
    }

}
