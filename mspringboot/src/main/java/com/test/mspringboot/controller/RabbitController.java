package com.test.mspringboot.controller;

import com.test.mspringboot.rabbitmq.mutil.Tut2Sender;
import com.test.mspringboot.rabbitmq.point.Tut1Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Create by Administrator on 2018/10/11
 * rabbitmq 测试
 * @author admin
 */
@Controller
public class RabbitController {

    @Autowired
    private Tut1Sender tut1Sender;

    @Autowired
    private Tut2Sender tut2Sender;
    /**
     * 发送消息
     * @return
     */
    @RequestMapping("/sendPoint")
    @ResponseBody
    private String sendPoint(){
        tut1Sender.send();
        return "ok";
    }

    @RequestMapping("/sendMutil")
    @ResponseBody
    private String sendMutil(){
        for(int i =0;i<10;i++){
            tut2Sender.send();
        }
        return "ok";
    }
}
