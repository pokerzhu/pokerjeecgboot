package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.system.entity.Equipment;
import org.jeecg.modules.system.entity.Relationship;
import org.jeecg.modules.system.vo.EquipmentVO;

import java.util.List;

/**
 * @Description: 设备表_何豪
 * @Author: jeecg-boot
 * @Date:   2019-07-05
 * @Version: V1.0
 */
@Mapper
public interface EquipmentMapper extends BaseMapper<Equipment> {
    /**
     * 查询设备_何豪
     * @return
     */
    @Select("select user_id,equipment_id,e.commodity_id,commodity_name,\n" +
            "s.depart_name departname,client_name,ids,enabled,leasestate," +
            "mainboard,filterelement_Type,commodity_prices,commodity_rent,lossratio \n" +
            "FROM equipment as e\n" +
            "left join commodity as c on e.commodity_id=c.commodity_id\n" +
            "left join sys_depart as s on e.user_id=s.id\n" +
            "left join client as l on e.client_id=l.client_id")
    List<EquipmentVO> Equfindselect();

    /**
     * 查询设备_何豪
     * @return
     */
    @Select("select user_id,equipment_id,e.commodity_id,commodity_name,\n" +
            "s.depart_name departname,client_name,ids,enabled,leasestate," +
            "mainboard,filterelement_Type,commodity_prices,commodity_rent,lossratio \n" +
            "FROM equipment as e\n" +
            "left join commodity as c on e.commodity_id=c.commodity_id\n" +
            "left join sys_depart as s on e.user_id=s.id\n" +
            "left join client as l on e.client_id=l.client_id" +
            "where e.equipment_id LIKE concat('%','#{equipmentId}','%') AND" +
            "ids LIKE concat('%','#{ids}','%') AND " +
            "enabled LIKE concat('%','#{enabled}','%')")
    List<EquipmentVO> Equfindselectby();

    /**
     * 更新设备客户_张文
     * @param equipment
     * @return
     */
    @Update("UPDATE `equipment` SET `client_id`= #{clientId} WHERE `equipment_id` = #{equipmentId}")
    boolean updateequipment(Equipment equipment);

    /**
     * 设备编辑_何豪
     * @param equipment
     * @return
     */
    @Update("UPDATE `jeecg-boot`.`equipment` SET `commodity_id` = #{commodityId},`user_id` = #{userId},`ids` = #{ids}," +
            "`enabled` = #{enabled},`leasestate`=#{leasestate} WHERE `equipment_id` = #{equipmentId};")
            boolean UpdEquipment(Equipment equipment);

    /**
     * 设备客户编辑-何豪
     */
    @Update("UPDATE `jeecg-boot`.`equipment` SET  `client_id` = #{clientId},lossratio = #{lossratio} WHERE `equipment_id` = #{equipmentId}")
    boolean UpdEquipmentClient(Equipment equipment);

    /**
     * 查出设备中的类型id
     */
    @Select("select type_id  from commodity WHERE commodity_id=#{equipmentId}")
    Equipment SelectEqu();

    /**
     * 查出滤芯关系表中的滤芯编号
     */
    @Select("select filterelement_id FROM relationship WHERE type_id=#{type_id}")
    Relationship SelectRel();

    /**
     * 根据设备Id获取一条信息
     * @param equipmentId
     * @return
     */
    @Select("select user_id,equipment_id,e.commodity_id,commodity_name,\n" +
            "s.depart_name departname,client_name,ids,enabled,leasestate,mainboard," +
            "filterelement_Type,commodity_prices,commodity_rent,e.lossratio\n" +
            "FROM equipment as e\n" +
            "left join commodity as c on e.commodity_id=c.commodity_id\n" +
            "left join sys_depart as s on e.user_id=s.id\n" +
            "left join client as l on e.client_id=l.client_id WHERE equipment_id=#{equipmentId}")
    List<EquipmentVO> SelectByEquId(@Param("equipmentId") String equipmentId);

    /**
     * 设备模糊查询
     *
     *  AND" +
     *             "ids LIKE concat('%','#{ids}','%') AND " +
     *             "enabled LIKE concat('%','#{enabled}','%')
     * @return
     */
//    @Select("select user_id,equipment_id,e.commodity_id,commodity_name,s.depart_name departname,client_name,ids, " +
//            "enabled,leasestate,mainboard,filterelement_Type,commodity_prices,commodity_rent " +
//            "FROM equipment as e  " +
//            "left join commodity as c on e.commodity_id=c.commodity_id  " +
//            "left join sys_depart as s on e.user_id=s.id " +
//            "left join client as l on e.client_id=l.client_id " +
//            "WHERE e.equipment_id LIKE concat('%',#{equipmentId},'%') AND ids LIKE concat('%',#{ids},'%') and enabled LIKE concat('%',#{enabled},'%')")
    List<EquipmentVO> likeEquipment(Page page,@Param("equipmentId") String equipmentId,@Param("ids") String ids,@Param("enabled") Integer enabled);

    /**
     * 设备模糊查询
     *
     *  AND" +
     *             "ids LIKE concat('%','#{ids}','%') AND " +
     *             "enabled LIKE concat('%','#{enabled}','%')
     * @return
//     */
//    @Select("select user_id,equipment_id,e.commodity_id,commodity_name,s.depart_name departname,client_name,ids, " +
//            "enabled,leasestate,mainboard,filterelement_Type,commodity_prices,commodity_rent " +
//            "FROM equipment as e  " +
//            "left join commodity as c on e.commodity_id=c.commodity_id  " +
//            "left join sys_depart as s on e.user_id=s.id " +
//            "left join client as l on e.client_id=l.client_id " +
//            "WHERE e.ids LIKE concat('%',#{ids},'%')")
//    List<EquipmentVO> likeEquipmentIds(Page page,@Param("ids") String ids);
}
