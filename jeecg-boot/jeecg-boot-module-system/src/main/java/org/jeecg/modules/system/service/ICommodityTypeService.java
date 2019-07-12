package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.entity.CommodityType;

/**
 * @Description: 商品类型
 * @Author: jeecg-boot
 * @Date:   2019-07-11
 * @Version: V1.0
 */
public interface ICommodityTypeService extends IService<CommodityType> {
    /**
     * 查询类型对应的滤芯规格
     * @param typeId
     * @return
     */
    Integer selectspecification(String typeId);

}
