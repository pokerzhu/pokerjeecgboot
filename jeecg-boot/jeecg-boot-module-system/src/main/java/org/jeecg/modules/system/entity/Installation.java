package org.jeecg.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

@Data
@TableName("installation_open")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="installation_open对象", description="设备开关权限表")
public class Installation {
    /**控制编号*/
    @Excel(name = "控制编号", width = 15)
    @ApiModelProperty(value = "控制编号")
    @TableId(type = IdType.UUID)
    private String openId;
    /**客户编号*/
    @Excel(name = "客户编号", width = 15)
    @ApiModelProperty(value = "客户编号")
    private String clientId;
    /**设备编号*/
    @Excel(name = "设备编号", width = 15)
    @ApiModelProperty(value = "设备编号")
    private String equipmentId;
    /**创建人*/
    @Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
    /**创建时间*/
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
    /**更新人*/
    @Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
    /**更新时间*/
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
}
