package org.jeecg.modules.system.vo;

public class DiagramVo {
    //代理商id
    String id;
    //代理商设备名称
    String name;
    //代理商设备数量
    String count = "0";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
