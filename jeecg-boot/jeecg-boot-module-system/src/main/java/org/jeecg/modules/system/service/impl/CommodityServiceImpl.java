package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.system.entity.Commodity;
import org.jeecg.modules.system.mapper.CommodityMapper;
import org.jeecg.modules.system.service.ICommodityService;
import org.jeecg.modules.system.vo.CommodityVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 商品表
 * @Author: jeecg-boot
 * @Date:   2019-07-04
 * @Version: V1.0
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {

    @Autowired
    private  CommodityMapper commodityMapper;

    /**
     * 查询商品信息
     * @param page
     * @return
     */
    @Override
    public IPage<CommodityVO> selectCommodity(Page page) {
        return page.setRecords(commodityMapper.selectCommodity());
    }
}
