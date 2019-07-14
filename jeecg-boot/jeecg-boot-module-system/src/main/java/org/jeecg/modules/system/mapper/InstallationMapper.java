package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.jeecg.modules.system.entity.Client;
import org.jeecg.modules.system.entity.Installation;
import org.jeecg.modules.system.vo.InstallationVO;

import java.util.List;

public interface InstallationMapper extends BaseMapper<Installation> {
    @Select("SELECT o.open_id,o.open,c.commodity_name,e.equipment_id \n" + "FROM installation_open as o\n" +
            "left join equipment as e on o.equipment_id=e.equipment_id\n" +
            "left join commodity as c on e.commodity_id=c.commodity_id\n" +
            "where o.`client_id`=#{clientId}")
    List<InstallationVO> findSelect(@Param("clientId") String clientId);

    /*
    UPDATE `jeecg-boot`.`installation_open` SET `client_id` = '2', `equipment_id` = '008', `open` = 1, `create_by` = '何豪', `create_time` = '2019-07-08 16:43:58', `update_by` = '何豪', `update_time` = '2019-07-08 16:44:03' WHERE `open_id` = '002';
     */

    /**
     * 修改设备当前状态
     * @param
     * @return
     */
    @Update("UPDATE `installation_open` SET `open` = #{open} WHERE `open_id` = #{openId}")
    boolean Updeteopen(Installation installation);


    /**
     * 删除
     */
    @Delete("DELETE FROM `installation_open` WHERE `equipment_id` = #{equipmentId}")
    boolean DeleteInstall(@Param("equipmentId") String equipmentId);

    /**
     * 客户设备添加
     */
    @Insert("INSERT INTO `installation_open` (open_id,`client_id`, `equipment_id`,`create_by`, `create_time`, `update_by`)\n" +
            "\tVALUES \n" + "\t(#{openId},#{clientId},#{equipmentId},#{createBy},CURRENT_TIMESTAMP,#{updateBy});")
    boolean EquAdd(Installation installation);


}
