package cn.itcast.mq.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class RabbitMqListener {

//    @RabbitListener(queues = {"simple.queue"})
//    public void listenSimpleQueue(String msg){
//        System.out.println("接收到simple.queue的消息【"+msg+"】");
//    }

    @RabbitListener(queues = {"simple.queue"})
    public void listenWorkQueue1(String msg){
        System.out.println("消费者1接收到消息【"+msg+"】"+ LocalDateTime.now());
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = {"simple.queue"})
    public void listenWorkQueue2(String msg){
        System.err.println("消费者2接收到消息【"+msg+"】"+LocalDateTime.now());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @RabbitListener(queues = {"fanout.queue1"})
    public void listenFanoutQueue1(String msg){
        System.out.println("消费者1接收到fanout.queue1消息【"+msg+"】"+LocalDateTime.now());

    }
    @RabbitListener(queues = {"fanout.queue2"})
    public void listenFanoutQueue2(String msg){
        System.err.println("消费者2接收到fanout.queue2消息【"+msg+"】"+LocalDateTime.now());

    }

    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "root.direct"),
            key = {"red","blue"}
    )})
    public void listenDirectQueue1(String message){
        System.out.println("接收到queue1的消息+【"+message+"】");
    }

    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "root.direct"),
            key = {"red","yellow"}
    )})
    public void listenDirectQueue2(String message){
        System.out.println("接收到queue2的消息+【"+message+"】");
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "topic.queue1"),
                    exchange = @Exchange(name = "root.topic",type = ExchangeTypes.TOPIC),
                    key = {"china.#"}
            )
    })
    public void listenTopicQueue1(String message){
        System.out.println("接收到queue1的消息【"+message+"】");
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "topic.queue2"),
                    exchange = @Exchange(name = "root.topic",type = ExchangeTypes.TOPIC),
                    key = {"#.news"}
            )
    })
    public void listenTopicQueue2(String message){
        System.out.println("接收到queue2的消息【"+message+"】");
    }

    @RabbitListener(queues = {"object.queue"})
    public void listenObjectQueue(Map<String,Object> map){
        System.out.println("接收到objeect.queue的消息"+map);
    }
}
