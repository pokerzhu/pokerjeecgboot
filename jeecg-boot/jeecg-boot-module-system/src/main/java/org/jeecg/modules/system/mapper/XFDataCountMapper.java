package org.jeecg.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.XFDataCount;

public interface XFDataCountMapper extends BaseMapper<XFDataCount> {
    /**
     * 统计这一周的时间
     */
    @Select("select SUBDATE(CURDATE(),DATE_FORMAT(CURDATE(),'%w')+#{CountTime}) as time")
    XFDataCount CountTime(@Param("CountTime")int CountTime);

    /**
     * 根据时间查询本周所在的当天的统计情况
     */
    @Select("SELECT count(1) as countnum,create_time as time from xf_letters where create_time like concat(#{xfStatisticst},'%')")
    XFDataCount wekkDateAll(String xfStatisticst);

}
