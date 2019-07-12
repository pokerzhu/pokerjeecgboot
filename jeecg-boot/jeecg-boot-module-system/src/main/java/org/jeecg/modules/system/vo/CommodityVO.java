package org.jeecg.modules.system.vo;

import lombok.Data;

@Data
public class CommodityVO {
    /**商品编号*/
    private String commodityId;
    /**商品名称*/
    private String commodityName;
    /**商品价格*/
    private String commodityPrices;
    /**商品租价*/
    private String commodityRent;
    /**商品描述*/
    private String description;
    /**商品图片*/
    private String images;
    /**商品类型*/
    private String typeId;
    /**商品类型名称*/
    private String typeName;
    /**创建人*/
    private String createBy;
    /**创建时间*/
    private java.util.Date createTime;
    /**更新人*/
    private String updateBy;
    /**更新时间*/
    private java.util.Date updateTime;
}
