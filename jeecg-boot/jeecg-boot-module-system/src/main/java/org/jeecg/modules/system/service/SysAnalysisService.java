package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.Equipment;
import org.jeecg.modules.system.vo.CommodityDiagramVo;
import org.jeecg.modules.system.vo.DailyVO;
import org.jeecg.modules.system.vo.DiagramVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface SysAnalysisService extends IService<Equipment> {
    /**
     * 查询设备数量
     * @return
     */
    Integer SelectByCount();

    /**
     * 查询异常设备数量
     * @return
     */
    Integer SelectByYcCount();

    /**
     * 查询每个代理有多少设备
     * @return
     */
    List<DiagramVo> SelectByUserId();
    /**
     * 查询预计下周异常设备
     * @return
     */
    List<String> SelectNextWeek();

    /**
     * 查询下周每天过期设备时间和数量
     * @return
     */
    List<DailyVO> SelectDaily();

    /**
     * 查询设备安装比例
     * @return
     */
    String SelectPercentage();

    /**
     * 查询每种商品多少设备
     * @return
     */
    List<CommodityDiagramVo> SelectCommodity();
}
