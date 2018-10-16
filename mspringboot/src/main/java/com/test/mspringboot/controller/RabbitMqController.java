package com.test.mspringboot.controller;

import com.test.mspringboot.rabbitmq.direct.Tut4Sender;
import com.test.mspringboot.rabbitmq.fanout.Tut3Sender;
import com.test.mspringboot.rabbitmq.queues.Tut2Sender;
import com.test.mspringboot.rabbitmq.simple.Tut1Sender;
import com.test.mspringboot.rabbitmq.topic.Tut5Sender;
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
public class RabbitMqController {

    //一对一
    @Autowired
    private Tut1Sender tut1Sender;

    //一对多
    @Autowired
    private Tut2Sender tut2Sender;

    //发布
    @Autowired
    private Tut3Sender tut3Sender;

    //直连
    @Autowired
    private Tut4Sender tut4Sender;

    //通配
    @Autowired
    private Tut5Sender tut5Sender;
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

    @RequestMapping("/sendFanout")
    @ResponseBody
    private String sendFanout(){
        for(int i =0;i<10;i++) {
            tut3Sender.send();
        }
        return "ok";
    }

    @RequestMapping("/sendDirect")
    @ResponseBody
    private String sendDirect(){
        for(int i =0;i<3;i++) {
            tut4Sender.send();
        }
        return "ok";
    }

    @RequestMapping("/sendTopic")
    @ResponseBody
    private String sendTopic(){
        for(int i =0;i<6;i++) {
            tut5Sender.send();
        }
        return "ok";
    }

}
