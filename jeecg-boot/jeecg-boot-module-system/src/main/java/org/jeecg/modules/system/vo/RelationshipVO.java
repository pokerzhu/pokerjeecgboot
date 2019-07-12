package org.jeecg.modules.system.vo;

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
 * @Description: 类型滤芯关联
 * @Author: jeecg-boot
 * @Date:   2019-07-05
 * @Version: V1.0
 */
@Data
public class RelationshipVO {
	/*滤芯编号*/
	private Integer filterelementId;
	/*滤芯名称*/
	private String filterelementName;
	/*类型编号*/
	private String typeId;
	/*关联id*/
	private String relationshipId;
	/*滤芯使用天数*/
	private String validity;
	/*最低更换天数*/
	private  String replacementdays;
}
