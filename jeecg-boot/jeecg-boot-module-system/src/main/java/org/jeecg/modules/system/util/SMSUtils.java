package org.jeecg.modules.system.util;


import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Random;

/**
 * @author Administrator
 *
 *  功能:		企信通 HTTP接口 发送短信
    修改日期:	2019-02-19
    说明:		http://api.cnsms.cn/?ac=send&uid=用户账号&pwd=MD5位32密码&mobile=号码&content=内容
    状态: 100 发送成功  101 验证失败
    103 操作失败
    104 非法字符
    105 内容过多
    106 号码过多
    107 频率过快
    108 号码内容空
    109 账号冻结
    110 禁止频繁单条发送
    111 系统暂定发送
    112 号码不正确
    120 系统升级
    102 短信不足
 */
public class SMSUtils {


    // 账号
    private static final String  ACCOUNT = "csjrb";

    // 密码
    private static final String  PASSWORD = "csjrb123";



    /**
     * 发送短信，account，pwd，参数值请向企信通申请， tel：发送的手机号， content:发送的内容
     */
    public static String send(String tel, String content) throws IOException {

        // 创建StringBuffer对象用来操作字符串   http://客户端地址/sms.aspx
        StringBuffer sb = new StringBuffer("http://47.104.131.56:8868/sms.aspx/?");
        //发送任务命令
        sb.append("action=send");
        //追加企业id
        sb.append("&userid=247");
        // 向StringBuffer追加用户名
        sb.append("&account="+ ACCOUNT);
        // 向StringBuffer追加密码
        sb.append("&password="+ PASSWORD);
        // 向StringBuffer追加手机号码
        sb.append("&mobile="+tel);
        // 追加发送时间 为空立即发送
        sb.append("&sendTime="+"");
        // 向StringBuffer追加消息内容转URL标准码
        sb.append("&content="+ URLEncoder.encode(content,"utf8"));
        // 创建url对象
        URL url = new URL(sb.toString());
        // 打开url连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 设置url请求方式 ‘get’ 或者 ‘post’
        connection.setRequestMethod("POST");
        // 发送
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        // 返回发送结果
        String inputline = in.readLine();
        return inputline;
    }


    /**
     * 生成四位随机数
     */
    public static String code(){
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb=new StringBuilder(4);
        for(int i=0;i<4;i++)
        {
            char ch=str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
       return sb.toString();
    }




    public static void main(String[] args){
         // 账号
         String  ACCOUNT = "csjrb";
         // 密码
         String  PASSWORD = "csjrb123";

         //日期
        String data =  DateFormatUtils.format(new Date(), "yyyy年MM月dd日");



        System.out.println(data);

         // 发送内容
        String CONTENT = "【长沙清理整顿办】“维财金”第二批资金返还工作已开始，请及时访问网站http://jrb.changsha.gov.cn或http://csga.changsha.gov.cn查阅返还公告，配合做好相关工作。";
         //String CONTENT = "【长沙市金融办】湖南维财大宗贵金属非法经营案第二批资金返还工作已开始，请及时登录网站@查阅，配合做好相关工作。"+data;
         //String CONTENT = "【长沙市金融办】验证码为:1XR3。（30分钟内有效）";
         String tel = "18574389770";
        try {
            System.out.println(send(tel, CONTENT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}