package org.jeecg.modules.system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.security.PrivateKey;
import java.util.Date;

/**
 * @Description: 类型滤芯关联
 * @Author: jeecg-boot
 * @Date:   2019-07-05
 * @Version: V1.0
 */
@Data
public class RelationshipVO {
    /**
     * 记录Id
     */
    @TableField("record_id")
    private String recordId;
    /*剩余天数*/
    @TableField("Remaining")
    private String Remaining;
    /*已使用天数*/
    @TableField("Used")
    private String Used;
    /**
     * 是否已提醒
     */
    @TableField("remind")
    @Dict(dicCode = "remind")
    private int remind;
    //设备编号
    @TableField("equipment_id")
    private String equipmentId;
	/*滤芯编号*/
    @TableField("filterelement_id")
	private String filterelementId;
	/*滤芯名称*/
    @TableField("filterelement_name")
	private String filterelementName;
	/*类型编号*/
    @TableField("type_id")
	private String typeId;
	/*关联id*/
    @TableField("relationship_id")
	private String relationshipId;
	/*滤芯使用天数*/
    @TableField("validity")
	private String validity;
	/*最低更换天数*/
    @TableField("replacementdays")
	private  String replacementdays;
    /*最低更换天数*/
    @TableField("updateBy")
    private  String updateBy;
}
