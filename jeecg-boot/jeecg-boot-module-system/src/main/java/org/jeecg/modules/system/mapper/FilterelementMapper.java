package org.jeecg.modules.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.Filterelement;
import org.jeecg.modules.system.vo.RelationshipVO;

/**
 * @Description: 滤芯表
 * @Author: jeecg-boot
 * @Date:   2019-07-03
 * @Version: V1.0
 */
public interface FilterelementMapper extends BaseMapper<Filterelement> {
    /**
     *添加图片
     */
    String insertImg(String name, String url, String mimeType,
                     String Suffix, String Created, long Size);

    /**
     * 条件查询该类型能够添加的滤芯
     * @param typeId
     * @return
     */
    @Select("SELECT\n" +
            "filterelement_id,\n" +
            "filterelement_name,\n" +
            "validity,\n" +
            "replacementdays,\n" +
            "Images,\n" +
            "create_by,\n" +
            "create_time,\n" +
            "update_by,\n" +
            "update_time\n" +
            "FROM\n" +
            "filterelement\n" +
            "where filterelement_id not in(\n" +
            "SELECT relationship.filterelement_id\n" +
            "FROM relationship\n" +
            "where relationship.type_id = #{typeId})")
    List<Filterelement> selectbytypeId(String typeId);

    /**
     * 设备更换记录详情
     * @return
     */
    @Select("select r.record_id recordId,filterelement_name filterelementName,validity validity,replacementdays replacementdays,(DATEDIFF(now(),r.installation_time)) Used,(f.validity-DATEDIFF(now(),r.installation_time)) Remaining \n" +
            "FROM filterelement f,filterelement_replace r \n" +
            "WHERE f.filterelement_id=r.filterelement_id \n" +
            "AND r.equipment_id=#{equipmentId}")
    List<RelationshipVO> selectLxXQ(@Param("equipmentId") String equipmentId);
}
