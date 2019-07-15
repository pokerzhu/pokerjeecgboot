package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.vo.EquipmentVO;


public interface EquipmentDbService extends IService<EquipmentVO> {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    IPage<EquipmentVO> EquipmentDbShow(Page page, String id);
}
