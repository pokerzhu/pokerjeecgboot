package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.system.entity.Commodity;
import org.jeecg.modules.system.entity.Equipment;
import org.jeecg.modules.system.mapper.CommodityMapper;
import org.jeecg.modules.system.mapper.SysAnalysisMapper;
import org.jeecg.modules.system.service.ICommodityService;
import org.jeecg.modules.system.service.SysAnalysisService;
import org.jeecg.modules.system.vo.CommodityDiagramVo;
import org.jeecg.modules.system.vo.DailyVO;
import org.jeecg.modules.system.vo.DiagramVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SysAnalysisServiceImpI extends ServiceImpl<SysAnalysisMapper, Equipment> implements SysAnalysisService {
    /*注入Mapper层*/
    @Autowired
    private SysAnalysisMapper sysAnalysisMapper;

    /**
     * 查询设备数量
     * @return
     */
    @Override
    public Integer SelectByCount() {
        return sysAnalysisMapper.SelectByCount();
    }

    /**
     * 查询异常设备数量
     * @param
     * @return
     */
    @Override
    public Integer SelectByYcCount() {
        return sysAnalysisMapper.SelectByYcCount();
    }

    /**\
     * 查询每个代理有多少设备
     * @return
     */
    @Override
    public List<DiagramVo> SelectByUserId() {
        List<DiagramVo> equCount=sysAnalysisMapper.SelectByUserId();
        List<DiagramVo> equName=sysAnalysisMapper.SelectName();
        for (int i=0;i<equCount.size();i++){
            for (int j=0;j<equName.size();j++){
                if (equCount.get(i).getId().equals(equName.get(j).getId())){
                    equName.get(j).setCount(equCount.get(i).getCount());
                }
            }
        }
        return equName;
    }

    /**
     * 查询下周周末过期设备
     * @return
     */
    @Override
    public List<String> SelectNextWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        //Calendar  java用于时间运算的工具类
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        //13,代表下周周末，
        cal.add(Calendar.DATE, 13);//8-04
        return sysAnalysisMapper.SelectNextWeek(sdf.format(cal.getTime()));
    }

    /**
     * 查询下周每天过期设备
     * @return
     */
    @Override
    public List<DailyVO> SelectDaily() {
        List<DailyVO> list=new ArrayList<DailyVO>();
        Date date = new Date();
        for(int day=7;day<=13;day++){
            Calendar cal = Calendar.getInstance();
            cal.setTime(getThisWeekMonday(date));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.DATE, day);//获取下周周一到周末时间
            DailyVO dv=new DailyVO();
            dv.setTime(sdf.format(cal.getTime()));
            List<String> time= sysAnalysisMapper.SelectNextWeek(sdf.format(cal.getTime()));
            dv.setCount(time.size());
            list.add(dv);
        }
        return list;
    }

    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }
    @Override
    public String SelectPercentage() {
        return sysAnalysisMapper.SelectPercentage();
    }

    /**
     * @return
     */
    @Override
    public List<CommodityDiagramVo> SelectCommodity() {
        List<CommodityDiagramVo> commCount = sysAnalysisMapper.SelectCommodity();
        List<CommodityDiagramVo> commName = sysAnalysisMapper.SelectCommodityName();
        for (int i = 0;i<commCount.size();i++) {
            for (int j = 0;j< commName.size();j++) {
                if (commCount.get(i).getId().equals(commName.get(j).getId())) {
                    commName.get(j).setCount(commCount.get(i).getCount());
                }
            }
        }
        return commName;
    }
}
