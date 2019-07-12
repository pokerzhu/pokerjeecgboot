package org.jeecg.modules.system.vo;

import lombok.Data;

@Data
public class CommodityVO {
    /**商品编号*/
    private java.lang.String commodityId;
    /**商品名称*/
    private java.lang.String commodityName;
    /**商品价格*/
    private java.lang.String commodityPrices;
    /**商品租价*/
    private java.lang.String commodityRent;
    /**商品描述*/
    private java.lang.String description;
    /**商品图片*/
    private java.lang.String images;
    /**商品类型*/
    private java.lang.String typeId;
    /**商品类型名称*/
    private java.lang.String typeName;
    /**创建人*/
    private java.lang.String createBy;
    /**创建时间*/
    private java.util.Date createTime;
    /**更新人*/
    private java.lang.String updateBy;
    /**更新时间*/
    private java.util.Date updateTime;
}
