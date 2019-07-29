package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.Equipment;
import org.jeecg.modules.system.vo.CommodityDiagramVo;
import org.jeecg.modules.system.vo.DailyVO;
import org.jeecg.modules.system.vo.DiagramVo;

import java.util.Date;
import java.util.List;

public interface SysAnalysisMapper extends BaseMapper<Equipment> {
    /**
     * 查询设备数量
     * @return
     */
    @Select("SELECT COUNT(equipment_id) FROM equipment")
    Integer SelectByCount();

    /**
     * 查询异常设备数量
     * @return
     */
    @Select("SELECT COUNT(equipment_id) FROM equipment WHERE filterelement_Type=0")
    Integer SelectByYcCount();

    /**
     * 查询每个代理有多少设备
     * @return
     */
    @Select("SELECT s.id,s.depart_name as name,COUNT(equipment_id) as count FROM equipment e,sys_depart s WHERE e.user_id=s.id GROUP BY s.id")
    List<DiagramVo> SelectByUserId();

    /**
     * 查询所有代理商
     * @return
     */
    @Select("SELECT id,depart_name as name FROM sys_depart GROUP BY depart_name")
    List<DiagramVo> SelectName();
    /**
     * 查询预计下周异常设备
     * @return
     */
    @Select("select fr.equipment_id\n" +
            "from\n" +
            "equipment as e\n" +
            "left join commodity as c on e.commodity_id = c.commodity_id\n" +
            "left join commodity_type as ct on c.type_id=ct.type_id\n" +
            "left join relationship as r  on ct.type_id = r.type_id\n" +
            "left join filterelement as f on r.filterelement_id=f.filterelement_id\n" +
            "left join filterelement_replace as fr on e.equipment_id=fr.equipment_id\n" +
            "where (f.validity*(1-e.lossratio))-DATEDIFF(#{time},fr.installation_time)<f.replacementdays GROUP BY fr.equipment_id")
    List<String> SelectNextWeek(@Param("time")String time);

    /**
     * 查询下周异常设备异常时间和数量
     * @return
     */
    List<DailyVO> SelectDaily();

    /**
     * 查询设备安装比例
     * @return
     */
    @Select("SELECT CONCAT(ROUND((SELECT COUNT(equipment_id) FROM equipment WHERE client_id IS NOT NULL)/(select count(equipment_id) FROM equipment)*100))")
    String SelectPercentage();

    /**
     * 查询每种商品多少设备
     * @return
     */
    @Select("SELECT c.commodity_id as id,commodity_name as name,COUNT(equipment_id) as count FROM equipment e,commodity c WHERE e.commodity_id=c.commodity_id  GROUP BY  c.commodity_id")
    List<CommodityDiagramVo> SelectCommodity();

    /**
     *  查询商品Id和对应的名称
     * @return
     */
    @Select("SELECT commodity_id as id,commodity_name as name FROM commodity")
    List<CommodityDiagramVo> SelectCommodityName();
}
