package org.jeecg.modules.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.Commodity;
import org.jeecg.modules.system.vo.CommodityVO;

import java.util.List;

/**
 * @Description: 商品表
 * @Author: jeecg-boot
 * @Date:   2019-07-04
 * @Version: V1.0
 */
public interface CommodityMapper extends BaseMapper<Commodity> {
    /**
     * 查询商品信息
     * @return
     */
    @Select("SELECT\n" +
            "c.commodity_id commodityId,\n" +
            "c.commodity_name commodityName,\n" +
            "c.commodity_prices commodityPrices,\n" +
            "c.commodity_rent commodityRent,\n" +
            "c.description description,\n" +
            "c.Images images,\n" +
            "c.type_id typeId,\n" +
            "c.create_by createBy,\n" +
            "c.create_time createTime,\n" +
            "c.update_by updateBy,\n" +
            "c.update_time updateTime,\n" +
            "t.type_name typeName\n" +
            "FROM\n" +
            "commodity c,\n" +
            "commodity_type t\n" +
            "where c.type_id=t.type_id")
    List<CommodityVO> selectCommodity();
}
