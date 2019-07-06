package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.entity.Equipment;
import org.jeecg.modules.system.vo.EquipmentVO;

import java.util.List;

/**
 * @Description: 设备表
 * @Author: jeecg-boot
 * @Date:   2019-07-05
 * @Version: V1.0
 */
public interface IEquipmentService extends IService<EquipmentVO> {
    IPage<EquipmentVO> Equfindselect(Page page);
}
