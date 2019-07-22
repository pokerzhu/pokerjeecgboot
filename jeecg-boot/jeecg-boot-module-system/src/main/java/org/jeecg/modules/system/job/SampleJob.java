package org.jeecg.modules.system.job;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.system.service.IFilterelementReplaceService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

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
	    boolean a =  iFilterelementReplaceService.UpdYC();
	    if (a){
            log.info(String.format("滤芯 状态 修改成功!  时间:" + DateUtils.getTimestamp()));
        }else{
            log.info(String.format(" 滤芯 状态 修改失败 !  时间:" + DateUtils.getTimestamp()));
        }
	}
}
