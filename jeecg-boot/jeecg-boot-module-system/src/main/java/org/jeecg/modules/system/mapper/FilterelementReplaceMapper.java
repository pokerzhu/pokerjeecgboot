package org.jeecg.modules.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}