package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.Equipment;
import org.jeecg.modules.system.vo.EquipmentVO;
import org.jeecg.modules.system.vo.equipmentDBVO;

import java.util.List;


public interface EquipmentDbMapper extends BaseMapper<EquipmentVO> {
    /**
     * 根据id查询
     * @param page
     * @param id
     * @return
     */
    @Select("select user_id,equipment_id,e.commodity_id,commodity_name,\n" +
            "s.depart_name departname,client_name,ids,enabled,leasestate,mainboard,filterelement_Type,commodity_prices,commodity_rent\n" +
            "FROM equipment as e\n" +
            "left join commodity as c on e.commodity_id=c.commodity_id\n" +
            "left join sys_depart as s on e.user_id=s.id\n" +
            "left join client as l on e.client_id=l.client_id\n" +
            "WHERE e.user_id =(SELECT id FROM sys_depart WHERE org_code=#{id})")
    List<EquipmentVO> EquipmentDbShow(@Param("id") String id);

    /**
     * 根据部门id批量更新设备信息
     * @param userId
     * @param list
     * @return
     */
    boolean updateuserid(@Param("userId") String userId, @Param("list") List<equipmentDBVO> list);

    /**
     * 根据登陆用户
     * @return
     */
    @Select("SELECT id FROM sys_depart WHERE org_code= #{orgCode}")
    String selectid (String orgCode);



    /**
     * 模糊查询
     * @return
     */
    List<EquipmentVO> likeEquipmentDb(Page page,@Param("equipmentVO") EquipmentVO equipmentVO);
}
