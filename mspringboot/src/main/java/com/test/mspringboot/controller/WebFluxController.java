package com.test.mspringboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * Create by Administrator on 2018/11/23
 *
 * @author admin
 */
@Controller
@RequestMapping("/webFlux")
public class WebFluxController {
    private Logger logger = LoggerFactory.getLogger(WebFluxController.class);
    @RequestMapping("/get")
    @ResponseBody
    public String getRestData(){
        WebClient webClient = WebClient.builder()
                .baseUrl("http://127.0.0.1:8090/getCacheUser")
                .build();
        Mono<String> mono = webClient.get().retrieve().bodyToMono(String.class);
        CompletableFuture<String> future =  mono.toFuture();
        //CompletableFuture 用法？
        future.whenComplete((s,e)->{
            logger.info("接受数据 ： " + s);
        });
        return "success";
    }
}
