package org.jeecg.modules.system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Date;

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
    //部门code
    @TableField("orgCode")
    private String orgCode;

    //过期时间
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("expirationtime")
    private Date expirationtime;

    //客户手机号码
    @TableField("phone")
    private String phone;
}
