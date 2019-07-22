package org.jeecg.modules.system.controller;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.jeecg.modules.system.util.SMSUtils;

import java.io.IOException;
import java.util.Date;

/**
 * 群发短信
 * Created by Administrator on 2019/2/15.
 */
public class MessageController {
    /**
     * 群发短信
     */
    public void group(String Phone) throws IOException {
        String phone = Phone;//手机号码
        String data =  DateFormatUtils.format(new Date(), "yyyy年MM月dd日");
        StringBuffer CONTENT = new StringBuffer();
        CONTENT.append("【长沙清理整顿办】“维财金”第二批资金返还工作已开始，请及时访问网站http://jrb.changsha.gov.cn或http://csga.changsha.gov.cn查阅返还公告，配合做好相关工作。");
        try {
            String  result = SMSUtils.send(phone,CONTENT.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }




}
