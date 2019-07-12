package org.jeecg.modules.system.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @Description: 设备表
 * @Author: jeecg-boot
 * @Date:   2019-07-05
 * @Version: V1.0
 */
@Data
@TableName("equipment")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="equipment对象", description="设备表")
public class Equipment {

	/**设备编号*/
	@Excel(name = "设备编号", width = 15)
    @ApiModelProperty(value = "设备编号")
    @TableId(type = IdType.UUID)
	private String equipmentId;
	/**商品编号，商品表外键*/
	@Excel(name = "商品编号，商品表外键", width = 15)
	@ApiModelProperty(value = "商品编号，商品表外键")
    @TableId(type = IdType.UUID)
	private String commodityId;
	/**所属人，用户表外键，属于哪个代理的*/
	@ApiModelProperty(value = "所属人，用户表外键，属于哪个代理的")
	private String userId;
	/**安装客户编号，客户表外键。*/
	@Excel(name = "安装客户编号，客户表外键。", width = 15)
	@ApiModelProperty(value = "安装客户编号，客户表外键。")
	private String clientId;
	/**硬件编号*/
	@Excel(name = "硬件编号", width = 15)
	@ApiModelProperty(value = "硬件编号")
	private String ids;
	/**是否启用，字典*/
	@Excel(name = "安装状态", width = 15)
    @ApiModelProperty(value = "是否启用")
	@Dict(dicCode = "enabled")
	private Integer enabled;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	@ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	@ApiModelProperty(value = "更新人")
	private String updateBy;
	/**更新时间*/
	@Excel(name = "更新时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
    @Excel(name="租赁状态",width = 20)
    @ApiModelProperty(value = "租赁状态")
    @Dict(dicCode = "leasestate")
	private Integer leasestate;
    @Excel(name="主板状态",width = 20)
    @ApiModelProperty(value = "主板状态")
    @Dict(dicCode = "mainboard")
    private Integer mainboard;
    @Excel(name="滤芯状态",width = 20)
    @ApiModelProperty(value = "滤芯状态")
    @Dict(dicCode = "filterelementType")
    private Integer filterelementType;
}
