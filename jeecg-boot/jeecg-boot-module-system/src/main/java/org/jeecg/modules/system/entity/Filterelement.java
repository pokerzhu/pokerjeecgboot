package org.jeecg.modules.system.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import org.jeecgframework.poi.excel.annotation.Excel;

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
	private String filterelementId;
	/**filterelementName*/
	@Excel(name = "filterelementName", width = 15)
    @ApiModelProperty(value = "filterelementName")
	private String filterelementName;
	/**images*/
	@Excel(name = "images", width = 15)
    @ApiModelProperty(value = "images")
	private String images;
	/**validity*/
	@Excel(name = "validity", width = 15)
    @ApiModelProperty(value = "validity")
	private String validity;
    @Excel(name = "create_time", width = 15)
    @ApiModelProperty(value = "create_time")
	private  String createTime;
}
