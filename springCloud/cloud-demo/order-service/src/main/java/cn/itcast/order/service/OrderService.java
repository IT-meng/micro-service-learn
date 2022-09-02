package cn.itcast.order.service;

import cn.itcast.feign.clients.UserClient;
import cn.itcast.feign.clients.fallBack.UserClientFallBackFactory;
import cn.itcast.feign.pojo.User;
import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

//    @Autowired
//    private RestTemplate restTemplate;
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserClient userClient;

    @Autowired
    private UserClientFallBackFactory userClientFallBackFactory;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);

//        String url = "http://userservice/user/"+order.getUserId();
//        User user = restTemplate.getForObject(url, User.class);
        User user = userClient.getById(order.getUserId());
        order.setUser(user);
        // 4.返回
        return order;
    }

    //查询商品
    @SentinelResource("goods")
    public void queryGoods(){
        System.err.println("查询商品");
    }
}
