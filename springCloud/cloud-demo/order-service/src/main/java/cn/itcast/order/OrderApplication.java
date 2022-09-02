package cn.itcast.order;

import cn.itcast.feign.clients.UserClient;
import cn.itcast.feign.clients.fallBack.UserClientFallBackFactory;
import feign.hystrix.FallbackFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@MapperScan("cn.itcast.order.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {UserClient.class})
public class OrderApplication {

//    @Bean
//    @LoadBalanced //负载均衡
//    public RestTemplate restTemplate(){
//        return new RestTemplate();
//    }

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Bean
    public UserClientFallBackFactory userClientFallBackFactory(){
        return  new UserClientFallBackFactory();
    }
}