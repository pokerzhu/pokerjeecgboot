package org.jeecg.modules.system.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 滤芯安装记录表
 * @Author: jeecg-boot
 * @Date:   2019-07-12
 * @Version: V1.0
 */
@Data
@TableName("filterelement_replace")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="filterelement_replace对象", description="滤芯安装记录表")
public class FilterelementReplace {
	/**记录编号*/
	@Excel(name = "记录编号", width = 15)
    @ApiModelProperty(value = "记录编号")
    @TableId(type=IdType.UUID)
	private String recordId;
	/**设备编号，设备表外键*/
	@Excel(name = "设备编号，设备表外键", width = 15)
    @ApiModelProperty(value = "设备编号，设备表外键")
	private String equipmentId;
	/**滤芯编号 */
	@Excel(name = "滤芯编号 ", width = 15)
    @ApiModelProperty(value = "滤芯编号 ")
	private String filterelementId;
	/**安装时间*/
	@Excel(name = "安装时间", width = 15)
    @ApiModelProperty(value = "安装时间")
	private String installationTime;
	/**安装人，用户表外键*/
	@Excel(name = "安装人，用户表外键", width = 15)
    @ApiModelProperty(value = "安装人，用户表外键")
	private String installationUserid;
	/**是否已经提醒，用字典*/
	@Excel(name = "是否已经提醒，用字典", width = 15)
    @ApiModelProperty(value = "是否已经提醒，用字典")
    @Dict(dicCode = "remind")
	private Integer remind;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
	private String updateBy;
	/**更新时间*/
	@Excel(name = "更新时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
	private Date updateTime;
}
