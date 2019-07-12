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
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 商品表
 * @Author: jeecg-boot
 * @Date:   2019-07-04
 * @Version: V1.0
 */
@Data
@TableName("commodity")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="commodity对象", description="商品表")
public class Commodity {

	/**商品编号*/
	@Excel(name = "商品编号", width = 15)
	@ApiModelProperty(value = "商品编号")
    @TableId(type = IdType.UUID)
	private String commodityId;
	/**商品名称*/
	@Excel(name = "商品名称", width = 15)
	@ApiModelProperty(value = "商品名称")
	private String commodityName;
	/**商品价格*/
	@Excel(name = "商品价格", width = 15)
	@ApiModelProperty(value = "商品价格")
	private String commodityPrices;
	/**商品租价*/
	@Excel(name = "商品租价", width = 15)
	@ApiModelProperty(value = "商品租价")
	private String commodityRent;
	/**商品描述*/
	@Excel(name = "商品描述", width = 15)
	@ApiModelProperty(value = "商品描述")
	private String description;
	/**商品图片*/
	@Excel(name = "商品图片", width = 15)
	@ApiModelProperty(value = "商品图片")
	private String images;
	/**商品类型*/
	@Excel(name = "商品类型", width = 15)
	@ApiModelProperty(value = "商品类型")
	private String typeId;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
	@ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建时间*/
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
	@ApiModelProperty(value = "更新人")
	private String updateBy;
	/**更新时间*/
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
}
