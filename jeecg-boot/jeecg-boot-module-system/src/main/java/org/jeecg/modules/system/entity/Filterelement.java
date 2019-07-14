package org.jeecg.modules.system.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description: 滤芯表
 * @Author: jeecg-boot
 * @Date:   2019-07-03
 * @Version: V1.0
 */
@Data
@TableName("filterelement")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="filterelement对象", description="滤芯表")
public class Filterelement {
    
	/**filterelementId*/
	@Excel(name = "filterelementId", width = 15)
    @ApiModelProperty(value = "filterelementId")
    @TableId(type = IdType.UUID)
	private String filterelementId;
	/**filterelementName*/
	@Excel(name = "filterelementName", width = 15)
    @ApiModelProperty(value = "filterelementName")
	private String filterelementName;
	/**filterelementName*/
	@Excel(name = "replacementdays", width = 15)
	@ApiModelProperty(value = "replacementdays")
	private String replacementdays;
	/**images*/
	@Excel(name = "images", width = 15)
    @ApiModelProperty(value = "images")
	private String images;
	/**validity*/
	@Excel(name = "validity", width = 15)
    @ApiModelProperty(value = "validity")
	private String validity;
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
}
