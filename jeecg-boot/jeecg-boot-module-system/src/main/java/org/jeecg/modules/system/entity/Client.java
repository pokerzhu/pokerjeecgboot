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
 * @Description: 客户表
 * @Author: jeecg-boot
 * @Date:   2019-07-03
 * @Version: V1.0
 */
@Data
@TableName("client")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="client对象", description="客户表")
public class Client {
    
	/**address*/
	@Excel(name = "address", width = 15)
    @ApiModelProperty(value = "address")
	private String address;
	/**clientId*/
	@Excel(name = "clientId", width = 15)
    @ApiModelProperty(value = "clientId")
	private String clientId;
	/**clientName*/
	@Excel(name = "clientName", width = 15)
    @ApiModelProperty(value = "clientName")
	private String clientName;
	/**open*/
	@Excel(name = "open", width = 15)
    @ApiModelProperty(value = "open")
	private String open;
	/**password*/
	@Excel(name = "password", width = 15)
    @ApiModelProperty(value = "password")
	private String password;
	/**phone*/
	@Excel(name = "phone", width = 15)
    @ApiModelProperty(value = "phone")
	private String phone;
    @Excel(name = "create_time", width = 15)
    @ApiModelProperty(value = "create_time")
    private  String createTime;
}
