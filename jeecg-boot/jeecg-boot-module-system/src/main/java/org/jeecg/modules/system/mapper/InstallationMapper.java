package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.Client;
import org.jeecg.modules.system.entity.Installation;
import org.jeecg.modules.system.vo.InstallationVO;

import java.util.List;

public interface InstallationMapper extends BaseMapper<Installation> {
    @Select("SELECT o.open_id,c.commodity_name,e.equipment_id \n" +
            "FROM installation_open as o\n" +
            "left join equipment as e on \n" +
            "o.equipment_id=e.equipment_id\n" +
            "left join commodity as c on\n" +
            "e.commodity_id=c.commodity_id\n" +
            "where o.`client_id`=#{clientId}")
    List<InstallationVO> findSelect(@Param("clientId")String clientId);

}
