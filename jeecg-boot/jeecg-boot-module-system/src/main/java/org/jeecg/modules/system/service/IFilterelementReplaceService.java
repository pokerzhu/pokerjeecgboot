package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
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
     *
     * @param list
     * @return
     */
    boolean insertByfilterelementid(List<FilterelementReplace> list);

    /**
     * 删除滤芯记录
     *
     * @param equipmentId
     * @return
     */
    boolean DelEquipmentId(String equipmentId);


    /**
     * 更换滤芯
     *
     * @return
     */
    boolean UpdfiletereMent( String updateBy,String id);


    /**
     *  判断可用时间已经过期的设备滤芯状态改为异常，并发短信提醒，需要更换滤芯
     * @return
     */
    boolean UpdYC();

    /**
     * 判断可用时间已经更新的设备滤芯状态改为正常，并发短信提醒，已经更换滤芯
     * @return
     */
    boolean UpdZC(String equipmentId);

    /**
     * 查寻滤芯状态为异常的设备id
     * @return
     */
    String UpdZT();

    /**
     * 根据记录id得到对应的设备id
     * @param recordId
     * @return
     */
    String findbyid(String recordId);
}
