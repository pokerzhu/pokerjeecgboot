package org.jeecg;
import org.jeecg.modules.system.mapper.FilterelementReplaceMapper;
import org.jeecg.modules.system.service.IFilterelementReplaceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class JeecgTimer {

    private static FilterelementReplaceMapper iFilterelementReplaceService;

    static int count = 0;//执行次数

    public static void showTimer() {//设置定时器
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ++count;
                System.out.println("时间=" + new Date() + " 执行了" + count + "次"); // 1次
            }
        };
        //设置执行时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);//每天
        //定制每天的10:45:00执行，
        calendar.set(year, month, day, 10, 11, 00);
        Date date = calendar.getTime();
        Timer timer = new Timer();
        System.out.println(date);//打印当前时间
//            int period = 60 * 1000;
//            //每天的date时刻执行task，每隔2秒重复执行
//            timer.schedule(task, date, period);

        //每天的date时刻执行task, 仅执行一次
        timer.schedule(task, date);
    }
    public static void main(String[] args) {//在main方法中定义引用
        showTimer();
    }
    }
