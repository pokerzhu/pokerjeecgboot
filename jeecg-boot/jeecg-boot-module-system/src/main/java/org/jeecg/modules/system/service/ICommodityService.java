package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.Commodity;
import org.jeecg.modules.system.vo.CommodityVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Description: 商品表
 * @Author: jeecg-boot
 * @Date:   2019-07-04
 * @Version: V1.0
 */
public interface ICommodityService extends IService<Commodity> {
    /**
     * 查询商品信息
     * @return
     */
    IPage<CommodityVO> selectCommodity(Page page);

    /**
     * 模糊查询商品
     * @param commodityName
     * @return
     */
    IPage<CommodityVO> selectLike(Page page,@Param("commdityName")String commodityName);
}
