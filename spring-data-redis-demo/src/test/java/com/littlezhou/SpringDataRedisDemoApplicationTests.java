package com.littlezhou;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

@SpringBootTest
class SpringDataRedisDemoApplicationTests {

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate template;

    @Test
   public void testValue() {
        ValueOperations valueOperations = template.opsForValue();
        valueOperations.set("username","小明");
        System.out.println(valueOperations.get("username"));

    }

    @Test
    public void testList(){

        template.opsForList().leftPush("list","a");
        template.opsForList().leftPushAll("list","c","d","e");
        System.out.println(template.opsForList().size("list"));
        ListOperations listOperations = template.opsForList();
//        Long len = listOperations.size("list");
//        for (int i = 0; i < len; i++) {
//            System.out.println(listOperations.rightPop("list"));
//        }

        List<String> list = listOperations.range("list", 0, -1);
        for (String str:list){
            System.out.println(str);
        }

    }


}
