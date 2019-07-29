package org.jeecg.modules.system.vo;

public class CommodityDiagramVo {


    //商品id;
    String id;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    //商品名称
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //商品数量
    String count="0";
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
