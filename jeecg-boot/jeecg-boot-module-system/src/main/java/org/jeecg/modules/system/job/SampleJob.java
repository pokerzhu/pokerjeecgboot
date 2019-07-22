package org.jeecg.modules.system.job;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.system.entity.Client;
import org.jeecg.modules.system.service.IFilterelementReplaceService;
import org.jeecg.modules.system.util.SMSUtils;
import org.jeecg.modules.system.vo.EquipmentVO;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 * 示例不带参定时任务
 * 
 * @Author Scott
 */
@Slf4j
public class SampleJob implements Job {
    @Autowired
    private IFilterelementReplaceService iFilterelementReplaceService;
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<EquipmentVO> filterelementids = iFilterelementReplaceService.UpdZT();//接收需要修改状态的设备信息
        if (filterelementids.size()==0){
            boolean a =  iFilterelementReplaceService.UpdYC();//修改设备状态为异常
            if (a){
                for (EquipmentVO c : filterelementids) {
                    StringBuffer CONTENT = new StringBuffer();
                    CONTENT.append("【弗瑞施新风】尊敬的:" +c.getClientName());
                    CONTENT.append("，您好，您的新风机"+c.getCommodityName());
                    CONTENT.append("滤芯将于"+String.format("%tY", c.getExpirationtime())+"年"+String.format("%tm", c.getExpirationtime())+"月"+String.format("%td", c.getExpirationtime())+"日过期，请联系售后更换，售后联系方式：17674792670");
                    try {
                        String send = SMSUtils.send(c.getPhone(), CONTENT.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                log.info(String.format("滤芯 状态 修改成功!  时间:" + DateUtils.getTimestamp()));
            }else{
                log.info(String.format(" 滤芯 状态 修改失败 !  时间:" + DateUtils.getTimestamp()));
            }
        }

	}
}
