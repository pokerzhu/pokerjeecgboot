package org.jeecg.modules.system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

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
    @TableField("realname")
    private String realname;
    //客户名
    @TableField("client_name")
    private String clientName;
    //硬件编号
    @TableField("ids")
    private String ids;
    //是否启用
    @TableField("enabled")
    private String enabled;
}
