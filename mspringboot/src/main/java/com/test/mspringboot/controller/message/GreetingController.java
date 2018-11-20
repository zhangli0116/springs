package com.test.mspringboot.controller.message;

import com.alibaba.fastjson.JSON;
import com.test.mspringboot.model.User;
import com.test.mspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

/**
 * Create by Administrator on 2018/11/19
 *
 * @author admin
 */
@Controller
public class GreetingController {

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/hello")
    //@SendTo("/topic/greetings")
    public void greeting(User user) throws  Exception{
        TimeUnit.SECONDS.sleep(2);
        simpMessagingTemplate.convertAndSend("/topic/greetings","hello spring stomp");
    }
    /**
     * 获得缓存数据
     * @return
     * @throws Exception
     */
    @RequestMapping("/getSocketCacheUser")
    @ResponseBody
    public String getCacheUser() throws Exception{
        User user =  userService.searchCachableUserById(2);
        simpMessagingTemplate.convertAndSend("/topic/greetings", JSON.toJSONString(user));
        return "success";
    }

    /**
     * java 这里不能作为客户端用户
     * @param user
     */
    @MessageMapping("/hello2")
    public void greeting2(User user){
        System.out.println("接受服务端发给服务端的数据 ： " + JSON.toJSONString(user));
    }
}
