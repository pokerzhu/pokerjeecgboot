package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.system.entity.Equipment;
import org.jeecg.modules.system.vo.EquipmentVO;

import java.util.List;

/**
 * @Description: 设备表_何豪
 * @Author: jeecg-boot
 * @Date:   2019-07-05
 * @Version: V1.0
 */
public interface IEquipmentService extends IService<Equipment> {
    /**
     * 设备查询_何豪
     * @param page
     * @return
     */
    IPage<EquipmentVO> Equfindselect(Page page);
    /**
     * 更新设备客户_张文
     * @param equipment
     * @return
     */
    boolean updateequipment(Equipment equipment);
    /**
     * 设备编辑_何豪
     * @param equipment
     * @return
     */
    boolean UpdEquipment(Equipment equipment);
}
