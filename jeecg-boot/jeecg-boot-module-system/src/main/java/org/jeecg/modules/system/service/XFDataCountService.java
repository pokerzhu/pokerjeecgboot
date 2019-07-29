package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.XFDataCount;

import java.text.ParseException;
import java.util.List;

public interface XFDataCountService extends IService<XFDataCount> {
    /**
     * 统计这一周的时间
     */
    public XFDataCount CountTime(@Param("CountTime")int CountTime);

    /**
     * 根据时间查询本周所在的当天的统计情况
     */
    XFDataCount wekkDateAll(String xfStatisticst);

    List<XFDataCount> wekkDateAll() throws ParseException;
}
