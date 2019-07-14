package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.Relationship;
import org.jeecg.modules.system.vo.RelationshipVO;

import java.util.List;

/**
 * @Description: 商品滤芯关联
 * @Author: jeecg-boot
 * @Date:   2019-07-05
 * @Version: V1.0
 */
public interface RelationshipMapper extends BaseMapper<Relationship> {
    /**
     * 根据类型id查询滤芯
     * @param commodityId
     * @return
     */
    @Select("SELECT r.relationship_id,f.filterelement_id filterelementId, f.filterelement_name filterelementName,f.validity,f.replacementdays  " +
            "FROM relationship r,filterelement f \n" +
            "WHERE r.filterelement_id = f.filterelement_id\n" +
            "and r.type_id = #{commodityId}")//e19793c6d89f65db88fb517416169fb8
    List<RelationshipVO> selectItemsByMainId(String commodityId);

    /**
     * 根据类型id查询滤芯
     * @param commodityId
     * @return
     */
    @Select("SELECT count(1) FROM relationship\n" +
            "where relationship.type_id=#{commodityId}")
    Integer selectBycommodityId(String commodityId);

    /**
     * 根据类型id删除类型对应的滤芯记录
     * @param typeId
     * @return
     */
    @Select("delete FROM relationship where relationship.type_id=#{commodityId}")
    void deletetypeId(String typeId);

    /**
     * 添加类型对应的滤芯
     * @param list
     * @return
     */
    boolean insertByfilterelementid(List<Relationship> list);
}
