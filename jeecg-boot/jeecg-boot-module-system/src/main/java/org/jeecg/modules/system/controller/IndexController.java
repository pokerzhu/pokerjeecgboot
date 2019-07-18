package org.jeecg.modules.system.controller;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.system.service.WebSocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class IndexController {

    /**
     * 跳转登录页
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        log.error("sssssssssssssssssssss");
        return "index";
    }

    /**
     * 跳转聊天页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/login/{id}", method = RequestMethod.GET)
    public String login(@PathVariable("id") int id, HttpServletRequest request){
        request.getSession().setAttribute("id", id);
        return "main";
    }

    /**
     * 发送消息
     * @param msg
     * @param id
     * @return
     */
    @RequestMapping(value = "/send/{id}/{msg}", method = RequestMethod.POST)
    @ResponseBody
    public String send(@PathVariable("msg") String msg, @PathVariable("id") int id){
        try{
            // sid 为null 推送所有人  sid不为null时  推送给指定的 id
            WebSocketServer.sendInfo(msg, null);
            return "success";
        }catch (Exception e){
            log.error(e.getMessage());
            return "faile";
        }
    }

}
