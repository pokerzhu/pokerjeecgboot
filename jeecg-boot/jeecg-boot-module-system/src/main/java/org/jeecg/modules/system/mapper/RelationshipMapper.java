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
     * 根据商品id查询滤芯
     * @param commodityId
     * @return
     */
    @Select("SELECT r.relationship_id,f.filterelement_id filterelementId, f.filterelement_name filterelementName \n" +
            "FROM relationship r,filterelement f \n" +
            "WHERE r.filterelement_id = f.filterelement_id\n" +
            "and commodity_id = #{commodityId}")
    List<RelationshipVO> selectItemsByMainId(String commodityId);
    /**
     * 根据商品id查询滤芯
     * @param commodityId
     * @return
     */
    @Select("SELECT count(1) FROM relationship\n" +
            "where relationship.commodity_id=#{commodityId}")
    Integer selectBycommodityId(String commodityId);
}
