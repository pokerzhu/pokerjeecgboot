package org.jeecg;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jeecg.modules.demo.mock.MockController;
import org.jeecg.modules.demo.test.entity.JeecgDemo;
import org.jeecg.modules.demo.test.mapper.JeecgDemoMapper;
import org.jeecg.modules.demo.test.service.IJeecgDemoService;
import org.jeecg.modules.system.service.ISysDataLogService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

	@Resource
	private JeecgDemoMapper jeecgDemoMapper;
	@Resource
	private IJeecgDemoService jeecgDemoService;
	@Resource
	private ISysDataLogService sysDataLogService;
	@Resource
	private MockController mock;

	@Test
	public void testSelect() {
		System.out.println(("----- selectAll method test ------"));
		List<JeecgDemo> userList = jeecgDemoMapper.selectList(null);
		Assert.assertEquals(5, userList.size());
		userList.forEach(System.out::println);
	}

	@Test
	public void testXmlSql() {
		System.out.println(("----- selectAll method test ------"));
		List<JeecgDemo> userList = jeecgDemoMapper.getDemoByName("Sandy12");
		userList.forEach(System.out::println);
	}

	/**
	 * 测试事务
	 */
	@Test
	public void testTran() {
		jeecgDemoService.testTran();
	}
	
	//author:lvdandan-----date：20190315---for:添加数据日志测试----
	/**
	 * 测试数据日志添加
	 */
	@Test
	public void testDataLogSave() {
		System.out.println(("----- datalog test ------"));
		String tableName = "jeecg_demo";
		String dataId = "4028ef81550c1a7901550c1cd6e70001";
		String dataContent = mock.sysDataLogJson();
		sysDataLogService.addDataLog(tableName, dataId, dataContent);
	}
	//author:lvdandan-----date：20190315---for:添加数据日志测试----
    public static Date geLastWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    public static Date getNextWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 13);
        return cal.getTime();
}

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = new Date();
            System.out.println("今天是" + sdf.format(date));
            System.out.println("上周一" + sdf.format(geLastWeekMonday(date)));
            System.out.println("本周一" + sdf.format(getThisWeekMonday(date)));
            System.out.println("下周天" + sdf.format(getNextWeekMonday(date)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
