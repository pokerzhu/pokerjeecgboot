package org.jeecg.modules.system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;

import java.io.Serializable;
@Data
public class EquipmentVO implements Serializable {
    //设备编号
    @TableField("equipment_id")
    private String equipmentId;
    //商品编号
    @TableField("commodity_id")
    private String commodityId;
    //商品名称
    @TableField("commodity_name")
    private String commodityName;
    //所属代理
    @TableField("departname")
    private String departname;
    //客户名
    @TableField("client_name")
    private String clientName;
    //硬件编号
    @TableField("ids")
    private String ids;
    //是否启用
    @TableField("enabled")
    @Dict(dicCode = "enabled")
    private Integer enabled;
    @TableField("user_id")
    private String userId;
    //租赁
    @TableField("leasestate")
    @Dict(dicCode = "leasestate")
    private Integer leasestate;
    //主板状态
    @TableField("mainboard")
    @Dict(dicCode = "mainboard")
    private Integer mainboard;
    //滤芯状态
    @TableField("filterelement_Type")
    @Dict(dicCode = "filterelementType")
    private Integer filterelementType;
    //卖出价格
    @TableField("commodity_prices")
    private String commodityPrices;
    //租赁价格
    @TableField("commodity_rent")
    private String commodityRent;
    //特殊地区设备损耗比例
    @TableField("lossrati")
    private String lossratio;
}
