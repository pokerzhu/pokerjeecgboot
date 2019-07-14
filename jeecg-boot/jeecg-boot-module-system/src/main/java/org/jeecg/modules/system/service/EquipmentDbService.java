package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.system.entity.Equipment;
import org.jeecg.modules.system.vo.EquipmentVO;
import org.springframework.stereotype.Service;

public interface EquipmentDbService extends IService<EquipmentVO> {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    IPage<EquipmentVO> EquipmentDbShow(Page page, String id);
}
