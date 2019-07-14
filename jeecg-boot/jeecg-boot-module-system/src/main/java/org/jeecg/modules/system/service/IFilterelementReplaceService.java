package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.entity.FilterelementReplace;

import java.util.List;

/**
 * @Description: 滤芯安装记录表
 * @Author: jeecg-boot
 * @Date:   2019-07-12
 * @Version: V1.0
 */
public interface IFilterelementReplaceService extends IService<FilterelementReplace> {
    /**
     * 新增多条滤芯纪录
     * @param list
     * @return
     */
    boolean insertByfilterelementid(List<FilterelementReplace> list);

    /**
     * 删除滤芯记录
     * @param equipmentId
     * @return
     */
    boolean DelEquipmentId(String equipmentId);

}
