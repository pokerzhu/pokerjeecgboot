package org.jeecg.modules.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.CommodityType;

/**
 * @Description: 商品类型
 * @Author: jeecg-boot
 * @Date:   2019-07-11
 * @Version: V1.0
 */
public interface CommodityTypeMapper extends BaseMapper<CommodityType> {
    /**
     * 查询类型对应的滤芯规格
     * @param typeId
     * @return
     */
    @Select("select specification from commodity_type where type_id = #{typeId}")
    Integer selectspecification(String typeId);
}
