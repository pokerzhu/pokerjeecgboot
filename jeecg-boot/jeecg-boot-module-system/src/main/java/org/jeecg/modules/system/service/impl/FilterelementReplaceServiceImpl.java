package org.jeecg.modules.system.service.impl;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.system.entity.Client;
import org.jeecg.modules.system.entity.FilterelementReplace;
import org.jeecg.modules.system.mapper.FilterelementReplaceMapper;
import org.jeecg.modules.system.service.IFilterelementReplaceService;
import org.jeecg.modules.system.vo.EquipmentVO;
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

    @Override
    public boolean UpdfiletereMent(String updateBy, String id) {
        return filterelementReplaceMapper.UpdfiletereMent(updateBy,id);
    }

    @Override
    public boolean UpdYC() {
        System.out.println("滤芯状态异常");
        return filterelementReplaceMapper.UpdYC();
}

    @Override
    public boolean UpdZC(String equipmentId) {
        return filterelementReplaceMapper.UpdZC(equipmentId);
    }

    @Override
    public List<EquipmentVO> UpdZT() {
        return filterelementReplaceMapper.UpdZT();
    }

    @Override
    public String findbyid(String recordId) {
        return filterelementReplaceMapper.findbyid(recordId);
    }

    @Override
    public List<Client> findbyequipmentid(List<String> equipmentids) {
        return filterelementReplaceMapper.findbyequipmentid(equipmentids);
    }
}
