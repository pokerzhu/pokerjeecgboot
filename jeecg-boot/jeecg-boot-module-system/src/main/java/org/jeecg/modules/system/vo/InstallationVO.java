package org.jeecg.modules.system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
@Data
public class InstallationVO implements Serializable {
    @TableField("commodity_name")
    private String commodityName;
    @TableField("equipment_id")
    private String equipmentId;
    @TableField("client_id")
    private String clientId;
    @TableField("open_id")
    private String openId;
}
