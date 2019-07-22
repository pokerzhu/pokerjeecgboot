package org.jeecg.modules.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.system.entity.FilterelementReplace;

/**
 * @Description: 滤芯安装记录表
 * @Author: jeecg-boot
 * @Date:   2019-07-12
 * @Version: V1.0
 */
public interface FilterelementReplaceMapper extends BaseMapper<FilterelementReplace> {
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
    @Delete("delete FROM filterelement_replace WHERE equipment_id=#{equipmentId}")
    boolean DelEquipmentId(@Param("equipmentId") String equipmentId);

    /**
     * 更换滤芯
     * @return
     */
    @Update("UPDATE `filterelement_replace` SET `installation_time` = CURRENT_TIMESTAMP ,update_by=#{updateBy} WHERE `record_id` = #{id}")
    boolean UpdfiletereMent(@Param("updateBy") String updateBy,@Param("id")String id);

    /**
     *  判断可用时间已经过期的设备滤芯状态改为异常，并发短信提醒，需要更换滤芯
     * @return
     */
//    @Update("UPDATE `jeecg-boot`.`equipment` SET `filterelement_Type` = 0  " +
//            "WHERE `equipment_id` " +
//            "in (SELECT equipment_id " +
//            "FROM filterelement f,filterelement_replace r WHERE  r.filterelement_id = f.filterelement_id and (f.validity-DATEDIFF(now(),r.installation_time)) <=0)")
    boolean UpdYC();

    /**
     * 判断可用时间已经更新的设备滤芯状态改为正常，并发短信提醒，已经更换滤芯
     * @return
     */
    boolean UpdZC(@Param("equipmentId") String equipmentId);

//    @Select("select e.equipment_id" +
//            "from " +
//            "equipment as e " +
//            "left join commodity as c on e.commodity_id = c.commodity_id " +
//            "left join commodity_type as ct on c.type_id=ct.type_id " +
//            "left join relationship as r  on ct.type_id = r.type_id " +
//            "left join filterelement as f on r.filterelement_id=f.filterelement_id " +
//            "left join filterelement_replace as fr on e.equipment_id=fr.equipment_id " +
//            "where (f.validity*(1-e.lossratio))-DATEDIFF(now(),fr.installation_time)<f.replacementdays")
    /**
     * 查询为空的设备ID
     * @return
     */
    String UpdZT();

    /**
     * 根据记录id得到对应的设备id
     * @param recordId
     * @return
     */
    @Select("select equipment_id from filterelement_replace WHERE record_id=#{recordId} ")
    String findbyid(String recordId);
}