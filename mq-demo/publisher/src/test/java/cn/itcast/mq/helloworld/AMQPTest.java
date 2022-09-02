package cn.itcast.mq.helloworld;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AMQPTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage(){
        String queue = "simple.queue";
        String message = "message__";
        for (int i = 0; i < 50; i++) {

            rabbitTemplate.convertAndSend(queue,message+(i+1));
        }
    }

    @Test
    public void testFanoutExchange(){
        String exchange = "root.fanout";
        String message ="hello everyone.";
        rabbitTemplate.convertAndSend(exchange,null,message);
    }

    @Test
    public void testDirecttExchange(){
        String exchange = "root.direct";
        String message ="hello yellow.";
        String key = "yellow";
        rabbitTemplate.convertAndSend(exchange,key,message);
    }

    @Test
    public void testTopicExchange(){
        String exchange = "root.topic";
        String message ="安倍死了.";
        String key = "japen.news";
        rabbitTemplate.convertAndSend(exchange,key,message);
    }

    @Test
    public void testObjectQueue(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","柳岩");
        map.put("age",18);
        map.put("sex","女");
        rabbitTemplate.convertAndSend("object.queue",map);
    }
}
