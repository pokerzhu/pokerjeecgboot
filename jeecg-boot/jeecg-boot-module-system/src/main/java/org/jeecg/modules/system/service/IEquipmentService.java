package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.Equipment;
import org.jeecg.modules.system.entity.Filterelement;
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

    /**
     * 设备客户编辑-何豪
     */
    boolean UpdEquipmentClient(Equipment equipment);

    /**
     * 根据设备Id获取一条信息
     * @param equipmentId
     * @return
     */
    IPage<EquipmentVO> SelectByEquId(Page page,String equipmentId);


    /**
     * 模糊查询
     * @return
     */
    IPage<EquipmentVO> likeEquipment(Page page,String equipmentId,String ids,Integer enabled);

//    /**
//     * 根据硬件编号模糊查询
//     * @param page
//     * @param ids
//     * @return
//     */
//    IPage<EquipmentVO> likeEquipmentIds(Page page,@Param("ids") String ids);
}
