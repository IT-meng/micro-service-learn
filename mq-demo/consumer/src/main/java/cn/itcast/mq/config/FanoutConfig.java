package cn.itcast.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

    //声明队列fanout.queue1
    @Bean
    public Queue fanoutQueue1(){
        return new Queue("fanout.queue1");
    }

    //声明队列fanout.queue2
    @Bean
    public Queue fanoutQueue2(){
        return new Queue("fanout.queue2");
    }

    //声明交换机root.fanout
    @Bean
    public FanoutExchange rootFanout(){
        return new FanoutExchange("root.fanout");
    }

    //声明两个Binding
    @Bean
    public Binding bindingQueue1(FanoutExchange rootFanout,Queue fanoutQueue1){
        return BindingBuilder.bind(fanoutQueue1).to(rootFanout);
    }

    @Bean
    public Binding bindingQueue2(FanoutExchange rootFanout,Queue fanoutQueue2){
        return BindingBuilder.bind(fanoutQueue2).to(rootFanout);
    }

    @Bean
    public Queue objectQueue(){
        return new Queue("object.queue");
    }
}
