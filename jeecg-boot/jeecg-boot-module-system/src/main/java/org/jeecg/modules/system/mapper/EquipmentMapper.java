package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.vo.EquipmentVO;

import java.util.List;

/**
 * @Description: 设备表
 * @Author: jeecg-boot
 * @Date:   2019-07-05
 * @Version: V1.0
 */
@Mapper
public interface EquipmentMapper extends BaseMapper<EquipmentVO> {
    @Select("select equipment_id,e.commodity_id,commodity_name,\n" +
            "\t\t\t\trealname,client_name,ids,enabled\n" +
            "\t\t\t\tFROM equipment as e\n" +
            "\t\t\t\tleft join commodity as c on e.commodity_id=c.commodity_id\n" +
            "\t\t\t\tleft join sys_user as s on e.user_id=s.id\n" +
            "\t\t\t\tleft join client as l on e.client_id=l.client_id")
    List<EquipmentVO> Equfindselect();

    @Select("")
    List<EquipmentVO> EquAdd();
}
