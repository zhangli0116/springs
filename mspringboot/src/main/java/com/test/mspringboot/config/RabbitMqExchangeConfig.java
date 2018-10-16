package com.test.mspringboot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create by Administrator on 2018/10/12
 * Exchange三种模式配置
 * @author admin
 */
@Configuration
public class RabbitMqExchangeConfig {

    /**
     * 直连交换器
     * @return
     */
    @Bean
    public DirectExchange direct() {
        return new DirectExchange("tut.direct");
    }
    /**
     * 广播交换器
     * @return
     */
    @Bean
    public FanoutExchange fanout(){
        return  new FanoutExchange("tut.fanout");
    }

    /**
     * 主题交换器
     * @return
     */
    @Bean
    public TopicExchange topic() {
        return new TopicExchange("tut.topic");
    }

    private static class TopicConfig{
        @Bean
        public Queue autoDeleteQueue5() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue6() {
            return new AnonymousQueue();
        }

        //              *.orange.* ->queue5
        // tut.topic -> *.*.rabbit->queue5
        //              lazy.#    ->queue6

        //星号匹配一个单词，哈希号匹配多个单词
        @Bean
        public Binding binding3a(TopicExchange topic,Queue autoDeleteQueue5){
            return BindingBuilder.bind(autoDeleteQueue5).to(topic).with("*.orange.*");
        }

        @Bean
        public Binding binding3b(TopicExchange topic,Queue autoDeleteQueue5){
            return BindingBuilder.bind(autoDeleteQueue5).to(topic).with("*.*.rabbit");
        }

        @Bean
        public Binding binding4a(TopicExchange topic,Queue autoDeleteQueue6){
            return BindingBuilder.bind(autoDeleteQueue6).to(topic).with("lazy.#");
        }


    }

    /**
     * @Bean 通过使用静态类封闭
     */
    private static class DirectConfig{

        @Bean
        public Queue autoDeleteQueue3() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue4() {
            return new AnonymousQueue();
        }

        //                orange  ->queue3
        //  tut.direct -> black   ->queue3,queue4
        //                green   ->queue4
        //


        @Bean
        public Binding binding1a(DirectExchange direct, Queue autoDeleteQueue3) {
            return BindingBuilder.bind(autoDeleteQueue3).to(direct).with("orange");
        }

        @Bean
        public Binding binding1b(DirectExchange direct, Queue autoDeleteQueue3) {
            return BindingBuilder.bind(autoDeleteQueue3).to(direct).with("black");
        }

        @Bean
        public Binding binding2a(DirectExchange direct, Queue autoDeleteQueue4) {
            return BindingBuilder.bind(autoDeleteQueue4).to(direct).with("green");
        }

        @Bean
        public Binding binding2b(DirectExchange direct, Queue autoDeleteQueue4) {
            return BindingBuilder.bind(autoDeleteQueue4).to(direct).with("black");
        }

    }
    private static class FanoutConfig {

        // tut.fanout -> queue1,queue2

        //AnonymousQueue类型的队列，它的名字是由客户端生成的，而且是非持久的，独占的，自动删除的队列
        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        //队列和交换机绑定
        //这种关系可以读作：这个队列对这个交换器里的消息感兴趣。
        //虽然 Queue类型有多个实例，但spring会自动更加名字匹配，bean名字匹配参数名字
        @Bean
        public Binding binding1(FanoutExchange fanout, Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(fanout);
        }

        @Bean
        public Binding binding2(FanoutExchange fanout, Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(fanout);
        }




    }


}
