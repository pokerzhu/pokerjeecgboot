package org.jeecg.modules.system.entity;

import lombok.Data;

@Data
public class XFDataCount {

    private String id;
    private String namea;//名称
    private String numberC;//数据
    private String time;//时间
    private String countnum = "0";//柱状图统计数据

}
