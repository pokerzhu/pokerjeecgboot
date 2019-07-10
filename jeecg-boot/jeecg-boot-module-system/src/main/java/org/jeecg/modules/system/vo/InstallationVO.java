package org.jeecg.modules.system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;

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
    /**设备编号*/
    @Excel(name = "设备状态", width = 15)
    @ApiModelProperty(value = "设备状态")
    @Dict(dicCode = "open")
    private Integer open;
}
