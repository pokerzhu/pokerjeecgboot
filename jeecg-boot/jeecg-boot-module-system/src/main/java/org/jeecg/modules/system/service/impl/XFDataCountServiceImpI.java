package org.jeecg.modules.system.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.system.entity.XFDataCount;
import org.jeecg.modules.system.mapper.XFDataCountMapper;
import org.jeecg.modules.system.service.XFDataCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class XFDataCountServiceImpI extends ServiceImpl<XFDataCountMapper, XFDataCount> implements XFDataCountService {
    @Autowired
    private XFDataCountMapper xfDataCountMapper;



    @Override
    public XFDataCount CountTime(int CountTime) {
        return xfDataCountMapper.CountTime(CountTime);
    }

    @Override
    public XFDataCount wekkDateAll(String xfStatisticst) {
        return xfDataCountMapper.wekkDateAll(xfStatisticst);
    }

    @Override
    public List<XFDataCount> wekkDateAll() throws ParseException{
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormat.format(date);
        //得到周数据
        List<XFDataCount> weekData = new ArrayList<>();
        //得到周时间
        List<XFDataCount> weekTime = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            //周时间
            XFDataCount timeWeek = xfDataCountMapper.CountTime(i - 1);
            //得到上面时间的所有时间
            String timee = timeWeek.getTime();
            //周数据
            XFDataCount data = xfDataCountMapper.wekkDateAll(timee);

            //将周时间add到我上面的weekTime集合
            weekTime.add(timeWeek);

            if (data.getTime() != null)
                //将周数据add到我上面的weekData集合
                weekData.add(data);
        }
        //遍历周数据
        for (int i = 0; i < weekData.size(); i++) {
            //遍历周时间
            for (int j = 0; j < weekTime.size(); j++) {
                //得到周数据的每个数据的时间
                String dataTime = weekData.get(i).getTime();
                //得到我当日时间
                String timeda = dateFormat.format(dateFormat.parse(dataTime));
                //周时间的当前时间,找到我的当前时间,进行数据对等
                if (weekTime.get(j).getTime().equals(timeda)) {
                    weekTime.get(j).setCountnum(weekData.get(i).getCountnum());
                }
            }
        }
        return weekTime;
    }

}