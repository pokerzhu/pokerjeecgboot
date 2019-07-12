package org.jeecg.modules.system.service.impl;

import org.jeecg.modules.system.entity.CommodityType;
import org.jeecg.modules.system.mapper.CommodityTypeMapper;
import org.jeecg.modules.system.service.ICommodityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 商品类型
 * @Author: jeecg-boot
 * @Date:   2019-07-11
 * @Version: V1.0
 */
@Service
public class CommodityTypeServiceImpl extends ServiceImpl<CommodityTypeMapper, CommodityType> implements ICommodityTypeService {

    @Autowired
    private CommodityTypeMapper commodityTypeMapper;

    @Override
    public Integer selectspecification(String typeId) {
        return commodityTypeMapper.selectspecification(typeId);
    }
}
